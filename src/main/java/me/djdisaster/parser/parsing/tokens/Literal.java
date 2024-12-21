package me.djdisaster.parser.parsing.tokens;

public class Literal extends Token {
	public Literal(String toMatch) {
		super(toMatch);
	}


	// Literal is used if nothing else works.
	// No regex is used for this as it's a special case.

	@Override
	public String getRegex() {
		return "";
	}

}
