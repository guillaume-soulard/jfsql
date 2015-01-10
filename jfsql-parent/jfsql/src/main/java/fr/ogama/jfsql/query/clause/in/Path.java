package fr.ogama.jfsql.query.clause.in;

import java.io.File;

public class Path {
	private File path;
	private int deep;
		
	public Path(File path, int deep) {
		super();
		this.path = path;
		this.deep = deep;
	}

	public File getPath() {
		return path;
	}
	
	public void setPath(File path) {
		this.path = path;
	}
	
	public int getDeep() {
		return deep;
	}
	
	public void setDeep(int deep) {
		this.deep = deep;
	}
}
