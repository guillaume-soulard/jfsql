package fr.ogama.jfsql.query.clause;

import java.io.File;
import java.util.List;

public interface InClause extends Clause {
	public List<File> getFiles();
}
