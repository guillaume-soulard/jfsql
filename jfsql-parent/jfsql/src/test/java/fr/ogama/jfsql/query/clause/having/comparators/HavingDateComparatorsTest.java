package fr.ogama.jfsql.query.clause.having.comparators;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.ogama.jfsql.JFSQL;
import fr.ogama.jfsql.query.clause.having.AbstractHavingTest;
import fr.ogama.utils.parser.model.Statement;

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
		Statement dateQuery = JFSQL.parseOneStatement(dateQueryString);
		List<Comparable> dateResuls = dateQuery.execute(new HashMap<String, Comparable>());

		assertThat(dateResuls).hasSize(1).hasOnlyElementsOfType(Date.class);

		String queryString = "get file in ('" + directory
				+ "') having creation_date = '" + dateResuls.get(0) + "';";
		Statement query = JFSQL.parseOneStatement(queryString);

		// WHEN
		List<Comparable> results = query.execute(new HashMap<String, Comparable>());

		// THEN
		assertThat(results).hasOnlyElementsOfType(File.class);
		assertThat(results).isNotNull();
	}

	@Test
	public void should_validate_unequal() throws Exception {
		// GIVEN
		String dateQueryString = "get 1 creation_date in ('" + directory
				+ "') sort by creation_date ascending;";
		Statement dateQuery = JFSQL.parseOneStatement(dateQueryString);
		List<Comparable> dateResuls = dateQuery.execute(new HashMap<String, Comparable>());

		assertThat(dateResuls).hasSize(1).hasOnlyElementsOfType(Date.class);

		String queryString = "get file in ('" + directory
				+ "') having creation_date <> '" + dateResuls.get(0) + "';";
		Statement query = JFSQL.parseOneStatement(queryString);

		// WHEN
		List<Comparable> results = query.execute(new HashMap<String, Comparable>());

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
		Statement query = JFSQL.parseOneStatement(queryString);

		// WHEN
		List<Comparable> results = query.execute(new HashMap<String, Comparable>());

		// THEN
		assertThat(results).hasOnlyElementsOfType(File.class);
		assertThat(results).isNotNull();
	}

	@Test
	public void should_validate_greather_than() throws Exception {
		// GIVEN
		String dateQueryString = "get 1 creation_date in ('" + directory
				+ "') sort by creation_date ascending;";
		Statement dateQuery = JFSQL.parseOneStatement(dateQueryString);
		List<Comparable> dateResuls = dateQuery.execute(new HashMap<String, Comparable>());

		assertThat(dateResuls).hasSize(1).hasOnlyElementsOfType(Date.class);

		String queryString = "get file in ('" + directory
				+ "') having creation_date > asDate('" + formatDate_yyyyMMdd((Date) dateResuls.get(0)) + "', 'yyyyMMdd');";
		Statement query = JFSQL.parseOneStatement(queryString);

		// WHEN
		List<Comparable> results = query.execute(new HashMap<String, Comparable>());

		// THEN
		assertThat(results).hasOnlyElementsOfType(File.class);
		assertThat(results).isNotNull();
	}

	@Test
	public void should_validate_greather_than_or_equal() throws Exception {
		// GIVEN
		String dateQueryString = "get 1 creation_date in ('" + directory
				+ "') sort by creation_date ascending;";
		Statement dateQuery = JFSQL.parseOneStatement(dateQueryString);
		List<Comparable> dateResuls = dateQuery.execute(new HashMap<String, Comparable>());

		assertThat(dateResuls).hasSize(1).hasOnlyElementsOfType(Date.class);

		String queryString = "get file in ('" + directory
				+ "') having creation_date >= asDate('" + formatDate_yyyyMMdd((Date) dateResuls.get(0)) + "', 'yyyyMMdd');";
		Statement query = JFSQL.parseOneStatement(queryString);

		// WHEN
		List<Comparable> results = query.execute(new HashMap<String, Comparable>());

		// THEN
		assertThat(results).hasOnlyElementsOfType(File.class);
		assertThat(results).isNotNull();
	}

	@Test
	public void should_validate_less_than() throws Exception {
		// GIVEN
		String dateQueryString = "get 1 creation_date in ('" + directory
				+ "') sort by creation_date descending;";
		Statement dateQuery = JFSQL.parseOneStatement(dateQueryString);
		List<Comparable> dateResuls = dateQuery.execute(new HashMap<String, Comparable>());

		assertThat(dateResuls).hasSize(1).hasOnlyElementsOfType(Date.class);

		String queryString = "get file in ('" + directory
				+ "') having creation_date < asDate('" + formatDate_yyyyMMdd((Date) dateResuls.get(0)) + "', 'yyyyMMdd');";
		Statement query = JFSQL.parseOneStatement(queryString);

		// WHEN
		List<Comparable> results = query.execute(new HashMap<String, Comparable>());

		// THEN
		assertThat(results).hasOnlyElementsOfType(File.class);
		assertThat(results).isNotNull();
	}

	@Test
	public void should_validate_less_than_or_equal() throws Exception {
		// GIVEN
		String dateQueryString = "get 1 creation_date in ('" + directory
				+ "') sort by creation_date ascending;";
		Statement dateQuery = JFSQL.parseOneStatement(dateQueryString);
		List<Comparable> dateResuls = dateQuery.execute(new HashMap<String, Comparable>());

		assertThat(dateResuls).hasSize(1).hasOnlyElementsOfType(Date.class);

		String queryString = "get file in ('" + directory
				+ "') having creation_date <= asDate('" + formatDate_yyyyMMdd((Date) dateResuls.get(0)) + "', 'yyyyMMdd');";
		Statement query = JFSQL.parseOneStatement(queryString);

		// WHEN
		List<Comparable> results = query.execute(new HashMap<String, Comparable>());

		// THEN
		assertThat(results).hasOnlyElementsOfType(File.class);
		assertThat(results).isNotNull();
	}

	@Test
	public void should_validate_between() throws Exception {
		// GIVEN
		String dateQueryString = "get 2 distinct creation_date in ('"
				+ directory + "') sort by creation_date ascending;";
		Statement dateQuery = JFSQL.parseOneStatement(dateQueryString);
		List<Comparable> dateResuls = dateQuery.execute(new HashMap<String, Comparable>());

		assertThat(dateResuls).hasOnlyElementsOfType(Date.class);

		String queryString = "get file in ('" + directory
				+ "') having creation_date between asDate('" + formatDate_yyyyMMdd((Date) dateResuls.get(0))
				+ "', 'yyyyMMdd') and asDate('" + formatDate_yyyyMMdd((Date) dateResuls.get(0)) + "', 'yyyyMMdd');";
		Statement query = JFSQL.parseOneStatement(queryString);

		// WHEN
		List<Comparable> results = query.execute(new HashMap<String, Comparable>());

		// THEN
		assertThat(results).hasOnlyElementsOfType(File.class);
		assertThat(results).isNotNull();
	}

	@Test(expected = Exception.class)
	public void should_unvalidate_like() throws Exception {
		// GIVEN
		String queryString = "get file in ('" + directory
				+ "') having creation_date like '2015-01-01 12-02-33';";
		Statement query = JFSQL.parseOneStatement(queryString);

		// WHEN
		List<Comparable> results = query.execute(new HashMap<String, Comparable>());

		// THEN
		// Exception must be throw
	}

	@Test(expected = Exception.class)
	public void should_unvalidate_match() throws Exception {
		// GIVEN
		String queryString = "get file in ('" + directory
				+ "') having creation_date match '.*';";
		Statement query = JFSQL.parseOneStatement(queryString);

		// WHEN
		List<Comparable> results = query.execute(new HashMap<String, Comparable>());

		// THEN
		// Exception must be throw
	}
	
	private String formatDate_yyyyMMdd(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		return dateFormat.format(date);
	}
}
