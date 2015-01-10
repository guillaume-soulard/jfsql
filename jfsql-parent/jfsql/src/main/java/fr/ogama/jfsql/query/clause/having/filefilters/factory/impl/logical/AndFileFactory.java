package fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.logical;

import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.commons.io.filefilter.AndFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;

import fr.ogama.jfsql.query.clause.having.filefilters.factory.FileFilterFactory;
import fr.ogama.jfsql.query.clause.having.filefilters.factory.FileFilterFactoryStrategy;
import fr.ogama.utils.parser.model.get.Expression;
import fr.ogama.utils.parser.model.get.ExpressionImpl;

public class AndFileFactory implements FileFilterFactory {

	public IOFileFilter getFileFilter(ExpressionImpl expression) {
		List<FileFilter> fileFilters = new ArrayList<FileFilter>();
		
		for (ExpressionImpl subExpression : (Vector<ExpressionImpl>) expression.getOperands()) {
			fileFilters.add(FileFilterFactoryStrategy.getInstance().getFileFilter(subExpression));
		}
		
		return new AndFileFilter(fileFilters);
	}

}
