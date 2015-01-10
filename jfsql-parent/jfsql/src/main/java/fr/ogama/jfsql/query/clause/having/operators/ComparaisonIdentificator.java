package fr.ogama.jfsql.query.clause.having.operators;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.ogama.jfsql.query.JFSQLUtils;
import fr.ogama.jfsql.query.Properties;
import fr.ogama.jfsql.query.clause.having.operators.comparaison.ComparatorOperator;
import fr.ogama.jfsql.query.clause.having.operators.comparaison.Equals;
import fr.ogama.jfsql.query.clause.having.operators.comparaison.GratherThan;
import fr.ogama.jfsql.query.clause.having.operators.comparaison.GratherThanOrEqual;
import fr.ogama.jfsql.query.clause.having.operators.comparaison.LessThan;
import fr.ogama.jfsql.query.clause.having.operators.comparaison.LessThanOrEqual;
import fr.ogama.jfsql.query.clause.having.operators.comparaison.Like;
import fr.ogama.jfsql.query.clause.having.operators.comparaison.Unequal;

public class ComparaisonIdentificator {

	private Map<String, Class<? extends ComparatorOperator>> regexps;

	public ComparaisonIdentificator() throws Exception {
		regexps = new HashMap<String, Class<? extends ComparatorOperator>>();

		regexps.put(Properties
				.getProperty(Properties.QUERY_READ_STATEMENT_HAVING_EQUAL),
				Equals.class);
		regexps.put(Properties
				.getProperty(Properties.QUERY_READ_STATEMENT_HAVING_UNEQUAL),
				Unequal.class);
		regexps.put(Properties
				.getProperty(Properties.QUERY_READ_STATEMENT_HAVING_GREATHERTHAN),
				GratherThan.class);
		regexps.put(Properties
				.getProperty(Properties.QUERY_READ_STATEMENT_HAVING_GREATHERTHANOREQUAL),
				GratherThanOrEqual.class);
		regexps.put(Properties
				.getProperty(Properties.QUERY_READ_STATEMENT_HAVING_LESSTHAN),
				LessThan.class);
		regexps.put(Properties
				.getProperty(Properties.QUERY_READ_STATEMENT_HAVING_LESSTHANOREQUAL),
				LessThanOrEqual.class);
		regexps.put(Properties
				.getProperty(Properties.QUERY_READ_STATEMENT_HAVING_LIKE),
				Like.class);
	}

	public ComparatorOperator identifyComparator(String expression)
			throws Exception {
		try {
			for (Map.Entry<String, Class<? extends ComparatorOperator>> regexp : regexps
					.entrySet()) {
				Pattern pattern = Pattern.compile(regexp.getKey(), Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
				Matcher matcher = JFSQLUtils.executeRegexp(pattern, expression);

				if (matcher.matches()) {
					String logicalOperator = matcher.group("operator");
					if (logicalOperator != null) {
						ComparatorOperator comparatorOperator = (ComparatorOperator) regexp.getValue()
								.newInstance();

						return comparatorOperator;
					}
				}
			}

			throw new Exception("Un expected operator in (" + expression + ")");
		} catch (InstantiationException e) {
			throw new Exception(e.getMessage(), e);
		} catch (IllegalAccessException e) {
			throw new Exception(e.getMessage(), e);
		}
	}
}
