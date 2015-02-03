package fr.ogama.jfsql.query.function;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import fr.ogama.jfsql.JFSQL;
import fr.ogama.utils.parser.JFSQLExecutionException;
import fr.ogama.utils.parser.model.Statement;

public class FunctionTest {

	@Test
	public void should_test_count_function_standalone()
			throws JFSQLExecutionException {
		// GIVEN
		Statement query = JFSQL.parseOneStatement("count(1, 2, 3, 4);");

		// WHEN
		List<Comparable> results = query
				.execute(new HashMap<String, Comparable>());

		// THEN
		assertThat(results).hasSize(1);
		assertThat(results.get(0)).isExactlyInstanceOf(Integer.class);
		Integer countResult = (Integer) results.get(0);
		assertThat(countResult).isEqualTo(4);
	}

	@Test
	public void should_test_sum_function_standalone()
			throws JFSQLExecutionException {
		// GIVEN
		Statement query = JFSQL.parseOneStatement("sum(1, 2, 3.9, 4.4);");

		// WHEN
		List<Comparable> results = query
				.execute(new HashMap<String, Comparable>());

		// THEN
		assertThat(results).hasSize(1);
		assertThat(results.get(0)).isExactlyInstanceOf(Double.class);
		Double sumResult = (Double) results.get(0);
		assertThat(sumResult).isEqualTo(11.3d);
	}

	@Test
	public void should_test_max_function_standalone()
			throws JFSQLExecutionException {
		// GIVEN
		Statement query = JFSQL.parseOneStatement("max(1, 2, 3.9, 4.4);");

		// WHEN
		List<Comparable> results = query
				.execute(new HashMap<String, Comparable>());

		// THEN
		assertThat(results).hasSize(1);
		assertThat(results.get(0)).isExactlyInstanceOf(Double.class);
		Double maxResult = (Double) results.get(0);
		assertThat(maxResult).isEqualTo(4.4d);
	}

	@Test
	public void should_test_min_function_standalone()
			throws JFSQLExecutionException {
		// GIVEN
		Statement query = JFSQL.parseOneStatement("min(1, 2, 3.9, 4.4);");

		// WHEN
		List<Comparable> results = query
				.execute(new HashMap<String, Comparable>());

		// THEN
		assertThat(results).hasSize(1);
		assertThat(results.get(0)).isExactlyInstanceOf(Double.class);
		Double minResult = (Double) results.get(0);
		assertThat(minResult).isEqualTo(1d);
	}

	@Test
	public void should_test_avg_function_standalone()
			throws JFSQLExecutionException {
		// GIVEN
		Statement query = JFSQL.parseOneStatement("avg(1, 2, 3.9, 4.4);");

		// WHEN
		List<Comparable> results = query
				.execute(new HashMap<String, Comparable>());

		// THEN
		assertThat(results).hasSize(1);
		assertThat(results.get(0)).isExactlyInstanceOf(Double.class);
		Double avgResult = (Double) results.get(0);
		assertThat(avgResult).isEqualTo(2.825d);
	}

	@Test
	public void should_test_count_function_in_get()
			throws JFSQLExecutionException {
		// GIVEN
		Statement query = JFSQL
				.parseOneStatement("count(get file in ('/' deep 1));");
		int count = new File("/").listFiles().length;

		// WHEN
		List<Comparable> results = query
				.execute(new HashMap<String, Comparable>());

		// THEN
		assertThat(results).hasSize(1);
		assertThat(results.get(0)).isExactlyInstanceOf(Integer.class);
		Integer countResult = (Integer) results.get(0);
		assertThat(countResult).isEqualTo(count);
	}

	@Test
	public void should_test_asDate_function_in_get()
			throws JFSQLExecutionException {
		// GIVEN
		Statement query = JFSQL
				.parseOneStatement("get 1 asDate('2015/01/01', 'yyyy/MM/dd') in ('.' deep 1);");

		// WHEN
		List<Comparable> results = query
				.execute(new HashMap<String, Comparable>());

		// THEN
		Calendar dateToCompare = Calendar.getInstance();
		dateToCompare.set(Calendar.YEAR, 2015);
		dateToCompare.set(Calendar.MONTH, 0);
		dateToCompare.set(Calendar.DAY_OF_MONTH, 0);
		dateToCompare.set(Calendar.HOUR_OF_DAY, 24);
		dateToCompare.set(Calendar.MINUTE, 0);
		dateToCompare.set(Calendar.SECOND, 0);
		assertThat(results).hasSize(1);
		assertThat(results.get(0)).isExactlyInstanceOf(Date.class);
		Date date = (Date) results.get(0);
		assertThat(date).isInSameDayAs(dateToCompare.getTime());
	}

	@Test
	public void should_test_asDateString_function_in_get()
			throws JFSQLExecutionException {
		// GIVEN
		Statement query = JFSQL
				.parseOneStatement("get 1 asDateString(asDate('2015/01/01', 'yyyy/MM/dd'), 'yyyy-MM-dd') in ('.' deep 1);");

		// WHEN
		List<Comparable> results = query
				.execute(new HashMap<String, Comparable>());

		// THEN
		assertThat(results).hasSize(1);
		assertThat(results.get(0)).isExactlyInstanceOf(String.class);
		String date = (String) results.get(0);
		assertThat(date).isEqualTo("2015-01-01");
	}

