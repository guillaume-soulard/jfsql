package fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.properties.size;

import java.io.File;
import java.util.List;

import fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.AbstractFileFilter;
import fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.Operators;
import fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.properties.FilePropertyHelper;

public class SizeFileFilter extends AbstractFileFilter {

	private List<Long> sizes;
	
	public SizeFileFilter(List<Long> sizes) {
		this.sizes = sizes;
	}
	
	@Override
	protected boolean acceptFile(File file, String name) {
		try {
			getOperator().getObjects().add(FilePropertyHelper.getSize(file));
			getOperator().getObjects().addAll(sizes);
			return getOperator().execute();
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	protected void setAllowedOperators() {
		addSupportedOperator(Operators.EQUAL);
		addSupportedOperator(Operators.UNEQUAL);
		addSupportedOperator(Operators.GREATHER_THAN);
		addSupportedOperator(Operators.GREATHER_THAN_OR_EQUAL);
		addSupportedOperator(Operators.LESS_THAN);
		addSupportedOperator(Operators.LESS_THAN_OR_EQUAL);
		addSupportedOperator(Operators.BETWEEN);
		addSupportedOperator(Operators.IN);
	}

}
