package fr.ogama.utils.parser.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class Utils {
	private static List<String> functions = null;
	public static final int VARIABLE_PLIST = 10000;

	static {
		addCustomFunction("asDateString");
		addCustomFunction("asDate");
		addCustomFunction("asInteger");
		addCustomFunction("asDouble");
		addCustomFunction("asString");
		addCustomFunction("concat");
		addCustomFunction("extract");
		addCustomFunction("count");
		addCustomFunction("sum");
		addCustomFunction("avg");
		addCustomFunction("min");
		addCustomFunction("max");
	}
	
	public static void addCustomFunction(String fct) {
		if (functions == null) {
			functions = new ArrayList<String>();
		}
		functions.add(fct.toUpperCase());
	}

	public static boolean isCustomFunction(String fct) {
		
		for (String function : functions) {
			if (function.equalsIgnoreCase(fct)) {
				return true;
			}
		}
		
		return false;		
	}

	public static boolean isAggregate(String op) {
		String tmp = op.toUpperCase().trim();
		return tmp.equals("SUM") || tmp.equals("AVG") || tmp.equals("MAX")
				|| tmp.equals("MIN") || tmp.equals("COUNT");
	}

	public static String getAggregateCall(String c) {
		int pos = c.indexOf('(');
		if (pos <= 0)
			return null;
		String call = c.substring(0, pos);
		if (Utils.isAggregate(call))
			return call.trim();
		else
			return null;
	}
	
	public static String toString(Comparable object) {
		if (object instanceof Integer) {
			return new BigDecimal((Integer)object).toPlainString();
		}
		
		if (object instanceof Double) {
			return new BigDecimal((Double)object).toPlainString();
		}
		
		return object.toString();
	}
	
	public static Number add(Number n1, Number n2) throws IllegalArgumentException {
		return n1.doubleValue() + n2.doubleValue();
	}
	
	public static Number divide(Number n1, Number n2) throws IllegalArgumentException {		
		if (n2.equals(0)) {
			throw new IllegalArgumentException("Division by 0");
		}
		
		return n1.doubleValue() / n2.doubleValue();
	}
}
