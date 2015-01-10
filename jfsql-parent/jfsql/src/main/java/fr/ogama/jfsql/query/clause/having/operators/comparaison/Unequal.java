package fr.ogama.jfsql.query.clause.having.operators.comparaison;

import fr.ogama.jfsql.query.clause.ClauseException;


public class Unequal extends Equals {

	public Unequal() {
		super();
	}
	
	@Override
	protected boolean executeComparator() throws ClauseException {
		return !super.executeComparator();
	}
}
