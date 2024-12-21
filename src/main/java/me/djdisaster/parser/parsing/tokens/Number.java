package me.djdisaster.parser.parsing.tokens;

public class Number extends Token {


	public Number(String toMatch) {
		super(toMatch);
	}

	@Override
	public String getRegex() {
		return "[0-9]+\\.[0-9]+|[0-9]+";
	}


}
