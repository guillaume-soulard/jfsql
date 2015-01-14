package fr.ogama.jfsql.query.clause.function;

import java.util.Map;

public class FunctionFactory {

	private static Map<String, Class<? extends Function>> strategy;

	private static void init() {
		if (strategy == null) {
			strategy.put("count", CountFunction.class);
		}
	}

	public static Function getFunction(String name) throws Exception {
		init();

		Class<? extends Function> functionClass = strategy
				.get(name != null ? name.toLowerCase() : null);

		if (functionClass == null) {
			throw new Exception("unknow function '" + name + "'");
		}

		return functionClass.newInstance();
	}
}
