package fr.ogama.jfsql.query.clause.having.operators.comparaison;

public class Between extends AbstractComparatorOperator {

	public Between() {
		super();
	}
	
	public boolean execute() {
		if (getObjects().size() == 2) {
			Comparable object1 = getObjects().get(0);
			Comparable object2 = getObjects().get(1);
			
			if (getObjectToCompare().compareTo(object1) >= 0 && getObjectToCompare().compareTo(object2) <= 0) {
				return true;
			}
			
			if (getObjectToCompare().compareTo(object2) >= 0
					&& getObjectToCompare().compareTo(object1) <= 0) {
				return true;
			}
		}
		return false;
	}

}
