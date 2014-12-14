package fr.ogama.jfsql.query.clause.sort;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import fr.ogama.jfsql.query.Query;
import fr.ogama.jfsql.query.QueryFactory;

@Ignore
public class SortTest {

	private static String directory;
	
	@BeforeClass
	public static void setup() {
		directory = System.getProperty("user.dir") + "/src/test/resources/sortDirectory";
		
		assertThat(new File(directory)).exists().isDirectory();
	}
	
	@Test
	public void should_get_in_given_directory() throws Exception {
		// GIVEN
		String query = "get file in('" + directory + "') sort by ";
		Query fileQuery = QueryFactory.newQuery(query);
		
		// WHEN
		List<Comparable> results = fileQuery.execute();
		
		// THEN
		assertThat(results).isNotNull().hasSize(1);
	}
}
