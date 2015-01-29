package fr.ogama.jfsql.query.clause.having.logical;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.ogama.jfsql.JFSQL;
import fr.ogama.jfsql.query.clause.having.AbstractHavingTest;
import fr.ogama.utils.parser.model.Statement;

public class HavingLogicalComparatorTest extends AbstractHavingTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		setUp();
	}
	
	@AfterClass 
	public static void tearDownAfterClass() {
		tearDown();
	}
	
	@Test
	public void should_validate_and() throws Exception {
		// GIVEN
		String query = "get file in ('" + directory
				+ "') having name like 'date' and name like '.txt' and type = 'file';";
		Statement fileQuery = JFSQL.parseOneStatement(query);

		// WHEN
		List<Comparable> results = fileQuery.execute(new HashMap<String, Comparable>());

		// THEN
		assertThat(results).isNotNull().hasSize(3)
				.hasOnlyElementsOfType(File.class);

	}

	@Test
	public void should_validate_or() throws Exception {
		// GIVEN
		String query = "get file in ('" + directory
				+ "') having name like 'date' or name like '.txt' and type = 'file';";
		Statement fileQuery = JFSQL.parseOneStatement(query);

		// WHEN
		List<Comparable> results = fileQuery.execute(new HashMap<String, Comparable>());

		// THEN
		assertThat(results).isNotNull().hasSize(7)
				.hasOnlyElementsOfType(File.class);
	}

	@Test
	public void should_validate_not() throws Exception {
		// GIVEN
		String query = "get file in ('" + directory
				+ "') having name not like 'date' and type = 'file';";
		Statement fileQuery = JFSQL.parseOneStatement(query);

		// WHEN
		List<Comparable> results = fileQuery.execute(new HashMap<String, Comparable>());

		// THEN
		assertThat(results).isNotNull().hasSize(4)
				.hasOnlyElementsOfType(File.class);
	}

	@Test
	public void should_validate_parenthesis() throws Exception {
		// GIVEN
		String query = "get file in ('"
				+ directory
				+ "') having size > 0 and type = 'file' and (content not like 'Content' or name like 'size');";
		Statement fileQuery = JFSQL.parseOneStatement(query);

		// WHEN
		List<Comparable> results = fileQuery.execute(new HashMap<String, Comparable>());

		// THEN
		assertThat(results).isNotNull().hasSize(1)
				.hasOnlyElementsOfType(File.class);
	}

	@Test(expected = Exception.class)
	public void should_get_error_because_syntaxe_error() throws Exception {
		// GIVEN
		String query = "get file in ('"
				+ directory
				+ "') having size > 0 ad (content not like 'Content'  name like 'content')";
		Statement fileQuery = JFSQL.parseOneStatement(query);

		// WHEN
		List<Comparable> results = fileQuery.execute(new HashMap<String, Comparable>());

		// THEN
	}
}
