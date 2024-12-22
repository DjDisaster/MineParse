package me.djdisaster.parser.parsing.utils;

public class Variable {

	private Object value = null;
	public void set(Object newValue) {
		value = newValue;
	}

	public Object get() {
		return value;
	}

	public String getString() {
		return value.toString();
	}

	public Number getNumber() {
		return (Number) value;
	}




}
