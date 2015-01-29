package fr.ogama.jfsql.query.clause.in;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import fr.ogama.jfsql.JFSQL;
import fr.ogama.utils.parser.model.Statement;

public class DeepTest {

	private static String inDirectory1;
	private static String inDirectory2;
	private static String inDirectory3;

	private static File file1;
	private static File file2;
	private static File file3;

	@BeforeClass
	public static void setup() {
		inDirectory1 = System.getProperty("user.dir")
				+ "/src/test/resources/inTestFolder/Folder 1";
		inDirectory2 = System.getProperty("user.dir")
				+ "/src/test/resources/inTestFolder/Folder 2";
		inDirectory3 = System.getProperty("user.dir")
				+ "/src/test/resources/inTestFolder/Folder 3";

		file1 = new File(inDirectory1 + "/Folder 1.1/File 1.txt");
		file2 = new File(inDirectory2 + "/Folder 2.1/Folder 2.2/File 2.txt");
		file3 = new File(inDirectory3
				+ "/Folder 3.1/Folder 3.2/Folder 3.3/File 3.txt");

		assertThat(file1).exists().isFile();
		assertThat(file2).exists().isFile();
		assertThat(file3).exists().isFile();

		assertThat(new File(inDirectory1)).exists().isDirectory();
		assertThat(new File(inDirectory2)).exists().isDirectory();
		assertThat(new File(inDirectory3)).exists().isDirectory();
	}

	@Test
	public void should_get_in_level_one_and_two_only() throws Exception {
		// GIVEN
		String query = "get file in ('" + inDirectory1 + "' deep 2, '"
				+ inDirectory2 + "' deep 2, '" + inDirectory3 + "' deep 2) having type = 'file';";
		Statement fileQuery = JFSQL.parseOneStatement(query);
		
		// WHEN
		List<Comparable> results = fileQuery.execute(new HashMap<String, Comparable>());

		// THEN
		assertThat(results).isNotNull().hasSize(1);
		assertThat(results).containsOnly(file1);
	}

	@Test
	public void should_get_in_any_level() throws Exception {
		// GIVEN
		String query = "get file in ('" + inDirectory1 + "', '" + inDirectory2
				+ "', '" + inDirectory3 + "') having type = 'file';";
		Statement fileQuery = JFSQL.parseOneStatement(query);
		
		// WHEN
		List<Comparable> results = fileQuery.execute(new HashMap<String, Comparable>());

		// THEN
		assertThat(results).isNotNull().hasSize(3);
		assertThat(results).containsOnly(file1, file2, file3);
	}

	@Test(expected = Exception.class)
	public void should_get_error_because_syntax_error() throws Exception {
		// GIVEN
		String query = "get file in ('" + inDirectory1 + "', '" + inDirectory2
				+ "' deep -1, '" + inDirectory3 + "');";
		Statement fileQuery = JFSQL.parseOneStatement(query);

		// WHEN
		List<Comparable> results = fileQuery.execute(new HashMap<String, Comparable>());

		// THEN
		assertThat(results).isNotNull().hasSize(3);
		assertThat(results).containsOnly(file1, file2, file3);
	}
}
