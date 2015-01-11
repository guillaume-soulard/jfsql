package fr.ogama.jfsql.query.clause.sort;

import java.io.File;
import java.util.Comparator;

import fr.ogama.jfsql.query.clause.ClauseException;
import fr.ogama.jfsql.query.clause.GetClause;
import fr.ogama.jfsql.query.clause.SortClause;

public class DefaultSorter implements SortClause {
	private SortOrder sortOrder;
		
	private GetClause getClause;
		
	@Override
	public void setSortOrder(SortOrder sortOrder) {
		this.sortOrder = sortOrder;
	}
	
	@Override
	public void setGetClause(GetClause getClause) {
		this.getClause = getClause;
	}
	
	@Override
	public Comparator<File> getComparator() {
				
		final Comparator<File> comparator = getAscendingComparator();
		
		if (sortOrder.equals(SortOrder.ASCENDING)) {
			return comparator;
		} else {
			return new Comparator<File>() {				
				@Override
				public int compare(File o1, File o2) {
					return -comparator.compare(o1, o2);
				}
			};
		}
	}
	
	protected Comparator<File> getAscendingComparator() {
		return new Comparator<File>() {			
			@Override
			public int compare(File o1, File o2) {
				try {
					return getClause.select(o1).compareTo(getClause.select(o2));
				} catch (ClauseException e) {
					return 0;
				}
			}
		};
	}
}
