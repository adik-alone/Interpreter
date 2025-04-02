import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.FileInputStream;
import java.io.InputStream;

public class Interpriter{
    public static void main(String[] args) throws Exception{
        String inputFile = null;
        if (args.length > 0) inputFile = args[0];
        InputStream in = System.in;
        if (inputFile!= null) in = new FileInputStream(inputFile);
        ANTLRInputStream input = new ANTLRInputStream(in);
        MyGramLexer lexer = new MyGramLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        MyGramParser parser = new MyGramParser(tokens);
        ParseTree tree = parser.prog();

        MyVisitor visitor = new MyVisitor();
        visitor.visit(tree);
    }

}