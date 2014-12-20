package fr.ogama.jfsql.query.clause.get;

import java.io.File;

import fr.ogama.jfsql.query.Status;
import fr.ogama.jfsql.query.clause.ClauseException;
import fr.ogama.jfsql.query.clause.GetClause;

public class GetStatus implements GetClause {

	@Override
	public Comparable select(File file) throws ClauseException {
		if (file.canRead() && !file.canWrite() && !file.canExecute()) {
			return Status.READABLE.getLabel();
		} else if (file.canRead() && file.canWrite() && !file.canExecute()) {
			return Status.WRITABLE.getLabel();
		} else if (file.canRead() && file.canWrite() && file.canExecute()) {
			return Status.EXECUTABLE.getLabel();
		} else {
			return Status.NONE.getLabel();
		}
	}

}
