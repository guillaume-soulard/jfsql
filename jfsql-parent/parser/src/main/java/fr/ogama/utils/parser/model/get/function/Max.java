package fr.ogama.utils.parser.model.get.function;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import fr.ogama.utils.parser.JFSQLExecutionException;
import fr.ogama.utils.parser.model.get.Expression;

public class Max extends AbstractFunction {
	
	public Max(String name) {
		super(name);
	}

	@Override
	public List<Comparable> execute(Map<String, Comparable> params)
			throws JFSQLExecutionException {
		Comparable max = null;
		
		for (Expression param : getOperands()) {
			if (param != null) {
				List<Comparable> results = param.execute(params);

				for (Comparable result : results) {
					if (max == null) {
						max = result;
					}
					
					if (max.compareTo(result) < 0) {
						max = result;
					}
				}
			}
		}

		return Arrays.asList(max);
	}
}
