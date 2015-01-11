package fr.ogama.jfsql.query.clause;

import java.io.File;
import java.util.Comparator;

import fr.ogama.jfsql.query.clause.sort.SortOrder;

public interface SortClause {
	void setSortOrder(SortOrder sortOrder);
	void setGetClause(GetClause getClause);
	Comparator<File> getComparator() throws ClauseException;
}
