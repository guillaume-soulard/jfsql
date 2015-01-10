package fr.ogama.jfsql.query;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import fr.ogama.jfsql.query.clause.GetClause;
import fr.ogama.jfsql.query.clause.HavingClause;
import fr.ogama.jfsql.query.clause.InClause;
import fr.ogama.jfsql.query.clause.RestrictionClause;
import fr.ogama.jfsql.query.clause.SortClause;
import fr.ogama.jfsql.query.clause.get.GetClauseFactory;
import fr.ogama.jfsql.query.clause.having.HavingClauseImpl;
import fr.ogama.jfsql.query.clause.in.In;
import fr.ogama.jfsql.query.clause.restriction.RestrictionDistinct;
import fr.ogama.jfsql.query.clause.restriction.RestrictionLimit;
import fr.ogama.jfsql.query.clause.sort.SortFactory;
import fr.ogama.utils.parser.JFSQLParser;
import fr.ogama.utils.parser.model.Statement;
import fr.ogama.utils.parser.model.get.ExpressionImpl;
import fr.ogama.utils.parser.model.get.GetStatement;

public class QueryFactory {

	/**
	 * Parse a single query statement.
	 * 
	 * @param query
	 *            query like "get file in ('.');"
	 * @return
	 * @throws Exception
	 */
	public static Query newQuery(String query) throws Exception {

		JFSQLParser parser = new JFSQLParser();
		Vector<Statement> statements = parser.parse(query);

		if (statements.size() != 1 && statements.get(0) instanceof GetStatement) {
			throw new Exception("Unable to parse query : " + query);
		}

		GetStatement getStatement = (GetStatement) statements.get(0);

		return asQuery(getStatement);
	}

	public static Query asQuery(GetStatement getStatement) throws Exception {
		GetClause getClause = getGetClause(getStatement);
		List<RestrictionClause> restrictions = getRestrictions(getStatement);
		InClause inClause = getInClause(getStatement);
		HavingClause havingClause = getHavingClause(getStatement);
		SortClause sortByClause = getSortByClause(getStatement);

		return new QueryImpl(getClause, restrictions, inClause, havingClause,
				sortByClause);
	}

	private static GetClause getGetClause(GetStatement getStatement)
			throws Exception {
		return GetClauseFactory.getInstance().getClause(
				getStatement.getGetClause().getProperty());
	}

	private static List<RestrictionClause> getRestrictions(
			GetStatement getStatement) throws Exception {
		List<RestrictionClause> restrictions = new ArrayList<RestrictionClause>();

		if (getStatement.getGetClause().isDistinct()) {
			restrictions.add(new RestrictionDistinct());
		}

		if (getStatement.getGetClause().getLimit() != null) {
			restrictions.add(new RestrictionLimit(Integer.valueOf(getStatement
					.getGetClause().getLimit())));
		}

		return restrictions;
	}

	private static InClause getInClause(GetStatement getStatement)
			throws Exception {
		return new In(getStatement.getInClause().getPathItems());
	}

	private static HavingClause getHavingClause(GetStatement getStatement)
			throws Exception {
		if (getStatement.getHavingClause() != null) {
			return new HavingClauseImpl((ExpressionImpl) getStatement
					.getHavingClause().getExpression());
		} else {
			return null;
		}
	}

	private static SortClause getSortByClause(GetStatement getStatement)
			throws Exception {
		if (getStatement.getSortByClause() != null) {
			return new SortFactory().getSortClause(getStatement
					.getSortByClause().getProperty(), getStatement
					.getSortByClause().isAscendingOrder());
		} else {
			return null;
		}
	}
}
