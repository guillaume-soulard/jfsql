package fr.ogama.jfsql.query;

import java.util.List;

public interface Query {
	public List<Comparable> execute() throws Exception;
}
