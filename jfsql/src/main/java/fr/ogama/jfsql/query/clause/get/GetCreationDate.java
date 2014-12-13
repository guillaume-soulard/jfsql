package fr.ogama.jfsql.query.clause.get;

import java.io.File;

import fr.ogama.jfsql.query.JFSQLUtils;
import fr.ogama.jfsql.query.clause.ClauseException;
import fr.ogama.jfsql.query.clause.GetClause;
import fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.properties.FilePropertyHelper;

public class GetCreationDate implements GetClause {

	@Override
	public Comparable select(File file) throws ClauseException {
		try {
			return JFSQLUtils.dateToString(FilePropertyHelper.getCreationDate(file));
		} catch (Exception e) {
			throw new ClauseException(e.getMessage(), e);
		}
	}

}
