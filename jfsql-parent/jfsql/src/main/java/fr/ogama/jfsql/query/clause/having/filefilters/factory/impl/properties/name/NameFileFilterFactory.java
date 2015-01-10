package fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.properties.name;

import java.util.ArrayList;
import java.util.List;

import fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.properties.PropertyFactory;
import fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.properties.PropertyFileFilter;
import fr.ogama.utils.parser.model.get.Constant;

public class NameFileFilterFactory implements PropertyFactory {

	public PropertyFileFilter getPropertyFileFilter(List<Constant> values) {
		List<String> names = new ArrayList<String>();
		for (Constant constant : values) {
			names.add(constant.getValue());
		}
		return new NameFileFilter(names);
	}

}
