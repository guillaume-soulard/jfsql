package fr.ogama.utils.parser;

import java.io.ByteArrayInputStream;
import java.util.Vector;

import fr.ogama.utils.parser.model.Statement;

public class JFSQLParser {
	private JFSQLJJParser parser;
	
	public JFSQLParser() {
		
	}
	
	public Vector<Statement> parse(String string) throws ParseException {
		parser = new JFSQLJJParser(new ByteArrayInputStream(string.getBytes()));
		return parser.JFSQLStatements();
	}
}
