package fr.ogama.jfsql.query.clause.having.comparators;

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

public class HavingSubQueryComparatorsTest extends AbstractHavingTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		setUp();
	}
	
	@AfterClass 
	public static void tearDownAfterClass() {
		tearDown();
	}

	@Test
	public void should_execute_sub_query() throws Exception {
		// GIVEN
		String query = "get file in ('" + directory
				+ "') having name in (get name in ('" + directory
				+ "') having name like 'with');";
		Statement fileQuery = JFSQL.parseOneStatement(query);

		// WHEN
		List<Comparable> results = fileQuery.execute(new HashMap<String, Comparable>());

		// THEN
		assertThat(results).isNotNull().hasSize(6);
	}

	@Test
	public void should_execute_sub_sub_query() throws Exception {
		// GIVEN
		String query = "get file in ('" + directory
				+ "') having type = 'file' and name in (get name in ('"
				+ directory + "') having name like get 1 name in ('"
				+ directory + "') having size > 0);";
		Statement fileQuery = JFSQL.parseOneStatement(query);

		// WHEN
		List<Comparable> results = fileQuery.execute(new HashMap<String, Comparable>());

		// THEN
		assertThat(results).isNotNull().hasSize(1);
	}

	@Test
	public void should_execute_sub_query_size() throws Exception {
		// GIVEN
		String query = "get file in ('/' deep 1) having type = 'file' and size = get 1 size in ('/' deep 1) sort by size descending;";
		Statement fileQuery = JFSQL.parseOneStatement(query);

		// WHEN
		List<Comparable> results = fileQuery.execute(new HashMap<String, Comparable>());

		// THEN
		assertThat(results).isNotNull();
	}
	
	@Test
	public void should_execute_confuse_sub_query() throws Exception {
		// GIVEN
		String bigFileQueryString = "get 1 file in ('/' deep 1) having type = 'file' sort by size descending;";
		Statement bigFileQuery = JFSQL.parseOneStatement(bigFileQueryString);
		
		List<Comparable> bigFile = bigFileQuery.execute(new HashMap<String, Comparable>());
		assertThat(bigFile).hasSize(1).hasOnlyElementsOfType(File.class);
		
		// This query should return the biggest file in root
		String query = "get file in ('/' deep 1) having type = 'file' and size = get 1 size in ('/' deep 1) having type = 'file' sort by size descending;";
		Statement fileQuery = JFSQL.parseOneStatement(query);

		// WHEN
		List<Comparable> results = fileQuery.execute(new HashMap<String, Comparable>());

		// THEN
		assertThat(results).containsExactly(bigFile.get(0));

	}
}
