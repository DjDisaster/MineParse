package me.djdisaster.parser.parsing.types;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Types {

	private static List<Class<?>> types = new ArrayList<>(Arrays.asList(new Class<?>[]{
			Number.class, String.class
	}));

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
