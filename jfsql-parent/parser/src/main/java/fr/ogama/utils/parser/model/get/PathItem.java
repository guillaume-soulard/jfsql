package fr.ogama.utils.parser.model.get;

public class PathItem {
	private Expression path;
	private String deep;
	
	public Expression getPath() {
		return path;
	}
	
	public void setPath(Expression path) {
		this.path = path;
	}
	
	public String getDeep() {
		return deep;
	}
	
	public void setDeep(String deep) {
		this.deep = deep;
	}
}
