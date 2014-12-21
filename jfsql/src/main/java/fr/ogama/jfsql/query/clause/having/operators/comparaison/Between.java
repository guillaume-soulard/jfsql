package fr.ogama.jfsql.query.clause.having.operators.comparaison;

import fr.ogama.jfsql.query.clause.ClauseException;

public class Between extends AbstractComparatorOperator {

	public Between() {
		super(2, 2);
	}
	
	@Override
	protected boolean executeComparator() throws ClauseException {
		Comparable object1 = getObjects().get(0);
		Comparable object2 = getObjects().get(1);
		
		if (getObjectToCompare().compareTo(object1) >= 0 && getObjectToCompare().compareTo(object2) <= 0) {
			return true;
		}
		
		if (getObjectToCompare().compareTo(object2) >= 0
				&& getObjectToCompare().compareTo(object1) <= 0) {
			return true;
		}
		
		return false;
	}
}
