package fr.ogama.jfsql.query.clause.having.logical;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import fr.ogama.jfsql.query.Query;
import fr.ogama.jfsql.query.QueryFactory;
import fr.ogama.jfsql.query.clause.having.AbstractHavingTest;

public class HavingLogicalComparatorTest extends AbstractHavingTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		setUp();
	}

	@Test
	public void should_validate_and() throws Exception {
		// GIVEN
		String query = "get file in ('" + directory
				+ "') having name like 'date' and name like '.txt'";
		Query fileQuery = QueryFactory.newQuery(query);

		// WHEN
		List<Comparable> results = fileQuery.execute();

		// THEN
		assertThat(results).isNotNull().hasSize(3)
				.hasOnlyElementsOfType(File.class);

	}

	@Test
	public void should_validate_or() throws Exception {
		// GIVEN
		String query = "get file in ('" + directory
				+ "') having name like 'date' or name like '.txt'";
		Query fileQuery = QueryFactory.newQuery(query);

		// WHEN
		List<Comparable> results = fileQuery.execute();

		// THEN
		assertThat(results).isNotNull().hasSize(7)
				.hasOnlyElementsOfType(File.class);
	}

	@Test
	public void should_validate_not() throws Exception {
		// GIVEN
		String query = "get file in ('" + directory
				+ "') having name not like 'date'";
		Query fileQuery = QueryFactory.newQuery(query);

		// WHEN
		List<Comparable> results = fileQuery.execute();

		// THEN
		assertThat(results).isNotNull().hasSize(4)
				.hasOnlyElementsOfType(File.class);
	}

	@Test
	public void should_validate_parenthesis() throws Exception {
		// GIVEN
		String query = "get file in ('"
				+ directory
				+ "') having size > 0 and (content not like 'Content' or name like 'size')";
		Query fileQuery = QueryFactory.newQuery(query);

		// WHEN
		List<Comparable> results = fileQuery.execute();

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
		Query fileQuery = QueryFactory.newQuery(query);

		// WHEN
		List<Comparable> results = fileQuery.execute();

		// THEN
	}
}
