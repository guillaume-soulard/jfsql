package fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.properties.lastmodificationdate;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.ogama.jfsql.query.JFSQLUtils;
import fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.properties.PropertyFactory;
import fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.properties.PropertyFileFilter;
import fr.ogama.utils.parser.model.get.Constant;

public class LastModificationFileFilterFactory implements PropertyFactory {

	public PropertyFileFilter getPropertyFileFilter(List<Constant> values) {
		List<Date> modificationDates = new ArrayList<Date>();
		for (Constant constant : values) {
			try {
				modificationDates.add(JFSQLUtils.parseDateFromCurrentLocal(constant
						.getValue()));
			} catch (ParseException e) {
				throw new IllegalArgumentException("Given the date "
						+ constant.getValue() + " no match with pattern "
						+ JFSQLUtils.datePattern);
			}
		}
		return new LastModificationFileFilter(modificationDates);
	}

}
