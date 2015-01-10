package fr.ogama.jfsql.query.clause.get;

import java.io.File;
import java.io.IOException;

import fr.ogama.jfsql.query.clause.ClauseException;
import fr.ogama.jfsql.query.clause.GetClause;
import fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.properties.FilePropertyHelper;

public class GetContent implements GetClause {

	@Override
	public Comparable select(File file) throws ClauseException {
		try {
			return FilePropertyHelper.getContent(file);
		} catch (IOException e) {
			throw new ClauseException(e.getMessage(), e);
		}
	}

}
