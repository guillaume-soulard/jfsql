package fr.ogama.utils.parser;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.*;

import java.util.Vector;

import org.junit.Ignore;
import org.junit.Test;

import fr.ogama.utils.parser.model.Statement;
import fr.ogama.utils.parser.model.get.Constant;
import fr.ogama.utils.parser.model.get.GetStatement;
import fr.ogama.utils.parser.model.get.PathItem;

public class TestParser {

	@Test
	public void should_parse_complete_simple_query() throws Exception {
		// GIVEN
		JFSQLParser jfsqlParser = new JFSQLParser();
		
		// WHEN
		Vector<Statement> statements = jfsqlParser.parse("get 1 distinct name in ('/home/ogama', '/home/otherUser' deep 3);");
	
		// THEN
		assertThat(statements).hasSize(1);
		assertThat(statements.get(0)).isExactlyInstanceOf(GetStatement.class);
		GetStatement getStatement = (GetStatement) statements.get(0);
		
		assertThat(getStatement.getGetClause().getLimit()).isEqualTo("1");
		assertThat(getStatement.getGetClause().getProperty()).isEqualTo("name");
		assertThat(getStatement.getGetClause().isDistinct()).isTrue();
		
		assertThat(getStatement.getInClause().getPathItems()).hasSize(2);
		
		PathItem pathItem1 = (PathItem) getStatement.getInClause().getPathItems().get(0);
		assertThat(pathItem1.getDeep()).isNull();
		assertThat(pathItem1.getPath()).isExactlyInstanceOf(Constant.class);
		assertThat(((Constant)pathItem1.getPath()).getValue()).isEqualTo("/home/ogama");
		
		PathItem pathItem2 = (PathItem) getStatement.getInClause().getPathItems().get(1);
		assertThat(pathItem2.getDeep()).isEqualTo("3");
		assertThat(((Constant)pathItem2.getPath()).getValue()).isEqualTo("/home/otherUser");
	}

	@Test
	public void should_parse_multi_queries() throws Exception {
		// GIVEN
		JFSQLParser jfsqlParser = new JFSQLParser();
		
		// WHEN
		Vector<Statement> statements = jfsqlParser.parse("get file in ('/home/ogama');get name in ('/home/otherUser');");

		// THEN
		assertThat(statements).hasSize(2);
	}
	
	@Test
	public void should_parse_having_query() throws Exception {
		// GIVEN
		JFSQLParser jfsqlParser = new JFSQLParser();

		// WHEN
		Vector<Statement> statements = jfsqlParser.parse("get file in ('/home/ogama') having name = 'test' and size between 1 and 3;");

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
		Vector<Statement> statements = jfsqlParser.parse(
				"get file "
				+ "in ('/home/ogama') "
				+ "having name match '[test|Test]' "
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
		Vector<Statement> statements = jfsqlParser.parse("get file in (get 2 file in ('/' deep 1) having size > 0 and type = 'directory' deep 2);");

		// THEN
		assertThat(statements).hasSize(1);
		assertThat(((GetStatement)statements.get(0)).getInClause().getPathItems()).hasSize(1);
		PathItem pathItem = (PathItem) ((GetStatement)statements.get(0)).getInClause().getPathItems().get(0);
		assertThat(pathItem.getPath()).isExactlyInstanceOf(GetStatement.class);
		assertThat(pathItem.getDeep()).isEqualTo("2");
	}
	
	@Test
	public void should_parse_sort_by_query() throws Exception {
		// GIVEN
		JFSQLParser jfsqlParser = new JFSQLParser();

		// WHEN
		Vector<Statement> statements = jfsqlParser.parse("get name in ('/home/ogama') sort by name ascending;");
	
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
		Vector<Statement> statements = jfsqlParser.parse("get file in ('/home/ogama') having name in (get 1 name in ('/home/ogama'));");

		// THEN
		assertThat(statements).hasSize(1);	
	}
	
	@Test
	public void should_parse_equals_sub_query() throws Exception {
		// GIVEN
		JFSQLParser jfsqlParser = new JFSQLParser();

		// WHEN
		Vector<Statement> statements = jfsqlParser.parse("get file in ('/home/ogama') having name = get 1 name in ('/home/ogama');");

		// THEN
		assertThat(statements).hasSize(1);	
	}
	
	@Test
	public void should_parse_like_sub_query() throws Exception {
		// GIVEN
		JFSQLParser jfsqlParser = new JFSQLParser();

		// WHEN
		Vector<Statement> statements = jfsqlParser.parse("get file in ('/home/ogama') having name like get 1 name in ('/home/ogama');");

		// THEN
		assertThat(statements).hasSize(1);	
	}
	
	@Test
	public void should_parse_between_sub_query() throws Exception {
		JFSQLParser jfsqlParser = new JFSQLParser();

		// WHEN
		Vector<Statement> statements = jfsqlParser.parse("get file in ('/home/ogama') having size between 10 and get 1 size in ('/home/ogama');");

		// THEN
		assertThat(statements).hasSize(1);	
	}
	
	@Test
	public void should_parse_match_sub_query() throws Exception {
		// GIVEN
		JFSQLParser jfsqlParser = new JFSQLParser();

		// WHEN
		Vector<Statement> statements = jfsqlParser.parse("get file in ('/home/ogama') having name match get 1 content in ('/home/ogama');");

		// THEN
		assertThat(statements).hasSize(1);	
	}
	
	@Test
	@Ignore
	public void should_parse_get_function() throws Exception {
		// GIVEN
		JFSQLParser jfsqlParser = new JFSQLParser();

		// WHEN
		Vector<Statement> countStatements = jfsqlParser.parse("get count(file) in ('/home/ogama');");

		// THEN
		assertThat(countStatements).hasSize(1);
		assertThat(countStatements.get(0)).isExactlyInstanceOf(GetStatement.class);
		GetStatement getStatement = (GetStatement) countStatements.get(0);
		assertThat(getStatement.getGetClause().getProperty()).isEqualTo("count(file)");
	}
}
