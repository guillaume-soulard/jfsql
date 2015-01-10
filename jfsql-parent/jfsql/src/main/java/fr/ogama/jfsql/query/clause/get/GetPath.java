package fr.ogama.jfsql.query.clause.get;

import java.io.File;

import fr.ogama.jfsql.query.clause.ClauseException;
import fr.ogama.jfsql.query.clause.GetClause;

public class GetPath implements GetClause {
	@Override
	public Comparable select(File file) throws ClauseException {
		if (file != null) {
			return file.getPath();
		} else {
			throw new ClauseException("Unable to get file path of : " + file);
		}
	}
}
