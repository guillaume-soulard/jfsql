package fr.ogama.utils.parser.model.get;

import java.util.Map;

import fr.ogama.utils.parser.JFSQLExecutionException;

public class GetClause {
	private Expression property;
	private Integer limit;
	private boolean distinct;

	public Comparable getProperty(Map<String, Comparable> params)
			throws JFSQLExecutionException {
		return property.execute(params).get(0);
	}

	public void setProperty(Expression property) {
		this.property = property;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(String limit) {
		if (limit != null) {
			this.limit = Integer.valueOf(limit);
			if (this.limit <= 0) {
				throw new IllegalArgumentException(
						"limit must be strictly greather than 0");
			}
		} else {
			this.limit = Integer.MAX_VALUE;
		}
	}

	public boolean isDistinct() {
		return distinct;
	}

	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}
}
