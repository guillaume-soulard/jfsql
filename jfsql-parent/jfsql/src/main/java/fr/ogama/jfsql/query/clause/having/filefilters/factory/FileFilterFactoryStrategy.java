package fr.ogama.jfsql.query.clause.having.filefilters.factory;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.NotFileFilter;

import fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.comparator.ComparatorFactoryStrategy;
import fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.logical.AndFileFactory;
import fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.logical.NotFileFilterFactory;
import fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.logical.OrFileFilterFactory;
import fr.ogama.jfsql.query.clause.having.operators.logic.Not;
import fr.ogama.utils.parser.model.get.ExpressionImpl;

public class FileFilterFactoryStrategy {
	
	private static FileFilterFactoryStrategy instance;	
	private Map<String, FileFilterFactory> strategy;
	
	private FileFilterFactoryStrategy(){
		
	}
		
	public static FileFilterFactoryStrategy getInstance() {
		if (instance == null) {
			instance = new FileFilterFactoryStrategy();
		}
		
		return instance;
	}
	
	private void init() {
		strategy = new HashMap<String, FileFilterFactory>();
		strategy.put("and", new AndFileFactory());
		strategy.put("or", new OrFileFilterFactory());
		strategy.put("not", new NotFileFilterFactory());
		
		strategy.put("=", new ComparatorFactoryStrategy());
		strategy.put("<>", new ComparatorFactoryStrategy());
		strategy.put(">", new ComparatorFactoryStrategy());
		strategy.put(">=", new ComparatorFactoryStrategy());
		strategy.put("<", new ComparatorFactoryStrategy());
		strategy.put("<=", new ComparatorFactoryStrategy());
		strategy.put("like", new ComparatorFactoryStrategy());
		strategy.put("in", new ComparatorFactoryStrategy());
		strategy.put("between", new ComparatorFactoryStrategy());
	}
	
	public IOFileFilter getFileFilter(ExpressionImpl expression) {		
		init();
		String operator = expression.getOperator().toLowerCase();
		boolean hasNotOperator = operator.contains("not");
		operator = operator.trim().replaceAll("[ ]?not[ ]?", "");
		
		FileFilterFactory fileFilterFactory = strategy.get(operator);
		IOFileFilter fileFilter = null;
				
		if (fileFilterFactory != null) {
			fileFilter = fileFilterFactory.getFileFilter(expression);
			
			if (hasNotOperator) {
				fileFilter = new NotFileFilter(fileFilter);
			}
		}		
		
		return fileFilter;
	}
}
