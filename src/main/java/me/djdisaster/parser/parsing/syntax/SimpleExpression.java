package me.djdisaster.parser.parsing.syntax;

import me.djdisaster.parser.parsing.tokens.Token;

import java.util.List;

public class SimpleExpression extends SimpleSyntax {

	private Class<Token> tokenClass;
	public SimpleExpression(Class<?> tokenClass, String pattern, String convertPattern) {
		super(pattern, convertPattern);
		this.tokenClass = (Class<Token>) tokenClass;
	}

	public Token getNewToken(List<Token> tokens) {
		try {
			return tokenClass.getDeclaredConstructor(String.class).newInstance(super.updateJava(tokens));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
