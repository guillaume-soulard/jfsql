package fr.ogama.jfsql.query.clause.sort;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import fr.ogama.jfsql.JFSQL;
import fr.ogama.utils.parser.model.Statement;

public class SortTest {

	private static String nameDirectory;
	private static File aaa;
	private static File bbb;
	private static File ccc;
	
	@BeforeClass
	public static void setup() {
		nameDirectory = System.getProperty("user.dir") + "/src/test/resources/sortDirectory/name";
		aaa = new File(nameDirectory + "/aaa.txt");
		bbb = new File(nameDirectory + "/bbb.txt");
		ccc = new File(nameDirectory + "/ccc.txt");
		
		assertThat(new File(nameDirectory)).exists().isDirectory();
		assertThat(aaa).exists().isFile();
		assertThat(bbb).exists().isFile();
		assertThat(ccc).exists().isFile();
	}
	
	@Test
	public void should_get_in_ascending_and_descending_order() throws Exception {
		// GIVEN		
		String ascendingQuery = "get file in('" + nameDirectory + "') sort by name ascending;";
		Statement ascendingFileQuery = JFSQL.parseOneStatement(ascendingQuery);
		
		String descendingQuery = "get file in('" + nameDirectory + "') sort by name descending;";
		Statement descendingFileQuery = JFSQL.parseOneStatement(descendingQuery);
		
		// WHEN
		List<Comparable> ascendingResults = ascendingFileQuery.execute(new HashMap<String, Comparable>());
		List<Comparable> descendingResults = descendingFileQuery.execute(new HashMap<String, Comparable>());
		
		// THEN
		assertThat(ascendingResults).isNotNull().hasSize(3);
		assertThat(ascendingResults).containsExactly(aaa, bbb, ccc);
		
		assertThat(descendingResults).isNotNull().hasSize(3);
		assertThat(descendingResults).containsExactly(ccc, bbb, aaa);
	}
	
	@Test
	public void should_get_with_limit() throws Exception {
		// GIVEN		
		String ascendingQuery = "get 2 file in('" + nameDirectory + "') sort by name ascending;";
		Statement ascendingFileQuery = JFSQL.parseOneStatement(ascendingQuery);
		
		String descendingQuery = "get 2 file in('" + nameDirectory + "') sort by name descending;";
		Statement descendingFileQuery = JFSQL.parseOneStatement(descendingQuery);
		
		// WHEN
		List<Comparable> ascendingResults = ascendingFileQuery.execute(new HashMap<String, Comparable>());
		List<Comparable> descendingResults = descendingFileQuery.execute(new HashMap<String, Comparable>());
		
		// THEN
		assertThat(ascendingResults).isNotNull().hasSize(2);
		assertThat(ascendingResults).containsExactly(aaa, bbb);
		
		assertThat(descendingResults).isNotNull().hasSize(2);
		assertThat(descendingResults).containsExactly(ccc, bbb);
	}
	
	@Test
	public void should_get_root_directories_size_sorted() throws Exception {
		// GIVEN		
		String ascendingQuery = "get size in('/' deep 1) sort by size ascending;";
		Statement ascendingFileQuery = JFSQL.parseOneStatement(ascendingQuery);
		
		// WHEN
		List<Comparable> ascendingResults = ascendingFileQuery.execute(new HashMap<String, Comparable>());
		
		// THEN
		assertThat(ascendingResults).isNotEmpty();
		assertThat(ascendingResults).isSorted();
	}
}
