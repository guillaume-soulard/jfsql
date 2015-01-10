package fr.ogama.jfsql.query.clause.having.operators.comparaison;

import fr.ogama.jfsql.query.clause.ClauseException;

public class In extends AbstractComparatorOperator {

	public In() {
		super(1, Integer.MAX_VALUE);
	}

	@Override
	protected boolean executeComparator() throws ClauseException {
		for (Comparable comparable : getObjects()) {
			if (comparable.compareTo(getObjectToCompare()) == 0) {
				return true;
			}
		}

		return false;
	}
}
