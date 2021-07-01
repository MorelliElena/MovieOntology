package utils;

public enum ParamType {
    RESOURCE("RESOURCE"),
    LITERAL("LITERAL");

    private final String type;

    ParamType(String type){
        this.type = type;
    }

    public String getType() {
        return type;
    }
}

