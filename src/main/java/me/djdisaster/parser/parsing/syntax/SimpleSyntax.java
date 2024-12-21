package me.djdisaster.parser.parsing.syntax;

import me.djdisaster.parser.parsing.Parser;
import me.djdisaster.parser.parsing.tokens.Token;

import java.util.List;

public class SimpleSyntax extends Syntax {
	private String convertPattern;
	private String java;
	public SimpleSyntax(String pattern, String convertPattern) {
		super(pattern);
		this.convertPattern = convertPattern;
	}

	@Override
	public String updateJava(List<Token> tokens) {

		Parser.removeLiterals(tokens);
		String pattern = convertPattern;

		int i = 0;
		while (i < tokens.size()) {
			pattern = pattern.replace("%expr-" + (i+1) +"%", tokens.get(i).getValue());
			i++;
		}
		java = pattern;
		return pattern;
	}

	public String getJava() {
		return java;
	}

}
