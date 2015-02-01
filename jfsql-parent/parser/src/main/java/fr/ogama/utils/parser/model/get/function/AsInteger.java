package fr.ogama.utils.parser.model.get.function;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import fr.ogama.utils.parser.JFSQLExecutionException;

public class AsInteger extends AbstractFunction {

	public AsInteger(String operator) {
		super(operator);
	}

	@Override
	public List<Comparable> execute(Map<String, Comparable> params)
			throws JFSQLExecutionException {
		if (nbOperands() != 1) {
			throw new IllegalArgumentException(
					"AsInteger : Expected 1 arguments but found "
							+ nbOperands());
		}

		List<Comparable> firstArgumentResult = getOperand(0).execute(params);

		Comparable object = firstArgumentResult.get(0);
		try {
			return Arrays
					.asList((Comparable) Integer.valueOf(object.toString()));
		} catch (Exception e) {
			throw new JFSQLExecutionException(e.getMessage(), e);
		}
	}
}
