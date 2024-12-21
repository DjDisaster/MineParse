package me.djdisaster.parser.parsing.tokens.string;

import me.djdisaster.parser.parsing.tokens.Token;

public class String extends Token {


	public String(java.lang.String toMatch) {
		super(toMatch);
	}

	@Override
	public java.lang.String getRegex() {
		return "\".+\"";
	}


}
