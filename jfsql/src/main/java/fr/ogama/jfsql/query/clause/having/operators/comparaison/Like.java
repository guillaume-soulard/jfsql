package fr.ogama.jfsql.query.clause.having.operators.comparaison;


public class Like extends AbstractComparatorOperator {

	public Like() {
		super();
	}
	
	public boolean execute() {	
		
		for (Comparable comparable : getObjects()) {
			if (!String.valueOf(getObjectToCompare()).contains(String.valueOf(comparable))){
				return false;
			}
		}
		
		return true;
	}

}
