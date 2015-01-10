package fr.ogama.jfsql.query.clause.having.operators.logic;

import fr.ogama.jfsql.query.clause.ClauseException;

public class Not extends AbstractLogicalOperator {

	public boolean execute() throws ClauseException {
		if (getOperators().size() == 1) {
			return !getOperators().get(0).execute();
		}
		
		return false;
	}

}
