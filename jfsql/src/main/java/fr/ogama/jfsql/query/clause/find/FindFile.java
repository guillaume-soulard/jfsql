package fr.ogama.jfsql.query.clause.find;

import java.io.File;
import java.util.List;

import fr.ogama.jfsql.query.clause.FindClause;

public class FindFile implements FindClause {

	public String getName() {
		return "get file";
	}

	public Comparable select(File file) {
		return file != null && file.isFile() ? file : null;
	}

}
