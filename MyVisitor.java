import java.util.HashMap;
import java.util.Map;

import cls.*;

public class MyVisitor extends MyGramBaseVisitor<Variable> {

    Map<String, Variable> memory = new HashMap<String, Variable>();

    /** ID '=' expr NEWLINE */
	@Override public Variable visitAssign(MyGramParser.AssignContext ctx) {
        String id = ctx.ID().getText();
        Variable var;
        if (memory.containsKey(id)) var = memory.get(id);
        else return null;
        Variable value = visit(ctx.expr());
        // switch (value.getType()){
        //     case "int":
        //         var.setValue(value.getIntNumber());
        //         break;
        //     case "float":
        //         var.setValue(value.getFloatNumber());
        //         break;
        //     case "str":
        //         var.setValue(value.getString());
        //     default:
        //         break;
        // }
        // System.out.println("Type is: " + var.getType());
        // System.out.println("FloatValue is: " + var.getFloatNumber());
        // System.out.println("IntValue is: " + var.getIntNumber());
        VariableUtils.assign(var, value);
        return var;
    }
    /** print_statment: 'print' '(' expr ')' NEWLINE */
	@Override public Variable visitPrint_statment(MyGramParser.Print_statmentContext ctx) {
        Variable value = visit(ctx.expr());
        // switch (value.getType()){
        //     case "int":
        //         System.out.println("Int type");
        //         System.out.println(value.getIntNumber());
        //         break;
        //     case "float":
        //         System.out.println("Float type");
        //         System.out.println(value.getFloatNumber());
        //         break;
        //     case "str":
        //         System.out.println("String type: " + value.getType());
        //         System.out.println(value.getString());
        //         break;
        //     default:
        //         System.out.println("Bad type");
        // }
        VariableUtils.print(value);
        return null;
    }
    /** '-' expr */
	@Override public Variable visitNeg(MyGramParser.NegContext ctx) {
        Variable var = visit(ctx.expr());
        // switch(var.getType()){
        //     case "int":
        //         var.setValue(-1 * var.getIntNumber());
        //         break;
        //     case "float":
        //         var.setValue(-1 * var.getFloatNumber());
        //         break;
        //     default:
        //         break;
        // }
        // return VariableUtils.neg(var);
        VariableUtils.neg(var);
        return var;
    }
    /**
     * '(' expr ')'
     */
	@Override public Variable visitParens(MyGramParser.ParensContext ctx) {
        return visit(ctx.expr());
    }
	/**
     * expr op=('*'|'/') expr
	 */
	@Override public Variable visitMulDiv(MyGramParser.MulDivContext ctx) {
        Variable left = visit(ctx.expr(0));
        Variable right = visit(ctx.expr(1));
        if (!left.getType().equals(right.getType())) return null;
        Variable result = new Variable(left.getType());
        if (ctx.op.getType() == MyGramParser.MUL){ 
            VariableUtils.operation(left, right, result, "*");
            // switch(left.getType()){
            //     case "int":
            //         result.setValue(left.getIntNumber() * right.getIntNumber());
            //         break;
            //     case "float":
            //         result.setValue(left.getFloatNumber() * right.getFloatNumber());
            //         break;
            //     default:
            //         result = null;
            //         break;
            // }
        }else{
            VariableUtils.operation(left, right, result, "/");
            // switch(left.getType()){
            //     case "int":
            //         result.setValue(left.getIntNumber() / right.getIntNumber());
            //         break;
            //     case "float":
            //         result.setValue(left.getFloatNumber() / right.getFloatNumber());
            //         break;
            //     default:
            //         result = null;
            //         break;
            // }
        }
        return result;
    }
	/**
     * expr op=('+'|'-') expr
	 */
	@Override public Variable visitAddSub(MyGramParser.AddSubContext ctx) {
        Variable left = visit(ctx.expr(0));
        Variable right = visit(ctx.expr(1));
        if (!left.getType().equals(right.getType())) return null; // place for error
        Variable result = new Variable(left.getType());
        if (ctx.op.getType() == MyGramParser.ADD){ 
            VariableUtils.operation(left, right, result, "+");
            // switch(left.getType()){
            //     case "int":
            //         result.setValue(left.getIntNumber() + right.getIntNumber());
            //         break;
            //     case "float":
            //         result.setValue(left.getFloatNumber() + right.getFloatNumber());
            //         break;
            //     default:
            //         result = null;
            //         break;
            // }
        }else{
            VariableUtils.operation(left, right, result, "-");
            // switch(left.getType()){
            //     case "int":
            //         result.setValue(left.getIntNumber() - right.getIntNumber());
            //         break;
            //     case "float":
            //         result.setValue(left.getFloatNumber() - right.getFloatNumber());
            //         break;
            //     default:
            //         result = null;
            //         break;
            // }

        }
        return result;
    }
	/**
     * ID
	 */
	@Override public Variable visitId(MyGramParser.IdContext ctx) {
        String id = ctx.ID().getText();
        if (memory.containsKey(id)) return memory.get(id);
        return null;
    }
	/**
     * INT
	 */
	@Override public Variable visitInt(MyGramParser.IntContext ctx) {
        Variable constVar = new Variable("int");
        constVar.setConst(true);
        constVar.setValue(Integer.valueOf(ctx.INT().getText()));
        return constVar;
    }
	/**
     * FLOAT
	 */
	@Override public Variable visitFloat(MyGramParser.FloatContext ctx) {
        Variable constVar = new Variable("float");
        constVar.setConst(true);
        constVar.setValue(Float.valueOf(ctx.FLOAT().getText()));
        // System.out.println("float value: " + constVar.getFloatNumber());
        // System.out.println("Type value: " + constVar.getType());
        return constVar;
    }
    /**
     * STR
     */
	@Override public Variable visitStr(MyGramParser.StrContext ctx) {
        Variable constVar = new Variable("str");
        constVar.setConst(true);
        String str = ctx.STR().getText();
        int len = str.length();
        constVar.setValue(str.substring(1, len - 1));
        // System.out.println("String = " + constVar.getString());
        // System.out.println("float value: " + constVar.getFloatNumber());
        // System.out.println("Type value: " + constVar.getType());
        return constVar;
    }

