package fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.properties.owner;

import java.util.ArrayList;
import java.util.List;

import org.gibello.zql.ZConstant;

import fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.properties.PropertyFactory;
import fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.properties.PropertyFileFilter;

public class OwnerFileFilterFactory implements PropertyFactory {

	public PropertyFileFilter getPropertyFileFilter(List<ZConstant> values) {
		List<String> owners = new ArrayList<String>();
		for (ZConstant constant : values) {
			owners.add(constant.getValue());
		}
		return new OwnerFileFilter(owners);
	}

}
