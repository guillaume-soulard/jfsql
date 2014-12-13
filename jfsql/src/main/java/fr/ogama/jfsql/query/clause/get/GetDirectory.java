package fr.ogama.jfsql.query.clause.get;

import java.io.File;

import fr.ogama.jfsql.query.clause.ClauseException;
import fr.ogama.jfsql.query.clause.GetClause;

public class GetDirectory implements GetClause {

	public Comparable select(File file) throws ClauseException {
		return file != null && file.isDirectory() ? file : null;
	}
}
