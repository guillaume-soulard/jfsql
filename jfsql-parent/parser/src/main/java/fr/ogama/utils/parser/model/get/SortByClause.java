package fr.ogama.utils.parser.model.get;

public class SortByClause {
	private String property;
	private boolean isAscendingOrder;
	
	public String getProperty() {
		return property;
	}
	
	public void setProperty(String property) {
		this.property = property;
	}
	
	public boolean isAscendingOrder() {
		return isAscendingOrder;
	}
	
	public void setAscendingOrder(boolean isAscendingOrder) {
		this.isAscendingOrder = isAscendingOrder;
	}	
}
