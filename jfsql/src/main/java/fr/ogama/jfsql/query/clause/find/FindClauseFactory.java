package fr.ogama.jfsql.query.clause.find;

import fr.ogama.jfsql.query.clause.FindClause;

public final class FindClauseFactory {
	public static FindClause getClause(String findClauses) throws Exception {

		findClauses = findClauses.toLowerCase();

		if (findClauses.equals("file")) {
			return new FindFile();
		} else if (findClauses.equals("directory")) {
			return new FindDirectory();
		} else if (findClauses.equals("file or directory")) {
			return new FindFileOrDirectory();
		} else if (findClauses.equals("name")) {
			return new FindName();
		} else {
			throw new Exception(findClauses.toString() + " is not managed yet");
		}
	}
}
