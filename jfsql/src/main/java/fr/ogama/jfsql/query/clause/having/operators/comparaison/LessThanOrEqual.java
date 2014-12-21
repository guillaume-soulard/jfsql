package fr.ogama.jfsql.query.clause.having.operators.comparaison;

import java.util.List;

import fr.ogama.jfsql.query.clause.ClauseException;

public class LessThanOrEqual extends AbstractComparatorOperator {

	public LessThanOrEqual() {
		super(1, 1);
	}
	
	@Override
	protected boolean executeComparator() throws ClauseException {
		Comparable comparable = getObjects().get(0);
		return getObjectToCompare().compareTo(comparable) <= 0;
	}
}
