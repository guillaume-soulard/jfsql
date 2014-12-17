package fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.properties.owner;

import java.io.File;
import java.util.List;

import fr.ogama.jfsql.query.JFSQLUtils;
import fr.ogama.jfsql.query.clause.ClauseException;
import fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.AbstractFileFilter;
import fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.Operators;
import fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.properties.FilePropertyHelper;

public class OwnerFileFilter extends AbstractFileFilter {

	private List<String> owners;

	public OwnerFileFilter(List<String> owners) {
		this.owners = owners;
	}

	@Override
	protected void setAllowedOperators() {
		addSupportedOperator(Operators.EQUAL);
		addSupportedOperator(Operators.UNEQUAL);
		addSupportedOperator(Operators.LIKE);
		addSupportedOperator(Operators.IN);
	}

	@Override
	protected Comparable getLeftValue(File file) throws ClauseException {
		try {
			return FilePropertyHelper.getOwner(file);
		} catch (Exception e) {
			throw new ClauseException(e.getMessage(), e);
		}
	}

	@Override
	protected List<Comparable> getRightValues(File file) throws ClauseException {
		return JFSQLUtils.toComparable(owners);
	}

}
