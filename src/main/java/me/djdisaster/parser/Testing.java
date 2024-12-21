package me.djdisaster.parser;

import me.djdisaster.parser.parsing.Parser;
import me.djdisaster.parser.parsing.compiling.Argument;
import me.djdisaster.parser.parsing.compiling.Compiler;
import me.djdisaster.parser.parsing.compiling.idk.CMethod;
import me.djdisaster.parser.parsing.syntax.FunctionSyntax;
import me.djdisaster.parser.parsing.syntax.SimpleExpression;
import me.djdisaster.parser.parsing.syntax.SimpleSyntax;
import me.djdisaster.parser.parsing.syntax.Syntax;
import me.djdisaster.parser.parsing.tokens.Number;
import org.codehaus.plexus.util.cli.Arg;

import java.util.Arrays;

public class Testing {

	public static void main(String[] args) {
		try {


			Syntax.getSyntaxes().add(

				new SimpleSyntax("print %Number%", "Test: %expr-1% dsa")
			);
			Syntax.getSyntaxes().add(
				new FunctionSyntax("handled in the class.")
			);

			Syntax.getExpressions().add(
					new SimpleExpression(Number.class, "%number%+%number%", "%expr-1%+%expr-2%")
			);
			Compiler compiler = new Compiler();
			Parser.parseLine(compiler, "function test(test: string, test2: number) :: number:");
			Parser.parseLine(compiler, "test 5");

			compiler.getCompiled();


		} catch (Exception e) {
			e.printStackTrace();
		}


		/*

		compiler.add(new CMethod("test", null, new Argument[]{
				new Argument("test", Number.class),
				new Argument("test2" ,String.class)
		}));
		compiler.getCompiled();

		 */
	}

}
