package fr.ogama.utils.parser;

public class JFSQLException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8794936084652069599L;

	public JFSQLException(String message, Throwable cause) {
		super(message, cause);
	}

	public JFSQLException(String message) {
		super(message);
	}
}
