package fr.ogama.jfsql.query.clause.having.comparators;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import fr.ogama.jfsql.JFSQL;
import fr.ogama.jfsql.query.clause.having.AbstractHavingTest;
import fr.ogama.utils.parser.model.Statement;
import fr.ogama.utils.parser.model.get.FilePropertiesUtils;

public class HavingIntegerComparatorsTest extends AbstractHavingTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		setUp();
	}

	@Test
	public void should_validate_equal() throws Exception {
		// GIVEN
		String sizeQueryString = "get 1 file in ('" + directory
				+ "') sort by size descending;";
		Statement sizeQuery = JFSQL.parseOneStatement(sizeQueryString);
		List<Comparable> sizeResult = sizeQuery.execute(new HashMap<String, Comparable>());
		assertThat(sizeResult).hasSize(1).hasOnlyElementsOfType(File.class);

		String string = "get file in ('" + directory + "') having size = "
				+ FilePropertiesUtils.getSize((File) sizeResult.get(0)) + ";";
		Statement query = JFSQL.parseOneStatement(string);

		// WHEN
		List<Comparable> results = query.execute(new HashMap<String, Comparable>());

		// THEN
		assertThat(results).hasOnlyElementsOfType(File.class);
		assertThat(results).containsOnly(sizeResult.get(0));
	}

	@Test
	public void should_validate_unequal() throws Exception {
		// GIVEN
		String sizeQueryString = "get 1 file in ('" + directory
				+ "') sort by size ascending;";
		Statement sizeQuery = JFSQL.parseOneStatement(sizeQueryString);
		List<Comparable> sizeResult = sizeQuery.execute(new HashMap<String, Comparable>());
		assertThat(sizeResult).hasSize(1).hasOnlyElementsOfType(File.class);

		String string = "get file in ('" + directory + "') having size <> "
				+ FilePropertiesUtils.getSize((File) sizeResult.get(0)) + ";";
		Statement query = JFSQL.parseOneStatement(string);

		// WHEN
		List<Comparable> results = query.execute(new HashMap<String, Comparable>());

		// THEN
		assertThat(results).hasOnlyElementsOfType(File.class);
		assertThat(results).doesNotContain(sizeResult.get(0));
	}

	@Test
	public void should_validate_in() throws Exception {
		// GIVEN
		String sizeQueryString = "get 2 file in ('" + directory
				+ "') having type = 'file' sort by size descending;";
		Statement sizeQuery = JFSQL.parseOneStatement(sizeQueryString);
		List<Comparable> sizeResult = sizeQuery.execute(new HashMap<String, Comparable>());
		assertThat(sizeResult).hasSize(2).hasOnlyElementsOfType(File.class);

		String string = "get file in ('" + directory + "') having size in ("
				+ FilePropertiesUtils.getSize((File) sizeResult.get(0)) + ", "
				+ FilePropertiesUtils.getSize((File) sizeResult.get(1)) + ");";
		Statement query = JFSQL.parseOneStatement(string);

		// WHEN
		List<Comparable> results = query.execute(new HashMap<String, Comparable>());

		// THEN
		assertThat(results).hasOnlyElementsOfType(File.class);
		assertThat(results).containsOnly(sizeResult.get(0), sizeResult.get(1));
	}

	@Test
	public void should_validate_greather_than() throws Exception {
		// GIVEN
		String sizeQueryString = "get 2 file in ('" + directory
				+ "') sort by size descending;";
		Statement sizeQuery = JFSQL.parseOneStatement(sizeQueryString);
		List<Comparable> sizeResult = sizeQuery.execute(new HashMap<String, Comparable>());
		assertThat(sizeResult).hasSize(2).hasOnlyElementsOfType(File.class);

		String string = "get file in ('" + directory
				+ "') having type = 'file' and size > "
				+ FilePropertiesUtils.getSize((File) sizeResult.get(1)) + ";";
		Statement query = JFSQL.parseOneStatement(string);

		// WHEN
		List<Comparable> results = query.execute(new HashMap<String, Comparable>());

		// THEN
		assertThat(results).hasOnlyElementsOfType(File.class);
		assertThat(results).containsOnly(sizeResult.get(0));
	}

	@Test
	public void should_validate_greather_than_or_equal() throws Exception {
		// GIVEN
		String sizeQueryString = "get 2 file in ('" + directory
				+ "') having type = 'file' sort by size descending;";
		Statement sizeQuery = JFSQL.parseOneStatement(sizeQueryString);
		List<Comparable> sizeResult = sizeQuery.execute(new HashMap<String, Comparable>());
		assertThat(sizeResult).hasSize(2).hasOnlyElementsOfType(File.class);

		String string = "get file in ('" + directory
				+ "') having type = 'file' and size >= "
				+ FilePropertiesUtils.getSize((File) sizeResult.get(1)) + ";";
		Statement query = JFSQL.parseOneStatement(string);

		// WHEN
		List<Comparable> results = query.execute(new HashMap<String, Comparable>());

		// THEN
		assertThat(results).hasOnlyElementsOfType(File.class);
		assertThat(results).containsOnly(sizeResult.get(0), sizeResult.get(1));
	}

	@Test
	public void should_validate_less_than() throws Exception {
		// GIVEN
		String sizeQueryString = "get 2 file in ('" + directory
				+ "') sort by size ascending;";
		Statement sizeQuery = JFSQL.parseOneStatement(sizeQueryString);
		List<Comparable> sizeResult = sizeQuery.execute(new HashMap<String, Comparable>());
		assertThat(sizeResult).hasSize(2).hasOnlyElementsOfType(File.class);

		String string = "get file in ('" + directory
				+ "') having type = 'file' and size < "
				+ FilePropertiesUtils.getSize((File) sizeResult.get(0)) + ";";
		Statement query = JFSQL.parseOneStatement(string);

		// WHEN
		List<Comparable> results = query.execute(new HashMap<String, Comparable>());

		// THEN
		assertThat(results).hasOnlyElementsOfType(File.class);
		assertThat(results).doesNotContain(sizeResult.get(0));
	}

	@Test
	public void should_validate_less_than_or_equal() throws Exception {
		// GIVEN
		String sizeQueryString = "get 2 file in ('" + directory
				+ "') having type = 'file' sort by size descending;";
		Statement sizeQuery = JFSQL.parseOneStatement(sizeQueryString);
		List<Comparable> sizeResult = sizeQuery.execute(new HashMap<String, Comparable>());
		assertThat(sizeResult).hasSize(2).hasOnlyElementsOfType(File.class);

		String string = "get file in ('" + directory
				+ "') having type = 'file' and size <= "
				+ FilePropertiesUtils.getSize((File) sizeResult.get(1)) + ";";
		Statement query = JFSQL.parseOneStatement(string);

		// WHEN
		List<Comparable> results = query.execute(new HashMap<String, Comparable>());

		// THEN
		assertThat(results).hasOnlyElementsOfType(File.class);
		assertThat(results).doesNotContain(sizeResult.get(0));
	}

	@Test
	public void should_validate_between() throws Exception {
		// GIVEN
		String sizeQueryString = "get 1 file in ('" + directory
				+ "') having type = 'file' sort by size descending;";
		Statement sizeQuery = JFSQL.parseOneStatement(sizeQueryString);
		List<Comparable> sizeResult = sizeQuery.execute(new HashMap<String, Comparable>());
		assertThat(sizeResult).hasSize(1).hasOnlyElementsOfType(File.class);

		String string = "get file in ('" + directory
				+ "') having type = 'file' and size between 0 and "
				+ FilePropertiesUtils.getSize((File) sizeResult.get(0)) + ";";
		Statement query = JFSQL.parseOneStatement(string);

		// WHEN
		List<Comparable> results = query.execute(new HashMap<String, Comparable>());

		// THEN
		assertThat(results).hasOnlyElementsOfType(File.class);
		assertThat(results).containsOnly(bigSizeFile, contentFile,
				creationDateFile, lastAccessFile, lastUpdateFile, nameFile,
				regularFile);
	}

	@Test(expected = Exception.class)
	public void should_unvalidate_like() throws Exception {
		// GIVEN
		String string = "get file in ('" + directory + "') having size like 0;";
		Statement query = JFSQL.parseOneStatement(string);

		// WHEN
		List<Comparable> results = query.execute(new HashMap<String, Comparable>());

		// THEN
		// Exception mst be throw
	}

	@Test(expected = Exception.class)
	public void should_invalidate_match() throws Exception {
		// GIVEN
		String string = "get file in ('" + directory
				+ "') having size match '.*';";
		Statement query = JFSQL.parseOneStatement(string);

		// WHEN
		List<Comparable> results = query.execute(new HashMap<String, Comparable>());

		// THEN
		// Exception mst be throw

	}
}
