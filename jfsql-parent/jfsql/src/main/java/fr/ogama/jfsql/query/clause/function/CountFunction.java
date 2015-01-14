package fr.ogama.jfsql.query.clause.function;

public class CountFunction implements Function {

	@Override
	public Comparable execute(Comparable... params) throws Exception {
		return params != null ? params.length : 0;
	}

	@Override
	public boolean isAggregate() {
		return true;
	}
}
