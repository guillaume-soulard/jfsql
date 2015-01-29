package fr.ogama.utils.parser.model;

import java.util.Hashtable;

public class Utils {
	private static Hashtable fcts = null;
	public static final int VARIABLE_PLIST = 10000;

	static {
		addCustomFunction("asDateString", 2);
		addCustomFunction("asDate", 2);
		addCustomFunction("asInteger", 1);
		addCustomFunction("asDecimal", 1);
		addCustomFunction("asString", 1);
		addCustomFunction("concat", 2);
		addCustomFunction("extract", 2);
	}
	
	public static void addCustomFunction(String fct, int nparm) {
		if (fcts == null)
			fcts = new Hashtable();
		if (nparm < 0)
			nparm = 1;
		fcts.put(fct.toUpperCase(), new Integer(nparm));
	}

	public static int isCustomFunction(String fct) {
		Integer nparm;
		if (fct == null || fct.length() < 1 || fcts == null
				|| (nparm = (Integer) fcts.get(fct.toUpperCase())) == null)
			return -1;
		return nparm.intValue();
	}

	public static boolean isAggregate(String op) {
		String tmp = op.toUpperCase().trim();
		return tmp.equals("SUM") || tmp.equals("AVG") || tmp.equals("MAX")
				|| tmp.equals("MIN") || tmp.equals("COUNT")
				|| (fcts != null && fcts.get(tmp) != null);
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
	
	public static Number add(Number n1, Number n2) throws IllegalArgumentException {
		
		if (!n1.getClass().equals(n2.getClass())) {
			throw new IllegalArgumentException("Not Same types");
		}
		
		if (n1 instanceof Integer && n2 instanceof Integer) {
			return (Integer) n1 + (Integer) n2;
		}
		
		if (n1 instanceof Double && n2 instanceof Double) {
			return (Double) n1 + (Double) n2;
		}
		
		if (n1 instanceof Float && n2 instanceof Float) {
			return (Float) n1 + (Float) n2;
		}
		
		if (n1 instanceof Long && n2 instanceof Long) {
			return (Long) n1 + (Long) n2;
		}
		
		return null;
	}
	
	public static Number divide(Number n1, Number n2) throws IllegalArgumentException {
		if (!n1.getClass().equals(n2.getClass())) {
			throw new IllegalArgumentException("Not Same types");
		}
		
		if (n2.equals(0)) {
			throw new IllegalArgumentException("Division by 0");
		}
		
		if (n1 instanceof Integer && n2 instanceof Integer) {
			return (Integer) n1 / (Integer) n2;
		}
		
		if (n1 instanceof Double && n2 instanceof Double) {
			return (Double) n1 / (Double) n2;
		}
		
		if (n1 instanceof Float && n2 instanceof Float) {
			return (Float) n1 / (Float) n2;
		}
		
		if (n1 instanceof Long && n2 instanceof Long) {
			return (Long) n1 / (Long) n2;
		}
		
		return null;
	}
}
