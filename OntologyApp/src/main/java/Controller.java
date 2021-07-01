import utils.QueryType;

public class Controller {
    View view;

    public Controller (){
         view = new View(this);
    }

    public void startSimulation(QueryType queryType){
        ModelImpl model = new ModelImpl();
        model.addObserver(view);
        model.setExecution(queryType);
    }

}
