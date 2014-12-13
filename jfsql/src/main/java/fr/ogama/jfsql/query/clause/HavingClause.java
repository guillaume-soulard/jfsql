package fr.ogama.jfsql.query.clause;

import org.apache.commons.io.filefilter.IOFileFilter;

public interface HavingClause extends Clause {
	public IOFileFilter getFilter() throws ClauseException;
}
