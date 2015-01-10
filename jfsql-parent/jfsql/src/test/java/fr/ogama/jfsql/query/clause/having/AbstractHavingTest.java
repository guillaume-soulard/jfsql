package fr.ogama.jfsql.query.clause.having;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileOwnerAttributeView;
import java.nio.file.attribute.UserPrincipal;
import java.nio.file.attribute.UserPrincipalLookupService;


public class AbstractHavingTest {
	
	protected static String directory;
	
	protected static File regularFile;
	protected static File bigSizeFile;
	protected static File contentFile;
	protected static File creationDateFile;
	protected static File lastUpdateFile;
	protected static File lastAccessFile;
	protected static File nameFile;
	protected static File ownerFile;
	
	public static void setUp() throws IOException {
		directory = System.getProperty("user.dir") + "/src/test/resources/havingTestFolder";
		regularFile = new File(directory + "/Regular file.txt");
		bigSizeFile = new File(directory + "/File with big size.txt");
		contentFile = new File(directory + "/File with content to search.txt");
		creationDateFile = new File(directory + "/File with creation date 01-01-2014.txt");
		lastUpdateFile = new File(directory + "/File with last update date 01-02-2014.txt");
		lastAccessFile = new File(directory + "/File with last access date 01-03-2014.txt");
		nameFile = new File(directory + "/File with name to search.txt");
		
		assertThat(new File(directory)).isNotNull().exists();
		
		assertThat(regularFile).isNotNull().exists().isFile();
		assertThat(bigSizeFile).isNotNull().exists().isFile();
		assertThat(contentFile).isNotNull().exists().isFile();
		assertThat(creationDateFile).isNotNull().exists().isFile();
		assertThat(lastUpdateFile).isNotNull().exists().isFile();
		assertThat(lastAccessFile).isNotNull().exists().isFile();
		assertThat(nameFile).isNotNull().exists().isFile();
	}
}
