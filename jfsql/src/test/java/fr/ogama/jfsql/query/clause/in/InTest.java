package fr.ogama.jfsql.query.clause.in;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.ogama.jfsql.query.Query;
import fr.ogama.jfsql.query.QueryFactory;

public class InTest {

	private static String inDirectory1;
	private static String inDirectory2;
	private static String inDirectory3;
	
	private static File file1;
	private static File file2;
	private static File file3;
	
	@BeforeClass
	public static void setup() {
		inDirectory1 = System.getProperty("user.dir") + "/src/test/resources/inTestFolder/Folder 1";
		inDirectory2 = System.getProperty("user.dir") + "/src/test/resources/inTestFolder/Folder 2";
		inDirectory3 = System.getProperty("user.dir") + "/src/test/resources/inTestFolder/Folder 3";
		
		file1 = new File(inDirectory1 + "/Folder 1.1/File 1.txt");
		file2 = new File(inDirectory2 + "/Folder 2.1/Folder 2.2/File 2.txt");
		file3 = new File(inDirectory3 + "/Folder 3.1/Folder 3.2/Folder 3.3/File 3.txt");
		
		assertThat(file1).exists().isFile();
		assertThat(file2).exists().isFile();
		assertThat(file3).exists().isFile();
		
		assertThat(new File(inDirectory1)).exists().isDirectory();
		assertThat(new File(inDirectory2)).exists().isDirectory();
		assertThat(new File(inDirectory3)).exists().isDirectory();
	}
	
	@Test
	public void should_get_in_given_directory() throws Exception {
		// GIVEN
		String query = "get file in('" + inDirectory1 + "')";
		Query fileQuery = QueryFactory.newQuery(query);
		
		// WHEN
		List<Comparable> results = fileQuery.execute();
		
		// THEN
		assertThat(results).isNotNull().hasSize(1);
		assertThat(results).containsOnly(file1);
	}
	
	@Test
	public void should_get_directories_in_current_directory() throws Exception {
		String query = "get file in ('.')";
		Query fileQuery = QueryFactory.newQuery(query);
		
		// WHEN
		List<Comparable> results = fileQuery.execute();
		
		// THEN
		assertThat(results).isNotNull();
	}
	
	@Test
	public void should_get_in_given_directories() throws Exception {
		String query = "get file in ('" + inDirectory1 + "', '" + inDirectory2 + "')";
		Query fileQuery = QueryFactory.newQuery(query);
		
		// WHEN
		List<Comparable> results = fileQuery.execute();
		
		// THEN
		assertThat(results).isNotNull().hasSize(2);
		assertThat(results).containsOnly(file1, file2);
	}

	@Test(expected = Exception.class)
	public void should_get_error_because_directory_not_exists() throws Exception {
		String query = "get file in ('/directory/not/exists')";
		Query fileQuery = QueryFactory.newQuery(query);
		
		// WHEN
		List<Comparable> results = fileQuery.execute();
		
		// THEN
	}
	
	@Test(expected = Exception.class)
	public void should_get_error_because_directories_not_exists() throws Exception {
		String query = "get file in ('/directory/not/exists', '/and/this/too')";
		Query fileQuery = QueryFactory.newQuery(query);
		
		// WHEN
		List<Comparable> results = fileQuery.execute();
		
		// THEN
	}
	
	@Test(expected = Exception.class)
	public void should_get_error_because_one_directory_not_exists() throws Exception {
		String query = "get file in ('/directory/not/exists', '" + inDirectory1 + "')";
		Query fileQuery = QueryFactory.newQuery(query);
		
		// WHEN
		List<Comparable> results = fileQuery.execute();
		
		// THEN
	}
	
	@Test(expected = Exception.class)
	public void should_get_error_because_synthax_error_quote() throws Exception {
		String query = "get file in (" + inDirectory1 + "')";
		Query fileQuery = QueryFactory.newQuery(query);
		
		// WHEN
		
		// THEN
	}
	
	@Test(expected = Exception.class)
	public void should_get_error_because_synthax_error_parenthesis() throws Exception {
		String query = "get file in '" + inDirectory1 + "')";
		Query fileQuery = QueryFactory.newQuery(query);
		
		// WHEN
		
		// THEN
	}
	
	@Test(expected = Exception.class)
	public void should_get_error_because_synthax_error_no_path_specified() throws Exception {
		String query = "get file in ()";
		Query fileQuery = QueryFactory.newQuery(query);
		
		// WHEN
		
		// THEN
	}
	
	@Test(expected = Exception.class)
	public void should_get_error_because_empty() throws Exception {
		String query = "get file in ('')";
		Query fileQuery = QueryFactory.newQuery(query);
		
		// WHEN
		
		// THEN
	}
}
