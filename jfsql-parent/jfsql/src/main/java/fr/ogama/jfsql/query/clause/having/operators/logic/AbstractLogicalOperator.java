package fr.ogama.jfsql.query.clause.having.operators.logic;

import java.util.ArrayList;
import java.util.List;

import fr.ogama.jfsql.query.clause.having.operators.Operator;

public abstract class AbstractLogicalOperator implements LogicalOperator {

	private List<Operator> operators;
	
	public AbstractLogicalOperator() {
		operators = new ArrayList<Operator>();
	}

	public List<Operator> getOperators() {
		return operators;
	}

	public void setOperators(List<Operator> operators) {
		this.operators = operators;
	}
}
