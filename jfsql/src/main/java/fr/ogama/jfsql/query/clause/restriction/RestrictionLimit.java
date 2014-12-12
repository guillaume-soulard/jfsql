package fr.ogama.jfsql.query.clause.restriction;

import java.io.File;
import java.util.List;

import fr.ogama.jfsql.query.clause.FindClause;
import fr.ogama.jfsql.query.clause.RestrictionClause;

public class RestrictionLimit implements RestrictionClause {

	private int selectionLimit;
	
	public RestrictionLimit(int selectionLimit) {
		this.selectionLimit = selectionLimit;
	}
	
	public boolean canSelect(FindClause findClause, File object, List<File> selectableObjects,
			List<Comparable> selectedObjects) {
		if (selectedObjects.size() >= selectionLimit) {
			return false;
		}
		
		return true;
	}

}
