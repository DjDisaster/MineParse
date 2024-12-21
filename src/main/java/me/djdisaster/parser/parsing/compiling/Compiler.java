package me.djdisaster.parser.parsing.compiling;

import me.djdisaster.parser.parsing.compiling.idk.CMethod;
import me.djdisaster.parser.parsing.syntax.FunctionSyntax;
import me.djdisaster.parser.parsing.syntax.SimpleSyntax;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.ExceptionMethod;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import org.bukkit.Bukkit;
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
		compiler.cook(classCode);
		return compiler.getClassLoader().loadClass("dynamic.CodeExecutor");
	}


	private static final String classStart = "package dynamic;import java.lang.Number;public class CodeExecutor {";
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
	public void add(Object obj) {

		System.out.println("ADD: " + obj);
		if (obj instanceof FunctionSyntax) {
			addMethod(((FunctionSyntax) obj).getcMethod());
 		} else if (obj instanceof SimpleSyntax) {
			System.out.println("TJAVA: " + ((SimpleSyntax) obj).getJava());
			current += ((SimpleSyntax) obj).getJava();
		}

	}

	private void addMethod(CMethod method) {
		addCurrent();
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



		current += "public " + returnTypeString + " " + method.getName() + "(" + argumentString + ") {";

		System.out.println("Adding method.");

	}

	public void addCurrent() {
		if (!current.isEmpty()) {
			code += current;

			if (!hasReturnValue) {
				code += "return null;";
			}

			code += "}";

		}
		current = "";
	}

	public Class<?> getCompiled() {
		addCurrent();
		String compileCode = code + classEnd;

		System.out.println("Compiling:\n\n\n" + pretty(compileCode));

		try {
			return compiled(compileCode);
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
				out.append("\n").append("\t".repeat(indentation));
			}

			if (c == '}') {
				out.append("\n").append("\t".repeat(indentation));
			}


			if (c == ';') {
				out.append("\n").append("\t".repeat(indentation));
			}

		}

		return out.toString();


	}

}
