package fr.ogama.utils.parser.model.get.function;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.ogama.utils.parser.JFSQLExecutionException;

public class Extract extends AbstractFunction {

	public Extract(String operator) {
		super(operator);
	}

	@Override
	public List<Comparable> execute(Map<String, Comparable> params)
			throws JFSQLExecutionException {
		if (nbOperands() < 2) {
			throw new IllegalArgumentException(
					"Extract : Expected 2 arguments but found "
							+ nbOperands());
		}

		List<Comparable> firstArgumentResult = getOperand(0).execute(params);
		List<Comparable> secondArgumentResult = getOperand(1).execute(params);

		if (firstArgumentResult.size() != 1
				&& !(firstArgumentResult.get(0) instanceof String)) {
			throw new IllegalArgumentException(
					"Extract first argument must be a String");
		}

		if (secondArgumentResult.size() != 1
				&& !(secondArgumentResult.get(0) instanceof String)) {
			throw new IllegalArgumentException(
					"Extract second argument must be a String");
		}
		
		String object = (String) firstArgumentResult.get(0);
		String format = (String) secondArgumentResult.get(0);
		
		String result = "";
		
		Pattern pattern = Pattern.compile(format);
		Matcher matcher = pattern.matcher(object);
		if (matcher.find())
		{
		    result = matcher.group();
		}
				
		return Arrays.asList((Comparable) result);
	}
}