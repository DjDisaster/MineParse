package me.djdisaster.parser;

import me.djdisaster.parser.parsing.Parser;
import me.djdisaster.parser.parsing.compiling.Compiler;
import me.djdisaster.parser.parsing.syntax.*;
import me.djdisaster.parser.parsing.tokens.Number;
import me.djdisaster.parser.parsing.utils.Variables;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.codehaus.janino.Java;

public final class MineParse extends JavaPlugin {

    private static Plugin plugin;
    private static Variables variables;
    public static Plugin getPlugin() {
        return plugin;
    }
    public static Variables getVariables() {
        return variables;
    }

    @Override
    public void onEnable() {
        plugin = this;
        variables = new Variables();
        // Plugin startup logic
        SyntaxRegistery.registerSyntax();

        Compiler compiler = new Compiler();

        long parseStart = System.currentTimeMillis();

        //Parser.parseLine(compiler, "\tbroadcast funExpression");
        //Parser.parseLine(compiler, "function test2(test: string, test2: number) :: number:");
        //Parser.parseLine(compiler, "\tbroadcast 1+1");
        //Parser.parseLine(compiler, "test");
        long parseEnd = System.currentTimeMillis();
        long compileStart = System.currentTimeMillis();
        Class<?> clazz = compiler.getCompiled();
        long compileEnd = System.currentTimeMillis();
        System.out.println("Parsing took " + (parseEnd - parseStart) + " ms");
        System.out.println("Compiling took " + (compileEnd - compileStart) + " ms");

        Compiler.loadCompiledClass(clazz);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


}
