package fr.ogama.jfsql.query.clause.find;

import java.io.File;

import fr.ogama.jfsql.query.clause.FindClause;

public class FindDirectory implements FindClause {

	public String getName() {
		return "get directory";
	}

	public Comparable select(File file) {
		return file != null && file.isDirectory() ? file : null;
	}
}
