package fr.ogama.jfsql.query;

import java.io.IOException;

public class Properties {
	private static java.util.Properties properties;
	private final static String RESSOURCE = "regexp.properties";

	public final static String QUERY_READ_STATEMENT_GENERAL = "query.read.statement.general";
	public final static String QUERY_READ_STATEMENT_FIND = "query.read.statement.find";
	public final static String QUERY_READ_STATEMENT_IN = "query.read.statement.in";
	public final static String QUERY_READ_STATEMENT_HAVING = "query.read.statement.having";

	public final static String QUERY_READ_STATEMENT_HAVING_EQUAL = "query.read.statement.having.equal";
	public final static String QUERY_READ_STATEMENT_HAVING_LIKE = "query.read.statement.having.like";
	public final static String QUERY_READ_STATEMENT_HAVING_UNEQUAL = "query.read.statement.having.unequal";
	public final static String QUERY_READ_STATEMENT_HAVING_GREATHERTHAN = "query.read.statement.having.gratherthan";
	public final static String QUERY_READ_STATEMENT_HAVING_GREATHERTHANOREQUAL = "query.read.statement.having.gratherthanorequal";
	public final static String QUERY_READ_STATEMENT_HAVING_LESSTHAN = "query.read.statement.having.lessthan";
	public final static String QUERY_READ_STATEMENT_HAVING_LESSTHANOREQUAL = "query.read.statement.having.lessthanorequal";

	private Properties() {

	}

	public static String getProperty(String key) throws Exception {
		if (properties == null) {
			ClassLoader cl = Thread.currentThread().getContextClassLoader();
			java.io.InputStream is = cl.getResourceAsStream(RESSOURCE);
			if (is != null) {
				properties = new java.util.Properties();
				try {
					properties.load(is);
				} catch (IOException e) {
					throw new Exception(e.getMessage(), e);
				}
			}

		}
		return properties.getProperty(key);

	}
}