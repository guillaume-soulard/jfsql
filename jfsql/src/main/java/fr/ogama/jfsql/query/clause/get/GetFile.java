package fr.ogama.jfsql.query.clause.get;

import java.io.File;
import java.util.List;

import fr.ogama.jfsql.query.clause.ClauseException;
import fr.ogama.jfsql.query.clause.GetClause;

public class GetFile implements GetClause {

	public Comparable select(File file) throws ClauseException {
		return file != null && file.isFile() ? file : null;
	}

}
