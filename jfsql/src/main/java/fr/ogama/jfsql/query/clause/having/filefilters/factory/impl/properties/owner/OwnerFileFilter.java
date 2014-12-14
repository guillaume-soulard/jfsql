package fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.properties.owner;

import java.io.File;
import java.util.List;

import fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.AbstractFileFilter;
import fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.Operators;
import fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.properties.FilePropertyHelper;

public class OwnerFileFilter extends AbstractFileFilter {

	private List<String> owners;

	public OwnerFileFilter(List<String> owners) {
		this.owners = owners;
	}

	@Override
	protected boolean acceptFile(File file, String name) {
		try {
			getOperator().setObjectToCompare(FilePropertyHelper.getOwner(file));
			getOperator().getObjects().addAll(owners);
			return getOperator().execute();
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	protected void setAllowedOperators() {
		addSupportedOperator(Operators.EQUAL);
		addSupportedOperator(Operators.UNEQUAL);
		addSupportedOperator(Operators.LIKE);
		addSupportedOperator(Operators.IN);
	}

}
