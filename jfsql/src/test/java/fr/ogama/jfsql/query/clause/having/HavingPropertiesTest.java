package fr.ogama.jfsql.query.clause.having;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import fr.ogama.jfsql.query.JFSQLUtils;
import fr.ogama.jfsql.query.Query;
import fr.ogama.jfsql.query.QueryFactory;
import fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.properties.FilePropertyHelper;

public class HavingPropertiesTest extends AbstractHavingTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		setUp();
	}

	@Test
	public void should_get_only_files_by_name() throws Exception {
		// GIVEN
		String query = "get file in ('" + directory
				+ "') having name like 'date'";
		Query fileQuery = QueryFactory.newQuery(query);

		// WHEN
		List<Comparable> results = fileQuery.execute();

		// THEN
		assertThat(results).isNotNull().hasSize(3)
				.hasOnlyElementsOfType(File.class);
	}

	@Test
	public void should_get_only_files_by_path() throws Exception {
		// GIVEN
		String query = "get file in ('" + directory + "') having path = '"
				+ regularFile.getPath() + "'";
		Query fileQuery = QueryFactory.newQuery(query);

		// WHEN
		List<Comparable> results = fileQuery.execute();

		// THEN
		assertThat(results).isNotNull().hasSize(1)
				.hasOnlyElementsOfType(File.class);
	}

	@Test
	public void should_get_only_files_by_parent_name() throws Exception {
		// GIVEN
		String query = "get file in ('" + directory + "') having parent in ('"
				+ new File(directory).getName() + "')";
		Query fileQuery = QueryFactory.newQuery(query);

		// WHEN
		List<Comparable> results = fileQuery.execute();

		// THEN
		assertThat(results).isNotNull().hasSize(7)
				.hasOnlyElementsOfType(File.class);
	}

	@Test
	public void should_get_only_files_by_owner() throws Exception {
		// GIVEN
		Query ownerQuery = QueryFactory.newQuery("get distinct owner in ('"
				+ directory + "')");
		List<Comparable> ownerResult = ownerQuery.execute();
		assertThat(ownerResult).isNotNull().hasSize(1)
				.hasOnlyElementsOfType(String.class);

		String query = "get file in ('" + directory + "') having owner = '"
				+ String.valueOf(ownerResult.get(0)) + "'";
		Query fileQuery = QueryFactory.newQuery(query);

		// WHEN
		List<Comparable> results = fileQuery.execute();

		// THEN
		assertThat(results).isNotNull().hasSize(7)
				.hasOnlyElementsOfType(File.class);
	}

	@Test
	public void should_get_only_files_by_size() throws Exception {
		// GIVEN
		String query = "get file in ('" + directory + "') having size > 0";
		Query fileQuery = QueryFactory.newQuery(query);

		// WHEN
		List<Comparable> results = fileQuery.execute();

		// THEN
		assertThat(results).isNotNull().hasSize(2)
				.hasOnlyElementsOfType(File.class);
	}

	@Test
	public void should_get_only_files_by_creation_date() throws Exception {
		// GIVEN
		String regularFileCreationDate = JFSQLUtils
				.dateToString(FilePropertyHelper.getCreationDate(regularFile));
		String creationFileCreationDate = JFSQLUtils
				.dateToString(FilePropertyHelper
						.getCreationDate(creationDateFile));

		String query = "get file in ('" + directory
				+ "') having creation_date in ('" + regularFileCreationDate
				+ "', '" + creationFileCreationDate + "')";
		Query fileQuery = QueryFactory.newQuery(query);

		// WHEN
		List<Comparable> results = fileQuery.execute();

		// THEN
		assertThat(results).isNotEmpty()
				.hasOnlyElementsOfType(File.class);
	}

	@Test
	public void should_get_only_files_by_update_date() throws Exception {
		// GIVEN
		String regularFileUpdateDate = JFSQLUtils
				.dateToString(FilePropertyHelper.getLastModificationDate(regularFile));

		String query = "get file in ('" + directory
				+ "') having last_update_date = '" + regularFileUpdateDate + "'";
		Query fileQuery = QueryFactory.newQuery(query);

		// WHEN
		List<Comparable> results = fileQuery.execute();

		// THEN
		assertThat(results).isNotEmpty()
				.hasOnlyElementsOfType(File.class);

	}

	@Test
	public void should_get_only_files_by_access_date() throws Exception {
		// GIVEN
		String accessFileAccessDate = JFSQLUtils
				.dateToString(FilePropertyHelper
						.getLastAccessDate(lastAccessFile));

		String regularFileAccessDate = JFSQLUtils
				.dateToString(FilePropertyHelper.getLastAccessDate(regularFile));

		String query = "get file in ('" + directory
				+ "') having last_access_date between '"
				+ accessFileAccessDate + "' and '" + regularFileAccessDate
				+ "'";
		Query fileQuery = QueryFactory.newQuery(query);

		// WHEN
		List<Comparable> results = fileQuery.execute();

		// THEN
		assertThat(results).isNotEmpty()
				.hasOnlyElementsOfType(File.class);

	}

	@Test
	public void should_get_only_files_by_content() throws Exception {
		// GIVEN
		String query = "get file in ('" + directory
				+ "') having content like 'Content'";
		Query fileQuery = QueryFactory.newQuery(query);

		// WHEN
		List<Comparable> results = fileQuery.execute();

		// THEN
		assertThat(results).isNotNull().hasSize(1)
				.hasOnlyElementsOfType(File.class);

	}
}
