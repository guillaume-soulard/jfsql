package fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.properties.size;

import java.io.File;
import java.util.List;

import fr.ogama.jfsql.query.JFSQLUtils;
import fr.ogama.jfsql.query.clause.ClauseException;
import fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.AbstractFileFilter;
import fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.Operators;
import fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.properties.FilePropertyHelper;

public class SizeFileFilter extends AbstractFileFilter {

	private List<Long> sizes;
	
	public SizeFileFilter(List<Long> sizes) {
		this.sizes = sizes;
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
			return FilePropertyHelper.getSize(file);
		} catch (Exception e) {
			throw new ClauseException(e.getMessage(), e);
		}
	}

	@Override
	protected List<Comparable> getRightValues(File file) throws ClauseException {
		return JFSQLUtils.toComparable(sizes);
	}

}
