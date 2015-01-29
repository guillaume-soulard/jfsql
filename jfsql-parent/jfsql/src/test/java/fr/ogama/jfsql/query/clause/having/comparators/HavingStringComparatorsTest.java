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
		Statement query = JFSQL.parseOneStatement(string);

		// WHEN
		List<Comparable> results = query.execute(new HashMap<String, Comparable>());

		// THEN
		assertThat(results).hasOnlyElementsOfType(File.class);
		assertThat(results).containsOnly(regularFile);

	}

	@Test
	public void should_validate_unequal() throws Exception {
		// GIVEN
		String string = "get file in ('" + directory
				+ "') having name <> 'Regular file.txt' and type = 'file';";
		Statement query = JFSQL.parseOneStatement(string);

		// WHEN
		List<Comparable> results = query.execute(new HashMap<String, Comparable>());

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
		Statement query = JFSQL.parseOneStatement(string);

		// WHEN
		List<Comparable> results = query.execute(new HashMap<String, Comparable>());

		// THEN
		assertThat(results).hasOnlyElementsOfType(File.class);
		assertThat(results).containsOnly(contentFile, nameFile);

	}

	@Test
	public void should_validate_match() throws Exception {
		// GIVEN
		String string = "get file in ('" + directory
				+ "') having name match '^.*[0-9]{2}-[0-9]{2}-[0-9]{4}.*$';";
		Statement query = JFSQL.parseOneStatement(string);

		// WHEN
		List<Comparable> results = query.execute(new HashMap<String, Comparable>());

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
		Statement query = JFSQL.parseOneStatement(string);

		// WHEN
		List<Comparable> results = query.execute(new HashMap<String, Comparable>());

		// THEN
		assertThat(results).hasOnlyElementsOfType(File.class);
		assertThat(results).containsOnly(regularFile, bigSizeFile);

	}

	@Test
	public void should_unvalidate_greather_than() throws Exception {
		// GIVEN
		String string = "get file in ('" + directory
				+ "') having name > 'Regular file.txt';";
		Statement query = JFSQL.parseOneStatement(string);

		// WHEN
		List<Comparable> results = query.execute(new HashMap<String, Comparable>());

		// THEN
		assertThat(results).hasSize(0);

	}

	@Test
	public void should_unvalidate_greather_than_or_equal() throws Exception {
		// GIVEN
		String string = "get file in ('" + directory
				+ "') having name >= 'Regular file.txt';";
		Statement query = JFSQL.parseOneStatement(string);

		// WHEN
		List<Comparable> results = query.execute(new HashMap<String, Comparable>());

		// THEN
		assertThat(results).hasSize(1);
	}

	@Test
	public void should_validate_less_than() throws Exception {
		// GIVEN
		String string = "get file in ('" + directory
				+ "') having name < 'Regular file.txt';";
		Statement query = JFSQL.parseOneStatement(string);

		// WHEN
		List<Comparable> results = query.execute(new HashMap<String, Comparable>());

		// THEN
		assertThat(results).hasSize(8);
	}

	@Test
	public void should_unvalidate_less_than_or_equal() throws Exception {
		// GIVEN
		String string = "get file in ('" + directory
				+ "') having name <= 'Regular file.txt';";
		Statement query = JFSQL.parseOneStatement(string);

		// WHEN
		List<Comparable> results = query.execute(new HashMap<String, Comparable>());

		// THEN
		assertThat(results).hasSize(9);
	}

	@Test
	public void should_unvalidate_between() throws Exception {
		// GIVEN
		String string = "get file in ('" + directory
				+ "') having name between 'File with last access date 01-03-2014.txt' and 'Regular file.txt';";
		Statement query = JFSQL.parseOneStatement(string);

		// WHEN
		List<Comparable> results = query.execute(new HashMap<String, Comparable>());

		// THEN
		assertThat(results).hasSize(6);
	}
}
