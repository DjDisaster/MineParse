package me.djdisaster.parser.parsing.syntax;

import com.google.errorprone.annotations.Var;
import me.djdisaster.parser.parsing.Parser;
import me.djdisaster.parser.parsing.tokens.Literal;
import me.djdisaster.parser.parsing.tokens.Token;
import me.djdisaster.parser.parsing.tokens.Variable;
import me.djdisaster.parser.parsing.types.Types;

import java.util.ArrayList;
import java.util.List;

public class SimpleSyntax extends Syntax {
	private String convertPattern;
	private String java;
	private String typePattern;
	private List<Class<?>> classes = new ArrayList<>();
	public SimpleSyntax(String pattern, String convertPattern) {
		super(pattern);
		this.convertPattern = convertPattern;
		this.typePattern = pattern;
		createClasses();
	}

	private void createClasses() {

		if (!typePattern.contains("%")) {
			return;
		}

		String[] split = typePattern.split("%");
		int i = 0;
		for (String s : split) {
			if (i % 2 == 1) {
				System.out.println("Adding class: " + s);
				Class<?> clazz = Types.getClassFromName(s);
				classes.add(clazz);
				System.out.println("CLAZZ: " + clazz);
			}

			i++;
		}
	}


	@Override
	public String updateJava(List<Token> tokens) {

		Parser.removeLiterals(tokens);
		String pattern = convertPattern;

		int i = 0;
		while (i < tokens.size()) {
			Token currentToken = tokens.get(i);
			if (currentToken.getType().equalsIgnoreCase("variable")) {
				Variable variable = (Variable) currentToken;
				System.out.println("I: " + i);
				System.out.println("CLASSES: " + classes.size());
				if (!classes.isEmpty()) {
					System.out.println("CLASS1: " + classes.get(0));
				}
				pattern = pattern.replace("%expr-" + (i + 1) + "%", variable.getUseValue(classes.get(i)));

				pattern = pattern.replace("%expr-" + (i + 1) + "%", currentToken.getValue());
			} else {
				pattern = pattern.replace("%expr-" + (i + 1) + "%", currentToken.getValue());
			}
			i++;
		}
		java = pattern;
		return pattern;
	}

	public String getJava() {
		return java;
	}

}
