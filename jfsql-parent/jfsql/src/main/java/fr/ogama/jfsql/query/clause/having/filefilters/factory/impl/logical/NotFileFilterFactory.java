package fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.logical;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.commons.io.filefilter.AndFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.NotFileFilter;

import fr.ogama.jfsql.query.clause.having.filefilters.factory.FileFilterFactory;
import fr.ogama.jfsql.query.clause.having.filefilters.factory.FileFilterFactoryStrategy;
import fr.ogama.utils.parser.model.get.ExpressionImpl;

public class NotFileFilterFactory implements FileFilterFactory {

	public IOFileFilter getFileFilter(ExpressionImpl expression) {
		IOFileFilter fileFilter = null;
		
		for (ExpressionImpl subExpression : (Vector<ExpressionImpl>) expression.getOperands()) {
			fileFilter = FileFilterFactoryStrategy.getInstance().getFileFilter(subExpression);
			break;
		}
		
		return new NotFileFilter(fileFilter);
	}
}
