package fr.ogama.jfsql.query.clause;

import java.io.File;
import java.util.List;

public interface RestrictionClause {
	public boolean canSelect(GetClause findClause, File file,
			List<File> selectableFile, List<Comparable> selectedObjects)
			throws ClauseException;
}
