package fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.properties;

import java.util.List;

import org.gibello.zql.ZConstant;

public interface PropertyFactory {
	PropertyFileFilter getPropertyFileFilter(List<ZConstant> values);
}
