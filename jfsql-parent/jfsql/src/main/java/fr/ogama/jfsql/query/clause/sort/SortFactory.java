package fr.ogama.jfsql.query.clause.sort;

import java.util.HashMap;
import java.util.Map;

import fr.ogama.jfsql.query.clause.ClauseException;
import fr.ogama.jfsql.query.clause.SortClause;
import fr.ogama.jfsql.query.clause.get.GetClauseFactory;

public class SortFactory {

	private Map<String, Class<? extends SortClause>> strategy;

	public SortFactory() {
		strategy = new HashMap<String, Class<? extends SortClause>>();
		strategy.put("content", DefaultSorter.class);
		strategy.put("name", DefaultSorter.class);
		strategy.put("path", DefaultSorter.class);
		strategy.put("content", DefaultSorter.class);
		strategy.put("parent", DefaultSorter.class);
		strategy.put("size", DefaultSorter.class);
		strategy.put("creation_date", DefaultSorter.class);
		strategy.put("last_update_date", DefaultSorter.class);
		strategy.put("last_access_date", DefaultSorter.class);
		strategy.put("owner", DefaultSorter.class);
		strategy.put("type", DefaultSorter.class);
	}

	public SortClause getSortClause(String property, boolean sortOrderAscending) throws ClauseException {
		SortOrder order = null;
		
		if (sortOrderAscending) {
			order = SortOrder.ASCENDING;
		} else {
			order = SortOrder.DESCENDING;
		}
		
		try {
			if (!strategy.containsKey(property.toLowerCase())) {
				throw new ClauseException("Unable to sort on property : " + property);
			}
			
			SortClause sortClause = strategy.get(property.toLowerCase()).newInstance();
			sortClause.setSortOrder(order);
			sortClause.setGetClause(GetClauseFactory.getInstance().getClause(property));
			
			return sortClause;
		} catch (Exception e) {
			throw new ClauseException(e.getMessage(), e);
		}
	}
}