package fr.ogama.jfsql.query.clause.having.operators.comparaison;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractComparatorOperator implements ComparatorOperator {

	private Comparable objectToCompare;
	private List<Comparable> objects;

	public AbstractComparatorOperator() {
		objects = new ArrayList<Comparable>();
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
}
