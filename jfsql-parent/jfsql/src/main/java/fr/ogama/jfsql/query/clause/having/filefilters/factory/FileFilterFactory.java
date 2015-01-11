package fr.ogama.jfsql.query.clause.having.filefilters.factory;

import org.apache.commons.io.filefilter.IOFileFilter;

import fr.ogama.utils.parser.model.get.ExpressionImpl;

public interface FileFilterFactory {
	public IOFileFilter getFileFilter(ExpressionImpl expression) throws Exception;
}