	@Test
	public void should_test_concat_function_in_get()
			throws JFSQLExecutionException {
		// GIVEN
		Statement query = JFSQL
				.parseOneStatement("get 1 concat('String 1', 'String 2', 'String 3', 'String 3') in ('.' deep 1);");

		// WHEN
		List<Comparable> results = query
				.execute(new HashMap<String, Comparable>());

		// THEN
		assertThat(results).hasSize(1);
		assertThat(results.get(0)).isExactlyInstanceOf(String.class);
		String date = (String) results.get(0);
		assertThat(date).isEqualTo("String 1String 2String 3String 3");
	}

	@Test
	public void should_test_extract_function_in_get()
			throws JFSQLExecutionException {
		// GIVEN
		Statement query = JFSQL
				.parseOneStatement("get 1 extract('the date 2015/02/03 should be extract', '[0-9]{4}\\/[0-9]{2}\\/[0-9]{2}') in ('.' deep 1);");

		// WHEN
		List<Comparable> results = query
				.execute(new HashMap<String, Comparable>());

		// THEN
		assertThat(results).hasSize(1);
		assertThat(results.get(0)).isExactlyInstanceOf(String.class);
		String date = (String) results.get(0);
		assertThat(date).isEqualTo("2015/02/03");
	}

	@Test
	public void should_test_asInteger_function_in_get()
			throws JFSQLExecutionException {
		// GIVEN
		Statement query_1 = JFSQL
				.parseOneStatement("get 1 asInteger('1') in ('.' deep 1);");

		Statement query_minus_1 = JFSQL
				.parseOneStatement("get 1 asInteger('-1') in ('.' deep 1);");

		// WHEN
		List<Comparable> results_1 = query_1
				.execute(new HashMap<String, Comparable>());
		List<Comparable> results_minus_1 = query_minus_1
				.execute(new HashMap<String, Comparable>());

		// THEN
		assertThat(results_1).hasSize(1);
		assertThat(results_1.get(0)).isExactlyInstanceOf(Integer.class);
		Integer integer_1 = (Integer) results_1.get(0);
		assertThat(integer_1).isEqualTo(1);

		assertThat(results_minus_1).hasSize(1);
		assertThat(results_minus_1.get(0)).isExactlyInstanceOf(Integer.class);
		Integer integer_minus_1 = (Integer) results_minus_1.get(0);
		assertThat(integer_minus_1).isEqualTo(-1);
	}

	@Test
	public void should_test_asLong_function_in_get()
			throws JFSQLExecutionException {
		// GIVEN
		Statement query_1 = JFSQL
				.parseOneStatement("get 1 asDouble('10.265') in ('.' deep 1);");

		Statement query_minus_1 = JFSQL
				.parseOneStatement("get 1 asDouble('-16546.685') in ('.' deep 1);");

		// WHEN
		List<Comparable> results_1 = query_1
				.execute(new HashMap<String, Comparable>());
		List<Comparable> results_minus_1 = query_minus_1
				.execute(new HashMap<String, Comparable>());

		// THEN
		assertThat(results_1).hasSize(1);
		assertThat(results_1.get(0)).isExactlyInstanceOf(Double.class);
		Double integer_1 = (Double) results_1.get(0);
		assertThat(integer_1).isEqualTo(10.265);

		assertThat(results_minus_1).hasSize(1);
		assertThat(results_minus_1.get(0)).isExactlyInstanceOf(Double.class);
		Double integer_minus_1 = (Double) results_minus_1.get(0);
		assertThat(integer_minus_1).isEqualTo(-16546.685);
	}

	@Test
	public void should_test_asString_function_in_get()
			throws JFSQLExecutionException {
		// GIVEN
		Statement query_1 = JFSQL
				.parseOneStatement("get 1 asString(1) in ('.' deep 1);");

		Statement query_minus_1 = JFSQL
				.parseOneStatement("get 1 asString(-1) in ('.' deep 1);");

		// WHEN
		List<Comparable> results_1 = query_1
				.execute(new HashMap<String, Comparable>());
		List<Comparable> results_minus_1 = query_minus_1
				.execute(new HashMap<String, Comparable>());

		// THEN
		assertThat(results_1).hasSize(1);
		assertThat(results_1.get(0)).isExactlyInstanceOf(String.class);
		String string_1 = (String) results_1.get(0);
		assertThat(string_1).isEqualTo("1");

		assertThat(results_minus_1).hasSize(1);
		assertThat(results_minus_1.get(0)).isExactlyInstanceOf(String.class);
		String string_minus_1 = (String) results_minus_1.get(0);
		assertThat(string_minus_1).isEqualTo("-1");
	}

	@Test
	public void should_concat_strings_with_specials_caracters()
			throws JFSQLExecutionException {
		// GIVEN
		Statement query = JFSQL
				.parseOneStatement("concat('Ogama''s', ' home');");

		// WHEN
		List<Comparable> results = query
				.execute(new HashMap<String, Comparable>());

		// THEN
		assertThat(results).hasSize(1);
		assertThat(results.get(0)).isEqualTo("Ogama's home");
	}

	@Test
	public void should_concat_strings_format() throws JFSQLExecutionException {
		// GIVEN
		Statement query = JFSQL
				.parseOneStatement("concat('Size : ', sum(get size in('/'deep 2)));");

		// WHEN
		List<Comparable> results = query
				.execute(new HashMap<String, Comparable>());

		// THEN
		assertThat(results).hasSize(1);
	}
}
