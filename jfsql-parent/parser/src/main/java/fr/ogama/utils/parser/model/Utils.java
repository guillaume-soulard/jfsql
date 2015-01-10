package fr.ogama.utils.parser.model;

import java.util.Hashtable;

public class Utils {
	private static Hashtable fcts = null;
	public static final int VARIABLE_PLIST = 10000;

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
}
