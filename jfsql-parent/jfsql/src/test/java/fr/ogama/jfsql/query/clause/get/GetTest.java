package fr.ogama.jfsql.query.clause.get;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import fr.ogama.jfsql.JFSQL;
import fr.ogama.utils.parser.model.Statement;
import fr.ogama.utils.parser.model.get.FileStatus;
import fr.ogama.utils.parser.model.get.FileType;

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
		Statement fileQuery = JFSQL.parseOneStatement(file);

		// WHEN
		List<Comparable> fileResults = fileQuery
				.execute(new HashMap<String, Comparable>());

		// THEN
		assertThat(fileResults).isNotNull().isNotEmpty();

		assertThat(fileResults).hasOnlyElementsOfType(File.class);

		assertThat(fileResults).containsOnly(folder1, folder11, folder2, file1,
				file2);

		for (Comparable result : fileResults) {
			assertThat(((File) result)).exists();
		}
	}

	@Test
	public void should_get_names() throws Exception {
		// GIVEN
		String query = "get name in ('" + getTestDirectory + "');";
		Statement fileQuery = JFSQL.parseOneStatement(query);

		// WHEN
		List<Comparable> results = fileQuery
				.execute(new HashMap<String, Comparable>());

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
		String query = "get distinct parent in ('" + getTestDirectory + "' deep 1);";
		Statement fileQuery = JFSQL.parseOneStatement(query);

		// WHEN
		List<Comparable> results = fileQuery
				.execute(new HashMap<String, Comparable>());

		// THEN
		assertThat(results).isNotNull().isNotEmpty();
		assertThat(results).hasOnlyElementsOfType(String.class);
		assertThat(results).containsOnly(new File(getTestDirectory).getName());

		for (Comparable result : results) {
			assertThat((String) result).isNotEmpty();
		}
	}

	@Test
	public void should_get_path() throws Exception {
		// GIVEN
		String query = "get path in ('" + getTestDirectory + "');";
		Statement fileQuery = JFSQL.parseOneStatement(query);

		// WHEN
		List<Comparable> results = fileQuery
				.execute(new HashMap<String, Comparable>());

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
		Statement fileQuery = JFSQL.parseOneStatement(query);

		// WHEN
		List<Comparable> results = fileQuery
				.execute(new HashMap<String, Comparable>());

		// THEN
		assertThat(results).isNotNull().isNotEmpty();
		assertThat(results).hasOnlyElementsOfType(Date.class);
	}

	@Test
	public void should_get_last_update_date() throws Exception {
		// GIVEN
		String query = "get last_update_date in ('" + getTestDirectory + "');";
		Statement fileQuery = JFSQL.parseOneStatement(query);

		// WHEN
		List<Comparable> results = fileQuery
				.execute(new HashMap<String, Comparable>());

		// THEN
		assertThat(results).isNotNull().isNotEmpty();
		assertThat(results).hasOnlyElementsOfType(Date.class);
	}

	@Test
	public void should_get_last_access_date() throws Exception {
		// GIVEN
		String query = "get last_access_date in ('" + getTestDirectory + "');";
		Statement fileQuery = JFSQL.parseOneStatement(query);

		// WHEN
		List<Comparable> results = fileQuery
				.execute(new HashMap<String, Comparable>());

		// THEN
		assertThat(results).isNotNull().isNotEmpty();
		assertThat(results).hasOnlyElementsOfType(Date.class);
	}

	@Test
	public void should_get_size() throws Exception {
		// GIVEN
		String query = "get size in ('" + getTestDirectory + "');";
		Statement fileQuery = JFSQL.parseOneStatement(query);

		// WHEN
		List<Comparable> results = fileQuery
				.execute(new HashMap<String, Comparable>());

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
		Statement fileQuery = JFSQL.parseOneStatement(query);

		// WHEN
		List<Comparable> results = fileQuery
				.execute(new HashMap<String, Comparable>());

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
		Statement fileQuery = JFSQL.parseOneStatement(query);

		// WHEN
		List<Comparable> results = fileQuery
				.execute(new HashMap<String, Comparable>());

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
		Statement fileQuery = JFSQL.parseOneStatement(query);

		// WHEN
		List<Comparable> results = fileQuery
				.execute(new HashMap<String, Comparable>());

		// THEN
		assertThat(results).isNotEmpty();
		assertThat(results).hasOnlyElementsOfType(String.class);

		for (Comparable result : results) {
			assertThat((String) result).isNotEmpty();
			assertThat((String) result).isIn(
					FileStatus.UNACCISSIBLE.getLabel(),
					FileStatus.READABLE.getLabel(),
					FileStatus.WRITABLE.getLabel(),
					FileStatus.EXECUTABLE.getLabel());
		}
	}
}
