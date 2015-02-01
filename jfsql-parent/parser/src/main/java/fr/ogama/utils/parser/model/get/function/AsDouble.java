package fr.ogama.utils.parser.model.get.function;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import fr.ogama.utils.parser.JFSQLExecutionException;

public class AsDouble extends AbstractFunction {

	public AsDouble(String operator) {
		super(operator);
	}

	@Override
	public List<Comparable> execute(Map<String, Comparable> params)
			throws JFSQLExecutionException {
		if (nbOperands() != 1) {
			throw new IllegalArgumentException(
					"AsFloat : Expected 1 arguments but found "
							+ nbOperands());
		}

		List<Comparable> firstArgumentResult = getOperand(0).execute(params);

		Comparable object = firstArgumentResult.get(0);
		try {
			return Arrays
					.asList((Comparable) Double.valueOf(object.toString()));
		} catch (Exception e) {
			throw new JFSQLExecutionException(e.getMessage(), e);
		}
	}
}