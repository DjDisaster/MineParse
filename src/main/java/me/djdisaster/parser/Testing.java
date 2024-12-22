package me.djdisaster.parser;

import me.djdisaster.parser.parsing.Parser;
import me.djdisaster.parser.parsing.compiling.Argument;
import me.djdisaster.parser.parsing.compiling.Compiler;
import me.djdisaster.parser.parsing.compiling.idk.CMethod;
import me.djdisaster.parser.parsing.syntax.*;
import me.djdisaster.parser.parsing.tokens.Number;
import org.bukkit.event.player.PlayerJoinEvent;
import org.codehaus.plexus.util.cli.Arg;

import java.util.Arrays;

public class Testing {

	public static void main(String[] args) {
		try {


			Syntax.getSyntaxes().add(
					new SimpleSyntax("broadcast %string%", "Bukkit.broadcastMessage(%expr-1%);")
			);
			Syntax.getSyntaxes().add(
					new SimpleSyntax("broadcast %number%", "Bukkit.broadcastMessage(\"\" + %expr-1%);")
			);
			Syntax.getSyntaxes().add(
					new FunctionSyntax("handled in the class.")
			);
			Syntax.getSyntaxes().add(
					new EventSyntax("on load:", PlayerJoinEvent.class)
			);


			Syntax.getExpressions().add(
					new SimpleExpression(Number.class, "%number%+%number%", "(%expr-1% add %expr-2%)")
			);
			Compiler compiler = new Compiler();

			long parseStart = System.currentTimeMillis();
			Parser.parseLine(compiler, "on load:");
			Parser.parseLine(compiler, "\tbroadcast \"hello\"");
			Parser.parseLine(compiler, "function test2(test: string, test2: number) :: number:");
			Parser.parseLine(compiler, "\tbroadcast 1+1");
			long parseEnd = System.currentTimeMillis();
			long compileStart = System.currentTimeMillis();
			compiler.getCompiled();
			long compileEnd = System.currentTimeMillis();
			System.out.println("Parsing took " + (parseEnd - parseStart) + " ms");
			System.out.println("Compiling took " + (compileEnd - compileStart) + " ms");




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
