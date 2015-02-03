package fr.ogama.jfsql;

import java.io.File;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.ogama.utils.parser.JFSQLExecutionException;
import fr.ogama.utils.parser.JFSQLParser;
import fr.ogama.utils.parser.ParseException;
import fr.ogama.utils.parser.model.Statement;
import fr.ogama.utils.parser.model.Utils;

public class JFSQL {

	public static void main(String[] args) {
		if (args != null || args.length == 1) {
			try {
				Map<String, Comparable> params = new HashMap<String, Comparable>();
				List<Statement> statements = parseStatements(args[0]);

				for (Statement statement : statements) {
					List<Comparable> results = statement.execute(params);

					if (results != null) {
						for (Comparable result : results) {
							System.out
									.println(Utils
											.toString(result instanceof File ? ((File) result)
													.getPath() : result));
						}
					}
				}
			} catch (Exception e) {
				System.err.println("Error : " + e.getCause().toString());
			}
		}
	}

	public static Statement parseOneStatement(String string)
			throws JFSQLExecutionException {
		List<Statement> statements = parseStatements(string);

		if (statements != null && statements.size() > 1) {
			throw new JFSQLExecutionException("Too many statements");
		}

		return statements.size() == 1 ? statements.get(0) : null;
	}

	public static List<Statement> parseStatements(String string)
			throws JFSQLExecutionException {
		if (string != null) {
			JFSQLParser jfsqlParser = new JFSQLParser();
			try {
				return jfsqlParser.parse(string);
			} catch (ParseException e) {
				throw new JFSQLExecutionException(e.getMessage(), e);
			}
		}
		throw new IllegalArgumentException("Unable to parse null string");
	}
}
