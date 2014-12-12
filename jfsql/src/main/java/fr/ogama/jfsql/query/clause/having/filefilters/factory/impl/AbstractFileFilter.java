package fr.ogama.jfsql.query.clause.having.filefilters.factory.impl;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.filefilter.IOFileFilter;

import fr.ogama.jfsql.query.clause.having.filefilters.factory.impl.properties.PropertyFileFilter;
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

public abstract class AbstractFileFilter implements IOFileFilter, PropertyFileFilter  {
	private Map<Operators, ComparatorOperator> supportedOperators;
	private Map<Operators, ComparatorOperator> availableOperators;
	
	private ComparatorOperator operator;
	
	public AbstractFileFilter() {	
		supportedOperators = new HashMap<Operators, ComparatorOperator>();
		availableOperators = new HashMap<Operators, ComparatorOperator>();
		
		availableOperators.put(Operators.BETWEEN, new Between());
		availableOperators.put(Operators.EQUAL, new Equals());
		availableOperators.put(Operators.GREATHER_THAN , new GratherThan());
		availableOperators.put(Operators.GREATHER_THAN_OR_EQUAL , new GratherThanOrEqual());
		availableOperators.put(Operators.IN , new In());
		availableOperators.put(Operators.LESS_THAN , new LessThan());
		availableOperators.put(Operators.LESS_THAN_OR_EQUAL , new LessThanOrEqual());
		availableOperators.put(Operators.LIKE , new Like());
		availableOperators.put(Operators.UNEQUAL , new Unequal());
		
		setAllowedOperators();
	}
			
	public boolean accept(File file) {
		boolean accept = acceptFile(file, file.getName());
		getOperator().getObjects().clear();
		return accept;
	}

	public boolean accept(File dir, String name) {
		boolean accept = acceptFile(dir, name);
		getOperator().getObjects().clear();
		return accept;
	}

	protected void addSupportedOperator(Operators operator) {
		supportedOperators.put(operator, availableOperators.get(operator));
	}

	protected abstract boolean acceptFile(File file, String name);
	
	protected abstract void setAllowedOperators();
	
	public ComparatorOperator getOperator() {
		return operator;
	}

	public void setOperator(ComparatorOperator operator) {
		boolean isSupported = false;
		for (Map.Entry<Operators, ComparatorOperator> entry : supportedOperators.entrySet()) {
			if (entry.getValue().getClass().equals(operator.getClass())) {
				isSupported = true;
			}
		}		
		
		if (!isSupported) {
			throw new RuntimeException("Unsupported operator " + operator.getClass().getName());
		}
		
		this.operator = operator;
	}
}
