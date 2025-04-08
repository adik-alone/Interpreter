package cls;

public class Variable{
    /**
     * field for different types
     */
    private Integer numberInt;
    private Float numberFloat;
    private String str;

    /**
     * type =
     * "int"
     * "float"
     * "str"
     */
    private String type;
    /**
     * field for constants;
     */
    private boolean constId;

    public Variable(String type){
        this.constId = false;
        this.type = type;
    }

    public Integer getIntNumber(){
        return numberInt;
    }
    public Float getFloatNumber(){
        return numberFloat;
    }
    public String getString(){
        return str;
    }
    public String getType(){
        return type;
    }

    /**
     * method for place value in target
     */
    public void getToSetValue(Variable target){
        switch(this.type){
            case "int":
                target.setValue(numberInt);
                break;
            case "float":
                target.setValue(numberFloat);
                break;
            case "str":
                target.setValue(str);
                break;
            default:
                break;
        }
    }
    public Object getValue(){
        switch(this.type){
            case "int":
                return numberInt;
            case "float":
                return numberFloat;
            case "str":
                return str;
            default:
                return null;
        }
    }

    public void setValue(Integer value){
        numberInt = value;
    }
    public void setValue(Float value){
        numberFloat = value;
    }
    public void setValue(String string){
        str = string;
    }
    public void setValue(Variable var){
        if(!var.getType().equals(type)){
            // place for error
        }
        switch(this.type){
            case "int":
                this.setValue(var.getIntNumber());
                break;
            case "float":
                this.setValue(var.getFloatNumber());
                break;
            case "str":
                this.setValue(var.getString());
                break;
            default:
                break;
        }
    }

    public boolean isConst(){
        return constId;
    }
    public void setConst(boolean constid){
        this.constId = constid;
    }
}