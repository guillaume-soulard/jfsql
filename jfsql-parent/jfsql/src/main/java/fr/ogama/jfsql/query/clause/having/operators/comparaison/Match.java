package fr.ogama.jfsql.query.clause.having.operators.comparaison;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.ogama.jfsql.query.JFSQLUtils;
import fr.ogama.jfsql.query.clause.ClauseException;

public class Match extends AbstractComparatorOperator {

	public Match() {
		super(1, 1);
	}

	@Override
	protected boolean executeComparator() throws ClauseException {
		Comparable comparable = getObjects().get(0);

		if (!(comparable instanceof String)) {
			throw new ClauseException(
					"Match right value must be a string. Given : "
							+ comparable.getClass().getName());
		}
		
		if (!(getObjectToCompare() instanceof String)) {
			throw new ClauseException(
					"Match left value must be a string. Given : "
							+ comparable.getClass().getName());
		}
		
		Pattern pattern = Pattern.compile((String) comparable);
		Matcher matcher = JFSQLUtils.executeRegexp(pattern, (String) getObjectToCompare());

		return matcher.matches();
	}
}
