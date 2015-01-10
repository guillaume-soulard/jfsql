package fr.ogama.jfsql.query.clause.get;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.ogama.jfsql.query.FileType;
import fr.ogama.jfsql.query.Query;
import fr.ogama.jfsql.query.QueryFactory;
import fr.ogama.jfsql.query.Status;

public class GetTest {

	private static String getTestDirectory;

	private static File folder1;
	private static File folder11;
	private static File folder2;
	private static File file1;
	private static File file2;

	@BeforeClass
	public static void setup() {
		getTestDirectory = System.getProperty("user.dir");
		getTestDirectory += "/src/test/resources/getTestFolder";

		folder1 = new File(getTestDirectory + "/Folder 1");
		folder11 = new File(getTestDirectory + "/Folder 1/Folder 1.1");
		folder2 = new File(getTestDirectory + "/Folder 2");
		file1 = new File(getTestDirectory + "/Folder 1/Folder 1.1/File 1.txt");
		file2 = new File(getTestDirectory + "/Folder 2/File 2.xml");

		assertThat(folder1).isNotNull().exists().isDirectory();
		assertThat(folder11).isNotNull().exists().isDirectory();
		assertThat(folder2).isNotNull().exists().isDirectory();
		assertThat(file1).isNotNull().exists().isFile();
		assertThat(file2).isNotNull().exists().isFile();

	}

	@Test
	public void should_get_files_and_directories() throws Exception {
		// GIVEN
		String file = "get file in ('" + getTestDirectory + "');";
		Query fileQuery = QueryFactory.newQuery(file);

		// WHEN
		List<Comparable> fileResults = fileQuery.execute();

		// THEN
		assertThat(fileResults).isNotNull().isNotEmpty();

		assertThat(fileResults).hasOnlyElementsOfType(File.class);

		assertThat(fileResults).containsOnly(folder1, folder11,
				folder2, file1, file2);

		for (Comparable result : fileResults) {
			assertThat(((File) result)).exists();
		}
	}
	
	@Test
	public void should_get_names() throws Exception {
		// GIVEN
		String query = "get name in ('" + getTestDirectory + "');";
		Query fileQuery = QueryFactory.newQuery(query);

		// WHEN
		List<Comparable> results = fileQuery.execute();

		// THEN
		assertThat(results).isNotNull().isNotEmpty();
		assertThat(results).hasOnlyElementsOfType(String.class);
		assertThat(results).containsOnly(folder1.getName(), folder11.getName(),
				folder2.getName(), file1.getName(), file2.getName());

		for (Comparable result : results) {
			assertThat((String) result).isNotEmpty();
		}
	}

	@Test
	public void should_get_parent() throws Exception {
		// GIVEN
		String query = "get parent in ('" + getTestDirectory + "');";
		Query fileQuery = QueryFactory.newQuery(query);

		// WHEN
		List<Comparable> results = fileQuery.execute();

		// THEN
		assertThat(results).isNotNull().isNotEmpty();
		assertThat(results).hasOnlyElementsOfType(String.class);
		assertThat(results).containsOnly(folder1.getParentFile().getName(),
				folder11.getParentFile().getName(),
				folder2.getParentFile().getName(),
				file1.getParentFile().getName(),
				file2.getParentFile().getName());

		for (Comparable result : results) {
			assertThat((String) result).isNotEmpty();
		}
	}

	@Test
	public void should_get_path() throws Exception {
		// GIVEN
		String query = "get path in ('" + getTestDirectory + "');";
		Query fileQuery = QueryFactory.newQuery(query);

		// WHEN
		List<Comparable> results = fileQuery.execute();

		// THEN
		assertThat(results).isNotNull().isNotEmpty();
		assertThat(results).hasOnlyElementsOfType(String.class);
		assertThat(results).containsOnly(folder1.getPath(), folder11.getPath(),
				folder2.getPath(), file1.getPath(), file2.getPath());

		for (Comparable result : results) {
			assertThat((String) result).isNotEmpty();
			assertThat(new File((String) result)).exists();
		}
	}

