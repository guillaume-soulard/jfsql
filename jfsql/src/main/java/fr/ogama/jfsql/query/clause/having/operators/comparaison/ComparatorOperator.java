package fr.ogama.jfsql.query.clause.having.operators.comparaison;

import java.util.List;

import fr.ogama.jfsql.query.clause.having.operators.Operator;

public interface ComparatorOperator extends Operator {
	public List<Comparable> getObjects();

	public void setObjects(List<Comparable> objects);
}
