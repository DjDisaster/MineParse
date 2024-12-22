package me.djdisaster.parser.parsing.tokens;

public class Tab extends Token {
	public Tab(String toMatch) {
		super(toMatch);
	}


	// Literal is used if nothing else works.
	// No regex is used for this as it's a special case.

	@Override
	public String getRegex() {
		return "\t";
	}

}
