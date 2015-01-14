package fr.ogama.utils.parser.model.get;

import java.util.Vector;

public class Function implements Expression {
	private String name;
	private Vector params;
	private boolean isAggregate = false;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Vector getParams() {
		return params;
	}
	
	public void setParams(Vector params) {
		this.params = params;
	}
	
	public boolean isAggregate() {
		return isAggregate;
	}
	
	public void setAggregate(boolean isAggregate) {
		this.isAggregate = isAggregate;
	}		
}
