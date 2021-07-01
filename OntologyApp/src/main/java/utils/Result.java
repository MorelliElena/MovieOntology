package utils;

import java.util.List;

public class Result {
    private final List<Parameter> paramList;
    private final List<String> label;

    public Result(List<Parameter> paramList, List<String> label) {
        this.paramList = paramList;
        this.label = label;
    }

    public void addParameter(List<Parameter> list){
        this.paramList.addAll(list);
    }

    public void addParameter(Parameter param){
        this.paramList.add(param);
    }

    public List<Parameter> getParamList() {
        return paramList;
    }

    public List<String> getLabel() {
        return label;
    }
}
