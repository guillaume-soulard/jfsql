package fr.ogama.jfsql.query.clause.having.operators.comparaison;

import java.util.ArrayList;
import java.util.List;

import fr.ogama.jfsql.query.clause.ClauseException;

public abstract class AbstractComparatorOperator implements ComparatorOperator {

	private Comparable objectToCompare;
	private List<Comparable> objects;
	private int minRightValuesCount;
	private int maxRightValuesCount;

	public AbstractComparatorOperator(int minRightValuesCount,
			int maxRightValuesCount) {
		objects = new ArrayList<Comparable>();
		this.minRightValuesCount = minRightValuesCount;
		this.maxRightValuesCount = maxRightValuesCount;
	}

	public Comparable getObjectToCompare() {
		return objectToCompare;
	}

	public void setObjectToCompare(Comparable objectToCompare) {
		this.objectToCompare = objectToCompare;
	}

	public List<Comparable> getObjects() {
		return objects;
	}

	public void setObjects(List<Comparable> objects) {
		this.objects = objects;
	}

	@Override
	public boolean execute() throws ClauseException {
		verify();
		return executeComparator();
	}

	protected void verify() throws ClauseException {
		if (getObjects().size() < minRightValuesCount) {
			throw new ClauseException("too few values. Expected at least "
					+ minRightValuesCount + " given " + getObjects().size());
		} else if (getObjects().size() > maxRightValuesCount) {
			throw new ClauseException("too many values. Expected at least "
					+ minRightValuesCount + " given " + getObjects().size());
		}
	}

	protected abstract boolean executeComparator() throws ClauseException;
}
