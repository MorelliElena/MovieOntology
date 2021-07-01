package utils;

import java.util.List;

public class Result {
    private final List<Parameter> paramList;
    private final List<String> label;

    public Result(List<Parameter> paramList, List<String> label) {
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
