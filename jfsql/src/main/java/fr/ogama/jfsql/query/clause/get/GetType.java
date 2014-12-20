package fr.ogama.jfsql.query.clause.get;

import java.io.File;

import fr.ogama.jfsql.query.FileType;
import fr.ogama.jfsql.query.clause.ClauseException;
import fr.ogama.jfsql.query.clause.GetClause;

public class GetType implements GetClause {

	@Override
	public Comparable select(File file) throws ClauseException {
		if (file.isFile()) {
			return FileType.FILE.getLabel();
		} else if (file.isDirectory()) {
			return FileType.DIRECTORY.getLabel();
		}
		
		throw new ClauseException("Unknow type of file " + file.getPath());
	}

}
