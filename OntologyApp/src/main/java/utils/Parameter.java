package utils;

public class Parameter {
    private final String param;
    private final ParamType type;

    public Parameter(String param, ParamType type){
        this.param = param;
        this.type = type;
    }

    public String getParam() {
        return param;
    }

    public ParamType getType() {
        return type;
    }

}
