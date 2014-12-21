package fr.ogama.jfsql;

import java.io.File;
import java.util.List;

import fr.ogama.jfsql.query.Query;
import fr.ogama.jfsql.query.QueryFactory;

public class JFSQL {

	public static void main(String[] args) {
		if (args != null || args.length == 1) {
			try {
				Query query = QueryFactory.newQuery(args[0]);
				List<Comparable> results = query.execute();
				
				if (results != null) {
					for (Comparable result : results) {
						String resultToPrint = "";
						if (result instanceof File) {
							resultToPrint = ((File) result).getPath();
						} else {
							resultToPrint = String.valueOf(result);
						}
						System.out.println(resultToPrint);
					}
				}
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}
	}
}
