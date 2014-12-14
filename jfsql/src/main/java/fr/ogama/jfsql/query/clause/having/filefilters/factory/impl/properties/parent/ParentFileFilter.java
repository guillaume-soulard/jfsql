package fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.properties.parent;

import java.io.File;
import java.util.List;

import fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.AbstractFileFilter;
import fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.Operators;

public class ParentFileFilter extends AbstractFileFilter {

	private List<String> parents;
	
	public ParentFileFilter(List<String> parents) {
		this.parents = parents;
	}
	
	@Override
	protected boolean acceptFile(File file, String name) {
		getOperator().getObjects().addAll(parents);
		getOperator().setObjectToCompare(file.getParentFile().getName());
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
