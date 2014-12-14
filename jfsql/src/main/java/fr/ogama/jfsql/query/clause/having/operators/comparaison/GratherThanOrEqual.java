package fr.ogama.jfsql.query.clause.having.operators.comparaison;

import java.util.List;

public class GratherThanOrEqual extends AbstractComparatorOperator {

	public GratherThanOrEqual() {
		super();
	}
	
	public boolean execute() {	
		
		for (Comparable comparable : getObjects()) {
			if (getObjectToCompare().compareTo(comparable) >= 0) {
				return true;
			}
		}

		return false;
	}
}
