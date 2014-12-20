package fr.ogama.jfsql.query.clause.get;

import java.util.HashMap;
import java.util.Map;

import fr.ogama.jfsql.query.clause.ClauseException;
import fr.ogama.jfsql.query.clause.GetClause;

public final class GetClauseFactory {
	
	private static GetClauseFactory instance;
	
	private Map<String, Class<? extends GetClause>> strategy;
	
	public static GetClauseFactory getInstance() {
		if (instance == null) {
			instance = new GetClauseFactory();
		}
		
		return instance;
	}
	
	private GetClauseFactory() {
		strategy = new HashMap<String, Class<? extends GetClause>>();
		strategy.put("file", GetFile.class);
		strategy.put("file or directory", GetFileOrDirectory.class);
		strategy.put("directory", GetDirectory.class);
		strategy.put("name", GetName.class);
		strategy.put("size", GetSize.class);
		strategy.put("creation_date", GetCreationDate.class);
		strategy.put("last_update_date", GetLastModificationDate.class);
		strategy.put("last_access_date", GetLastAccessDate.class);
		strategy.put("parent", GetParent.class);
		strategy.put("path", GetPath.class);
		strategy.put("content", GetContent.class);
		strategy.put("owner", GetOwner.class);
		strategy.put("type", GetType.class);
		strategy.put("status", GetStatus.class);
	}
	
	public GetClause getClause(String getClauses) throws Exception {
		Class<? extends GetClause> getClause = strategy.get(getClauses.toLowerCase());
		
		if (getClause != null) {
			return getClause.newInstance();
		}
		
		throw new ClauseException("Unexpected attribute : " + getClauses);
	}
}
