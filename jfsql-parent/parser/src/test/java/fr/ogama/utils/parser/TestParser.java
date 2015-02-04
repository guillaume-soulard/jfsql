package fr.ogama.utils.parser;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Vector;

import org.junit.Test;

import fr.ogama.utils.parser.model.Statement;
import fr.ogama.utils.parser.model.get.ExpressionImpl;
import fr.ogama.utils.parser.model.get.GetStatement;
import fr.ogama.utils.parser.model.get.PathItem;
import fr.ogama.utils.parser.model.get.expressions.comparator.Equal;
import fr.ogama.utils.parser.model.get.expressions.comparator.Is;
import fr.ogama.utils.parser.model.get.expressions.logical.NotExpression;
import fr.ogama.utils.parser.model.get.function.AsDate;
import fr.ogama.utils.parser.model.get.function.AsDateString;
import fr.ogama.utils.parser.model.get.function.Sum;

public class TestParser {

	@Test
	public void should_parse_complete_simple_query() throws Exception {
		// GIVEN
		JFSQLParser jfsqlParser = new JFSQLParser();

		// WHEN
		Vector<Statement> statements = jfsqlParser
				.parse("get 1 distinct name in ('/home/ogama', '/home/otherUser' deep 3);");

		// THEN
		assertThat(statements).hasSize(1);
		assertThat(statements.get(0)).isExactlyInstanceOf(GetStatement.class);
		GetStatement getStatement = (GetStatement) statements.get(0);

		assertThat(getStatement.getGetClause().getLimit()).isEqualTo(1);
		assertThat(getStatement.getGetClause().isDistinct()).isTrue();

		assertThat(getStatement.getInClause().getPathItems()).hasSize(2);

		PathItem pathItem1 = (PathItem) getStatement.getInClause()
				.getPathItems().get(0);
		assertThat(pathItem1.getDeep()).isEqualTo(Integer.MAX_VALUE);

		PathItem pathItem2 = (PathItem) getStatement.getInClause()
				.getPathItems().get(1);
		assertThat(pathItem2.getDeep()).isEqualTo(3);
	}

	@Test
	public void should_parse_multi_queries() throws Exception {
		// GIVEN
		JFSQLParser jfsqlParser = new JFSQLParser();

		// WHEN
		Vector<Statement> statements = jfsqlParser
				.parse("get file in ('/home/ogama');get name in ('/home/otherUser');");

		// THEN
		assertThat(statements).hasSize(2);
	}

	@Test
	public void should_parse_having_query() throws Exception {
		// GIVEN
		JFSQLParser jfsqlParser = new JFSQLParser();

		// WHEN
		Vector<Statement> statements = jfsqlParser
				.parse("get file in ('/home/ogama') having name = 'test' and size between 1 and 3;");

		// THEN
		assertThat(statements).hasSize(1);

		GetStatement getStatement = (GetStatement) statements.get(0);
		assertThat(getStatement.getHavingClause()).isNotNull();
	}

	@Test
	public void should_parse_complex_having_query() throws Exception {
		// GIVEN
		JFSQLParser jfsqlParser = new JFSQLParser();

		// WHEN
		Vector<Statement> statements = jfsqlParser.parse("get file "
				+ "in ('/home/ogama') " + "having name match '[test|Test]' "
				+ "and size between 1 and 3 "
				+ "or (parent not in ('parent 1', 'parent 2')"
				+ "and path like 'home');");

		// THEN
		assertThat(statements).hasSize(1);

		GetStatement getStatement = (GetStatement) statements.get(0);
		assertThat(getStatement.getHavingClause()).isNotNull();
	}

	@Test
	public void should_parse_sub_query_in_in_clause() throws Exception {
		// GIVEN
		JFSQLParser jfsqlParser = new JFSQLParser();

		// WHEN
		Vector<Statement> statements = jfsqlParser
				.parse("get file in (get 2 file in ('/' deep 1) having size > 0 and type = 'directory' deep 2);");

		// THEN
		assertThat(statements).hasSize(1);
		assertThat(
				((GetStatement) statements.get(0)).getInClause().getPathItems())
				.hasSize(1);
		PathItem pathItem = (PathItem) ((GetStatement) statements.get(0))
				.getInClause().getPathItems().get(0);
		assertThat(pathItem.getDeep()).isEqualTo(2);
	}

	@Test
	public void should_parse_sort_by_query() throws Exception {
		// GIVEN
		JFSQLParser jfsqlParser = new JFSQLParser();

		// WHEN
		Vector<Statement> statements = jfsqlParser
				.parse("get name in ('/home/ogama') sort by name ascending;");

		assertThat(statements).hasSize(1);

		// THEN
		GetStatement getStatement = (GetStatement) statements.get(0);
		assertThat(getStatement.getSortByClause()).isNotNull();
	}

	@Test
	public void should_parse_in_sub_query() throws Exception {
		// GIVEN
		JFSQLParser jfsqlParser = new JFSQLParser();

		// WHEN
		Vector<Statement> statements = jfsqlParser
				.parse("get file in ('/home/ogama') having name in (get 1 name in ('/home/ogama'));");

		// THEN
		assertThat(statements).hasSize(1);
	}

