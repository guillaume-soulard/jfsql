package fr.ogama.jfsql.query.clause.in;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import fr.ogama.jfsql.query.clause.InClause;

public class In implements InClause {

	private String[] paths;
	
	public In(String ...paths) {
		this.paths = paths;
	}
	
	public String getName() {
		return "in";
	}

	public List<File> getFiles() {
		List<File> files = new ArrayList<File>(paths.length);
		
		for (String path : paths) {
			files.add(new File(path));
		}
		
		return files;
	}

}
