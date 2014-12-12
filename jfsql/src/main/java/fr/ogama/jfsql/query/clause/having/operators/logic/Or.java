package fr.ogama.jfsql.query.clause.having.operators.logic;

import fr.ogama.jfsql.query.clause.having.operators.Operator;

public class Or extends AbstractLogicalOperator {

	public boolean execute() {
		boolean isTrue = false;
		
		for (Operator operator : getOperators()) {
			if (operator.execute()) {
				isTrue = true;
			}
		}
		
		return isTrue;
	}

}
