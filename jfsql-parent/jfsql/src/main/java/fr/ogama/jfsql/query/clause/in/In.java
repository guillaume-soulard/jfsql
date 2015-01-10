package fr.ogama.jfsql.query.clause.in;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import fr.ogama.jfsql.query.clause.ClauseException;
import fr.ogama.jfsql.query.clause.InClause;
import fr.ogama.utils.parser.model.get.PathItem;

public class In implements InClause {

	private List<PathItem> paths;

	public In(List<PathItem> paths) {
		this.paths = paths;
	}

	public List<Path> getFiles() throws ClauseException {
		List<Path> files = new ArrayList<Path>(paths.size());

		for (PathItem path : paths) {
			File in = new File(path.getPath());

			if (!in.exists()) {
				throw new ClauseException(path + " not exist");
			}

			if (!in.isDirectory()) {
				throw new ClauseException(path + " is a file");
			}

			Integer deep = path.getDeep() != null ? Integer
					.valueOf(path.getDeep()) : Integer.MAX_VALUE;
			if (deep <= 0) {
				throw new ClauseException("Deep must be positive. Given : " + deep);
			}
					
			files.add(new Path(in, deep));
		}

		return files;
	}

}
