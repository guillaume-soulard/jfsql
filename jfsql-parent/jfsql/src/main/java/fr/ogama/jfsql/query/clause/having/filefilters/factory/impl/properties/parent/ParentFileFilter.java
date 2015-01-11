package fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.properties.parent;

import java.io.File;
import java.util.List;

import fr.ogama.jfsql.query.JFSQLUtils;
import fr.ogama.jfsql.query.clause.ClauseException;
import fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.AbstractFileFilter;
import fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.Operators;
import fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.properties.FilePropertyHelper;

public class ParentFileFilter extends AbstractFileFilter {

	private List<String> parents;
	
	public ParentFileFilter(List<String> parents) {
		this.parents = parents;
	}
	
	@Override
	protected void setAllowedOperators() {
		addSupportedOperator(Operators.EQUAL);
		addSupportedOperator(Operators.UNEQUAL);
		addSupportedOperator(Operators.LIKE);
		addSupportedOperator(Operators.IN);
		addSupportedOperator(Operators.MATCH);
	}

	@Override
	protected Comparable getLeftValue(File file) throws ClauseException {
		try {
			return file.getParentFile().getName();
		} catch (Exception e) {
			throw new ClauseException(e.getMessage(), e);
		}
	}

	@Override
	protected List<Comparable> getRightValues(File file) throws ClauseException {
		return JFSQLUtils.toComparable(parents);
	}

}
