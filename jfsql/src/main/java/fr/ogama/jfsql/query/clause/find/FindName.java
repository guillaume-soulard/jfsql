package fr.ogama.jfsql.query.clause.find;

import java.io.File;

import fr.ogama.jfsql.query.clause.FindClause;

public class FindName implements FindClause {

	public String getName() {
		return "get name";
	}

	public Comparable select(File file) {
		return file != null ? file.getName() : null;
	}

}
