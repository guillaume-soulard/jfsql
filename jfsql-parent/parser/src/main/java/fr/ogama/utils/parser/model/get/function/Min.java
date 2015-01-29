package fr.ogama.utils.parser.model.get.function;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import fr.ogama.utils.parser.JFSQLExecutionException;
import fr.ogama.utils.parser.model.get.Expression;

public class Min extends AbstractFunction {
	
	public Min(String name) {
		super(name);
	}

	@Override
	public List<Comparable> execute(Map<String, Comparable> params)
			throws JFSQLExecutionException {
		Comparable min = null;
		
		for (Expression param : getOperands()) {
			if (param != null) {
				List<Comparable> results = param.execute(params);

				for (Comparable result : results) {
					if (min == null) {
						min = result;
					}
					
					if (min.compareTo(result) > 0) {
						min = result;
					}
				}
			}
		}

		return Arrays.asList(min);
	}
}