	@Test
	public void should_get_creation_date() throws Exception {
		// GIVEN
		String query = "get creation_date in ('" + getTestDirectory + "');";
		Query fileQuery = QueryFactory.newQuery(query);

		// WHEN
		List<Comparable> results = fileQuery.execute();

		// THEN
		assertThat(results).isNotNull().isNotEmpty();
		assertThat(results).hasOnlyElementsOfType(String.class);

		for (Comparable result : results) {
			assertThat((String) result).isNotEmpty();
			assertThat((String) result).matches(
					"[0-9]{4}/[0-9]{2}/[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}");
		}
	}

	@Test
	public void should_get_last_update_date() throws Exception {
		// GIVEN
		String query = "get last_update_date in ('" + getTestDirectory + "');";
		Query fileQuery = QueryFactory.newQuery(query);

		// WHEN
		List<Comparable> results = fileQuery.execute();

		// THEN
		assertThat(results).isNotNull().isNotEmpty();
		assertThat(results).hasOnlyElementsOfType(String.class);

		for (Comparable result : results) {
			assertThat((String) result).isNotEmpty();
			assertThat((String) result).matches(
					"[0-9]{4}/[0-9]{2}/[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}");
		}
	}

	@Test
	public void should_get_last_access_date() throws Exception {
		// GIVEN
		String query = "get last_access_date in ('" + getTestDirectory + "');";
		Query fileQuery = QueryFactory.newQuery(query);

		// WHEN
		List<Comparable> results = fileQuery.execute();

		// THEN
		assertThat(results).isNotNull().isNotEmpty();
		assertThat(results).hasOnlyElementsOfType(String.class);

		for (Comparable result : results) {
			assertThat((String) result).isNotEmpty();
			assertThat((String) result).matches(
					"[0-9]{4}/[0-9]{2}/[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}");
		}
	}

	@Test
	public void should_get_size() throws Exception {
		// GIVEN
		String query = "get size in ('" + getTestDirectory + "');";
		Query fileQuery = QueryFactory.newQuery(query);

		// WHEN
		List<Comparable> results = fileQuery.execute();

		// THEN
		assertThat(results).isNotNull().isNotEmpty();
		assertThat(results).hasOnlyElementsOfType(Long.class);

		for (Comparable result : results) {
			assertThat((Long) result).isNotNull().isGreaterThanOrEqualTo(0L);
		}
	}

	@Test
	public void should_get_owner() throws Exception {
		// GIVEN
		String query = "get owner in ('" + getTestDirectory + "');";
		Query fileQuery = QueryFactory.newQuery(query);

		// WHEN
		List<Comparable> results = fileQuery.execute();

		// THEN
		assertThat(results).isNotNull().isNotEmpty();
		assertThat(results).hasOnlyElementsOfType(String.class);

		for (Comparable result : results) {
			assertThat((String) result).isNotEmpty();
		}
	}

	@Test
	public void should_get_type() throws Exception {
		// GIVEN
		String query = "get type in ('" + getTestDirectory + "');";
		Query fileQuery = QueryFactory.newQuery(query);

		// WHEN
		List<Comparable> results = fileQuery.execute();

		// THEN
		assertThat(results).isNotEmpty();
		assertThat(results).hasOnlyElementsOfType(String.class);

		for (Comparable result : results) {
			assertThat((String) result).isNotEmpty();
			assertThat((String) result).isIn(FileType.FILE.getLabel(),
					FileType.DIRECTORY.getLabel());
		}
	}

	@Test
	public void should_get_status() throws Exception {
		// GIVEN
		String query = "get status in ('" + getTestDirectory + "');";
		Query fileQuery = QueryFactory.newQuery(query);

		// WHEN
		List<Comparable> results = fileQuery.execute();

		// THEN
		assertThat(results).isNotEmpty();
		assertThat(results).hasOnlyElementsOfType(String.class);

		for (Comparable result : results) {
			assertThat((String) result).isNotEmpty();
			assertThat((String) result).isIn(Status.NONE.getLabel(),
					Status.READABLE.getLabel(), Status.WRITABLE.getLabel(),
					Status.EXECUTABLE.getLabel());
		}
	}
}
