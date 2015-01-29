package fr.ogama.utils.parser.model;

import java.util.List;
import java.util.Map;

import fr.ogama.utils.parser.JFSQLExecutionException;



public interface Statement {
	/**
	 * 
	 * @param params : list of parameters by variable name
	 * @return the result of the method
	 * @throws JFSQLExecutionException Exception while executing this statement
	 */
	List<Comparable> execute(Map<String, Comparable> params) throws JFSQLExecutionException;
}
