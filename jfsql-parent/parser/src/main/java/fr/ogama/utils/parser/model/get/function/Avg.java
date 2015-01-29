package fr.ogama.utils.parser.model.get.function;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import fr.ogama.utils.parser.JFSQLExecutionException;
import fr.ogama.utils.parser.model.Utils;
import fr.ogama.utils.parser.model.get.Expression;

public class Avg extends AbstractFunction {
	
	public Avg(String name) {
		super(name);
	}

	@Override
	public List<Comparable> execute(Map<String, Comparable> params)
			throws JFSQLExecutionException {
		Number sum = new Double(0);
		AtomicInteger count = new AtomicInteger(0);

		for (Expression param : getOperands()) {
			if (param != null) {
				List<Comparable> results = param.execute(params);

				for (Comparable result : results) {
					if (Number.class.isAssignableFrom(result.getClass())) {
						sum = Utils.add(sum, (Number) result);
						count.incrementAndGet();
					}
				}
			}
		}

		return Arrays.asList((Comparable) Utils.divide(sum, count.get()));
	}
}
