package fr.ogama.jfsql.query;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Vector;

import org.gibello.zql.ParseException;
import org.gibello.zql.ZStatement;
import org.gibello.zql.ZqlParser;
import org.junit.Ignore;
import org.junit.Test;

public class ZQLTest {

	@Test
	public void should_parse_sql_queries() throws ParseException {
		String query = "select file_or_directory from in__slash_home_slash_ogama, in__slash_opt_slash_tomcat where name like 'test' or (author = 'toto' and path in ('_slash_home_slash_ogama', '_slash_opt_slash_tomcat'));";
		InputStream in = new ByteArrayInputStream(query.getBytes());
		ZqlParser parser = new ZqlParser(in);
		Vector<ZStatement> statements = parser.readStatements();
		assertThat(statements).isNotEmpty();
	}

	@Ignore
	@Test
	public void should_validate_aggregation_functions() throws ParseException {
		String query = "select max(size) from directory where asDate(extract(content, '[0-9]{4}/[0-9]{2}/[0-9]{2}'))";
		InputStream in = new ByteArrayInputStream(query.getBytes());
		ZqlParser parser = new ZqlParser(in);
		Vector<ZStatement> statements = parser.readStatements();
		assertThat(statements).isNotEmpty();
	}
}
