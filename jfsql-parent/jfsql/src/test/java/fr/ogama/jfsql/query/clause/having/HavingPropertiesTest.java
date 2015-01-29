package fr.ogama.jfsql.query.clause.having;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.ogama.jfsql.JFSQL;
import fr.ogama.utils.parser.model.Statement;
import fr.ogama.utils.parser.model.get.FilePropertiesUtils;

public class HavingPropertiesTest extends AbstractHavingTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		setUp();
	}

	@AfterClass
	public static void tearDownAfterClass() {
		tearDown();
	}

	@Test
	public void should_get_only_files_by_name() throws Exception {
		// GIVEN
		String query = "get file in ('" + directory
				+ "') having name like 'date'; and type = 'file'";
		Statement fileQuery = JFSQL.parseOneStatement(query);

		// WHEN
		List<Comparable> results = fileQuery
				.execute(new HashMap<String, Comparable>());

		// THEN
		assertThat(results).isNotNull().hasSize(3)
				.hasOnlyElementsOfType(File.class);
	}

	@Test
	public void should_get_only_files_by_path() throws Exception {
		// GIVEN
		String query = "get file in ('" + directory + "') having path = '"
				+ regularFile.getPath() + "' and type = 'file';";
		Statement fileQuery = JFSQL.parseOneStatement(query);

		// WHEN
		List<Comparable> results = fileQuery
				.execute(new HashMap<String, Comparable>());

		// THEN
		assertThat(results).isNotNull().hasSize(1)
				.hasOnlyElementsOfType(File.class);
	}

	@Test
	public void should_get_only_files_by_parent_name() throws Exception {
		// GIVEN
		String query = "get file in ('" + directory + "') having parent in ('"
				+ new File(directory).getName() + "') and type = 'file';";
		Statement fileQuery = JFSQL.parseOneStatement(query);

		// WHEN
		List<Comparable> results = fileQuery
				.execute(new HashMap<String, Comparable>());

		// THEN
		assertThat(results).isNotNull().hasSize(7)
				.hasOnlyElementsOfType(File.class);
	}

	@Test
	public void should_get_only_files_by_owner() throws Exception {
		// GIVEN
		Statement ownerQuery = JFSQL
				.parseOneStatement("get distinct owner in ('" + directory
						+ "') having type = 'file';");

		List<Comparable> ownerResult = ownerQuery
				.execute(new HashMap<String, Comparable>());
		assertThat(ownerResult).isNotNull().hasSize(1)
				.hasOnlyElementsOfType(String.class);

		String query = "get file in ('" + directory + "') having owner = '"
				+ String.valueOf(ownerResult.get(0)) + "' and type = 'file';";
		Statement fileQuery = JFSQL.parseOneStatement(query);

		// WHEN
		List<Comparable> results = fileQuery
				.execute(new HashMap<String, Comparable>());

		// THEN
		assertThat(results).isNotNull().hasSize(7)
				.hasOnlyElementsOfType(File.class);
	}

	@Test
	public void should_get_only_files_by_size() throws Exception {
		// GIVEN
		String query = "get file in ('" + directory
				+ "') having size > 0 and type = 'file';";
		Statement fileQuery = JFSQL.parseOneStatement(query);

		// WHEN
		List<Comparable> results = fileQuery
				.execute(new HashMap<String, Comparable>());

		// THEN
		assertThat(results).isNotNull().hasSize(2)
				.hasOnlyElementsOfType(File.class);
	}

	@Test
	public void should_get_only_files_by_creation_date() throws Exception {
		// GIVEN
		List<Comparable> creationResult = JFSQL.parseOneStatement(
				"get creation_date in('" + directory
						+ "');").execute(
				new HashMap<String, Comparable>());

		String query = "get file in ('" + directory
				+ "') having asDate(creation_date, 'yyyyMMdd') in (asDate('"
				+ formatDate_yyyyMMdd((Date) creationResult.get(0))
				+ "', 'yyyyMMdd'), asDate('"
				+ formatDate_yyyyMMdd((Date) creationResult.get(0))
				+ "', 'yyyyMMdd'));";
		Statement fileQuery = JFSQL.parseOneStatement(query);

		// WHEN
		List<Comparable> results = fileQuery
				.execute(new HashMap<String, Comparable>());

		// THEN
		assertThat(results).isNotEmpty().hasOnlyElementsOfType(File.class);
	}

	@Test
	public void should_get_only_files_by_update_date() throws Exception {
		// GIVEN
		List<Comparable> lastUpdateResult = JFSQL.parseOneStatement(
				"get distinct last_update_date in('" + directory
						+ "') sort by last_update_date ascending;").execute(
				new HashMap<String, Comparable>());

		String query = "get file in ('" + directory
				+ "') having asDate(last_update_date, 'yyyyMMdd') = asDate('"
				+ formatDate_yyyyMMdd((Date) lastUpdateResult.get(0))
				+ "', 'yyyyMMdd');";
		Statement fileQuery = JFSQL.parseOneStatement(query);

		// WHEN
		List<Comparable> results = fileQuery
				.execute(new HashMap<String, Comparable>());

		// THEN
		assertThat(results).isNotEmpty().hasOnlyElementsOfType(File.class);

	}

	@Test
	public void should_get_only_files_by_access_date() throws Exception {
		// GIVEN
		List<Comparable> lastAccessResult = JFSQL.parseOneStatement(
				"get distinct last_access_date in('" + directory
						+ "') sort by last_access_date ascending;").execute(
				new HashMap<String, Comparable>());

		String query = "get file in ('"
				+ directory
				+ "') having asDate(last_access_date, 'yyyyMMdd') between asDate('"
				+ formatDate_yyyyMMdd((Date) lastAccessResult.get(0))
				+ "', 'yyyyMMdd') and asDate('"
				+ formatDate_yyyyMMdd((Date) lastAccessResult
						.get(lastAccessResult.size() - 1))
				+ "', 'yyyyMMdd');";
		Statement fileQuery = JFSQL.parseOneStatement(query);

		// WHEN
		List<Comparable> results = fileQuery
				.execute(new HashMap<String, Comparable>());

		// THEN
		assertThat(results).isNotEmpty().hasOnlyElementsOfType(File.class);

	}

	@Test
	public void should_get_only_files_by_content() throws Exception {
		// GIVEN
		String query = "get file in ('" + directory
				+ "') having content like 'Content' and type = 'file';";
		Statement fileQuery = JFSQL.parseOneStatement(query);

		// WHEN
		List<Comparable> results = fileQuery
				.execute(new HashMap<String, Comparable>());

		// THEN
		assertThat(results).isNotNull().hasSize(1)
				.hasOnlyElementsOfType(File.class);

	}

	@Test
	public void should_get_names_by_file_type() throws Exception {
		// GIVEN
		String query = "get name in ('" + directory
				+ "') having type = 'file';";
		Statement fileQuery = JFSQL.parseOneStatement(query);

		// WHEN
		List<Comparable> results = fileQuery
				.execute(new HashMap<String, Comparable>());

		// THEN
		assertThat(results).isNotNull().hasSize(7)
				.hasOnlyElementsOfType(String.class);

	}

	@Test
	public void should_get_names_by_directory_type() throws Exception {
		// GIVEN
		String query = "get name in ('" + directory
				+ "') having type = 'directory';";
		Statement fileQuery = JFSQL.parseOneStatement(query);

		// WHEN
		List<Comparable> results = fileQuery
				.execute(new HashMap<String, Comparable>());

		// THEN
		assertThat(results).isNotNull().hasOnlyElementsOfType(String.class);

	}

	@Test
	public void should_get_names_by_status() throws Exception {
		// GIVEN
		String query = "get name in ('" + directory
				+ "') having status = 'executable' and type = 'file';";
		Statement fileQuery = JFSQL.parseOneStatement(query);

		// WHEN
		List<Comparable> results = fileQuery
				.execute(new HashMap<String, Comparable>());

		// THEN
		assertThat(results).isNotNull().hasSize(3)
				.hasOnlyElementsOfType(String.class);
	}

	private String formatDate_yyyyMMdd(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		return dateFormat.format(date);
	}

	private Date addSeconds(Date date, int seconds) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.SECOND, seconds);
		return cal.getTime();
	}
}
