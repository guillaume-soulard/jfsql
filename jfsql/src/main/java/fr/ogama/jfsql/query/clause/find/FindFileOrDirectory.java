package fr.ogama.jfsql.query.clause.find;

import java.io.File;

import fr.ogama.jfsql.query.clause.FindClause;

public class FindFileOrDirectory implements FindClause {

	public String getName() {
		return "get file or directory";
	}

	public Comparable select(File file) {
		return file;
	}

}
