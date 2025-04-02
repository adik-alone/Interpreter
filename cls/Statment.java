package cls;

public class Statment{
    private Integer numberInt;
    private Float numberFloat;
    /**
     * int = false
     * float = true
     */
    private boolean type;

    public Statment(boolean type){
        this.type = type;
    }
    public Integer getIntNumber(){
        return numberInt;
    }
    public void setIntNumber(Integer num){
        numberInt = num;
    }
    public FLoat getFloatNumber(){
        return numberFloat;
    }
    public void setFloatNumber(Float num){
        numberFloat = num;
    }
}