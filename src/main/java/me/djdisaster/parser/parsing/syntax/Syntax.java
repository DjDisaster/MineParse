package me.djdisaster.parser.parsing.syntax;

import me.djdisaster.parser.parsing.tokens.Literal;
import me.djdisaster.parser.parsing.tokens.Token;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Syntax {


	private  static ParseContext context = new ParseContext();
	private static List<Syntax> syntaxes = new ArrayList<>();

	public static List<Syntax> getSyntaxes() {
		List<Syntax> currentSyntax = new ArrayList<>(syntaxes);
		currentSyntax.addAll(context.getSyntaxes());
		return currentSyntax;
	}

	public static ParseContext getContext() {
		return context;
	}


	public static void addSyntax(Syntax syntax) {
		syntaxes.add(syntax);
	}


	private static List<SimpleExpression> expressions = new ArrayList<>();
	public static List<SimpleExpression> getExpressions() {
		List<SimpleExpression> currentExpressions = new ArrayList<>(expressions);
		currentExpressions.addAll(context.getExpressions());
		return currentExpressions;
	}

	public static void addExpression(SimpleExpression expression) {
		expressions.add(expression);
	}




	private String syntaxPattern;
	private String syntaxToMatch;
	private List<Token> tokenisedSyntaxPattern;

	public Syntax(String pattern) {
		this.syntaxToMatch = pattern;
		generateTokenisedSyntaxPattern();
		System.out.println("TokensZ: " + tokenisedSyntaxPattern.size());
	}

	private void generateTokenisedSyntaxPattern() {


		int i = 0;
		String[] split = syntaxToMatch.split("%");

		List<Token> tokens = new ArrayList<>();

		for (String s : split) {
			if (i % 2 == 0) {
				if (!s.isEmpty()) {
					System.out.println("literal: " + "\"" + s + "\"");
					for (char c : s.toCharArray()) {
						tokens.add(new Literal(String.valueOf(c)));
					}
				}
			} else {
				System.out.println("non-literal: " + "\"" + s + "\"");
				Class<Token> clazz = Token.getClassFromName(s);
				System.out.println("CLAZZ: " + clazz);
				try {
					Token token = clazz.getDeclaredConstructor(String.class).newInstance("");
					tokens.add(token);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			i++;
		}

		for (Token token : tokens) {
			System.out.println(token.getType() + ": " + token.getValue());
		}

		tokenisedSyntaxPattern = tokens;

	}


	public abstract String updateJava(List<Token> tokens);

	public String getSyntaxToMatch() {
		return syntaxToMatch;
	}

	public String getSyntaxPattern() {
		return syntaxPattern;
	}




	public boolean matches(List<Token> tokens) {

		//System.out.println("SYNTAX: " + tokenisedSyntaxPattern.toString());

		//System.out.println("TOKENS: " + tokens.size());
		//System.out.println("TOKENS2: " + tokenisedSyntaxPattern.size());

		//System.out.println("Checking: " + syntaxToMatch);
		if (tokens.size() != tokenisedSyntaxPattern.size()) {
			//System.out.println("B - T1: " + tokens.size() + " T2: " + tokenisedSyntaxPattern.size());
			return false;
		}

		int i = 0;
		while (i < tokens.size()) {
			Token token1 = tokenisedSyntaxPattern.get(i);
			Token token2 = tokens.get(i);

			if (!token1.getType().equals(token2.getType())) {
				if (!token2.getType().equalsIgnoreCase("variable")) {
					return false;
				}
				//System.out.println("A");
				//System.out.println("T1: " + token1.getType());
				//System.out.println("T2: " + token2.getType());
			}

			if (token1.getType().equalsIgnoreCase("literal")) {
				if (!token1.getValue().equalsIgnoreCase(token2.getValue())) {
					//System.out.println("T1V: " + token1.getValue());
					//System.out.println("T2V: " + token2.getValue());

					return false;
				}
			}
			i++;
		}

		return true;
	}

}
