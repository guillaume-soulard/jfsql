package fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.properties.path;

import java.util.ArrayList;
import java.util.List;

import fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.properties.PropertyFactory;
import fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.properties.PropertyFileFilter;
import fr.ogama.utils.parser.model.get.Constant;

public class PathFileFilterFactory implements PropertyFactory {

	public PropertyFileFilter getPropertyFileFilter(List<Constant> values) {
		List<String> paths = new ArrayList<String>();
		for (Constant constant : values) {
			paths.add(constant.getValue());
		}
		return new PathFileFilter(paths);
	}
}
