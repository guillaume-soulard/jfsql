package fr.ogama.utils.parser;

import static org.assertj.core.api.Assertions.*;

import java.io.File;
import java.io.FileFilter;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.junit.Test;

import fr.ogama.utils.parser.model.Statement;
import fr.ogama.utils.parser.model.get.GetStatement;

public class QueryTest {
	@Test
	public void should_return_directories_on_root() throws Exception {
		// GIVEN
		JFSQLParser jfsqlParser = new JFSQLParser();
		Vector<Statement> statements = jfsqlParser
				.parse("get name in ('/' deep 1) having type = 'directory' sort by name ascending;");
		GetStatement statement = (GetStatement) statements.get(0);

		// WHEN
		List<Comparable> result = statement
				.execute(new HashMap<String, Comparable>());

		// THEN
		assertThat(result).isNotEmpty();
	}

	@Test
	public void should_return_a_part_of_date() throws Exception {
		// GIVEN
		JFSQLParser jfsqlParser = new JFSQLParser();
		Vector<Statement> statements = jfsqlParser
				.parse("get asDateString(creation_date, 'yyyy') in ('/' deep 1) having type = 'directory' sort by name ascending;");
		GetStatement statement = (GetStatement) statements.get(0);

		// WHEN
		List<Comparable> results = statement
				.execute(new HashMap<String, Comparable>());

		// THEN
		assertThat(results).isNotEmpty();
		for (Comparable result : results) {
			assertThat(result).isExactlyInstanceOf(String.class);
			assertThat((String) result).hasSize(4);
		}
	}

	@Test
	public void should_return_the_number_of_directories_in_root()
			throws Exception {
		// GIVEN
		JFSQLParser jfsqlParser = new JFSQLParser();
		Vector<Statement> statements = jfsqlParser
				.parse("count(get file in ('/' deep 1) having type = 'directory' sort by name ascending);");
		Statement statement = statements.get(0);

		File[] directoriesOnRoot = new File("/").listFiles(new FileFilter() {
			
			public boolean accept(File pathname) {
				return pathname.isDirectory();
			}
		});
		
		// WHEN
		List<Comparable> result = statement
				.execute(new HashMap<String, Comparable>());

		// THEN
		assertThat(result).hasSize(1);
		assertThat(result.get(0)).isExactlyInstanceOf(Integer.class);
		assertThat(result.get(0)).isEqualTo(directoriesOnRoot.length);
	}
}