    /**
     * expr op=('>'|'<') expr
     */
    @Override public Variable visitMoreLess(MyGramParser.MoreLessContext ctx){
        Variable left = visit(ctx.expr(0));
        Variable right = visit(ctx.expr(1));
        if(!left.getType().equals(right.getType())) return null;
        Variable result = new Variable(left.getType());
        if (ctx.op.getType() == MyGramParser.MORES){ 
            switch(left.getType()){
                case "int":
                    if (left.getIntNumber() > right.getIntNumber())
                        result.setValue(1);
                    else
                        result.setValue(0);
                    break;
                case "float":
                    if (left.getFloatNumber() > right.getFloatNumber())
                        result.setValue(1f);
                    else
                        result.setValue(0f);
                    break;
                default:
                    result = null;
                    break;
            }
        }else{
            switch(left.getType()){
                case "int":
                    if (left.getIntNumber() < right.getIntNumber())
                        result.setValue(1);
                    else
                        result.setValue(0);
                    break;
                case "float":
                    if (left.getFloatNumber() < right.getFloatNumber())
                        result.setValue(1f);
                    else
                        result.setValue(0f);
                    break;
                default:
                    result = null;
                    break;
            }
        }
        return result;
    }
    
    /**
     * expr op=('=='|'!=') expr
     */
    @Override public Variable visitEquN(MyGramParser.EquNContext ctx){
        Variable left = visit(ctx.expr(0));
        Variable right = visit(ctx.expr(1));
        if(!left.getType().equals(right.getType())) return null;
        Variable result = new Variable(left.getType());
        if (ctx.op.getType() == MyGramParser.EQU){ 
            switch(left.getType()){
                case "int":
                    if (left.getIntNumber() == right.getIntNumber())
                        result.setValue(1);
                    else
                        result.setValue(0);
                    break;
                case "float":
                    if (left.getFloatNumber() == right.getFloatNumber())
                        result.setValue(1f);
                    else
                        result.setValue(0f);
                    break;
                default:
                    result = null;
                    break;
            }
        }else{
            switch(left.getType()){
                case "int":
                    if (left.getIntNumber() != right.getIntNumber())
                        result.setValue(1);
                    else
                        result.setValue(0);
                    break;
                case "float":
                    if (left.getFloatNumber() != right.getFloatNumber())
                        result.setValue(1f);
                    else
                        result.setValue(0f);
                    break;
                default:
                    result = null;
                    break;
            }
        }
        return result;
    }


