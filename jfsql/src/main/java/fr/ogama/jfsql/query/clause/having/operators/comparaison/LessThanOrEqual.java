package fr.ogama.jfsql.query.clause.having.operators.comparaison;

import java.util.List;

public class LessThanOrEqual extends AbstractComparatorOperator {

	public LessThanOrEqual() {
		super();
	}
	
	public boolean execute() {		
		
		for (Comparable comparable : getObjects()) {
			if (getObjectToCompare().compareTo(comparable) <= 0) {
				return true;
			}
		}

		return false;
	}

}
