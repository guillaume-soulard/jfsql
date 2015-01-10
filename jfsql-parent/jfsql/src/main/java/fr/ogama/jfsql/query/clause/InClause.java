package fr.ogama.jfsql.query.clause;

import java.util.List;

import fr.ogama.jfsql.query.clause.in.Path;

public interface InClause extends Clause {
	public List<Path> getFiles() throws ClauseException;
}
