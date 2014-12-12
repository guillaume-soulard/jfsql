package fr.ogama.jfsql.query.clause.having.operators.comparaison;


public class Unequal extends Equals {

	public Unequal() {
		super();
	}
	
	public boolean execute() {
		return !super.execute();
	}

}
