package fr.ogama.utils.parser.model.get;

import fr.ogama.utils.parser.model.Statement;

public class GetStatement implements Statement, Expression {
	private GetClause getClause;
	private InClause inClause;
	private HavingClause havingClause;
	private SortByClause sortByClause;

	public GetClause getGetClause() {
		return getClause;
	}

	public void setGetClause(GetClause getClause) {
		this.getClause = getClause;
	}

	public InClause getInClause() {
		return inClause;
	}

	public void setInClause(InClause inClause) {
		this.inClause = inClause;
	}

	public HavingClause getHavingClause() {
		return havingClause;
	}

	public void setHavingClause(HavingClause havingClause) {
		this.havingClause = havingClause;
	}

	public SortByClause getSortByClause() {
		return sortByClause;
	}

	public void setSortByClause(SortByClause sortByClause) {
		this.sortByClause = sortByClause;
	}
}
