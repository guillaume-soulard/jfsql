package fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.properties.name;

import java.io.File;
import java.util.List;

import fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.AbstractFileFilter;
import fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.Operators;


public class NameFileFilter extends AbstractFileFilter {

	private List<String> names;
	
	public NameFileFilter(List<String> names) {
		this.names = names;
	}
	
	@Override
	protected boolean acceptFile(File file, String name) {
		getOperator().getObjects().add(file.getName());
		getOperator().getObjects().addAll(names);
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
