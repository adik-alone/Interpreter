import java.util.HashMap;
import java.util.Map;

import cls.*;

public class MyVisitor extends MyGramBaseVisitor<Variable> {

    Map<String, Variable> memory = new HashMap<String, Variable>();
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	// @Override public T visitCalcExpr(MyGramParser.CalcExprContext ctx) { return visitChildren(ctx); }

    /** ID '=' expr NEWLINE */
	@Override public Variable visitAssign(MyGramParser.AssignContext ctx) {
        String id = ctx.ID().getText();
        Variable var;
        if (memory.containsKey(id)) var = memory.get(id);
        else return null;
        Variable value = visit(ctx.expr());
        switch (value.getType()){
            case "int":
                var.setValue(value.getIntNumber());
                break;
            case "float":
                var.setValue(value.getFloatNumber());
                break;
            default:
                break;
        }
        return var;
    }
    /** print_statment: 'print' '(' expr ')' NEWLINE */
	@Override public Variable visitPrint_statment(MyGramParser.Print_statmentContext ctx) {
        Variable value = visit(ctx.expr());
        switch (value.getType()){
            case "int":
                System.out.println(value.getIntNumber());
                break;
            case "float":
                System.out.println(value.getFloatNumber());
                break;
            default:
                System.out.println("Bad type");
        }
        return null;
    }
    /** '-' expr */
	@Override public Variable visitNeg(MyGramParser.NegContext ctx) {
        Variable var = visit(ctx.expr());
        switch(var.getType()){
            case "int":
                var.setValue(-1 * var.getIntNumber());
                break;
            case "float":
                var.setValue(-1 * var.getFloatNumber());
                break;
            default:
                break;
        }
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
            switch(left.getType()){
                case "int":
                    result.setValue(left.getIntNumber() * right.getIntNumber());
                    break;
                case "float":
                    result.setValue(left.getFloatNumber() * right.getFloatNumber());
                    break;
                default:
                    result = null;
                    break;
            }
        }else{
            switch(left.getType()){
                case "int":
                    result.setValue(left.getIntNumber() / right.getIntNumber());
                    break;
                case "float":
                    result.setValue(left.getFloatNumber() / right.getFloatNumber());
                    break;
                default:
                    result = null;
                    break;
            }

        }
        return result;
    }
	/**
     * expr op=('+'|'-') expr
	 */
	@Override public Variable visitAddSub(MyGramParser.AddSubContext ctx) {
        Variable left = visit(ctx.expr(0));
        Variable right = visit(ctx.expr(1));
        if (!left.getType().equals(right.getType())) return null;
        Variable result = new Variable(left.getType());
        if (ctx.op.getType() == MyGramParser.ADD){ 
            switch(left.getType()){
                case "int":
                    result.setValue(left.getIntNumber() + right.getIntNumber());
                    break;
                case "float":
                    result.setValue(left.getFloatNumber() + right.getFloatNumber());
                    break;
                default:
                    result = null;
                    break;
            }
        }else{
            switch(left.getType()){
                case "int":
                    result.setValue(left.getIntNumber() - right.getIntNumber());
                    break;
                case "float":
                    result.setValue(left.getFloatNumber() - right.getFloatNumber());
                    break;
                default:
                    result = null;
                    break;
            }

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
        return constVar;
    }


    /**
     * Declare section
     * TINT
     * TFLOAT
     * 
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
        memory.put(id, var);
        return null;
    }
    /**
     * TINT
     */
    @Override public Variable visitTypeInt(MyGramParser.TypeIntContext ctx){
        String type = ctx.TINT().getText();
        return new Variable(type);
    }
    /**
     * IFLOAT
     */
    @Override public Variable visitTypeFloat(MyGramParser.TypeFloatContext ctx){
        String type = ctx.TFLOAT().getText();
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
            default:
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
            default:
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
            default:
                break;
        }
        return visitWhileSt(ctx);
    }
}