package fr.ogama.jfsql.query.clause.having.operators.logic;

import java.util.List;

import fr.ogama.jfsql.query.clause.having.operators.Operator;

public interface LogicalOperator extends Operator {

	public List<Operator> getOperators();

	public void setOperators(List<Operator> operators);
}
