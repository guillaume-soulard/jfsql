package fr.ogama.jfsql.query.clause;

import java.io.File;

public interface GetClause extends Clause {
	public Comparable select(File file) throws ClauseException;
}
