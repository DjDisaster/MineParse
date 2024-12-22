package me.djdisaster.parser.parsing.compiling;

import me.djdisaster.parser.MineParse;
import me.djdisaster.parser.parsing.compiling.idk.CMethod;
import me.djdisaster.parser.parsing.compiling.idk.EventMethod;
import me.djdisaster.parser.parsing.syntax.*;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.ExceptionMethod;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.codehaus.janino.SimpleCompiler;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

import static net.bytebuddy.matcher.ElementMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class Compiler {

	private static Class<?> compiled(String classCode) throws Exception {
		SimpleCompiler compiler = new SimpleCompiler();
		compiler.setParentClassLoader(Compiler.class.getClassLoader());
		compiler.cook(classCode);
		return compiler.getClassLoader().loadClass("dynamic.CodeExecutor");
	}


	private static final String classStart = "package dynamic;import org.bukkit.event.server.*;import me.djdisaster.parser.parsing.utils.Variables;import org.bukkit.event.player.*;import org.bukkit.Bukkit;import java.lang.Number;import org.bukkit.event.Listener; import org.bukkit.event.EventHandler; public class CodeExecutor implements Listener {"
			+ "public Variables variables;";
	private static String classEnd = "}";

	private String code = classStart;

	public Compiler() {
		/*
		Class<?> funImplClass = compileMethod(methodCode);

		Object instance = funImplClass.newInstance();

		Method[] methods = funImplClass.getDeclaredMethods();

		System.out.println("fields: " + Arrays.toString(methods));
		Object obj = funImplClass.getDeclaredMethod("fun").invoke(instance);
		System.out.println("OBJ: " + obj);

		 */
	}

	private String current = "";
	private boolean hasReturnValue = false;
	private int currentIndentation = 0;
	public void add(int indentation, Object obj) {

		System.out.println("ADD: " + obj);
		if (obj instanceof FunctionSyntax) {
			addCurrent();
			addMethod(((FunctionSyntax) obj).getcMethod());
			setCurrentIndentation(indentation);
			EventSyntax.getContext().clearSectionSyntax();
			EventSyntax.getContext().clearSectionExpressions();
		} else if (obj instanceof SimpleSyntax) {
			System.out.println("TJAVA: " + ((SimpleSyntax) obj).getJava());
			setCurrentIndentation(indentation);
			current += ((SimpleSyntax) obj).getJava();
		} else if (obj instanceof EventSyntax) {
			addCurrent();
			EventSyntax eventSyntax = (EventSyntax) obj;
			addMethod(eventSyntax.getEventMethod());
			ParseContext parseContext = EventSyntax.getContext();
			parseContext.addSectionSyntax(
					eventSyntax.getSectionSyntax()
			);
			parseContext.addSectionExpression(
					eventSyntax.getSectionExpressions()
			);

		}


	}

	private boolean returnsVoid = false;
	private void addMethod(EventMethod event) {
		addCurrent();
		hasReturnValue = false;
		code += "@EventHandler\npublic void " + event.getName() + "(" + event.getEventClass().getSimpleName() + " event) ";
		returnsVoid = true;
	}

	private void addMethod(CMethod method) {
		addCurrent();
		returnsVoid = false;
		hasReturnValue = false;
		Class<?> returnType = method.getReturnType();
		String returnTypeString = "void";

		if (returnType != null) {
			returnTypeString = returnType.getSimpleName();
		} else {
			hasReturnValue = true;
		}

		String argumentString = "";
		Argument[] args = method.getArgs();
		for (int i = 0; i < args.length;) {
			Argument arg = args[i];
			argumentString += arg.getType().getSimpleName() + " " + arg.getName();
			i++;
			if (i != args.length) {
				argumentString += ", ";
			}
		}



		current += "public " + returnTypeString + " " + method.getName() + "(" + argumentString + ")";

		System.out.println("Adding method.");

	}

	private void setCurrentIndentation(int indentation) {
		while (currentIndentation > indentation) {
			currentIndentation--;
			current += "}";
		}
		while (currentIndentation < indentation) {
			currentIndentation++;
			current += "{";
		}
	}


	public void addCurrent() {
		if (!current.isEmpty()) {


			code += current;

			if (!hasReturnValue && !returnsVoid) {
				code += "return null;";
			}
			setCurrentIndentation(0);




			code += "}";

		}

		current = "";
	}

	public Class<?> getCompiled() {
		addCurrent();
		setCurrentIndentation(0);
		String compileCode = code + classEnd;

		System.out.println("Compiling:\n\n\n" + pretty(compileCode));

		try {
			return compiled(pretty(compileCode));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String pretty(String javaCode) {
		StringBuilder out = new StringBuilder();
		int indentation = 0;
		for (char c : javaCode.toCharArray()) {

			if (c == '}') {
				indentation--;
				out.setLength(out.length()-1);
			}
			out.append(c);
			if (c == '{') {
				indentation++;
				out.append("\n").append("\t".repeat(Math.max(indentation, 0)));
			}

			if (c == '}') {
				out.append("\n").append("\t".repeat(Math.max(indentation, 0)));
			}


			if (c == ';') {
				out.append("\n").append("\t".repeat(Math.max(indentation, 0)));
			}

		}

		return out.toString();


	}

	public static void loadCompiledClass(Class<?> clazz) {
		try {
			Object instance = clazz.getDeclaredConstructor().newInstance();
			clazz.getDeclaredField("variables").set(instance, MineParse.getVariables());
			Bukkit.getPluginManager().registerEvents((Listener) instance, MineParse.getPlugin());
			Bukkit.broadcastMessage("Loaded script.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
