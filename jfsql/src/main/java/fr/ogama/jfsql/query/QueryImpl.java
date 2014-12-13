package fr.ogama.jfsql.query;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.io.DirectoryWalker;
import org.apache.commons.io.filefilter.IOFileFilter;

import fr.ogama.jfsql.query.clause.GetClause;
import fr.ogama.jfsql.query.clause.HavingClause;
import fr.ogama.jfsql.query.clause.InClause;
import fr.ogama.jfsql.query.clause.RestrictionClause;

public class QueryImpl implements Query {

	private GetClause findClause;
	private InClause inClause;
	private HavingClause havingClause;
	private List<RestrictionClause> restrictionClauses;

	public QueryImpl(GetClause findClause,
			List<RestrictionClause> restrictionClauses, InClause inClause,
			HavingClause havingClause) {
		this.findClause = findClause;
		this.inClause = inClause;
		this.havingClause = havingClause;
		this.restrictionClauses = restrictionClauses;
	}

	public List<Comparable> execute() throws Exception {
		List<File> inFiles = inClause.getFiles();
		List<File> matchFiles = new LinkedList<File>();
		IOFileFilter fileFilter = havingClause != null ? havingClause
				.getFilter() : null;

		for (File inFile : inFiles) {
			List<File> results = getMathingFileOrDirectoryInDirectory(inFile,
					fileFilter);
			matchFiles.addAll(results);
		}

		List<Comparable> selectedItems = new LinkedList<Comparable>();

		for (File file : matchFiles) {
			boolean canSelectMore = true;
			for (RestrictionClause restrictionClause : restrictionClauses) {
				if (!restrictionClause.canSelect(findClause, file, matchFiles,
						selectedItems)) {
					canSelectMore = false;
				}
			}

			if (!canSelectMore) {
				break;
			}
			Comparable selected = findClause.select(file);
			if (selected != null) {
				selectedItems.add(selected);
			}
		}

		return selectedItems;
	}

	private List<File> getMathingFileOrDirectoryInDirectory(File directory,
			IOFileFilter fileFilter) {
		List<File> matchingFiles = new LinkedList<File>();

		for (File file : directory.listFiles()) {
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
						file, fileFilter));
			}
		}

		return matchingFiles;
	}
}
