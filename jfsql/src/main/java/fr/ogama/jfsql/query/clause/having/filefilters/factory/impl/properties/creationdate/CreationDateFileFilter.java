package fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.properties.creationdate;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import fr.ogama.jfsql.query.JFSQLUtils;
import fr.ogama.jfsql.query.clause.ClauseException;
import fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.AbstractFileFilter;
import fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.Operators;
import fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.properties.FilePropertyHelper;

public class CreationDateFileFilter extends AbstractFileFilter {

	private List<Date> creationsDates;
	
	public CreationDateFileFilter(List<Date> creationsDates) {
		this.creationsDates = creationsDates;
	}
	
	@Override
	protected void setAllowedOperators() {
		addSupportedOperator(Operators.EQUAL);
		addSupportedOperator(Operators.UNEQUAL);
		addSupportedOperator(Operators.GREATHER_THAN);
		addSupportedOperator(Operators.GREATHER_THAN_OR_EQUAL);
		addSupportedOperator(Operators.LESS_THAN);
		addSupportedOperator(Operators.LESS_THAN_OR_EQUAL);
		addSupportedOperator(Operators.BETWEEN);
		addSupportedOperator(Operators.IN);
	}

	@Override
	protected Comparable getLeftValue(File file) throws ClauseException {
		try {
			return FilePropertyHelper.getCreationDate(file);
		} catch (Exception e) {
			throw new ClauseException(e.getMessage(), e);
		}
	}

	@Override
	protected List<Comparable> getRightValues(File file) throws ClauseException {
		return JFSQLUtils.toComparable(creationsDates);
	}
}
