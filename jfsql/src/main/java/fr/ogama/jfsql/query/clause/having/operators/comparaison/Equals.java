package fr.ogama.jfsql.query.clause.having.operators.comparaison;


public class Equals extends AbstractComparatorOperator {

	public Equals() {
		super();
	}
	
	public boolean execute() {		
		
		for (Comparable comparable : getObjects()) {
			if (comparable.compareTo(getObjectToCompare()) == 0) {
				return true;
			}
		}

		return false;
	}
}
