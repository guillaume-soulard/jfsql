package fr.ogama.utils.parser;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import fr.ogama.utils.parser.model.get.Expression;
import fr.ogama.utils.parser.model.get.expressions.logical.AndExpression;

public class TestLogicalOperators {

	@Test
	public void should_test_and_operator() throws Exception {
		// GIVEN
		AndExpression trueAndExpression = new AndExpression("and",
				new Expression() {

					public List<Comparable> execute(Map<String, Comparable> params) throws JFSQLExecutionException {
						return Arrays.asList((Comparable) Boolean.TRUE);
					}
				}, new Expression() {

					public List<Comparable> execute(Map<String, Comparable> params) throws JFSQLExecutionException {
						return Arrays.asList((Comparable) Boolean.TRUE);
					}
				});

		AndExpression falseAndExpression = new AndExpression("and",
				new Expression() {

					public List<Comparable> execute(Map<String, Comparable> params) throws JFSQLExecutionException {
						return Arrays.asList((Comparable) Boolean.TRUE);
					}
				}, new Expression() {

					public List<Comparable> execute(Map<String, Comparable> params) throws JFSQLExecutionException {
						return Arrays.asList((Comparable) Boolean.FALSE);
					}
				});

		// WHEN
		List<Comparable> trueResult = trueAndExpression.execute(new HashMap<String, Comparable>());
		List<Comparable> falseResult = falseAndExpression.execute(new HashMap<String, Comparable>());
		
		// THEN
		assertThat(trueResult).hasSize(1);
		assertThat(trueResult.get(0)).isExactlyInstanceOf(Boolean.class);
		assertThat((Boolean)trueResult.get(0)).isTrue();
		
		assertThat(falseResult).hasSize(1);
		assertThat(falseResult.get(0)).isExactlyInstanceOf(Boolean.class);
		assertThat((Boolean)falseResult.get(0)).isFalse();
	}
	
	@Test(expected = Exception.class)
	public void should_exception_thrown_with_incorrect_and_expression() throws Exception {
		// GIVEN
		AndExpression errorAndExpression = new AndExpression("and",
				new Expression() {

					public List<Comparable> execute(Map<String, Comparable> params) throws JFSQLExecutionException {
						return Arrays.asList((Comparable) Boolean.TRUE,
								(Comparable) Boolean.TRUE);
					}
				}, new Expression() {

					public List<Comparable> execute(Map<String, Comparable> params) throws JFSQLExecutionException {
						return Arrays.asList((Comparable) Boolean.TRUE);
					}
				});
		
		//WHEN
		List<Comparable> errorResult = errorAndExpression.execute(new HashMap<String, Comparable>());
		
		// THEN
		// Exception
	}
	
}
