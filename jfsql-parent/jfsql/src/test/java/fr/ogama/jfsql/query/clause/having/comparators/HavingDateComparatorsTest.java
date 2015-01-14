package fr.ogama.jfsql.query.clause.having.comparators;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import fr.ogama.jfsql.query.Query;
import fr.ogama.jfsql.query.QueryFactory;
import fr.ogama.jfsql.query.clause.having.AbstractHavingTest;

public class HavingDateComparatorsTest extends AbstractHavingTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		setUp();
	}	
	
	@AfterClass 
	public static void tearDownAfterClass() {
		tearDown();
	}

	@Test
	public void should_validate_equal() throws Exception {
		// GIVEN
		String dateQueryString = "get 1 creation_date in ('" + directory
				+ "') sort by creation_date ascending;";
		Query dateQuery = QueryFactory.newQuery(dateQueryString);
		List<Comparable> dateResuls = dateQuery.execute();

		assertThat(dateResuls).hasSize(1).hasOnlyElementsOfType(String.class);

		String queryString = "get file in ('" + directory
				+ "') having creation_date = '" + dateResuls.get(0) + "';";
		Query query = QueryFactory.newQuery(queryString);

		// WHEN
		List<Comparable> results = query.execute();

		// THEN
		assertThat(results).hasOnlyElementsOfType(File.class);
		assertThat(results).isNotNull();
	}

	@Test
	public void should_validate_unequal() throws Exception {
		// GIVEN
		String dateQueryString = "get 1 creation_date in ('" + directory
				+ "') sort by creation_date ascending;";
		Query dateQuery = QueryFactory.newQuery(dateQueryString);
		List<Comparable> dateResuls = dateQuery.execute();

		assertThat(dateResuls).hasSize(1).hasOnlyElementsOfType(String.class);

		String queryString = "get file in ('" + directory
				+ "') having creation_date <> '" + dateResuls.get(0) + "';";
		Query query = QueryFactory.newQuery(queryString);

		// WHEN
		List<Comparable> results = query.execute();

		// THEN
		assertThat(results).hasOnlyElementsOfType(File.class);
		assertThat(results).isNotNull();
	}

	@Test
	public void should_validate_in() throws Exception {
		// GIVEN
		String queryString = "get file in ('"
				+ directory
				+ "') having creation_date in (get 2 distinct creation_date in ('"
				+ directory + "') sort by creation_date descending);";
		Query query = QueryFactory.newQuery(queryString);

		// WHEN
		List<Comparable> results = query.execute();

		// THEN
		assertThat(results).hasOnlyElementsOfType(File.class);
		assertThat(results).isNotNull();
	}

	@Test
	public void should_validate_greather_than() throws Exception {
		// GIVEN
		String dateQueryString = "get 1 creation_date in ('" + directory
				+ "') sort by creation_date ascending;";
		Query dateQuery = QueryFactory.newQuery(dateQueryString);
		List<Comparable> dateResuls = dateQuery.execute();

		assertThat(dateResuls).hasSize(1).hasOnlyElementsOfType(String.class);

		String queryString = "get file in ('" + directory
				+ "') having creation_date > '" + dateResuls.get(0) + "';";
		Query query = QueryFactory.newQuery(queryString);

		// WHEN
		List<Comparable> results = query.execute();

		// THEN
		assertThat(results).hasOnlyElementsOfType(File.class);
		assertThat(results).isNotNull();
	}

	@Test
	public void should_validate_greather_than_or_equal() throws Exception {
		// GIVEN
		String dateQueryString = "get 1 creation_date in ('" + directory
				+ "') sort by creation_date ascending;";
		Query dateQuery = QueryFactory.newQuery(dateQueryString);
		List<Comparable> dateResuls = dateQuery.execute();

		assertThat(dateResuls).hasSize(1).hasOnlyElementsOfType(String.class);

		String queryString = "get file in ('" + directory
				+ "') having creation_date >= '" + dateResuls.get(0) + "';";
		Query query = QueryFactory.newQuery(queryString);

		// WHEN
		List<Comparable> results = query.execute();

		// THEN
		assertThat(results).hasOnlyElementsOfType(File.class);
		assertThat(results).isNotNull();
	}

	@Test
	public void should_validate_less_than() throws Exception {
		// GIVEN
		String dateQueryString = "get 1 creation_date in ('" + directory
				+ "') sort by creation_date descending;";
		Query dateQuery = QueryFactory.newQuery(dateQueryString);
		List<Comparable> dateResuls = dateQuery.execute();

		assertThat(dateResuls).hasSize(1).hasOnlyElementsOfType(String.class);

		String queryString = "get file in ('" + directory
				+ "') having creation_date < '" + dateResuls.get(0) + "';";
		Query query = QueryFactory.newQuery(queryString);

		// WHEN
		List<Comparable> results = query.execute();

		// THEN
		assertThat(results).hasOnlyElementsOfType(File.class);
		assertThat(results).isNotNull();
	}

	@Test
	public void should_validate_less_than_or_equal() throws Exception {
		// GIVEN
		String dateQueryString = "get 1 creation_date in ('" + directory
				+ "') sort by creation_date ascending;";
		Query dateQuery = QueryFactory.newQuery(dateQueryString);
		List<Comparable> dateResuls = dateQuery.execute();

		assertThat(dateResuls).hasSize(1).hasOnlyElementsOfType(String.class);

		String queryString = "get file in ('" + directory
				+ "') having creation_date <= '" + dateResuls.get(0) + "';";
		Query query = QueryFactory.newQuery(queryString);

		// WHEN
		List<Comparable> results = query.execute();

		// THEN
		assertThat(results).hasOnlyElementsOfType(File.class);
		assertThat(results).isNotNull();
	}

	@Test
	public void should_validate_between() throws Exception {
		// GIVEN
		String dateQueryString = "get 2 distinct creation_date in ('"
				+ directory + "') sort by creation_date ascending;";
		Query dateQuery = QueryFactory.newQuery(dateQueryString);
		List<Comparable> dateResuls = dateQuery.execute();

		assertThat(dateResuls).hasOnlyElementsOfType(String.class);

		String queryString = "get file in ('" + directory
				+ "') having creation_date between '" + dateResuls.get(0)
				+ "' and '" + dateResuls.get(1) + "';";
		Query query = QueryFactory.newQuery(queryString);

		// WHEN
		List<Comparable> results = query.execute();

		// THEN
		assertThat(results).hasOnlyElementsOfType(File.class);
		assertThat(results).isNotNull();
	}

	@Test(expected = Exception.class)
	public void should_unvalidate_like() throws Exception {
		// GIVEN
		String queryString = "get file in ('" + directory
				+ "') having creation_date like '2015-01-01 12-02-33';";
		Query query = QueryFactory.newQuery(queryString);

		// WHEN
		List<Comparable> results = query.execute();

		// THEN
		// Exception must be throw
	}

	@Test(expected = Exception.class)
	public void should_unvalidate_match() throws Exception {
		// GIVEN
		String queryString = "get file in ('" + directory
				+ "') having creation_date match '.*';";
		Query query = QueryFactory.newQuery(queryString);

		// WHEN
		List<Comparable> results = query.execute();

		// THEN
		// Exception must be throw
	}
}
