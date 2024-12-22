package me.djdisaster.parser.parsing.syntax;

import me.djdisaster.parser.parsing.Parser;
import me.djdisaster.parser.parsing.compiling.Argument;
import me.djdisaster.parser.parsing.compiling.idk.CMethod;
import me.djdisaster.parser.parsing.compiling.idk.EventMethod;
import me.djdisaster.parser.parsing.tokens.Token;
import me.djdisaster.parser.parsing.types.Types;
import org.bukkit.event.Event;
import org.codehaus.plexus.util.cli.Arg;

import java.util.ArrayList;
import java.util.List;

public class EventSyntax extends Syntax {
	private static double events = 0;
	private EventMethod eventMethod;
	private Class<? extends Event> event;
	private List<SimpleExpression> sectionExpressions = new ArrayList<>();
	private List<SimpleSyntax> sectionSyntax = new ArrayList<>();
	/*
	Example patterns:
	on load:
	on join:
	on quit:
	 */


	public EventSyntax(String pattern, Class<? extends Event> event) {
		super(pattern);
		this.event = event;
	}

	public EventSyntax addSectionExpression(SimpleExpression expression) {
		sectionExpressions.add(expression);
		return this;
	}

	public EventSyntax addSectionSyntax(SimpleSyntax syntax) {
		sectionSyntax.add(syntax);
		return this;
	}

	public List<SimpleExpression> getSectionExpressions() {
		return sectionExpressions;
	}

	public List<SimpleSyntax> getSectionSyntax() {
		return sectionSyntax;
	}




	@Override
	public String updateJava(List<Token> tokens) {
		return "";
	}


	@Override
	public boolean matches(List<Token> tokens) {
		boolean matches = super.matches(tokens);
		if (!matches) {
			return false;
		}
		Parser.removeLiterals(tokens);

		// name must be unique
		events++;
		eventMethod = new EventMethod("event" + String.valueOf(events).split("\\.")[0], event);

		return true;
	}

	public EventMethod getEventMethod() {
		return eventMethod;
	}
}
