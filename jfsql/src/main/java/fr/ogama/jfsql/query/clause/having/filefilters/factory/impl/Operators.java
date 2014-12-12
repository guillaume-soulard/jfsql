package fr.ogama.jfsql.query.clause.having.filefilters.factory.impl;

public enum Operators {
	
	EQUAL("="),
	UNEQUAL("<>"),
	GREATHER_THAN(">"),
	GREATHER_THAN_OR_EQUAL(">="),
	LESS_THAN("<"),
	LESS_THAN_OR_EQUAL("<="),
	IN("in"),
	BETWEEN("between"),
	LIKE("like");
	
	private String name;
	
	private Operators(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
