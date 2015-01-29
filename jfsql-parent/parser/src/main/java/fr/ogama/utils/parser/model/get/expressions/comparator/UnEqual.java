package fr.ogama.utils.parser.model.get.expressions.comparator;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import fr.ogama.utils.parser.JFSQLExecutionException;
import fr.ogama.utils.parser.model.get.Expression;
import fr.ogama.utils.parser.model.get.ExpressionImpl;

public class UnEqual extends ExpressionImpl {

	public UnEqual(String operator, Expression o1, Expression o2) {
		super(operator, o1, o2);
	}

	public UnEqual(String op, Expression o1) {
		super(op, o1);
	}

	public UnEqual(String operator) {
		super(operator);
	}
	
	public List<Comparable> execute(Map<String, Comparable> params) throws JFSQLExecutionException {
		if (nbOperands() != 2) {
			throw new IllegalArgumentException(
					"<> expression must have 2 operands");
		}

		List<Comparable> operand1Results = getOperand(0).execute(params);
		List<Comparable> operand2Results = getOperand(1).execute(params);

		if (operand1Results.size() != 1 || operand2Results.size() != 1) {
			throw new IllegalArgumentException("Too operand results");
		}

		Comparable result1 = operand1Results.get(0);
		Comparable result2 = operand2Results.get(0);

		return Arrays.asList((Comparable) new Boolean(!result1.equals(result2)));
	}
}
