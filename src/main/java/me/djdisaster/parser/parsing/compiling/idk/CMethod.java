package me.djdisaster.parser.parsing.compiling.idk;

import me.djdisaster.parser.parsing.compiling.Argument;

public class CMethod {

	private String name;
	private Class<?> returnType;
	private Argument[] args;
	public CMethod(String name, Class<?> returnType, Argument... arguments) {
		this.name = name;
		this.returnType = returnType;
		this.args = arguments;
	}

	public String getName() {
		return name;
	}

	public Argument[] getArgs() {
		return args;
	}

	public Class<?> getReturnType() {
		return returnType;
	}

}
