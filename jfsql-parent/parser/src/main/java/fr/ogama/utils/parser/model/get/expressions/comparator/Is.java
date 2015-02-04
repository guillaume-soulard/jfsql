package fr.ogama.utils.parser.model.get.expressions.comparator;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import fr.ogama.utils.parser.JFSQLExecutionException;
import fr.ogama.utils.parser.model.get.Expression;
import fr.ogama.utils.parser.model.get.ExpressionImpl;

public class Is extends ExpressionImpl {

	public Is(String operator, Expression o1, Expression o2) {
		super(operator, o1, o2);
	}

	public Is(String op, Expression o1) {
		super(op, o1);
	}

	public Is(String operator) {
		super(operator);
	}

	public List<Comparable> execute(Map<String, Comparable> params) throws JFSQLExecutionException {
		if (nbOperands() != 2) {
			throw new IllegalArgumentException(
					"Is expression must have 2 operands");
		}
		
		List<Comparable> result1 = new LinkedList<Comparable>();
		List<Comparable> operand2Results = getOperand(1).execute(params);
		
		for (Expression e : getOperands()) {
			if (!e.equals(getOperand(1))) {
				result1.addAll(e.execute(params));
			}
		}

		if (operand2Results.size() != 1 || result1.size() <= 0) {
			throw new IllegalArgumentException("Too few operand results");
		}

		Comparable result2 = operand2Results.get(0);

		return Arrays
				.asList((Comparable) new Boolean(result1.contains(result2)));
	}
}
