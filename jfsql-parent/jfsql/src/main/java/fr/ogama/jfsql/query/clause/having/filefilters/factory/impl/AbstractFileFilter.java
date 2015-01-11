package fr.ogama.jfsql.query.clause.having.filefilters.factory.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.filefilter.IOFileFilter;

import fr.ogama.jfsql.query.JFSQLUtils;
import fr.ogama.jfsql.query.Properties;
import fr.ogama.jfsql.query.Query;
import fr.ogama.jfsql.query.QueryFactory;
import fr.ogama.jfsql.query.clause.ClauseException;
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
import fr.ogama.jfsql.query.clause.having.operators.comparaison.Match;
import fr.ogama.jfsql.query.clause.having.operators.comparaison.Unequal;

public abstract class AbstractFileFilter implements IOFileFilter,
		PropertyFileFilter {
	private Map<Operators, ComparatorOperator> supportedOperators;
	private Map<Operators, ComparatorOperator> availableOperators;

	private ComparatorOperator operator;

	public AbstractFileFilter() {
		supportedOperators = new HashMap<Operators, ComparatorOperator>();
		availableOperators = new HashMap<Operators, ComparatorOperator>();

		availableOperators.put(Operators.BETWEEN, new Between());
		availableOperators.put(Operators.EQUAL, new Equals());
		availableOperators.put(Operators.GREATHER_THAN, new GratherThan());
		availableOperators.put(Operators.GREATHER_THAN_OR_EQUAL,
				new GratherThanOrEqual());
		availableOperators.put(Operators.IN, new In());
		availableOperators.put(Operators.LESS_THAN, new LessThan());
		availableOperators.put(Operators.LESS_THAN_OR_EQUAL,
				new LessThanOrEqual());
		availableOperators.put(Operators.LIKE, new Like());
		availableOperators.put(Operators.UNEQUAL, new Unequal());
		availableOperators.put(Operators.MATCH, new Match());
		
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

	protected Comparable convertValue(Comparable value) throws ClauseException {
		return value;
	}
	
	protected boolean acceptFile(File file, String name) {
		try {			
			List<Comparable> rightValues = getRightValues(file);
			List<Comparable> resultsToAdd = new LinkedList<Comparable>();
			List<Comparable> valuesToRemove = new LinkedList<Comparable>();
			// sub queries management
			try {
				Pattern pattern = Pattern.compile(Properties.getProperty(Properties.QUERY_READ_STATEMENT_GENERAL));
				for (Comparable value : rightValues) {
					if (value instanceof String &&
							JFSQLUtils.executeRegexp(pattern, (String) value).find()) {
						Query query = QueryFactory.newQuery((String) value);
						List<Comparable> results = query.execute();
						resultsToAdd.addAll(results);
						valuesToRemove.add(value);
					}
				}
			} catch (Exception e) {
				throw new IllegalArgumentException(e.getMessage(), e);
			}
			
			rightValues.removeAll(valuesToRemove);			
			rightValues.addAll(resultsToAdd);
			
			for (Comparable rightValue : rightValues) {
				rightValues.set(rightValues.indexOf(rightValue), convertValue(rightValue));
			}
			
			getOperator().setObjectToCompare(getLeftValue(file));
			getOperator().setObjects(rightValues);
			return getOperator().execute();
		} catch (ClauseException e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		}
	}

	protected abstract void setAllowedOperators();

	protected abstract Comparable getLeftValue(File file)
			throws ClauseException;

	protected abstract List<Comparable> getRightValues(File file)
			throws ClauseException;

	public ComparatorOperator getOperator() {
		return operator;
	}

	public void setOperator(ComparatorOperator operator) {
		boolean isSupported = false;
		for (Map.Entry<Operators, ComparatorOperator> entry : supportedOperators
				.entrySet()) {
			if (entry.getValue().getClass().equals(operator.getClass())) {
				isSupported = true;
			}
		}

		if (!isSupported) {
			throw new RuntimeException("Unsupported operator "
					+ operator.getClass().getName());
		}

		this.operator = operator;
	}
}
