package fr.ogama.utils.parser.model.get.function;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import fr.ogama.utils.parser.JFSQLExecutionException;

public class AsDateString extends AbstractFunction {

	public AsDateString(String operator) {
		super(operator);
	}

	@Override
	public List<Comparable> execute(Map<String, Comparable> params)
			throws JFSQLExecutionException {
		if (nbOperands() != 2) {
			throw new IllegalArgumentException(
					"AsDateString : Expected 2 arguments but found "
							+ nbOperands());
		}

		List<Comparable> firstArgumentResult = getOperand(0).execute(params);
		List<Comparable> secondArgumentResult = getOperand(1).execute(params);

		if (firstArgumentResult.size() != 1
				&& !(firstArgumentResult.get(0) instanceof Date)) {
			throw new IllegalArgumentException(
					"AsDateString first argument must be a Date");
		}

		if (secondArgumentResult.size() != 1
				&& !(secondArgumentResult.get(0) instanceof String)) {
			throw new IllegalArgumentException(
					"AsDateString second argument must be a String");
		}
		
		Date date = (Date) firstArgumentResult.get(0);
		String format = (String) secondArgumentResult.get(0);
		
		return Arrays.asList((Comparable) new SimpleDateFormat(format).format(date));
	}
}
