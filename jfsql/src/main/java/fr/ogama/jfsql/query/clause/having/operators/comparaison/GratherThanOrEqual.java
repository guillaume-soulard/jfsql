package fr.ogama.jfsql.query.clause.having.operators.comparaison;

import java.util.List;

public class GratherThanOrEqual extends AbstractComparatorOperator {

	public GratherThanOrEqual() {
		super();
	}
	
	public boolean execute() {
		Comparable objectToCompare = getObjects().get(0);
		List<Comparable> listToSearch = getObjects().subList(1, getObjects().size());		
		
		for (Comparable comparable : listToSearch) {
			if (comparable.compareTo(objectToCompare) >= 0) {
				return true;
			}
		}

		return false;
	}
}
