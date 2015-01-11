package fr.ogama.jfsql.query.clause.in;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import fr.ogama.jfsql.query.Query;
import fr.ogama.jfsql.query.QueryFactory;
import fr.ogama.jfsql.query.clause.ClauseException;
import fr.ogama.jfsql.query.clause.InClause;
import fr.ogama.utils.parser.model.get.Constant;
import fr.ogama.utils.parser.model.get.GetStatement;
import fr.ogama.utils.parser.model.get.PathItem;

public class In implements InClause {

	private List<PathItem> paths;

	public In(List<PathItem> paths) {
		this.paths = paths;
	}

	public List<Path> getFiles() throws ClauseException {
		try {
			List<Path> files = new ArrayList<Path>(paths.size());

			for (PathItem path : paths) {
				if (path.getPath() instanceof Constant) {
					files.add(asPath(((Constant) path.getPath()).getValue(),
							path.getDeep()));
				} else if (path.getPath() instanceof GetStatement) {
					Query query = QueryFactory.asQuery((GetStatement) path
							.getPath());
					List<Comparable> results = query.execute();
					for (Comparable result : results) {
						files.add(asPath(result.toString(), path.getDeep()));
					}
				}

			}

			return files;
		} catch (Exception e) {
			throw new ClauseException(e.getMessage(), e);
		}
	}

	private Path asPath(String path, String stringDeep) throws ClauseException {
		File in = new File(path);

		if (!in.exists()) {
			throw new ClauseException(path + " not exist");
		}

		if (!in.isDirectory()) {
			throw new ClauseException(path + " is a file");
		}

		Integer deep = stringDeep != null ? Integer.valueOf(stringDeep)
				: Integer.MAX_VALUE;
		if (deep <= 0) {
			throw new ClauseException("Deep must be positive. Given : " + deep);
		}

		return new Path(in, deep);
	}
}