    /**
     * Declare section
     * TINT
     * TFLOAT
     * TSTR
     * 
     * 
     * 
     */


	/**
     * type ID NEWLINE
	 */
	@Override public Variable visitDeclareSt(MyGramParser.DeclareStContext ctx) {
        String id = ctx.ID().getText();
        Variable var = visit(ctx.type());
        // System.out.println("(declare)Type is: " + var.getType());
        memory.put(id, var);
        return null;
    }
    /**
     * TINT
     */
    @Override public Variable visitTypeInt(MyGramParser.TypeIntContext ctx){
        String type = ctx.TINT().getText();
        // System.out.println("(int)Type is: " + type);
        return new Variable(type);
    }
    /**
     * IFLOAT
     */
    @Override public Variable visitTypeFloat(MyGramParser.TypeFloatContext ctx){
        String type = ctx.TFLOAT().getText();
        // System.out.println("(float)Type is: " + type);
        return new Variable(type);
    }
    /**
     * IFLOAT
     */
    @Override public Variable visitTypeStr(MyGramParser.TypeStrContext ctx){
        String type = ctx.TSTR().getText();
        // System.out.println("(float)Type is: " + type);
        return new Variable(type);
    }


    /**
     * IF section
     * 
     * 
     */


	/**
     * 'if' '(' expr ')' '{' statments '}' NEWLINE
	 */
	@Override public Variable visitIfSt(MyGramParser.IfStContext ctx) {
        Variable condition = visit(ctx.expr());
        switch(condition.getType()){
            case "int":
                if (condition.getIntNumber() != 0) return visit(ctx.statments()); //?
                return null;
            case "float":
                if (condition.getFloatNumber() != 0) return visit(ctx.statments()); //?
                return null;
            case "str":
                //place for error
                return null;
            default:
                //place for error
                return null;
        }
    }
	/**
     * 'if' '(' expr ')' '{' statments '}' (NEWLINE)? 'else' '{' statments '}'
	 */
	@Override public Variable visitIfElSt(MyGramParser.IfElStContext ctx) { 
        Variable condition = visit(ctx.expr());
        switch(condition.getType()){
            case "int":
                if (condition.getIntNumber() != 0) return visit(ctx.statments(0)); //?
                return visit(ctx.statments(1));
            case "float":
                if (condition.getFloatNumber() != 0) return visit(ctx.statments(0)); //?
                return visit(ctx.statments(1));
            case "str":
                //place for error
                return null;
            default:
                //place for error
                return null;
        }
    }
    /**
     * WHILE section
     * 
     */
	/**
     * 'while' '(' expr ')' '{' statments '}'
	 */
	@Override public Variable visitWhileSt(MyGramParser.WhileStContext ctx) {
        Variable value = visit(ctx.expr());
        switch(value.getType()){
            case "int":
                if (value.getIntNumber() != 0){
                    visit(ctx.statments());
                }else{
                    return null;
                }
                break;
            case "float":
                if (value.getFloatNumber() != 0){
                    visit(ctx.statments());
                }else{
                    return null;
                }
                break;
            case "str":
                //place for error
                return null;
            default:
                //place for error
                return null;
        }
        return visitWhileSt(ctx);
    }
}