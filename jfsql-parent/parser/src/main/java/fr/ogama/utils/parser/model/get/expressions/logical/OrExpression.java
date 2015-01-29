package fr.ogama.utils.parser.model.get.expressions.logical;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import fr.ogama.utils.parser.JFSQLExecutionException;
import fr.ogama.utils.parser.model.get.Expression;
import fr.ogama.utils.parser.model.get.ExpressionImpl;

public class OrExpression extends ExpressionImpl {
	
	
	
	public OrExpression(String operator, Expression o1, Expression o2) {
		super(operator, o1, o2);
	}

	public OrExpression(String op, Expression o1) {
		super(op, o1);
	}

	public OrExpression(String operator) {
		super(operator);
	}

	public List<Comparable> execute(Map<String, Comparable> params) throws JFSQLExecutionException {
		if (nbOperands() < 2) {
			throw new IllegalArgumentException(
					"Or expression must have at least 2 operands");
		}
		
		for (Expression expression : getOperands()) {
			List<Comparable> operandResults = expression.execute(params);
			
			if (operandResults.size() != 1) {
				throw new IllegalArgumentException("Too operand results");
			}

			if (!(operandResults.get(0) instanceof Boolean)) {
				throw new IllegalArgumentException(
						"Logical operand result must be boolean");
			}

			if ((Boolean) operandResults.get(0)) {
				return Arrays.asList((Comparable) Boolean.TRUE);
			}
		}

		return Arrays.asList((Comparable) Boolean.FALSE);
	}
}
