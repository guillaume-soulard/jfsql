package fr.ogama.jfsql.query;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class QueryTest {

	private String directory;
	
	@Before
	public void setup() {
		String workingDir = System.getProperty("user.dir");
		directory = workingDir + "/src/test/resources/TestFolder";
	}
	
	@Test
	public void should_create_query() throws Exception {
		Query query = QueryFactory.newQuery("get file in ('" + directory + "');");		
		assertThat(query).isNotNull();
	}
	
	@Test
	public void should_return_files() throws Exception {
		Query query = QueryFactory.newQuery("get file in ('" + directory +"');");
		List<Comparable> objects = query.execute();
		assertThat(objects).isNotNull().isNotEmpty();
		assertThat(objects).hasOnlyElementsOfType(File.class);
	}
	
	@Test
	public void should_return_only_3_files() throws Exception {
		Query query = QueryFactory.newQuery("get 3 file in ('" + directory +"');");
		List<Comparable> objects = query.execute();
		assertThat(objects).isNotNull().hasSize(3);
		assertThat(objects).hasOnlyElementsOfType(File.class);
	}
	
	@Test
	public void should_return_only_3_directory() throws Exception {
		Query query = QueryFactory.newQuery("get 3 file in ('" + directory +"') having type = 'directory';");
		List<Comparable> objects = query.execute();
		assertThat(objects).isNotNull().hasSize(3);
		assertThat(objects).hasOnlyElementsOfType(File.class);
	}
	
	@Test
	public void should_return_filtered_files() throws Exception {
		Query query = QueryFactory.newQuery("get file in ('" + directory +"') having name = 'File with content.txt';");
		List<Comparable> objects = query.execute();
		assertThat(objects).isNotNull().hasSize(1);
		assertThat(objects).hasOnlyElementsOfType(File.class);
	}
	
	@Test
	public void should_return_filtered_directories() throws Exception {
		Query query = QueryFactory.newQuery("get file in ('" + directory +"') having type = 'directory' and name = 'Level 1' or name = 'Level 2';");
		List<Comparable> objects = query.execute();
		assertThat(objects).isNotNull().hasSize(2);
		assertThat(objects).hasOnlyElementsOfType(File.class);
	}
	
	@Test
	public void should_return_filtered_directories_with_in_clause() throws Exception {
		Query query = QueryFactory.newQuery("get file in ('" + directory +"') having type = 'directory' and name in ('Level 1', 'Level 2');");
		List<Comparable> objects = query.execute();
		assertThat(objects).isNotNull().hasSize(2);
		assertThat(objects).hasOnlyElementsOfType(File.class);
	}
	
	@Test
	public void should_return_filtered_directories_by_path() throws Exception {
		String path = directory + "/Level 2/Level 2 2";
		Query query = QueryFactory.newQuery("get file in ('" + directory +"') having type = 'directory' and path = '" + path + "';");
		List<Comparable> objects = query.execute();
		assertThat(objects).isNotNull().hasSize(1);
		assertThat(objects).hasOnlyElementsOfType(File.class);
	}
	
	@Test
	public void should_return_one_file_with_complex_having__clause() throws Exception {
		Query query = QueryFactory.newQuery("get file in ('" + directory +"') having parent like 'Level' and (name like 'File' or content like 'Content');");
		List<Comparable> objects = query.execute();
		assertThat(objects).isNotNull().hasSize(4);
		assertThat(objects).hasOnlyElementsOfType(File.class);
	}
	
	@Test
	public void should_execute_multi_line_and_spaces_query() throws Exception {
		// GIVEN		
		Query query = QueryFactory.newQuery("get     file \nin ('/' deep 1)\nhaving   type  =   'directory' \nand name like 'o';");
		
		// WHEN
		List<Comparable> objects = query.execute();
		
		// THEN
		assertThat(objects).isNotEmpty();
		assertThat(objects).hasOnlyElementsOfType(File.class);
	}
}
