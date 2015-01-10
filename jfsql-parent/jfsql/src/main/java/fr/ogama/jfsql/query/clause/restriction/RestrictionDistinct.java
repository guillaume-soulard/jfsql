package fr.ogama.jfsql.query.clause.restriction;

import java.io.File;
import java.util.List;

import fr.ogama.jfsql.query.clause.ClauseException;
import fr.ogama.jfsql.query.clause.GetClause;
import fr.ogama.jfsql.query.clause.RestrictionClause;

public class RestrictionDistinct implements RestrictionClause {
	public boolean canSelect(GetClause findClause, File file, List<File> selectableObjects,
			List<Comparable> selectedObjects) throws ClauseException {
		Object object = findClause.select(file);
		if (selectedObjects.contains(object)) {
			return false;
		}
		return true;
	}
}
