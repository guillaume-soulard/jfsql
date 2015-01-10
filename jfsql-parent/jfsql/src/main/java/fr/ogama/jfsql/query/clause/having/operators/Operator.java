package fr.ogama.jfsql.query.clause.having.operators;

import fr.ogama.jfsql.query.clause.ClauseException;

public interface Operator {
	public boolean execute() throws ClauseException;
}
