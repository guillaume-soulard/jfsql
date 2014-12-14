package fr.ogama.jfsql.query.clause.get;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.ogama.jfsql.query.Query;
import fr.ogama.jfsql.query.QueryFactory;

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
		String fileOrDirectory = "get 2 file or directory in ('"
				+ getTestDirectory + "')";
		Query fileOrDirectoryQuery = QueryFactory.newQuery(fileOrDirectory);

		// WHEN
		List<Comparable> fileOrDirectoryResults = fileOrDirectoryQuery
				.execute();

		// THEN
		assertThat(fileOrDirectoryResults).isNotNull().hasSize(2);
	}
	
	@Test
	public void should_get_0_files_and_directories() throws Exception {
		// GIVEN
		String fileOrDirectory = "get 0 file or directory in ('"
				+ getTestDirectory + "')";
		Query fileOrDirectoryQuery = QueryFactory.newQuery(fileOrDirectory);

		// WHEN
		List<Comparable> fileOrDirectoryResults = fileOrDirectoryQuery
				.execute();

		// THEN
		assertThat(fileOrDirectoryResults).isNotNull().isEmpty();
	}
	
	@Test(expected = Exception.class)
	public void should_get_error_on_negative_numbers() throws Exception {
		// GIVEN
		String fileOrDirectory = "get -1 file or directory in ('"
				+ getTestDirectory + "')";
		Query fileOrDirectoryQuery = QueryFactory.newQuery(fileOrDirectory);

		// WHEN

		// THEN
	}
	
	@Test(expected = Exception.class)
	public void should_get_error_on_strings() throws Exception {
		// GIVEN
		String fileOrDirectory = "get one file or directory in ('"
				+ getTestDirectory + "')";
		Query fileOrDirectoryQuery = QueryFactory.newQuery(fileOrDirectory);

		// WHEN
		List<Comparable> fileOrDirectoryResults = fileOrDirectoryQuery
				.execute();

		// THEN
	}
}
