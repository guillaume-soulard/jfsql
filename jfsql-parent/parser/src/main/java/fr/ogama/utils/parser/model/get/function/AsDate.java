package fr.ogama.utils.parser.model.get.function;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import fr.ogama.utils.parser.JFSQLExecutionException;

public class AsDate extends AbstractFunction {

	public AsDate(String operator) {
		super(operator);
	}

	@Override
	public List<Comparable> execute(Map<String, Comparable> params)
			throws JFSQLExecutionException {
		if (nbOperands() != 2) {
			throw new IllegalArgumentException(
					"AsDate : Expected 2 arguments but found " + nbOperands());
		}

		List<Comparable> firstArgumentResult = getOperand(0).execute(params);
		List<Comparable> secondArgumentResult = getOperand(1).execute(params);

		if (firstArgumentResult.size() != 1
				&& !(firstArgumentResult.get(0) instanceof String)) {
			throw new IllegalArgumentException(
					"AsDate first argument must be a String");
		}

		if (secondArgumentResult.size() != 1
				&& !(secondArgumentResult.get(0) instanceof String)) {
			throw new IllegalArgumentException(
					"AsDate second argument must be a String");
		}

		String format = (String) secondArgumentResult.get(0);

		try {
			if (firstArgumentResult.get(0) instanceof Date) {
				Date date = (Date) firstArgumentResult.get(0);

				return Arrays.asList(((Comparable) new SimpleDateFormat(format)
						.parse(new SimpleDateFormat(format).format(date))));
			} else {
				String dateString = (String) firstArgumentResult.get(0);

				return Arrays.asList((Comparable) new SimpleDateFormat(format)
						.parse(dateString));
			}
		} catch (ParseException e) {
			throw new JFSQLExecutionException(e.getMessage(), e);
		}
	}
}
