package fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.properties;

import java.util.List;

import fr.ogama.utils.parser.model.get.Constant;


public interface PropertyFactory {
	PropertyFileFilter getPropertyFileFilter(List<Constant> values);
}
