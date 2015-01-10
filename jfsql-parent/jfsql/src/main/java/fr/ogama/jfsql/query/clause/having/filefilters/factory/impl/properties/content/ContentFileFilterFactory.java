package fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.properties.content;

import java.util.ArrayList;
import java.util.List;

import fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.properties.PropertyFactory;
import fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.properties.PropertyFileFilter;
import fr.ogama.utils.parser.model.get.Constant;

public class ContentFileFilterFactory implements PropertyFactory {

	public PropertyFileFilter getPropertyFileFilter(List<Constant> values) {
		List<String> contents = new ArrayList<String>();
		for (Constant constant : values) {
			contents.add(constant.getValue());
		}
		return new ContentFileFilter(contents);
	}
}
