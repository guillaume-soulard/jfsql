package fr.ogama.jfsql.query.clause.having.operators.logic;

public class Not extends AbstractLogicalOperator {

	public boolean execute() {
		if (getOperators().size() == 1) {
			return !getOperators().get(0).execute();
		}
		
		return false;
	}

}
