package fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.properties.path;

import java.io.File;
import java.util.List;

import fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.AbstractFileFilter;
import fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.Operators;

public class PathFileFilter extends AbstractFileFilter {

	private List<String> paths;
	
	public PathFileFilter(List<String> paths) {
		this.paths = paths;
	}

	@Override
	protected boolean acceptFile(File file, String name) {
		getOperator().setObjectToCompare(file.getPath().replaceAll("\\\\", "/"));
		getOperator().getObjects().addAll(paths);
		return getOperator().execute();
	}

	@Override
	protected void setAllowedOperators() {
		addSupportedOperator(Operators.EQUAL);
		addSupportedOperator(Operators.UNEQUAL);
		addSupportedOperator(Operators.LIKE);
		addSupportedOperator(Operators.IN);
	}

}
