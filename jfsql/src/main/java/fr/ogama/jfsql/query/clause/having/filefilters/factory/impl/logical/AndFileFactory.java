package fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.logical;

import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.commons.io.filefilter.AndFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.gibello.zql.ZExpression;

import fr.ogama.jfsql.query.clause.having.filefilters.factory.FileFilterFactory;
import fr.ogama.jfsql.query.clause.having.filefilters.factory.FileFilterFactoryStrategy;

public class AndFileFactory implements FileFilterFactory {

	public IOFileFilter getFileFilter(ZExpression expression) {
		List<FileFilter> fileFilters = new ArrayList<FileFilter>();
		
		for (ZExpression subExpression : (Vector<ZExpression>) expression.getOperands()) {
			fileFilters.add(FileFilterFactoryStrategy.getInstance().getFileFilter(subExpression));
		}
		
		return new AndFileFilter(fileFilters);
	}

}
