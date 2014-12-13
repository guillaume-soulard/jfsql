package fr.ogama.jfsql.query.clause.get;

import java.io.File;

import fr.ogama.jfsql.query.clause.ClauseException;
import fr.ogama.jfsql.query.clause.GetClause;

public class GetParent implements GetClause {

	@Override
	public Comparable select(File file) throws ClauseException {
		if (file != null && file.getParentFile() != null) {
			return file.getParentFile().getName();
		} else {
			throw new ClauseException("Unable to get parent of : " + file);
		}
	}

}
