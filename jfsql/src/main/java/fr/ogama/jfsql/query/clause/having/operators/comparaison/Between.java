package fr.ogama.jfsql.query.clause.having.operators.comparaison;

public class Between extends AbstractComparatorOperator {

	public Between() {
		super();
	}
	
	public boolean execute() {
		if (getObjects().size() == 3) {
			Comparable object = getObjects().get(0);
			Comparable object1 = getObjects().get(1);
			Comparable object2 = getObjects().get(2);
			
			if (object.compareTo(object1) >= 0 && object.compareTo(object2) <= 0) {
				return true;
			}
			
			if (object.compareTo(object2) >= 0
					&& object.compareTo(object1) <= 0) {
				return true;
			}
		}
		return false;
	}

}
