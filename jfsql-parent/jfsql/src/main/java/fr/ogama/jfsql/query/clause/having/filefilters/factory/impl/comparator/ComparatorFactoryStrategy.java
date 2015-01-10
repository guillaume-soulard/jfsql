package fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.comparator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.io.filefilter.IOFileFilter;

import fr.ogama.jfsql.query.Query;
import fr.ogama.jfsql.query.QueryFactory;
import fr.ogama.jfsql.query.clause.having.filefilters.factory.FileFilterFactory;
import fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.properties.PropertyFactory;
import fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.properties.PropertyFileFilter;
import fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.properties.content.ContentFileFilterFactory;
import fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.properties.creationdate.CreationDateFileFilterFactory;
import fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.properties.lastaccessdate.LastAccessDateFileFilterFactory;
import fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.properties.lastmodificationdate.LastModificationFileFilterFactory;
import fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.properties.name.NameFileFilterFactory;
import fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.properties.owner.OwnerFileFilterFactory;
import fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.properties.parent.ParentFileFilterFactory;
import fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.properties.path.PathFileFilterFactory;
import fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.properties.size.SizeFileFilterFactory;
import fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.properties.status.StatusFileFilterFactory;
import fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.properties.type.TypeFileFilterFactory;
import fr.ogama.jfsql.query.clause.having.operators.comparaison.Between;
import fr.ogama.jfsql.query.clause.having.operators.comparaison.ComparatorOperator;
import fr.ogama.jfsql.query.clause.having.operators.comparaison.Equals;
import fr.ogama.jfsql.query.clause.having.operators.comparaison.GratherThan;
import fr.ogama.jfsql.query.clause.having.operators.comparaison.GratherThanOrEqual;
import fr.ogama.jfsql.query.clause.having.operators.comparaison.In;
import fr.ogama.jfsql.query.clause.having.operators.comparaison.LessThan;
import fr.ogama.jfsql.query.clause.having.operators.comparaison.LessThanOrEqual;
import fr.ogama.jfsql.query.clause.having.operators.comparaison.Like;
import fr.ogama.jfsql.query.clause.having.operators.comparaison.Unequal;
import fr.ogama.utils.parser.model.get.Constant;
import fr.ogama.utils.parser.model.get.Expression;
import fr.ogama.utils.parser.model.get.ExpressionImpl;
import fr.ogama.utils.parser.model.get.GetStatement;

public class ComparatorFactoryStrategy implements FileFilterFactory {

	private Map<String, Class<? extends ComparatorOperator>> operatorStrategy;
	private Map<String, PropertyFactory> propertyStrategy;
	
	public ComparatorFactoryStrategy() {
		
		operatorStrategy = new HashMap<String, Class<? extends ComparatorOperator>>();
		operatorStrategy.put("=", Equals.class);
		operatorStrategy.put("<>", Unequal.class);
		operatorStrategy.put(">", GratherThan.class);
		operatorStrategy.put(">=", GratherThanOrEqual.class);
		operatorStrategy.put("<", LessThan.class);
		operatorStrategy.put("<=", LessThanOrEqual.class);
		operatorStrategy.put("like", Like.class);
		operatorStrategy.put("in", In.class);
		operatorStrategy.put("between", Between.class);

		propertyStrategy = new HashMap<String, PropertyFactory>();
		propertyStrategy.put("name", new NameFileFilterFactory());
		propertyStrategy.put("path", new PathFileFilterFactory());
		propertyStrategy.put("content", new ContentFileFilterFactory());
		propertyStrategy.put("parent", new ParentFileFilterFactory());
		propertyStrategy.put("size", new SizeFileFilterFactory());
		propertyStrategy.put("creation_date", new CreationDateFileFilterFactory());
		propertyStrategy.put("last_update_date", new LastModificationFileFilterFactory());
		propertyStrategy.put("last_access_date", new LastAccessDateFileFilterFactory());
		propertyStrategy.put("owner", new OwnerFileFilterFactory());
		propertyStrategy.put("type", new TypeFileFilterFactory());
		propertyStrategy.put("status", new StatusFileFilterFactory());
	}

	public IOFileFilter getFileFilter(ExpressionImpl expression) {
		try {
			String operator = expression.getOperator().toLowerCase();
			operator = operator.trim().replaceAll("[ ]?not[ ]?", "");
			
			List<Constant> constants = new ArrayList<Constant>();
			for (Expression exp : (Vector<Expression>) expression.getOperands()) {
				if (exp instanceof Constant) {
					constants.add((Constant) exp);
				} else if (exp instanceof GetStatement) {
					Query subQuery = QueryFactory.asQuery((GetStatement) exp);
					List<Comparable> results = subQuery.execute();
					
					for (Comparable result : results) {
						constants.add(new Constant(result.toString(), Constant.STRING));
					}
				}
			}

			if (constants.size() >= 2) {
				ComparatorOperator comparatorOperator = operatorStrategy.get(
						operator).newInstance();
				
				PropertyFileFilter propertyFileFilter =  propertyStrategy.get(
						constants.get(0).getValue().toLowerCase()).getPropertyFileFilter(constants.subList(1, constants.size()));
				
				propertyFileFilter.setOperator(comparatorOperator);
				
				return (IOFileFilter) propertyFileFilter;
			}			
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
