package fr.ogama.jfsql.query.clause.having.operators.logic;

import fr.ogama.jfsql.query.clause.having.operators.Operator;


public class And extends AbstractLogicalOperator {

	public boolean execute() {
		boolean isTrue = true;
		
		for (Operator operator : getOperators()) {
			if (!operator.execute()) {
				isTrue = false;
			}
		}
		
		return isTrue;
	}

}
