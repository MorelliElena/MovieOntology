package utils;

import java.util.List;

public class QueryBuilder {
    private final List<Parameter> paramList;
    private final List<String> label;

    public QueryBuilder(List<Parameter> paramList, List<String> label) {
        this.paramList = paramList;
        this.label = label;
    }

    public List<Parameter> getParamList() {
        return paramList;
    }

    public List<String> getLabel() {
        return label;
    }
}
