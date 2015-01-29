package fr.ogama.utils.parser.model.get.expressions.logical;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import fr.ogama.utils.parser.JFSQLExecutionException;
import fr.ogama.utils.parser.model.get.Expression;
import fr.ogama.utils.parser.model.get.ExpressionImpl;

public class NotExpression extends ExpressionImpl {

	public NotExpression(String operator, Expression o1, Expression o2) {
		super(operator, o1, o2);
	}

	public NotExpression(String op, Expression o1) {
		super(op, o1);
	}

	public NotExpression(String operator) {
		super(operator);
	}
	
	public List<Comparable> execute(Map<String, Comparable> params) throws JFSQLExecutionException {
		if (nbOperands() != 1) {
			throw new IllegalArgumentException(
					"Not expression must have 1 operands");
		}

		List<Comparable> operandResults = getOperand(0).execute(params);

		if (operandResults.size() != 1) {
			throw new IllegalArgumentException("Too operand results");
		}

		if (!(operandResults.get(0) instanceof Boolean)) {
			throw new IllegalArgumentException(
					"Logical operand result must be boolean");
		}

		Boolean result = (Boolean) operandResults.get(0);

		return Arrays.asList((Comparable) new Boolean(!result));
	}
}
