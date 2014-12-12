package fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.properties.parent;

import java.util.ArrayList;
import java.util.List;

import org.gibello.zql.ZConstant;

import fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.properties.PropertyFactory;
import fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.properties.PropertyFileFilter;

public class ParentFileFilterFactory implements PropertyFactory {

	public PropertyFileFilter getPropertyFileFilter(List<ZConstant> values) {
		List<String> parents = new ArrayList<String>();
		for (ZConstant constant : values) {
			parents.add(constant.getValue());
		}
		return new ParentFileFilter(parents);
	}
}
