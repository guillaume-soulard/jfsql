package fr.ogama.utils.parser.model.get;

import java.io.File;
import java.util.Map;

import fr.ogama.utils.parser.JFSQLExecutionException;

public class PathItem {
	private Expression path;
	private Integer deep;

	public String getPath(Map<String, Comparable> params)
			throws JFSQLExecutionException {
		if (path != null) {
			Comparable result = path.execute(params).get(0);
			if (result instanceof File) {
				return ((File) result).getAbsolutePath();
			}

			if (result instanceof String && result.equals(".")) {
				return System.getProperty("user.dir");
			}
			return (String) result;
		} else {
			return null;
		}
	}

	public void setPath(Expression path) {
		this.path = path;
	}

	public Integer getDeep() {
		return deep;
	}

	public void setDeep(String deep) {
		if (deep != null && "INFINITY".equals(deep.toUpperCase())) {
			this.deep = Integer.MAX_VALUE;
		} else {
			if (deep == null) {
				this.deep = Integer.MAX_VALUE;
			} else {
				this.deep = Integer.valueOf(deep);
				if (this.deep < 0) {
					throw new IllegalArgumentException("deep must be positive");
				}
			}
		}
	}
}
