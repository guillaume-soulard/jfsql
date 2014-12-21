package fr.ogama.jfsql.query.clause.having.operators.comparaison;

import fr.ogama.jfsql.query.clause.ClauseException;

public class LessThan extends AbstractComparatorOperator {

	public LessThan() {
		super(1, 1);
	}
	
	@Override
	protected boolean executeComparator() throws ClauseException {
		Comparable comparable = getObjects().get(0);
		return getObjectToCompare().compareTo(comparable) < 0;
	}
}
