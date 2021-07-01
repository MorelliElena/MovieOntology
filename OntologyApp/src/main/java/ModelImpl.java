import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.util.FileManager;
import utils.*;
import utils.QueryType;
import utils.Record;
import utils.Parameter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ModelImpl implements Observable<Record> {
    public static java.lang.String PREFIX =
            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
            "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
            "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
            "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n"+
            "PREFIX m: <http://www.movieontology.org/2009/11/09/>\n" +
            "PREFIX mo: <http://www.movieontology.org/2009/10/01/movieontology.owl#>\n" +
            "PREFIX me: <http://www.semanticweb.org/elenamorelli/ontologies/2021/5/movie_ontology#>\n" +
            "PREFIX fn: <http://www.w3.org/2005/xpath-functions#>";

    private final List<Observer<Record>> observerList;
    private final List<Record> recordList;
    private final List<String> resultList;
    private final Model model;


    public ModelImpl() {
        FileManager.getInternal().addLocatorClassLoader(ModelImpl.class.getClassLoader());
        model = FileManager.getInternal().
                loadModelInternal("movie-ontology_RDF_3.owl");
        this.observerList = new ArrayList<>();
        this.recordList = new ArrayList<>();
        this.resultList = new ArrayList<>();
    }

    public void setExecution(QueryType queryType) {
        Query query = QueryFactory.create(PREFIX + queryType.getQuery());
        try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
            ResultSet results = qexec.execSelect();
            while (results.hasNext()) {
               test(queryType, results);
                if(resultList.size() == queryType.getNumParameters()){
                    Record record = new Record();
                    record.addParameter(resultList);
                    System.out.println(record);
                    recordList.add(record);
                    resultList.clear();
                }

                /*QuerySolution soln = results.nextSolution();
                Resource param1 = soln.getResource("?retailer");
                Literal param2 = soln.getLiteral("?movies");
                System.out.println(getResourceValue(param1) + "-->"+ param2.toString());*/
                /*if(queryType.getNResource() + queryType.geNLiterals() > 2) {
                    Resource param3 = soln.getResource(queryType.getResultParameter().getParam3());
                    list.add(new Result(getResourceValue(param1), param2.getString(), getResourceValue(param3)));
                } else {
                    list.add(new Result(getResourceValue(param1), param2.getString()));
                } */
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
            notifyError(e.getMessage());
        }
        System.out.println(recordList);
        notifyObserver();
    }

    private void test (QueryType queryType, ResultSet results){
        QuerySolution soln = results.nextSolution();
        for(Parameter p: queryType.getResult().getParamList()){
            if(p.getType() == ParamType.RESOURCE){
                //System.out.println(soln.getResource(p.getParam()).toString());
                resultList.add(getResourceValue(soln.getResource(p.getParam())));
            } else if(p.getType() == ParamType.LITERAL){
                //System.out.println(soln.getLiteral(p.getParam()).getString());
                resultList.add(soln.getLiteral(p.getParam()).getString());
            }

            //System.out.println(resultList);

        }

    }

    private static String getResourceValue(Resource value){
        return value.getURI().substring(value.getURI().indexOf("#") + 1);
    }

    @Override
    public void addObserver(Observer<Record> observer) {
        Objects.requireNonNull(observer);
        if(!observerList.contains(observer)) {
            observerList.add(observer);
        }
    }

    @Override
    public void removeObserver(Observer<Record> observer) {
        Objects.requireNonNull(observer);
        observerList.remove(observer);
    }

    private void notifyObserver() {
        for(final Observer<Record> obv : observerList) {
            obv.updateResult(recordList);
        }
    }

    private void notifyError(String error) {
        for(final Observer<Record> obv : observerList) {
            obv.notifyError(error);
        }
    }
}
