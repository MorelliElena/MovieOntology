package utils;

import java.util.ArrayList;
import java.util.Arrays;

public enum QueryType {
    QUERY1("Numero film per distributori online",
            "SELECT ?retailer (COUNT(?movie) as ?movies)\n" +
                    "WHERE {\n" +
                    "?retailer rdf:type mo:Online_retailer.\n" +
                    "?movie me:hasDistributor ?retailer.\n" +
                    "}\n" +
                    "GROUP BY ?retailer\n" +
                    "ORDER BY ASC (?movies)",
            new Result(new ArrayList<>(
                    Arrays.asList(
                            new Parameter("retailer", ParamType.RESOURCE),
                            new Parameter("movies", ParamType.LITERAL))),
                    new ArrayList<>(Arrays.asList("Retailer", "Movies"))), 2),

    QUERY2("Numero award per film girati negli USA",
            "SELECT ?movie (COUNT(?award) as ?awards)\n" +
                    "WHERE {\n" +
                    "?movie rdf:type m:Movie.\n" +
                    "?movie mo:isAwardedWith ?award.\n" +
                    "?movie mo:hasFilmLocation ?location.\n" +
                    "FILTER( ?location = mo:USA).\n" +
                    "}\n" +
                    "GROUP BY ?movie\n" +
                    "ORDER BY DESC (?awards)",
            new Result(new ArrayList<>(
                    Arrays.asList(
                            new Parameter("movie", ParamType.RESOURCE),
                            new Parameter("awards",ParamType.LITERAL))),
                    new ArrayList<>(Arrays.asList("Movie", "Awards"))), 2),

    QUERY3("Incassi film d'animazione usciti nel 2020",
            "SELECT ?movie ?gross\n" +
            "WHERE {\n" +
            "?movie rdf:type m:Movie.\n" +
            "?movie mo:belongsToGenre ?genre.\n" +
            "FILTER(?genre = mo:Animation).\n" +
            "?movie  mo:releasedate ?rdate.\n" +
            "BIND(year(xsd:date(?rdate)) as ?year).\n" +
            "FILTER(?year = 2020).\n" +
            "?movie me:worldwidegross ?gross.\n" +
            "}\n" +
            "ORDER BY ASC (?gross)",
            new Result(new ArrayList<>(
                    Arrays.asList(
                            new Parameter("movie", ParamType.RESOURCE),
                            new Parameter("gross", ParamType.LITERAL))),
                    new ArrayList<>(Arrays.asList("Movie", "Worldwide gross($)"))), 2),

    QUERY4("Budget film girati in Europa",
            "SELECT ?movie ?budget ?location\n" +
            "WHERE {\n" +
            "?movie rdf:type m:Movie.\n" +
            "?movie me:budget ?budget.\n" +
            "?movie mo:hasFilmLocation ?location.\n" +
            "?location rdf:type mo:Europe\n" +
            "}\n" +
            "ORDER BY ASC (?budget)",
            new Result(new ArrayList<>(
                    Arrays.asList(
                            new Parameter("movie", ParamType.RESOURCE),
                            new Parameter("budget", ParamType.LITERAL),
                            new Parameter("location",ParamType.RESOURCE))),
            new ArrayList<>(Arrays.asList("Movie", "Budget($)","Location"))), 3);

    private final String title;
    private final String query;
    private final Result result;
    private final int numParameters;

    QueryType(String title, String query, Result result, int numParameters) {
        this.title = title;
        this.query = query;
        this.result = result;
        this.numParameters = numParameters;
    }

    public String getQuery() {
        return this.query;
    }

    public String getTitle() {
        return title;
    }

    public int getNumParameters() {
        return numParameters;
    }

    public Result getResult() {
        return result;
    }
}
