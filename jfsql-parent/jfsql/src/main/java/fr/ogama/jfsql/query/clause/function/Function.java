package fr.ogama.jfsql.query.clause.function;

public interface Function {
	public Comparable execute(Comparable ... params) throws Exception;
	public boolean isAggregate();
}
