package me.djdisaster.parser.parsing.compiling.idk;

import me.djdisaster.parser.parsing.compiling.Argument;

public class EventMethod {

	private String name;
	private Class<?> eventClass;
	//private Argument[] args;
	public EventMethod(String name, Class<?> eventClass, Argument... arguments) {
		this.name = name;
		this.eventClass = eventClass;
		//this.args = arguments;
	}

	public String getName() {
		return name;
	}
	/*
	public Argument[] getArgs() {
		return args;
	}
	 */

	public Class<?> getEventClass() {
		return eventClass;
	}

}
