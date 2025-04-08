package cls;

public class Variable{
    private Integer numberInt;
    private Float numberFloat;
    private String str;
    /**
     * int = false
     * float = true
     */
    private String type;
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

    public void setValue(Integer value){
        numberInt = value;
    }
    public void setValue(Float value){
        numberFloat = value;
    }
    public void setValue(String string){
        str = string;
    }


    public boolean isConst(){
        return constId;
    }
    public void setConst(boolean constid){
        this.constId = constid;
    }
}