package fr.ogama.jfsql.query;

import java.io.File;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.filefilter.IOFileFilter;

import fr.ogama.jfsql.query.clause.GetClause;
import fr.ogama.jfsql.query.clause.HavingClause;
import fr.ogama.jfsql.query.clause.InClause;
import fr.ogama.jfsql.query.clause.RestrictionClause;
import fr.ogama.jfsql.query.clause.SortClause;

public class QueryImpl implements Query {

	private GetClause getClause;
	private InClause inClause;
	private HavingClause havingClause;
	private List<RestrictionClause> restrictionClauses;
	private SortClause sortClause;
	private int maxDeep;

	public QueryImpl(GetClause getClause,
			List<RestrictionClause> restrictionClauses, InClause inClause,
			HavingClause havingClause, SortClause sortClause, Integer maxDeep) {
		this.getClause = getClause;
		this.inClause = inClause;
		this.havingClause = havingClause;
		this.restrictionClauses = restrictionClauses;
		this.sortClause = sortClause;
		this.maxDeep = maxDeep == null ? -1 : maxDeep.intValue();
	}

	public List<Comparable> execute() throws Exception {
		List<File> inFiles = inClause.getFiles();
		List<File> matchFiles = new LinkedList<File>();
		IOFileFilter fileFilter = havingClause != null ? havingClause
				.getFilter() : null;

		for (File inFile : inFiles) {
			List<File> results = getMathingFileOrDirectoryInDirectory(inFile,
					fileFilter, 1);
			matchFiles.addAll(results);
		}

		List<Comparable> selectedItems = new LinkedList<Comparable>();

		if (sortClause != null) {
			Collections.sort(matchFiles, sortClause.getComparator(getClause));
		}
		
		for (File file : matchFiles) {
			boolean canSelectMore = true;
			for (RestrictionClause restrictionClause : restrictionClauses) {
				if (!restrictionClause.canSelect(getClause, file, matchFiles,
						selectedItems)) {
					canSelectMore = false;
				}
			}

			if (!canSelectMore) {
				continue;
			}
			Comparable selected = getClause.select(file);
			if (selected != null) {
				selectedItems.add(selected);
			}
		}
					
		return selectedItems;
	}

	private List<File> getMathingFileOrDirectoryInDirectory(File directory,
			IOFileFilter fileFilter, int deepLevel) {
		List<File> matchingFiles = new LinkedList<File>();

		if (deepLevel <= maxDeep) {
			File[] files = directory.listFiles();
			if (files != null) {
				for (File file : files) {
					boolean accept = false;
					if (fileFilter == null) {
						accept = true;
					} else {
						accept = fileFilter.accept(file);
					}
					if (accept) {
						matchingFiles.add(file);
					}
	
					if (file.isDirectory()) {
						matchingFiles.addAll(getMathingFileOrDirectoryInDirectory(
								file, fileFilter, deepLevel + 1));
					}
				}
			}
		}

		return matchingFiles;
	}
}
