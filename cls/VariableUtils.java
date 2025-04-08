package cls;

public class VariableUtils{
    public static void assign(Variable target, Variable value){
        value.getToSetValue(target);
    }
    public static void print(Variable value){
        System.out.println(value.getValue());
    }
    public static void neg(Variable value){
        Variable constZero = new Variable(value.getType());
        constZero.setValue(0);
        operation(constZero, value, value, "-");
    }
    /**
     * op1 operation_symb op2
     * operation_symb = 
     * "+"
     * "-"
     * "*"
     * "/"
     */
    public static void operation(Variable op1, Variable op2, Variable result, String operation_symb){
        switch(operation_symb){
            case "+":
                sumVar(op1, op2, result);
                break;
            case "-":
                subVar(op1, op2, result);
                break;
            case "*":
                mulVar(op1, op2, result);
                break;
            case "/":
                divVar(op1, op2, result);
                break;
            default:
                break;
        }
        // return result;
    }






    private static void sumVar(Variable op1, Variable op2, Variable result){
        switch(result.getType()){
            case "int":
                result.setValue(op1.getIntNumber() + op2.getIntNumber());
                break;
            case "float":
                result.setValue(op1.getFloatNumber() + op2.getFloatNumber());
                break;
            case "str":
                result.setValue(op1.getString() + op2.getString());
                break;
            default:
                result = null;
                break;
        }
    }
    private static void subVar(Variable op1, Variable op2, Variable result){
        switch(result.getType()){
            case "int":
                result.setValue(op1.getIntNumber() - op2.getIntNumber());
                break;
            case "float":
                result.setValue(op1.getFloatNumber() - op2.getFloatNumber());
                break;
            case "str":
                // place for error
                break;
            default:
                result = null;
                break;
        }
    }
    private static void mulVar(Variable op1, Variable op2, Variable result){
        switch(result.getType()){
            case "int":
                result.setValue(op1.getIntNumber() * op2.getIntNumber());
                break;
            case "float":
                result.setValue(op1.getFloatNumber() * op2.getFloatNumber());
                break;
            case "str":
                // place for error
                break;
            default:
                result = null;
                break;
        }
    }
    private static void divVar(Variable op1, Variable op2, Variable result){
        switch(result.getType()){
            case "int":
                result.setValue(op1.getIntNumber() / op2.getIntNumber());
                break;
            case "float":
                result.setValue(op1.getFloatNumber() / op2.getFloatNumber());
                break;
            case "str":
                // place for error
                break;
            default:
                result = null;
                break;
        }
    }
}