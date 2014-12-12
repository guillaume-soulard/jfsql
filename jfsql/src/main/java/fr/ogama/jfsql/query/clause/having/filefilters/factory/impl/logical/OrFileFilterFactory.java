package fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.logical;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.OrFileFilter;
import org.gibello.zql.ZExpression;

import fr.ogama.jfsql.query.clause.having.filefilters.factory.FileFilterFactory;
import fr.ogama.jfsql.query.clause.having.filefilters.factory.FileFilterFactoryStrategy;

public class OrFileFilterFactory implements FileFilterFactory {

	public IOFileFilter getFileFilter(ZExpression expression) {
		List<IOFileFilter> fileFilters = new ArrayList<IOFileFilter>();
		
		for (ZExpression subExpression : (Vector<ZExpression>) expression.getOperands()) {
			fileFilters.add(FileFilterFactoryStrategy.getInstance().getFileFilter(subExpression));
		}
		
		return new OrFileFilter(fileFilters);
	}
}
