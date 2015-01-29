package fr.ogama.utils.parser.model.get.expressions.comparator;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import fr.ogama.utils.parser.JFSQLExecutionException;
import fr.ogama.utils.parser.model.get.Expression;
import fr.ogama.utils.parser.model.get.ExpressionImpl;

public class In extends ExpressionImpl {

	public In(String operator, Expression o1, Expression o2) {
		super(operator, o1, o2);
	}

	public In(String op, Expression o1) {
		super(op, o1);
	}

	public In(String operator) {
		super(operator);
	}

	public List<Comparable> execute(Map<String, Comparable> params) throws JFSQLExecutionException {
		if (nbOperands() < 2) {
			throw new IllegalArgumentException(
					"in expression must have at least 2 operands");
		}

		List<Comparable> operand1Results = getOperand(0).execute(params);
		List<Comparable> result2 = new LinkedList<Comparable>();

		for (Expression e : getOperands()) {
			if (!e.equals(getOperand(0))) {
				result2.addAll(e.execute(params));
			}
		}

		if (operand1Results.size() != 1 || result2.size() <= 0) {
			throw new IllegalArgumentException("Too few operand results");
		}

		Comparable result1 = operand1Results.get(0);

		return Arrays
				.asList((Comparable) new Boolean(result2.contains(result1)));
	}
}