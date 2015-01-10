package fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.properties.status;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import fr.ogama.jfsql.query.JFSQLUtils;
import fr.ogama.jfsql.query.Status;
import fr.ogama.jfsql.query.clause.ClauseException;
import fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.AbstractFileFilter;
import fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.Operators;

public class StatusFileFilter extends AbstractFileFilter {

	private List<String> status;

	public StatusFileFilter(List<String> status) {
		this.status = status;
	}

	@Override
	protected void setAllowedOperators() {
		addSupportedOperator(Operators.EQUAL);
		addSupportedOperator(Operators.UNEQUAL);
		addSupportedOperator(Operators.GREATHER_THAN);
		addSupportedOperator(Operators.GREATHER_THAN_OR_EQUAL);
		addSupportedOperator(Operators.LESS_THAN);
		addSupportedOperator(Operators.LESS_THAN_OR_EQUAL);
		addSupportedOperator(Operators.IN);
		addSupportedOperator(Operators.BETWEEN);
	}

	@Override
	protected Comparable getLeftValue(File file) throws ClauseException {
		if (file.canRead() && !file.canWrite() && !file.canExecute()) {
			return Status.READABLE.getLevel();
		} else if (file.canRead() && file.canWrite() && !file.canExecute()) {
			return Status.WRITABLE.getLevel();
		} else if (file.canRead() && file.canWrite() && file.canExecute()) {
			return Status.EXECUTABLE.getLevel();
		} else {
			return Status.NONE.getLevel();
		}
	}

	@Override
	protected List<Comparable> getRightValues(File file) throws ClauseException {
		List<Integer> intStatus = new ArrayList<Integer>();

		for (String stat : status) {
			Status tempStatus = Status.getStatusByLabel(stat);
			
			if (tempStatus == null) {
				throw new ClauseException("Unknow status " + stat);
			}
			
			intStatus.add(tempStatus.getLevel());
		}

		return JFSQLUtils.toComparable(intStatus);
	}
}
