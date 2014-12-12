package fr.ogama.jfsql.query.clause.restriction;

import java.io.File;
import java.util.List;

import fr.ogama.jfsql.query.clause.FindClause;
import fr.ogama.jfsql.query.clause.RestrictionClause;

public class RestrictionDistinct implements RestrictionClause {
	public boolean canSelect(FindClause findClause, File file, List<File> selectableObjects,
			List<Comparable> selectedObjects) {
		Object object = findClause.select(file);
		if (selectedObjects.contains(object)) {
			return false;
		}
		return true;
	}
}
