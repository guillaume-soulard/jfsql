package fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.properties.type;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import fr.ogama.jfsql.query.FileType;
import fr.ogama.jfsql.query.JFSQLUtils;
import fr.ogama.jfsql.query.clause.ClauseException;
import fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.AbstractFileFilter;
import fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.Operators;

public class TypeFileFilter extends AbstractFileFilter {

	private List<String> types;
	
	public TypeFileFilter(List<String> types) {
		this.types = types;
	}
	
	@Override
	protected void setAllowedOperators() {
		addSupportedOperator(Operators.EQUAL);
		addSupportedOperator(Operators.UNEQUAL);
		addSupportedOperator(Operators.IN);
	}

	@Override
	protected Comparable getLeftValue(File file) throws ClauseException {
		String type = "";
		
		if (file.isDirectory()) {
			type = FileType.DIRECTORY.getLabel();
		} else if (file.isFile()) {
			type = FileType.FILE.getLabel();
		}
		
		return type;
	}

	@Override
	protected List<Comparable> getRightValues(File file) throws ClauseException {
		List<Comparable> lowtypes = new ArrayList<Comparable>();
		
		for (String type : types) {
			lowtypes.add(type.toLowerCase());
		}
		
		return lowtypes;
	}

}
