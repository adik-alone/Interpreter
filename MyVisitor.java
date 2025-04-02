import java.util.HashMap;
import java.util.Map;

public class MyVisitor extends MyGramBaseVisitor<Integer> {

    Map<String, Integer> memory = new HashMap<String, Integer>();
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	// @Override public T visitCalcExpr(MyGramParser.CalcExprContext ctx) { return visitChildren(ctx); }

    /** ID '=' expr NEWLINE */
	@Override public Integer visitAssign(MyGramParser.AssignContext ctx) {
        String id = ctx.ID().getText();
        int value = visit(ctx.expr());
        // memory.put(id, value);
        if (memory.containsKey(id)){
            memory.remove(id);
            memory.put(id, value);
        }
        return value;
    }
    /** print_statment: 'print' '(' expr ')' NEWLINE */
	@Override public Integer visitPrint_statment(MyGramParser.Print_statmentContext ctx) {
        Integer value = visit(ctx.expr());
        System.out.println(value);
        return 0;
    }
    /** '-' expr */
	@Override public Integer visitNeg(MyGramParser.NegContext ctx) {
        Integer value = -1 * visit(ctx.expr());
        return value;
    }
    /**
     * '(' expr ')'
     */
	@Override public Integer visitParens(MyGramParser.ParensContext ctx) {
        return visit(ctx.expr());
    }
	/**
     * expr op=('*'|'/') expr
	 */
	@Override public Integer visitMulDiv(MyGramParser.MulDivContext ctx) {
        Integer left = visit(ctx.expr(0));
        Integer right = visit(ctx.expr(1));
        if (ctx.op.getType() == MyGramParser.MUL) return left * right;
        return left / right;
    }
	/**
     * expr op=('+'|'-') expr
	 */
	@Override public Integer visitAddSub(MyGramParser.AddSubContext ctx) {
        Integer left = visit(ctx.expr(0));
        Integer right = visit(ctx.expr(1));
        if (ctx.op.getType() == MyGramParser.ADD) return left + right;
        return left - right;
    }
	/**
     * ID
	 */
	@Override public Integer visitId(MyGramParser.IdContext ctx) {
        String id = ctx.ID().getText();
        if (memory.containsKey(id)) return memory.get(id);
        return 0;
    }
	/**
     * INT
	 */
	@Override public Integer visitInt(MyGramParser.IntContext ctx) {
        return Integer.valueOf(ctx.INT().getText());
    }
	/**
     * 'int' ID
	 */
	@Override public Integer visitType(MyGramParser.TypeContext ctx) {
        String id = ctx.ID().getText();
        memory.put(id, 0);
        return 0;
    }
	/**
     * 'if' '(' expr ')' '{' statments '}' NEWLINE
	 */
	@Override public Integer visitIfSt(MyGramParser.IfStContext ctx) {
        Integer value = visit(ctx.expr());
        if (value != 0) return visit(ctx.statments()); //?
        return 0;
    }
	/**
     * 'if' '(' expr ')' '{' statments '}' (NEWLINE)? 'else' '{' statments '}'
	 */
	@Override public Integer visitIfElSt(MyGramParser.IfElStContext ctx) { 
        Integer value = visit(ctx.expr());
        if (value != 0) return visit(ctx.statments(0));
        return visit(ctx.statments(1));
    }
	/**
     * 'while' '(' expr ')' '{' statments '}'
	 */
	@Override public Integer visitWhileSt(MyGramParser.WhileStContext ctx) {
        Integer value = visit(ctx.expr());
        if (value != 0){
            visit(ctx.statments());
        }else{
            return 0;
        }
        return visitWhileSt(ctx);
    }
}