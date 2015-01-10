package fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.properties.status;

import java.util.ArrayList;
import java.util.List;

import fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.properties.PropertyFactory;
import fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.properties.PropertyFileFilter;
import fr.ogama.utils.parser.model.get.Constant;

public class StatusFileFilterFactory implements PropertyFactory {

	@Override
	public PropertyFileFilter getPropertyFileFilter(List<Constant> values) {
		List<String> status = new ArrayList<String>();
		for (Constant constant : values) {
			status.add(constant.getValue());
		}
		return new StatusFileFilter(status);
	}

}
