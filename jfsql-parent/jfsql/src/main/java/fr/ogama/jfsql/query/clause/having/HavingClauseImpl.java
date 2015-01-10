package fr.ogama.jfsql.query.clause.having;

import java.util.Map;

import org.apache.commons.io.filefilter.IOFileFilter;

import fr.ogama.jfsql.query.clause.ClauseException;
import fr.ogama.jfsql.query.clause.HavingClause;
import fr.ogama.jfsql.query.clause.having.filefilters.factory.FileFilterFactoryStrategy;
import fr.ogama.utils.parser.model.get.ExpressionImpl;

public class HavingClauseImpl implements HavingClause {

	private ExpressionImpl expression;
	
	public HavingClauseImpl(ExpressionImpl expression) {
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
