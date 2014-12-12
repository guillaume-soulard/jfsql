package fr.ogama.jfsql.query.clause.having.operators.comparaison;

import java.util.List;

public class Like extends AbstractComparatorOperator {

	public Like() {
		super();
	}
	
	public boolean execute() {
		Comparable objectToCompare = getObjects().get(0);
		List<Comparable> listToSearch = getObjects().subList(1, getObjects().size());		
		
		for (Comparable comparable : listToSearch) {
			if (!String.valueOf(comparable).contains(String.valueOf(objectToCompare))){
				return false;
			}
		}
		
		return true;
	}

}
