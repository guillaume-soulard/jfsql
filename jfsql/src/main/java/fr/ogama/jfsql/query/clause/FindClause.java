package fr.ogama.jfsql.query.clause;

import java.io.File;

public interface FindClause extends Clause {
	public Comparable select(File file);
}
