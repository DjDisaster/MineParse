package me.djdisaster.parser.parsing.compiling;

public class Argument {

	private String name;
	private Class<?> type;
	public Argument(String name, Class<?> type) {
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public Class<?> getType() {
		return type;
	}

}
