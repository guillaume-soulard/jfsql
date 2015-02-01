package fr.ogama.jfsql.query.clause.get;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import fr.ogama.jfsql.JFSQL;
import fr.ogama.utils.parser.model.Statement;

public class GetDistinctTest {

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
	public void should_get_parent_with_no_double() throws Exception {
		// GIVEN
		String query = "get distinct parent in ('" + getTestDirectory + "');";
		Statement fileQuery = JFSQL.parseOneStatement(query);

		// WHEN
		List<Comparable> results = fileQuery.execute(new HashMap<String, Comparable>());

		// THEN
		assertThat(results).isNotNull().isNotEmpty();
		assertThat(results).hasOnlyElementsOfType(String.class);
		assertThat(results).hasSize(4);
		assertThat(results).containsOnly(folder1.getParentFile().getName(),
				folder11.getParentFile().getName(),
				file1.getParentFile().getName(),
				file2.getParentFile().getName());

		for (Comparable result : results) {
			assertThat((String) result).isNotEmpty();
		}
	}
		
	@Test
	public void should_get_size_with_no_double() throws Exception {
		// GIVEN
		String query = "get distinct size in ('" + getTestDirectory + "');";
		Statement fileQuery = JFSQL.parseOneStatement(query);

		// WHEN
		List<Comparable> results = fileQuery.execute(new HashMap<String, Comparable>());

		// THEN
		assertThat(results).isNotNull().isNotEmpty();
		assertThat(results).hasOnlyElementsOfType(Double.class);
		assertThat(results).containsOnlyOnce(0d);

		for (Comparable result : results) {
			assertThat((Double) result).isNotNull().isGreaterThanOrEqualTo(0L);
		}
	}
	
	@Test
	public void should_get_owner_with_no_double() throws Exception {
		// GIVEN
		String query = "get distinct owner in ('" + getTestDirectory + "');";
		Statement fileQuery = JFSQL.parseOneStatement(query);

		// WHEN
		List<Comparable> results = fileQuery.execute(new HashMap<String, Comparable>());

		// THEN
		assertThat(results).isNotNull().isNotEmpty();
		assertThat(results).hasOnlyElementsOfType(String.class);
		assertThat(results).hasSize(1);
		
		for (Comparable result : results) {
			assertThat((String) result).isNotEmpty();
		}
	}
	
	@Test(expected = Exception.class)
	public void should_get_error_on_incorrect_string() throws Exception {
		// GIVEN
		String query = "get string owner in ('" + getTestDirectory + "');";
		Statement fileQuery = JFSQL.parseOneStatement(query);

		// WHEN
		List<Comparable> results = fileQuery.execute(new HashMap<String, Comparable>());

		// THEN
	}
}
