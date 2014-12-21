package fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.properties.size;

import java.util.ArrayList;
import java.util.List;

import org.gibello.zql.ZConstant;

import fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.properties.PropertyFactory;
import fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.properties.PropertyFileFilter;

public class SizeFileFilterFactory implements PropertyFactory {

	public PropertyFileFilter getPropertyFileFilter(List<ZConstant> values) {
		List<String> sizes = new ArrayList<String>();
		for (ZConstant constant : values) {
			sizes.add(constant.getValue());
		}
		return new SizeFileFilter(sizes);
	}

}
