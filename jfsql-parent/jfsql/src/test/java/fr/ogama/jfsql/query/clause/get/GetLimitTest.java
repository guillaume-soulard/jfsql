package fr.ogama.jfsql.query.clause.get;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import fr.ogama.jfsql.JFSQL;
import fr.ogama.utils.parser.model.Statement;

public class GetLimitTest {

	private static String getTestDirectory;

	@BeforeClass
	public static void setup() {
		getTestDirectory = System.getProperty("user.dir");
		getTestDirectory += "/src/test/resources/getTestFolder";
	}
	
	@Test
	public void should_get_2_files_and_directories() throws Exception {
		// GIVEN
		String fileOrDirectory = "get 2 file in ('"
				+ getTestDirectory + "');";
		Statement fileOrDirectoryQuery = JFSQL.parseOneStatement(fileOrDirectory);

		// WHEN
		List<Comparable> fileOrDirectoryResults = fileOrDirectoryQuery
				.execute(new HashMap<String, Comparable>());

		// THEN
		assertThat(fileOrDirectoryResults).isNotNull().hasSize(2);
	}
	
	@Test(expected = Exception.class)
	public void should_get_0_files_and_directories() throws Exception {
		// GIVEN
		String fileOrDirectory = "get 0 file in ('"
				+ getTestDirectory + "');";
		Statement fileOrDirectoryQuery = JFSQL.parseOneStatement(fileOrDirectory);

		// WHEN
		List<Comparable> fileOrDirectoryResults = fileOrDirectoryQuery
				.execute(new HashMap<String, Comparable>());

		// THEN
		// exception must be throw
	}
	
	@Test(expected = Exception.class)
	public void should_get_error_on_negative_numbers() throws Exception {
		// GIVEN
		String fileOrDirectory = "get -1 file in ('"
				+ getTestDirectory + "');";
		Statement fileOrDirectoryQuery = JFSQL.parseOneStatement(fileOrDirectory);

		// WHEN

		// THEN
	}
	
	@Test(expected = Exception.class)
	public void should_get_error_on_strings() throws Exception {
		// GIVEN
		String fileOrDirectory = "get one file or directory in ('"
				+ getTestDirectory + "');";
		Statement fileOrDirectoryQuery = JFSQL.parseOneStatement(fileOrDirectory);

		// WHEN
		List<Comparable> fileOrDirectoryResults = fileOrDirectoryQuery
				.execute(new HashMap<String, Comparable>());

		// THEN
	}
}
