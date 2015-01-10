package fr.ogama.jfsql.query.clause;

public class ClauseException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1982920886856132095L;

	public ClauseException(String message, Throwable cause) {
		super(message, cause);
	}

	public ClauseException(String message) {
		super(message);
	}

	public ClauseException(Throwable cause) {
		super(cause);
	}	
}
