package me.djdisaster.parser.parsing.tokens;

public class Variable extends Token {
	public Variable(String toMatch) {
		super(toMatch);
	}


	// Literal is used if nothing else works.
	// No regex is used for this as it's a special case.

	@Override
	public String getRegex() {
		return "\\{[A-z|0-9|::|\\.]+}";
	}

	public String getUseValue(Class<?> clazz) {
		String value = super.getValue();
		String subValue = value.substring(1, value.length()-1);
		if (clazz == me.djdisaster.parser.parsing.utils.Variable.class) {
			return "variables.getVariable(\"" + subValue + "\")";
		}
		return "variables.getVariable(\"" + subValue + "\").get" + clazz.getSimpleName()  + "()";
	}

}
