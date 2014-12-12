package fr.ogama.jfsql.query.clause.having.operators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.ogama.jfsql.query.clause.having.operators.comparaison.ComparatorOperator;
import fr.ogama.jfsql.query.clause.having.operators.comparaison.Equals;
import fr.ogama.jfsql.query.clause.having.operators.comparaison.GratherThan;
import fr.ogama.jfsql.query.clause.having.operators.comparaison.GratherThanOrEqual;
import fr.ogama.jfsql.query.clause.having.operators.comparaison.LessThan;
import fr.ogama.jfsql.query.clause.having.operators.comparaison.LessThanOrEqual;
import fr.ogama.jfsql.query.clause.having.operators.comparaison.Like;
import fr.ogama.jfsql.query.clause.having.operators.comparaison.Unequal;

public class ValueIdentificator {
	
	private Map<String, Class<? extends ComparatorOperator>> comparaisonOperators;
	
	public ValueIdentificator() {
		comparaisonOperators = new HashMap<String, Class<? extends ComparatorOperator>>();
		
		comparaisonOperators.put("=", Equals.class);
		comparaisonOperators.put("like", Like.class);
		comparaisonOperators.put(">", GratherThan.class);
		comparaisonOperators.put("<", LessThan.class);
		comparaisonOperators.put(">=", GratherThanOrEqual.class);
		comparaisonOperators.put("<=", LessThanOrEqual.class);
		comparaisonOperators.put("<>", Unequal.class);
	}
	
	public Comparable identifyValue(String expression) throws Exception {
		String operatorInExpression = null;
		
		for (Map.Entry<String, Class<? extends ComparatorOperator>> operator : comparaisonOperators.entrySet()) {
			if (expression.contains(" " + operator.getKey() + " ")) {
				operatorInExpression = operator.getKey();
			}
		}
		
		String[] values = expression.split(operatorInExpression);
		return null;
//		df
	}
}
