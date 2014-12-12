package fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.properties.lastaccessdate;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.gibello.zql.ZConstant;

import fr.ogama.jfsql.query.JFSQLUtils;
import fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.properties.PropertyFactory;
import fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.properties.PropertyFileFilter;

public class LastAccessDateFileFilterFactory implements PropertyFactory {

	public PropertyFileFilter getPropertyFileFilter(List<ZConstant> values) {
		List<Date> creationDates = new ArrayList<Date>();
		for (ZConstant constant : values) {
			try {
				creationDates.add(JFSQLUtils.parseDateFromCurrentLocal(constant
						.getValue()));
			} catch (ParseException e) {
				throw new IllegalArgumentException("Given the date "
						+ constant.getValue() + " no match with pattern "
						+ JFSQLUtils.datePattern);
			}
		}
		return new LastAccessDateFileFilter(creationDates);
	}

}
