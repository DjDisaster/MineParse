package me.djdisaster.parser.parsing.tokens;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public abstract class Token {

	public static final Class<Token>[] tokenClasses = new Class[]{
			Literal.class,
			Number.class,
			me.djdisaster.parser.parsing.tokens.string.String.class
	};

	private static final Map<String, Class<Token>> tokenNameMap = generateTokenNameMap();

	public static Class<Token> getClassFromName(String name) {
		System.out.println("TNM: " + tokenNameMap.toString());
		System.out.println("CHECK: " + name);
		System.out.println("CONTAINS: " + tokenNameMap.containsKey(name));
		return tokenNameMap.get(name);
	}

	private static Map<String, Class<Token>> generateTokenNameMap() {
		HashMap<String, Class<Token>> map = new HashMap<>();
		for (Class<Token> clazz : tokenClasses) {
			try {
				Token token = clazz.getDeclaredConstructor(String.class).newInstance("");
				map.put(token.getType().toLowerCase(), clazz);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println("MAP: " + map.toString());
		return map;
	}


	private String toMatch;
	public abstract String getRegex();
	public Token(String toMatch) {
		this.toMatch = toMatch;
	}

	public boolean matches() {
		return toMatch.matches(getRegex());
	}

	public String getValue() {
		return toMatch;
	}

	public String getType() {
		return this.getClass().getSimpleName();
	}

	public void addToValue(String s) {
		toMatch += s;
	}

}
