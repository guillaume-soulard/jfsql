package fr.ogama.utils.parser.model.get;

import java.util.Vector;

public class GetClause {
	private Expression property;
	private String limit;
	private boolean distinct;
	
	public Expression getProperty() {
		return property;
	}

	public void setProperty(Expression property) {
		this.property = property;
	}

	public String getLimit() {
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean isDistinct() {
		return distinct;
	}

	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}
}
