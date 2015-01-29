package fr.ogama.jfsql;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.ogama.utils.parser.JFSQLParser;
import fr.ogama.utils.parser.model.Statement;

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
							String resultToPrint = "";
							if (result instanceof File) {
								resultToPrint = ((File) result).getPath();
							} else {
								resultToPrint = String.valueOf(result);
							}
							System.out.println(resultToPrint);
						}
					}
				}
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}
	}

	public static Statement parseOneStatement(String string) throws Exception {
		List<Statement> statements = parseStatements(string);
		
		if (statements != null && statements.size() > 1) {
			throw new Exception("Too many statements");
		}
		
		return statements.size() == 1 ? statements.get(0) : null;
	}

	public static List<Statement> parseStatements(String string) throws Exception {
		if (string != null) {
			JFSQLParser jfsqlParser = new JFSQLParser();
			return jfsqlParser.parse(string);
		}
		throw new IllegalArgumentException("Unable to parse null string");
	}
}
