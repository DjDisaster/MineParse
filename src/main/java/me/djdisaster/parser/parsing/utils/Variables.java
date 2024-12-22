package me.djdisaster.parser.parsing.utils;

import java.util.HashMap;

public class Variables {

	private HashMap<String, Variable> variables = new HashMap<>();
	public Variables() {

	}

	public Variable getVariable(String name) {
		if (!variables.containsKey(name)) {
			variables.put(name, new Variable());
		}
		return variables.get(name);
	}

}
