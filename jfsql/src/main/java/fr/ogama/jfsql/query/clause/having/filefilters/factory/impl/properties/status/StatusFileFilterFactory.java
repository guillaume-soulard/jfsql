package fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.properties.status;

import java.util.ArrayList;
import java.util.List;

import org.gibello.zql.ZConstant;

import fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.properties.PropertyFactory;
import fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.properties.PropertyFileFilter;

public class StatusFileFilterFactory implements PropertyFactory {

	@Override
	public PropertyFileFilter getPropertyFileFilter(List<ZConstant> values) {
		List<String> status = new ArrayList<String>();
		for (ZConstant constant : values) {
			status.add(constant.getValue());
		}
		return new StatusFileFilter(status);
	}

}
