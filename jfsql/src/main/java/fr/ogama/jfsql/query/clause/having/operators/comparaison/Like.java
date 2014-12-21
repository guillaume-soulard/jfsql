package fr.ogama.jfsql.query.clause.having.operators.comparaison;

import fr.ogama.jfsql.query.clause.ClauseException;

public class Like extends AbstractComparatorOperator {

	public Like() {
		super(1, 1);
	}
	
	@Override
	protected boolean executeComparator() throws ClauseException {
		Comparable comparable = getObjects().get(0);
		return String.valueOf(getObjectToCompare()).contains(
				String.valueOf(comparable));
	}

}
