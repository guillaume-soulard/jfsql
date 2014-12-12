package fr.ogama.jfsql.query.clause.having.filefilters.factory;

import org.apache.commons.io.filefilter.IOFileFilter;
import org.gibello.zql.ZExpression;

public interface FileFilterFactory {
	public IOFileFilter getFileFilter(ZExpression expression);
}
