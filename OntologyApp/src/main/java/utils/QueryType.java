package utils;

import java.util.ArrayList;
import java.util.Arrays;

public enum QueryType {
    QUERY1("Numero film per distributori online",
            """
                    SELECT ?retailer (COUNT(?movie) as ?movies)
                    WHERE {
                    ?retailer rdf:type mo:Online_retailer.
                    ?movie me:hasDistributor ?retailer.
                    }
                    GROUP BY ?retailer
                    ORDER BY ASC (?movies)""",
            new QueryBuilder(new ArrayList<>(
                    Arrays.asList(
                            new Parameter("retailer", ParamType.RESOURCE),
                            new Parameter("movies", ParamType.LITERAL))),
                    new ArrayList<>(Arrays.asList("Retailer", "Movies"))), 2),

    QUERY2("Numero award per film girati negli USA",
            """
                    SELECT ?movie (COUNT(?award) as ?awards)
                    WHERE {
                    ?movie rdf:type m:Movie.
                    ?movie mo:isAwardedWith ?award.
                    ?movie mo:hasFilmLocation ?location.
                    FILTER( ?location = mo:USA).
                    }
                    GROUP BY ?movie
                    ORDER BY DESC (?awards)""",
            new QueryBuilder(new ArrayList<>(
                    Arrays.asList(
                            new Parameter("movie", ParamType.RESOURCE),
                            new Parameter("awards",ParamType.LITERAL))),
                    new ArrayList<>(Arrays.asList("Movie", "Awards"))), 2),

    QUERY3("Incassi film d'animazione usciti nel 2020",
            """
                    SELECT ?movie ?gross
                    WHERE {
                    ?movie rdf:type m:Movie.
                    ?movie mo:belongsToGenre ?genre.
                    FILTER(?genre = mo:Animation).
                    ?movie  mo:releasedate ?rdate.
                    BIND(year(xsd:date(?rdate)) as ?year).
                    FILTER(?year = 2020).
                    ?movie me:worldwidegross ?gross.
                    }
                    ORDER BY ASC (?gross)""",
            new QueryBuilder(new ArrayList<>(
                    Arrays.asList(
                            new Parameter("movie", ParamType.RESOURCE),
                            new Parameter("gross", ParamType.LITERAL))),
                    new ArrayList<>(Arrays.asList("Movie", "Worldwide gross($)"))), 2),

    QUERY4("Budget film girati in Europa",
            """
                    SELECT ?movie ?location ?budget
                    WHERE {
                    ?movie rdf:type m:Movie.
                    ?movie me:budget ?budget.
                    ?movie mo:hasFilmLocation ?location.
                    ?location rdf:type mo:Europe
                    }
                    ORDER BY ASC (?budget)""",
            new QueryBuilder(new ArrayList<>(
                    Arrays.asList(
                            new Parameter("movie", ParamType.RESOURCE),
                            new Parameter("location",ParamType.RESOURCE),
                            new Parameter("budget", ParamType.LITERAL))),
            new ArrayList<>(Arrays.asList("Movie", "Location", "Budget($)"))), 3);

    private final String title;
    private final String query;
    private final QueryBuilder queryBuilder;
    private final int numParameters;

    QueryType(String title, String query, QueryBuilder queryBuilder, int numParameters) {
        this.title = title;
        this.query = query;
        this.queryBuilder = queryBuilder;
        this.numParameters = numParameters;
    }

    public String getQuery() {
        return this.query;
    }

    public String getTitle() {
        return title;
    }

    public QueryBuilder getResult() {
        return queryBuilder;
    }

    public int getNumParameters() {
        return numParameters;
    }
}
