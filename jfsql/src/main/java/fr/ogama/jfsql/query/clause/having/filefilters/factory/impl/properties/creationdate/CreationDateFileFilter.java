package fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.properties.creationdate;

import java.io.File;
import java.util.Date;
import java.util.List;

import fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.AbstractFileFilter;
import fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.Operators;
import fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.properties.FilePropertyHelper;

public class CreationDateFileFilter extends AbstractFileFilter {

	private List<Date> creationsDates;
	
	public CreationDateFileFilter(List<Date> creationsDates) {
		this.creationsDates = creationsDates;
	}
	
	@Override
	protected boolean acceptFile(File file, String name) {
		try {
			getOperator().getObjects().add(FilePropertyHelper.getCreationDate(file));
			getOperator().getObjects().addAll(creationsDates);
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
