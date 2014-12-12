package fr.ogama.jfsql.query;

public class QueryClause {
	private String findClause;
	private String inClause;
	private String havingClause;
	
	public QueryClause() {
		
	}
	public String getFindClause() {
		return findClause;
	}
	
	public void setFindClause(String getClause) {
		this.findClause = getClause;
	}
	
	public String getInClause() {
		return inClause;
	}
	
	public void setInClause(String inClause) {
		this.inClause = inClause;
	}
	
	public String getHavingClause() {
		return havingClause;
	}
	
	public void setHavingClause(String havingClause) {
		this.havingClause = havingClause;
	}
}
