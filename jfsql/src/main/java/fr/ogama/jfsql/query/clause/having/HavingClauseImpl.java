package fr.ogama.jfsql.query.clause.having;

import org.apache.commons.io.filefilter.IOFileFilter;
import org.gibello.zql.ZExp;
import org.gibello.zql.ZExpression;

import fr.ogama.jfsql.query.clause.ClauseException;
import fr.ogama.jfsql.query.clause.HavingClause;
import fr.ogama.jfsql.query.clause.having.filefilters.factory.FileFilterFactoryStrategy;

public class HavingClauseImpl implements HavingClause {

	private ZExpression expression;

	public HavingClauseImpl(ZExpression expression) {
		this.expression = expression;
	}

	public IOFileFilter getFilter() throws ClauseException {
		try {
			return FileFilterFactoryStrategy.getInstance().getFileFilter(
					expression);
		} catch (Exception e) {
			throw new ClauseException(e.getMessage(), e);
		}
	}
}
