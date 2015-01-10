package fr.ogama.jfsql.query.clause.having.operators.comparaison;

import fr.ogama.jfsql.query.clause.ClauseException;

public class GratherThanOrEqual extends AbstractComparatorOperator {

	public GratherThanOrEqual() {
		super(1, 1);
	}
	
	@Override
	protected boolean executeComparator() throws ClauseException {
		Comparable comparable = getObjects().get(0);
		return getObjectToCompare().compareTo(comparable) >= 0;
	}
}
