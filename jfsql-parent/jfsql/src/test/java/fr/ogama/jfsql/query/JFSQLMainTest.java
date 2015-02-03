package fr.ogama.jfsql.query;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import fr.ogama.jfsql.JFSQL;
import fr.ogama.utils.parser.JFSQLExecutionException;
import fr.ogama.utils.parser.model.Statement;

public class JFSQLMainTest {

	private ByteArrayOutputStream out_baos;
	private ByteArrayOutputStream err_baos;

	@Before
	public void setup() {
		out_baos = new ByteArrayOutputStream();
		err_baos = new ByteArrayOutputStream();
		System.setOut(new PrintStream(out_baos));
		System.setErr(new PrintStream(err_baos));
	}

	@Test
	public void should_parse_one_statement() throws JFSQLExecutionException {
		// GIVEN
		String query = "get file in('/' deep 1);";

		// WHEN
		JFSQL.main(new String[] { query });

		// THEN
		String out = new String(out_baos.toByteArray());
		String err = new String(err_baos.toByteArray());
		assertThat(out).isNotEmpty();
		assertThat(err).isEmpty();
	}

	@Test
	public void should_get_well_formated_results()
			throws JFSQLExecutionException {
		// GIVEN
		String query = "concat('Size : ', sum(get size in('/' deep 4) having status > 'unaccessible'));";

		// WHEN
		JFSQL.main(new String[] { query });

		// THEN
		String out = new String(out_baos.toByteArray());
		String err = new String(err_baos.toByteArray());
		assertThat(out).isNotEmpty();
		assertThat(err).isEmpty();
	}

	@Test
	public void should_parse_multi_statements() throws JFSQLExecutionException {
		// GIVEN
		String query = "get file in('/' deep 1);get file in('/' deep 1);get file in('/' deep 1);get file in('/' deep 1);get file in('/' deep 1);get file in('/' deep 1);get file in('/' deep 1);get file in('/' deep 1);get file in('/' deep 1);get file in('/' deep 1);";

		// WHEN
		JFSQL.main(new String[] { query });

		// THEN
		String out = new String(out_baos.toByteArray());
		String err = new String(err_baos.toByteArray());
		assertThat(out).isNotEmpty();
		assertThat(err).isEmpty();
	}
}
