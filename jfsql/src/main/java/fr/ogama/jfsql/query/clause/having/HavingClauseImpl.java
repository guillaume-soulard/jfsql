package fr.ogama.jfsql.query.clause.having;

import java.util.Map;

import org.apache.commons.io.filefilter.IOFileFilter;
import org.gibello.zql.ZExp;
import org.gibello.zql.ZExpression;

import fr.ogama.jfsql.query.clause.ClauseException;
import fr.ogama.jfsql.query.clause.HavingClause;
import fr.ogama.jfsql.query.clause.having.filefilters.factory.FileFilterFactoryStrategy;

public class HavingClauseImpl implements HavingClause {

	private ZExpression expression;
	private Map<String, String> subQueries;
	
	public HavingClauseImpl(ZExpression expression, Map<String, String> subQueries) {
		this.subQueries = subQueries;
		this.expression = expression;
	}

	public IOFileFilter getFilter() throws ClauseException {
		try {
			FileFilterFactoryStrategy.setSubQueries(subQueries);
			return FileFilterFactoryStrategy.getInstance().getFileFilter(
					expression);
		} catch (Exception e) {
			throw new ClauseException(e.getMessage(), e);
		}
	}
}
