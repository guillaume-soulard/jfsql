package fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.properties.name;

import java.util.ArrayList;
import java.util.List;

import org.gibello.zql.ZConstant;

import fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.properties.PropertyFactory;
import fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.properties.PropertyFileFilter;

public class NameFileFilterFactory implements PropertyFactory {

	public PropertyFileFilter getPropertyFileFilter(List<ZConstant> values) {
		List<String> names = new ArrayList<String>();
		for (ZConstant constant : values) {
			names.add(constant.getValue());
		}
		return new NameFileFilter(names);
	}

}
