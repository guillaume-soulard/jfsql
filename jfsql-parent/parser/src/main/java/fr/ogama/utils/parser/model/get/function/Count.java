package fr.ogama.utils.parser.model.get.function;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import fr.ogama.utils.parser.JFSQLExecutionException;
import fr.ogama.utils.parser.model.get.Expression;

public class Count extends AbstractFunction {

	public Count(String name) {
		super(name);
	}

	@Override
	public List<Comparable> execute(Map<String, Comparable> params)
			throws JFSQLExecutionException {
		Integer counter = new Integer(0);
		
		for (Expression param : getOperands()) {
			if (param != null) {
				List<Comparable> result = param.execute(params);
				counter += result.size();
			}
 		}

		return Arrays.asList((Comparable) counter);
	}
}
