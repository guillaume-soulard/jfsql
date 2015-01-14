package fr.ogama.utils.parser;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.*;

import java.util.Vector;

import org.junit.Ignore;
import org.junit.Test;

import fr.ogama.utils.parser.model.Statement;
import fr.ogama.utils.parser.model.get.Constant;
import fr.ogama.utils.parser.model.get.Expression;
import fr.ogama.utils.parser.model.get.ExpressionImpl;
import fr.ogama.utils.parser.model.get.Function;
import fr.ogama.utils.parser.model.get.GetStatement;
import fr.ogama.utils.parser.model.get.HavingClause;
import fr.ogama.utils.parser.model.get.PathItem;

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

		assertThat(getStatement.getGetClause().getLimit()).isEqualTo("1");
		assertThat(getStatement.getGetClause().getProperty())
				.isExactlyInstanceOf(Constant.class);
		assertThat(
				((Constant) getStatement.getGetClause().getProperty())
						.getValue()).isEqualTo("name");
		assertThat(
				((Constant) getStatement.getGetClause().getProperty())
						.getType()).isEqualTo(Constant.COLUMNNAME);
		assertThat(getStatement.getGetClause().isDistinct()).isTrue();

		assertThat(getStatement.getInClause().getPathItems()).hasSize(2);

		PathItem pathItem1 = (PathItem) getStatement.getInClause()
				.getPathItems().get(0);
		assertThat(pathItem1.getDeep()).isNull();
		assertThat(pathItem1.getPath()).isExactlyInstanceOf(Constant.class);
		assertThat(((Constant) pathItem1.getPath()).getValue()).isEqualTo(
				"/home/ogama");

		PathItem pathItem2 = (PathItem) getStatement.getInClause()
				.getPathItems().get(1);
		assertThat(pathItem2.getDeep()).isEqualTo("3");
		assertThat(((Constant) pathItem2.getPath()).getValue()).isEqualTo(
				"/home/otherUser");
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
		assertThat(pathItem.getPath()).isExactlyInstanceOf(GetStatement.class);
		assertThat(pathItem.getDeep()).isEqualTo("2");
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
	public void should_parse_aggregate_function_on_get() throws Exception {
		// GIVEN
		JFSQLParser jfsqlParser = new JFSQLParser();

		// WHEN
		Vector<Statement> countStatements = jfsqlParser
				.parse("get count(file) in ('/home/ogama');");

		// THEN
		assertThat(countStatements).hasSize(1);
		assertThat(countStatements.get(0)).isExactlyInstanceOf(
				GetStatement.class);
		GetStatement getStatement = (GetStatement) countStatements.get(0);
		assertThat(getStatement.getGetClause().getProperty())
				.isExactlyInstanceOf(Function.class);
		Function function = (Function) getStatement.getGetClause()
				.getProperty();
		assertThat(function.getName()).isEqualTo("count");
		assertThat(function.getParams()).hasSize(1);
		assertThat(function.isAggregate()).isTrue();
	}

	@Test
	public void should_parse_normal_function_on_get() throws Exception {
		// GIVEN
		JFSQLParser jfsqlParser = new JFSQLParser();

		// WHEN
		Vector<Statement> statements = jfsqlParser
				.parse("get concat('string', name) in ('/home/ogama');");

		// THEN
		assertThat(statements).hasSize(1);
		assertThat(statements.get(0)).isExactlyInstanceOf(
				GetStatement.class);
		GetStatement getStatement = (GetStatement) statements.get(0);
		assertThat(getStatement.getGetClause().getProperty())
				.isExactlyInstanceOf(Function.class);
		Function function = (Function) getStatement.getGetClause()
				.getProperty();
		assertThat(function.getName()).isEqualTo("concat");
		assertThat(function.getParams()).hasSize(2);
		assertThat(function.getParams().get(0)).isExactlyInstanceOf(
				Constant.class);
		assertThat(((Constant) function.getParams().get(0)).getValue())
				.isEqualTo("string");
		assertThat(((Constant) function.getParams().get(0)).getType())
				.isEqualTo(Constant.STRING);
		assertThat(function.getParams().get(1)).isExactlyInstanceOf(
				Constant.class);
		assertThat(((Constant) function.getParams().get(1)).getValue())
				.isEqualTo("name");
		assertThat(((Constant) function.getParams().get(1)).getType())
				.isEqualTo(Constant.COLUMNNAME);
		assertThat(function.isAggregate()).isFalse();
	}

	@Test
	public void should_parse_function_on_having() throws Exception {
		// GIVEN
		JFSQLParser jfsqlParser = new JFSQLParser();

		// WHEN
		Vector<Statement> statements = jfsqlParser
				.parse("get file in ('.') having name like concat('file', 1) and asString(creation_date, 'yyyy/mm/dd') = '2014/01/01';");

		// THEN
		assertThat(statements).hasSize(1);
		assertThat(statements.get(0)).isExactlyInstanceOf(GetStatement.class);
		HavingClause havingClause = ((GetStatement) statements.get(0)).getHavingClause();
		assertThat(havingClause).isNotNull();
		assertThat(havingClause.getExpression()).isExactlyInstanceOf(ExpressionImpl.class);
		
		// like operation
		Expression rightExpression = ((ExpressionImpl) ((ExpressionImpl) havingClause.getExpression()).getOperand(0)).getOperand(1);
		assertThat(rightExpression).isExactlyInstanceOf(Function.class);
		assertThat(((Function)rightExpression).getName()).isEqualTo("concat");
		assertThat(((Function)rightExpression).getParams()).hasSize(2);
		assertThat(((Function)rightExpression).getParams().get(0)).isExactlyInstanceOf(Constant.class);
		assertThat(((Constant)((Function)rightExpression).getParams().get(0)).getValue()).isEqualTo("file");
		assertThat(((Constant)((Function)rightExpression).getParams().get(0)).getType()).isEqualTo(Constant.STRING);
		assertThat(((Function)rightExpression).getParams().get(1)).isExactlyInstanceOf(Constant.class);
		assertThat(((Constant)((Function)rightExpression).getParams().get(1)).getValue()).isEqualTo("1");
		assertThat(((Constant)((Function)rightExpression).getParams().get(1)).getType()).isEqualTo(Constant.NUMBER);
		
		// equals operation
		Expression leftExpression = ((ExpressionImpl) ((ExpressionImpl) havingClause.getExpression()).getOperand(1)).getOperand(0);
		assertThat(leftExpression).isExactlyInstanceOf(Function.class);
		assertThat(((Function)leftExpression).getName()).isEqualTo("asString");
		assertThat(((Function)leftExpression).getParams()).hasSize(2);
		assertThat(((Function)leftExpression).getParams().get(0)).isExactlyInstanceOf(Constant.class);
		assertThat(((Constant)((Function)leftExpression).getParams().get(0)).getValue()).isEqualTo("creation_date");
		assertThat(((Constant)((Function)leftExpression).getParams().get(0)).getType()).isEqualTo(Constant.COLUMNNAME);
		assertThat(((Function)leftExpression).getParams().get(1)).isExactlyInstanceOf(Constant.class);
		assertThat(((Constant)((Function)leftExpression).getParams().get(1)).getValue()).isEqualTo("yyyy/mm/dd");
		assertThat(((Constant)((Function)leftExpression).getParams().get(1)).getType()).isEqualTo(Constant.STRING);
	}
}
