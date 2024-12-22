package me.djdisaster.parser.parsing;

import me.djdisaster.parser.parsing.compiling.Compiler;
import me.djdisaster.parser.parsing.syntax.SimpleExpression;
import me.djdisaster.parser.parsing.syntax.SimpleSyntax;
import me.djdisaster.parser.parsing.syntax.Syntax;
import me.djdisaster.parser.parsing.tokens.Literal;
import me.djdisaster.parser.parsing.tokens.Token;
import org.bukkit.inventory.RecipeChoice;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Parser {


	public static String parseLine(Compiler compiler, String line) {
		List<Token> tokens = null;
		try {
			tokens = tokeniseLine(line);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (tokens == null) {
			System.out.println("Tokens is somehow null. Please report this.");
			return "// Error [Tokens is somehow null, Please report this]";
		}

		int indentation = 0;
		while (tokens.get(0).getType().equalsIgnoreCase("tab")) {
			tokens.remove(0);
			indentation++;
			System.out.println("+ TAB");
		}


		Syntax matchedSyntax = null;

		while (true) {
			System.out.println("LOOP");
			for (Syntax syntax : Syntax.getSyntaxes()) {
				if (syntax.matches(tokens)) {
					matchedSyntax = syntax;
					break;
				}
			}

			int length = tokens.size();
			checkExpressions(tokens);
			int length2 = tokens.size();
			if (length == length2) {
				break;
			}
			System.out.println("TOKENS: " + tokens);
		}




		System.out.println("NewTokenAmount:" + tokens.size());

		if (matchedSyntax == null) {
			System.out.println("Syntax not found: " + line);

			int i = 0;
			for (Token t : tokens) {
				i++;
				System.out.println(i + ": " + t.getType() + ": " + t.getValue());
			}

			return "// Error [Syntax not found]";
		}
		matchedSyntax.updateJava(tokens);
		compiler.add(indentation, matchedSyntax);
		return matchedSyntax.updateJava(tokens);
	}

	private static void checkExpressions(List<Token> tokens) {

		int start = 0;
		int end = 0;

		while (start < tokens.size()) {
			end++;

			List<Token> currentTokens = new ArrayList<>(tokens.subList(start, end));

			for (SimpleExpression syntax : Syntax.getExpressions()) {
				//System.out.println("SYN: " + syntax.getClass().getSimpleName());
				if (syntax.matches(currentTokens)) {

					Token token = syntax.getNewToken(currentTokens);
					tokens.subList(start, end).clear();
					tokens.add(start, token);
					//System.out.println("MATCH FOUNDS!!!! EXPR");
					return;
				}
			}

			if (end == tokens.size()) {
				start++;
				end = start;
			}

		}


	}


	public static List<Token> tokeniseLine(String line) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

		int length = line.length();
		int startPoint = 0;
		int endPoint = 0;
		List<Token> tokens = new ArrayList<>();

		whileLoop:
		while (startPoint < length) {
			endPoint++;

			String currentPart = line.substring(startPoint,endPoint);

			for (Class<Token> clazz : Token.tokenClasses) {
				Token currentToken = clazz.getDeclaredConstructor(String.class).newInstance(currentPart);
				if (!currentToken.matches()) {
					continue;
				}
				tokens.add(currentToken);

				startPoint = endPoint;

				continue whileLoop;

			}


			if (endPoint == length) {
				String currentStr = String.valueOf(line.charAt(startPoint));
				tokens.add(new Literal(currentStr));
				startPoint++;
				endPoint = startPoint;
			}
		}

		sanitise(tokens);
		for (Token token : tokens) {
			System.out.println("Token: " + token.getValue() + " Type: " + token.getType());
		}


		return tokens;
	}

	public static void sanitise(List<Token> tokens) {

		/*
		int i = 0;
		while (i < tokens.size()-1) {
			Token currentToken = tokens.get(i);
			Token nextToken = tokens.get(i+1);
			if (currentToken.getType().equals(nextToken.getType())) {
				currentToken.addToValue(nextToken.getValue());
				tokens.remove(i+1);
			} else {
				i++;
			}
		}

		 */

	}

	public static void removeLiterals(List<Token> tokens) {
		tokens.removeIf(token -> "literal".equalsIgnoreCase(token.getType()));
	}





}
