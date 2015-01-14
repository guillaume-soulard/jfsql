package fr.ogama.jfsql.query.clause.having.comparators;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.ogama.jfsql.query.Query;
import fr.ogama.jfsql.query.QueryFactory;
import fr.ogama.jfsql.query.clause.having.AbstractHavingTest;

public class HavingStringComparatorsTest extends AbstractHavingTest {

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
		String string = "get file in ('" + directory
				+ "') having name = 'Regular file.txt';";
		Query query = QueryFactory.newQuery(string);

		// WHEN
		List<Comparable> results = query.execute();

		// THEN
		assertThat(results).hasOnlyElementsOfType(File.class);
		assertThat(results).containsOnly(regularFile);

	}

	@Test
	public void should_validate_unequal() throws Exception {
		// GIVEN
		String string = "get file in ('" + directory
				+ "') having name <> 'Regular file.txt' and type = 'file';";
		Query query = QueryFactory.newQuery(string);

		// WHEN
		List<Comparable> results = query.execute();

		// THEN
		assertThat(results).hasOnlyElementsOfType(File.class);
		assertThat(results).containsOnly(bigSizeFile, contentFile,
				creationDateFile, lastAccessFile, lastUpdateFile, nameFile);

	}

	@Test
	public void should_validate_like() throws Exception {
		// GIVEN
		String string = "get file in ('" + directory
				+ "') having name like 'search';";
		Query query = QueryFactory.newQuery(string);

		// WHEN
		List<Comparable> results = query.execute();

		// THEN
		assertThat(results).hasOnlyElementsOfType(File.class);
		assertThat(results).containsOnly(contentFile, nameFile);

	}

	@Test
	public void should_validate_match() throws Exception {
		// GIVEN
		String string = "get file in ('" + directory
				+ "') having name match '^.*[0-9]{2}-[0-9]{2}-[0-9]{4}.*$';";
		Query query = QueryFactory.newQuery(string);

		// WHEN
		List<Comparable> results = query.execute();

		// THEN
		assertThat(results).hasOnlyElementsOfType(File.class);
		assertThat(results).containsOnly(creationDateFile, lastAccessFile,
				lastUpdateFile);
	}

	@Test
	public void should_validate_in() throws Exception {
		// GIVEN
		String string = "get file in ('"
				+ directory
				+ "') having name in ('Regular file.txt', 'File with big size.txt');";
		Query query = QueryFactory.newQuery(string);

		// WHEN
		List<Comparable> results = query.execute();

		// THEN
		assertThat(results).hasOnlyElementsOfType(File.class);
		assertThat(results).containsOnly(regularFile, bigSizeFile);

	}

	@Test(expected = Exception.class)
	public void should_unvalidate_greather_than() throws Exception {
		// GIVEN
		String string = "get file in ('" + directory
				+ "') having name > 'Regular file.txt';";
		Query query = QueryFactory.newQuery(string);

		// WHEN
		List<Comparable> results = query.execute();

		// THEN
		// Exception must be throw

	}

	@Test(expected = Exception.class)
	public void should_unvalidate_greather_than_or_equal() throws Exception {
		// GIVEN
		String string = "get file in ('" + directory
				+ "') having name >= 'Regular file.txt';";
		Query query = QueryFactory.newQuery(string);

		// WHEN
		List<Comparable> results = query.execute();

		// THEN
		// Exception must be throw
	}

	@Test(expected = Exception.class)
	public void should_unvalidate_less_than() throws Exception {
		// GIVEN
		String string = "get file in ('" + directory
				+ "') having name < 'Regular file.txt';";
		Query query = QueryFactory.newQuery(string);

		// WHEN
		List<Comparable> results = query.execute();

		// THEN
		// Exception must be throw
	}

	@Test(expected = Exception.class)
	public void should_unvalidate_less_than_or_equal() throws Exception {
		// GIVEN
		String string = "get file in ('" + directory
				+ "') having name <= 'Regular file.txt';";
		Query query = QueryFactory.newQuery(string);

		// WHEN
		List<Comparable> results = query.execute();

		// THEN
		// Exception must be throw
	}

	@Test(expected = Exception.class)
	public void should_unvalidate_between() throws Exception {
		// GIVEN
		String string = "get file in ('" + directory
				+ "') having name between 'Regular file.txt' and 'File with big size.txt';";
		Query query = QueryFactory.newQuery(string);

		// WHEN
		List<Comparable> results = query.execute();

		// THEN
		// Exception must be throw
	}
}
