package fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.properties.parent;

import java.util.ArrayList;
import java.util.List;

import fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.properties.PropertyFactory;
import fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.properties.PropertyFileFilter;
import fr.ogama.utils.parser.model.get.Constant;

public class ParentFileFilterFactory implements PropertyFactory {

	public PropertyFileFilter getPropertyFileFilter(List<Constant> values) {
		List<String> parents = new ArrayList<String>();
		for (Constant constant : values) {
			parents.add(constant.getValue());
		}
		return new ParentFileFilter(parents);
	}
}
