package fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.logical;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.OrFileFilter;

import fr.ogama.jfsql.query.clause.having.filefilters.factory.FileFilterFactory;
import fr.ogama.jfsql.query.clause.having.filefilters.factory.FileFilterFactoryStrategy;
import fr.ogama.utils.parser.model.get.ExpressionImpl;

public class OrFileFilterFactory implements FileFilterFactory {

	public IOFileFilter getFileFilter(ExpressionImpl expression) throws Exception {
		List<IOFileFilter> fileFilters = new ArrayList<IOFileFilter>();
		
		for (ExpressionImpl subExpression : (Vector<ExpressionImpl>) expression.getOperands()) {
			fileFilters.add(FileFilterFactoryStrategy.getInstance().getFileFilter(subExpression));
		}
		
		return new OrFileFilter(fileFilters);
	}
}
