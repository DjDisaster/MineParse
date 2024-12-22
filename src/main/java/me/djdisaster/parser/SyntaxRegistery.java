package me.djdisaster.parser;

import me.djdisaster.parser.parsing.syntax.*;
import me.djdisaster.parser.parsing.tokens.Number;
import me.djdisaster.parser.parsing.tokens.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.server.ServerLoadEvent;

public class SyntaxRegistery {

	public static void registerSyntax() {
		addEffects();
		addSections();
		addExpressions();
		addEvents();
	}

	private static void addEffects() {
		Syntax.addSyntax(
				new SimpleSyntax("broadcast %string%", "Bukkit.broadcastMessage(%expr-1%);")
		);
		Syntax.addSyntax(
				new SimpleSyntax("send %string% to %player%", "%expr-2%.sendMessage(%expr-1%);")
		);
		Syntax.addSyntax(
				new SimpleSyntax("broadcast %number%", "Bukkit.broadcastMessage(\"\" + %expr-1%);")
		);
		Syntax.addSyntax(
				new SimpleSyntax("broadcast %player%", "Bukkit.broadcastMessage(\"\" + %expr-1%);")
		);
		Syntax.addSyntax(
				new SimpleSyntax("set %variable% to %number%", "%expr-1%.set(%expr-2%);")
		);
	}
	private static void addSections() {
		Syntax.addSyntax(
				new FunctionSyntax("handled in the class.")
		);
	}

	private static void addExpressions() {
		Syntax.addExpression(
				new SimpleExpression(Number.class, "%number%+%number%", "(%expr-1%+%expr-2%)")
		);
	}


	private static void addEvents() {
		SimpleExpression eventPlayer = new SimpleExpression(Player.class, "player", "event.getPlayer()");
		Syntax.addSyntax(
				new EventSyntax("on join:", PlayerJoinEvent.class)
						.addSectionExpression(eventPlayer)
		);

		Syntax.addSyntax(
				new EventSyntax("on load:", ServerLoadEvent.class)
		);
	}


}
