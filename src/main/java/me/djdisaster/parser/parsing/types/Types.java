package me.djdisaster.parser.parsing.types;

import me.djdisaster.parser.parsing.utils.Variable;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Types {

	private static final List<Class<?>> types = new ArrayList<>(Arrays.asList(
			Number.class, String.class, Player.class, Variable.class
	));

	public static List<String> classNames = types.stream()
			.map(Class::getSimpleName)
			.map(String::toLowerCase)
			.collect(Collectors.toList());

	public static HashMap<String, Class<?>> classLookup = types.stream()
			.collect(Collectors.toMap(
					c -> c.getSimpleName().toLowerCase(),
					c -> c,
					(existing, replacement) -> existing,
					HashMap::new
			));
	public static List<Class<?>> getTypes() {
		return types;
	}

	public static Class<?> getClassFromName(String className) {
		System.out.println("get class: " + className);
		System.out.println("CONTAINS? " + classLookup.containsKey(className));
		return classLookup.get(className);
	}

	public static boolean classExists(String className) {
		return classLookup.containsKey(className);
	}



	public static List<String> getNames() {
		return classNames;
	}

	public static boolean exists(String className) {
		return classNames.contains(className);
	}

}
