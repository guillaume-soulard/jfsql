package fr.ogama.utils.parser;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.*;

import java.util.Vector;

import org.junit.Test;

import fr.ogama.utils.parser.model.Statement;
import fr.ogama.utils.parser.model.get.GetStatement;
import fr.ogama.utils.parser.model.get.PathItem;

public class TestParser {

	@Test
	public void should_parse_complete_simple_query() throws Exception {
		JFSQLParser jfsqlParser = new JFSQLParser();
		Vector<Statement> statements = jfsqlParser.parse("get 1 distinct name in ('/home/ogama', '/home/otherUser' deep 3);");
	
		assertThat(statements).hasSize(1);
		assertThat(statements.get(0)).isExactlyInstanceOf(GetStatement.class);
		GetStatement getStatement = (GetStatement) statements.get(0);
		
		assertThat(getStatement.getGetClause().getLimit()).isEqualTo("1");
		assertThat(getStatement.getGetClause().getProperty()).isEqualTo("name");
		assertThat(getStatement.getGetClause().isDistinct()).isTrue();
		
		assertThat(getStatement.getInClause().getPathItems()).hasSize(2);
		
		PathItem pathItem1 = (PathItem) getStatement.getInClause().getPathItems().get(0);
		assertThat(pathItem1.getDeep()).isNull();
		assertThat(pathItem1.getPath()).isEqualTo("/home/ogama");
		
		PathItem pathItem2 = (PathItem) getStatement.getInClause().getPathItems().get(1);
		assertThat(pathItem2.getDeep()).isEqualTo("3");
		assertThat(pathItem2.getPath()).isEqualTo("/home/otherUser");
	}

	@Test
	public void should_parse_multi_queries() throws Exception {
		JFSQLParser jfsqlParser = new JFSQLParser();
		Vector<Statement> statements = jfsqlParser.parse("get file in ('/home/ogama');get name in ('/home/otherUser');");
	
		assertThat(statements).hasSize(2);
	}
	
	@Test
	public void should_parse_having_query() throws Exception {
		JFSQLParser jfsqlParser = new JFSQLParser();
		Vector<Statement> statements = jfsqlParser.parse("get file in ('/home/ogama') having name = 'test' and size between 1 and 3;");
	
		assertThat(statements).hasSize(1);
		
		GetStatement getStatement = (GetStatement) statements.get(0);		
		assertThat(getStatement.getHavingClause()).isNotNull();
	}
	
	@Test
	public void should_parse_complex_having_query() throws Exception {
		JFSQLParser jfsqlParser = new JFSQLParser();
		Vector<Statement> statements = jfsqlParser.parse(
				"get file "
				+ "in ('/home/ogama') "
				+ "having name match '[test|Test]' "
					+ "and size between 1 and 3 "
					+ "or (parent not in ('parent 1', 'parent 2')"
					+ "and path like 'home');");
	
		assertThat(statements).hasSize(1);
		
		GetStatement getStatement = (GetStatement) statements.get(0);		
		assertThat(getStatement.getHavingClause()).isNotNull();
	}
	
	@Test
	public void should_parse_sort_by_query() throws Exception {
		JFSQLParser jfsqlParser = new JFSQLParser();
		Vector<Statement> statements = jfsqlParser.parse("get name in ('/home/ogama') sort by name ascending;");
	
		assertThat(statements).hasSize(1);
				
		GetStatement getStatement = (GetStatement) statements.get(0);		
		assertThat(getStatement.getSortByClause()).isNotNull();
	}
	
	@Test
	public void should_parse_in_sub_query() throws Exception {
		JFSQLParser jfsqlParser = new JFSQLParser();
		Vector<Statement> statements = jfsqlParser.parse("get file in ('/home/ogama') having name in (get 1 name in ('/home/ogama'));");
	
		assertThat(statements).hasSize(1);	
	}
	
	@Test
	public void should_parse_equals_sub_query() throws Exception {
		JFSQLParser jfsqlParser = new JFSQLParser();
		Vector<Statement> statements = jfsqlParser.parse("get file in ('/home/ogama') having name = get 1 name in ('/home/ogama');");
	
		assertThat(statements).hasSize(1);	
	}
	
	@Test
	public void should_parse_like_sub_query() throws Exception {
		JFSQLParser jfsqlParser = new JFSQLParser();
		Vector<Statement> statements = jfsqlParser.parse("get file in ('/home/ogama') having name like get 1 name in ('/home/ogama');");
	
		assertThat(statements).hasSize(1);	
	}
	
	@Test
	public void should_parse_between_sub_query() throws Exception {
		JFSQLParser jfsqlParser = new JFSQLParser();
		Vector<Statement> statements = jfsqlParser.parse("get file in ('/home/ogama') having size between 10 and get 1 size in ('/home/ogama');");
	
		assertThat(statements).hasSize(1);	
	}
	
	@Test
	public void should_parse_match_sub_query() throws Exception {
		JFSQLParser jfsqlParser = new JFSQLParser();
		Vector<Statement> statements = jfsqlParser.parse("get file in ('/home/ogama') having name match get 1 content in ('/home/ogama');");
	
		assertThat(statements).hasSize(1);	
	}
}
