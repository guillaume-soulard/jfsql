package fr.ogama.jfsql.query.clause.in;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import fr.ogama.jfsql.query.clause.ClauseException;
import fr.ogama.jfsql.query.clause.InClause;

public class In implements InClause {

	private String[] paths;
	
	public In(String ...paths) {
		this.paths = paths;
	}

	public List<File> getFiles() throws ClauseException {
		List<File> files = new ArrayList<File>(paths.length);
		
		for (String path : paths) {
			File in = new File(path);
			
			if (!in.exists()) {
				throw new ClauseException(path + " not exist");
			}
			
			if (!in.isDirectory()) {
				throw new ClauseException(path + " is a file");
			}
			
			files.add(new File(path));
		}
		
		return files;
	}

}
