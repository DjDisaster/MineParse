package me.djdisaster.parser.parsing.syntax;

import me.djdisaster.parser.parsing.compiling.Argument;
import me.djdisaster.parser.parsing.compiling.idk.CMethod;
import me.djdisaster.parser.parsing.tokens.Token;
import me.djdisaster.parser.parsing.types.Types;

import java.util.ArrayList;
import java.util.List;

public class FunctionSyntax extends Syntax {

	private CMethod cMethod;


	public FunctionSyntax(String pattern) {
		super(pattern);
	}

	@Override
	public String updateJava(List<Token> tokens) {
		return "";
	}


	@Override
	public boolean matches(List<Token> tokens) {
		String value = "";
		for (Token token : tokens) {
			value += token.getValue();
		}

		if (!value.toLowerCase().startsWith("function ")) {
			System.out.println("!= function Start");
			return false;
		}
		value = value.replaceFirst("function ", "");
		int start = value.indexOf("(");
		int end = value.lastIndexOf(")");

		String values = value.substring(start+1,end);

		List<Argument> argList = new ArrayList<>();
		for (String args : values.split(", {0,1}")) {
			String[] split = args.split(": {0,1}");
			// split0 = Name
			// split1 = Type

			if (!Types.exists(split[1])) {
				System.out.println(split[1] + " type doesn't exist!");
				return false;
			}

			argList.add(new Argument(split[0], Types.getClassFromName(split[1])));

		}
		String returnText = value.substring(end+1).replace(" ", "");
		Class<?> returnClass = null;
		System.out.println("RT: " + returnText);
		if (returnText.startsWith("::")) {
			if (!returnText.endsWith(":")) {
				System.out.println("Invalid ending.");
				return false;
			}
			returnText = returnText.substring(2);
			returnText = returnText.substring(0,returnText.length()-1);
			if (Types.classExists(returnText)) {
				returnClass = Types.getClassFromName(returnText);
			} else {
				System.out.println("Class: " + returnText + " doesn't exist!");
				return false;
			}
		} else if (!returnText.equals(":")) {
			System.out.println("Invalid ending statement for function");
			return false;
		}

		String functionName = value.substring(0,start);

		System.out.println("VALUE: " + value);
		System.out.println("VALUES: " + values);
		System.out.println("functionName: " + functionName);

		Argument[] arguments = argList.toArray(new Argument[0]);

		cMethod = new CMethod(functionName, returnClass, arguments);

		return true;
	}

	public CMethod getcMethod() {
		return cMethod;
	}
}