	@Test
	public void should_parse_equals_sub_query() throws Exception {
		// GIVEN
		JFSQLParser jfsqlParser = new JFSQLParser();

		// WHEN
		Vector<Statement> statements = jfsqlParser
				.parse("get file in ('/home/ogama') having name = get 1 name in ('/home/ogama');");

		// THEN
		assertThat(statements).hasSize(1);
	}

	@Test
	public void should_parse_like_sub_query() throws Exception {
		// GIVEN
		JFSQLParser jfsqlParser = new JFSQLParser();

		// WHEN
		Vector<Statement> statements = jfsqlParser
				.parse("get file in ('/home/ogama') having name like get 1 name in ('/home/ogama');");

		// THEN
		assertThat(statements).hasSize(1);
	}

	@Test
	public void should_parse_between_sub_query() throws Exception {
		JFSQLParser jfsqlParser = new JFSQLParser();

		// WHEN
		Vector<Statement> statements = jfsqlParser
				.parse("get file in ('/home/ogama') having size between 10 and get 1 size in ('/home/ogama');");

		// THEN
		assertThat(statements).hasSize(1);
	}

	@Test
	public void should_parse_match_sub_query() throws Exception {
		// GIVEN
		JFSQLParser jfsqlParser = new JFSQLParser();

		// WHEN
		Vector<Statement> statements = jfsqlParser
				.parse("get file in ('/home/ogama') having name match get 1 content in ('/home/ogama');");

		// THEN
		assertThat(statements).hasSize(1);
	}

	@Test
	public void should_parse_aggregation_function() throws Exception {
		// GIVEN
		JFSQLParser jfsqlParser = new JFSQLParser();

		// WHEN
		Vector<Statement> sumStatements = jfsqlParser
				.parse("sum(get size in ('/home/ogama'));");

		// THEN
		assertThat(sumStatements).hasSize(1);
		assertThat(sumStatements.get(0)).isExactlyInstanceOf(Sum.class);
		Sum expression = (Sum) sumStatements.get(0);
		assertThat(expression.getOperands()).hasSize(1);
		assertThat(expression.getOperand(0)).isExactlyInstanceOf(
				GetStatement.class);
	}

	@Test
	public void should_parse_function_in_get() throws Exception {
		// GIVEN
		JFSQLParser jfsqlParser = new JFSQLParser();

		// WHEN
		Vector<Statement> statements = jfsqlParser
				.parse("get asDateString(creation_date, 'yyyy/MM/dd') in ('/home/ogama');");

		// THEN
		assertThat(statements).hasSize(1);
		assertThat(statements.get(0)).isExactlyInstanceOf(GetStatement.class);
		GetStatement statement = (GetStatement) statements.get(0);
	}

	@Test
	public void should_parse_function_in_having() throws Exception {
		// GIVEN
		JFSQLParser jfsqlParser = new JFSQLParser();

		// WHEN
		Vector<Statement> statements = jfsqlParser
				.parse("get name in ('/home/ogama') having asDate(asDateString(creation_date, 'yyyymmdd'), 'yyyymmdd') = 'other';");

		// THEN
		assertThat(statements).hasSize(1);
		assertThat(statements.get(0)).isExactlyInstanceOf(GetStatement.class);
		GetStatement statement = (GetStatement) statements.get(0);
		assertThat(statement.getHavingClause().getExpression())
				.isExactlyInstanceOf(Equal.class);
		assertThat(
				((ExpressionImpl) statement.getHavingClause().getExpression())
						.getOperands()).hasSize(2);
		assertThat(
				((ExpressionImpl) statement.getHavingClause().getExpression())
						.getOperand(0)).isExactlyInstanceOf(AsDate.class);
		ExpressionImpl expression = (ExpressionImpl) ((ExpressionImpl) statement
				.getHavingClause().getExpression()).getOperand(0);
		assertThat(expression.getOperator()).isEqualTo("asDate");
		assertThat(expression.getOperands()).hasSize(2);
		assertThat(expression.getOperand(0)).isExactlyInstanceOf(
				AsDateString.class);
		ExpressionImpl subFunction = (ExpressionImpl) expression.getOperand(0);
		assertThat(subFunction.getOperator()).isEqualTo("asDateString");
		assertThat(subFunction.getOperands()).hasSize(2);
	}

	@Test
	public void should_parse_is() throws ParseException {
		// GIVEN
		JFSQLParser jfsqlParser = new JFSQLParser();

		// WHEN
		Vector<Statement> statements = jfsqlParser
				.parse("get name in ('.') having status is not 'readable';");

		// THEN
		assertThat(statements).hasSize(1);
		assertThat(statements.get(0)).isExactlyInstanceOf(GetStatement.class);
		GetStatement getStatement = (GetStatement) statements.get(0);
		assertThat(getStatement.getHavingClause().getExpression()).isExactlyInstanceOf(NotExpression.class);
		NotExpression notExpression = (NotExpression) getStatement.getHavingClause().getExpression();
		assertThat(notExpression.getOperands()).hasSize(1);
		assertThat(notExpression.getOperand(0)).isExactlyInstanceOf(Is.class);
	}
}
