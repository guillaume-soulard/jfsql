package fr.ogama.jfsql.query.clause.having.comparators;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import fr.ogama.jfsql.query.JFSQLUtils;
import fr.ogama.jfsql.query.Query;
import fr.ogama.jfsql.query.QueryFactory;
import fr.ogama.jfsql.query.clause.having.AbstractHavingTest;
import fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.properties.FilePropertyHelper;

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
		Query sizeQuery = QueryFactory.newQuery(sizeQueryString);
		List<Comparable> sizeResult = sizeQuery.execute();
		assertThat(sizeResult).hasSize(1).hasOnlyElementsOfType(File.class);

		String string = "get file in ('" + directory + "') having size = "
				+ FilePropertyHelper.getSize((File) sizeResult.get(0)) + ";";
		Query query = QueryFactory.newQuery(string);

		// WHEN
		List<Comparable> results = query.execute();

		// THEN
		assertThat(results).hasOnlyElementsOfType(File.class);
		assertThat(results).containsOnly(sizeResult.get(0));
	}

	@Test
	public void should_validate_unequal() throws Exception {
		// GIVEN
		String sizeQueryString = "get 1 file in ('" + directory
				+ "') sort by size ascending;";
		Query sizeQuery = QueryFactory.newQuery(sizeQueryString);
		List<Comparable> sizeResult = sizeQuery.execute();
		assertThat(sizeResult).hasSize(1).hasOnlyElementsOfType(File.class);

		String string = "get file in ('" + directory + "') having size <> "
				+ FilePropertyHelper.getSize((File) sizeResult.get(0)) + ";";
		Query query = QueryFactory.newQuery(string);

		// WHEN
		List<Comparable> results = query.execute();

		// THEN
		assertThat(results).hasOnlyElementsOfType(File.class);
		assertThat(results).doesNotContain(sizeResult.get(0));
	}

	@Test
	public void should_validate_in() throws Exception {
		// GIVEN
		String sizeQueryString = "get 2 file in ('" + directory
				+ "') having type = 'file' sort by size descending;";
		Query sizeQuery = QueryFactory.newQuery(sizeQueryString);
		List<Comparable> sizeResult = sizeQuery.execute();
		assertThat(sizeResult).hasSize(2).hasOnlyElementsOfType(File.class);

		String string = "get file in ('" + directory + "') having size in ("
				+ FilePropertyHelper.getSize((File) sizeResult.get(0)) + ", "
				+ FilePropertyHelper.getSize((File) sizeResult.get(1)) + ");";
		Query query = QueryFactory.newQuery(string);

		// WHEN
		List<Comparable> results = query.execute();

		// THEN
		assertThat(results).hasOnlyElementsOfType(File.class);
		assertThat(results).containsOnly(sizeResult.get(0), sizeResult.get(1));
	}

	@Test
	public void should_validate_greather_than() throws Exception {
		// GIVEN
		String sizeQueryString = "get 2 file in ('" + directory
				+ "') sort by size descending;";
		Query sizeQuery = QueryFactory.newQuery(sizeQueryString);
		List<Comparable> sizeResult = sizeQuery.execute();
		assertThat(sizeResult).hasSize(2).hasOnlyElementsOfType(File.class);

		String string = "get file in ('" + directory
				+ "') having type = 'file' and size > "
				+ FilePropertyHelper.getSize((File) sizeResult.get(1)) + ";";
		Query query = QueryFactory.newQuery(string);

		// WHEN
		List<Comparable> results = query.execute();

		// THEN
		assertThat(results).hasOnlyElementsOfType(File.class);
		assertThat(results).containsOnly(sizeResult.get(0));
	}

	@Test
	public void should_validate_greather_than_or_equal() throws Exception {
		// GIVEN
		String sizeQueryString = "get 2 file in ('" + directory
				+ "') having type = 'file' sort by size descending;";
		Query sizeQuery = QueryFactory.newQuery(sizeQueryString);
		List<Comparable> sizeResult = sizeQuery.execute();
		assertThat(sizeResult).hasSize(2).hasOnlyElementsOfType(File.class);

		String string = "get file in ('" + directory
				+ "') having type = 'file' and size >= "
				+ FilePropertyHelper.getSize((File) sizeResult.get(1)) + ";";
		Query query = QueryFactory.newQuery(string);

		// WHEN
		List<Comparable> results = query.execute();

		// THEN
		assertThat(results).hasOnlyElementsOfType(File.class);
		assertThat(results).containsOnly(sizeResult.get(0), sizeResult.get(1));
	}

	@Test
	public void should_validate_less_than() throws Exception {
		// GIVEN
		String sizeQueryString = "get 2 file in ('" + directory
				+ "') sort by size ascending;";
		Query sizeQuery = QueryFactory.newQuery(sizeQueryString);
		List<Comparable> sizeResult = sizeQuery.execute();
		assertThat(sizeResult).hasSize(2).hasOnlyElementsOfType(File.class);

		String string = "get file in ('" + directory
				+ "') having type = 'file' and size < "
				+ FilePropertyHelper.getSize((File) sizeResult.get(0)) + ";";
		Query query = QueryFactory.newQuery(string);

		// WHEN
		List<Comparable> results = query.execute();

		// THEN
		assertThat(results).hasOnlyElementsOfType(File.class);
		assertThat(results).doesNotContain(sizeResult.get(0));
	}

	@Test
	public void should_validate_less_than_or_equal() throws Exception {
		// GIVEN
		String sizeQueryString = "get 2 file in ('" + directory
				+ "') having type = 'file' sort by size descending;";
		Query sizeQuery = QueryFactory.newQuery(sizeQueryString);
		List<Comparable> sizeResult = sizeQuery.execute();
		assertThat(sizeResult).hasSize(2).hasOnlyElementsOfType(File.class);

		String string = "get file in ('" + directory
				+ "') having type = 'file' and size <= "
				+ FilePropertyHelper.getSize((File) sizeResult.get(1)) + ";";
		Query query = QueryFactory.newQuery(string);

		// WHEN
		List<Comparable> results = query.execute();

		// THEN
		assertThat(results).hasOnlyElementsOfType(File.class);
		assertThat(results).doesNotContain(sizeResult.get(0));
	}

	@Test
	public void should_validate_between() throws Exception {
		// GIVEN
		String sizeQueryString = "get 1 file in ('" + directory
				+ "') having type = 'file' sort by size descending;";
		Query sizeQuery = QueryFactory.newQuery(sizeQueryString);
		List<Comparable> sizeResult = sizeQuery.execute();
		assertThat(sizeResult).hasSize(1).hasOnlyElementsOfType(File.class);

		String string = "get file in ('" + directory
				+ "') having type = 'file' and size between 0 and "
				+ FilePropertyHelper.getSize((File) sizeResult.get(0)) + ";";
		Query query = QueryFactory.newQuery(string);

		// WHEN
		List<Comparable> results = query.execute();

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
		Query query = QueryFactory.newQuery(string);

		// WHEN
		List<Comparable> results = query.execute();

		// THEN
		// Exception mst be throw
	}

	@Test(expected = Exception.class)
	public void should_invalidate_match() throws Exception {
		// GIVEN
		String string = "get file in ('" + directory
				+ "') having size match '.*';";
		Query query = QueryFactory.newQuery(string);

		// WHEN
		List<Comparable> results = query.execute();

		// THEN
		// Exception mst be throw

	}
}
