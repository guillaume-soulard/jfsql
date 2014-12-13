package fr.ogama.jfsql.query;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.filefilter.IOFileFilter;
import org.gibello.zql.ZExpression;
import org.gibello.zql.ZQuery;
import org.gibello.zql.ZStatement;
import org.gibello.zql.ZqlParser;

import fr.ogama.jfsql.query.clause.GetClause;
import fr.ogama.jfsql.query.clause.HavingClause;
import fr.ogama.jfsql.query.clause.InClause;
import fr.ogama.jfsql.query.clause.RestrictionClause;
import fr.ogama.jfsql.query.clause.get.GetClauseFactory;
import fr.ogama.jfsql.query.clause.having.HavingClauseImpl;
import fr.ogama.jfsql.query.clause.having.operators.ComparaisonIdentificator;
import fr.ogama.jfsql.query.clause.having.operators.comparaison.ComparatorOperator;
import fr.ogama.jfsql.query.clause.having.operators.logic.And;
import fr.ogama.jfsql.query.clause.having.operators.logic.LogicalOperator;
import fr.ogama.jfsql.query.clause.having.operators.logic.Or;
import fr.ogama.jfsql.query.clause.in.In;
import fr.ogama.jfsql.query.clause.restriction.RestrictionDistinct;
import fr.ogama.jfsql.query.clause.restriction.RestrictionLimit;

public class QueryFactory {

	public static Query newQuery(String query) throws Exception {
		QueryClause queryClause = getClauses(query);

		Pair<GetClause, List<RestrictionClause>> findClause = parseGet(queryClause
				.getFindClause());
		InClause inClause = parseIn(queryClause.getInClause());
		HavingClause havingClause = parseHaving(queryClause.getHavingClause());		
		return new QueryImpl(findClause.getObject1(), findClause.getObject2(),
				inClause, havingClause);
	}

	private static QueryClause getClauses(String query) throws Exception {
		try {
			String regexp = Properties
					.getProperty(Properties.QUERY_READ_STATEMENT_GENERAL);
			Pattern pattern = Pattern.compile(regexp, Pattern.MULTILINE
					| Pattern.CASE_INSENSITIVE);
			Matcher matcher = JFSQLUtils.executeRegexp(pattern, query);

			if (matcher.matches()) {
				String findStatement = matcher.group("findStatement");
				String inStatement = matcher.group("inStatement");
				String havingStatement = matcher.group("havingStatement");

				if (findStatement != null && inStatement != null) {
					QueryClause queryClause = new QueryClause();
					queryClause
							.setFindClause(findStatement != null ? findStatement
									: "");
					queryClause.setInClause(inStatement != null ? inStatement
							: "");
					queryClause
							.setHavingClause(havingStatement != null ? havingStatement
									: "");
					return queryClause;
				}
			}

			throw new Exception("Error in query");
		} catch (Exception e) {
			throw new Exception(e.getMessage(), e);
		}
	}

	private static Pair<GetClause, List<RestrictionClause>> parseGet(
			String find) throws Exception {
		String regexp = Properties
				.getProperty(Properties.QUERY_READ_STATEMENT_FIND);
		Pattern pattern = Pattern.compile(regexp, Pattern.MULTILINE
				| Pattern.CASE_INSENSITIVE);

		Matcher matcher = JFSQLUtils.executeRegexp(pattern, find);

		List<RestrictionClause> restrictionClauses = new ArrayList<RestrictionClause>();
		GetClause findClause = null;

		if (matcher.matches()) {
			String limit = matcher.group("limit");
			if (limit != null) {
				restrictionClauses.add(new RestrictionLimit(Integer
						.valueOf(limit)));
			}

			String distinct = matcher.group("distinct");
			if (distinct != null) {
				restrictionClauses.add(new RestrictionDistinct());
			}

			String selector = matcher.group("selector");
			if (selector != null) {
				findClause = GetClauseFactory.getInstance().getClause(selector);
			}
		}

		return new Pair<GetClause, List<RestrictionClause>>(findClause,
				restrictionClauses);
	}

	private static InClause parseIn(String in) throws Exception {
		List<String> paths = new ArrayList<String>();
		boolean hasPaths = true;
		String regexp = Properties
				.getProperty(Properties.QUERY_READ_STATEMENT_IN);
		Pattern pattern = Pattern.compile(regexp, Pattern.CASE_INSENSITIVE
				| Pattern.MULTILINE);

		while (hasPaths) {
			Matcher matcher = JFSQLUtils.executeRegexp(pattern, in);
			if (matcher.find()) {
				String path = matcher.group("directory");
				paths.add(path);
				in = in.replaceFirst("'" + path + "'", "");
			} else {
				hasPaths = false;
			}
		}
		
		if (paths.size() == 0) {
			throw new Exception("Error in '" + in + "'");
		}

		return new In(paths.toArray(new String[] {}));
	}

	private static HavingClause parseHaving(String having) throws Exception {
		if (having != null && !having.isEmpty()) {
			String query = JFSQLUtils.jfsqlHavingClauseToSqlWhereClause(having);
			InputStream in = new ByteArrayInputStream(query.getBytes());
			ZqlParser parser = new ZqlParser(in);
			Vector<ZStatement> statements = parser.readStatements();

			for (ZStatement statement : statements) {
				if (statement instanceof ZQuery) {
					return new HavingClauseImpl(
							(ZExpression) ((ZQuery) statement).getWhere());
				}
			}
		}
		return null;
	}
}
