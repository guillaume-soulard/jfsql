package fr.ogama.utils.parser.model.get;

import java.io.File;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import fr.ogama.utils.parser.JFSQLExecutionException;
import fr.ogama.utils.parser.model.Statement;

public class GetStatement implements Statement, Expression {
	private GetClause getClause;
	private InClause inClause;
	private HavingClause havingClause;
	private SortByClause sortByClause;

	public GetClause getGetClause() {
		return getClause;
	}

	public void setGetClause(GetClause getClause) {
		this.getClause = getClause;
	}

	public InClause getInClause() {
		return inClause;
	}

	public void setInClause(InClause inClause) {
		this.inClause = inClause;
	}

	public HavingClause getHavingClause() {
		return havingClause;
	}

	public void setHavingClause(HavingClause havingClause) {
		this.havingClause = havingClause;
	}

	public SortByClause getSortByClause() {
		return sortByClause;
	}

	public void setSortByClause(SortByClause sortByClause) {
		this.sortByClause = sortByClause;
	}

	public List<Comparable> execute(Map<String, Comparable> params)
			throws JFSQLExecutionException {
		List<PathItem> inItems = inClause.getPathItems();
		List<File> matchFiles = new LinkedList<File>();
		Expression fileFilter = havingClause != null ? havingClause
				.getExpression() : null;

		for (PathItem inItem : inItems) {
			File inFile = new File(inItem.getPath(params));
			if (inFile != null && inFile.exists()) {
				List<File> results = getMathingFileOrDirectoryInDirectory(
						inFile, fileFilter, params, inItem.getDeep(), 1);
				matchFiles.addAll(results);
			} else {
				throw new JFSQLExecutionException(inFile + " not exists");
			}
		}

		List<Comparable> selectedItems = new LinkedList<Comparable>();

		if (sortByClause != null) {
			matchFiles = sortByClause.sort(matchFiles);
		}

		for (File file : matchFiles) {
			params.put("file", file);

			if (selectedItems.size() >= getClause.getLimit()) {
				// selected items limit is reached
				break;
			}

			Comparable selected = getClause.getProperty(params);

			if (getClause.isDistinct() && selectedItems.contains(selected)) {
				// file is already selected
				continue;
			}

			if (selected != null) {
				selectedItems.add(selected);
			}
		}

		return selectedItems;
	}

	private List<File> getMathingFileOrDirectoryInDirectory(File directory,
			Expression fileFilter, Map<String, Comparable> params, int maxDeep,
			int deepLevel) throws JFSQLExecutionException {
		List<File> matchingFiles = new LinkedList<File>();

		if (deepLevel <= maxDeep) {
			File[] files = directory.listFiles();
			if (files != null) {
				for (File file : files) {
					boolean accept = false;

					if (fileFilter == null) {
						accept = true;
					} else {
						params.put("file", file);
						List<Comparable> result = fileFilter.execute(params);

						if (result != null && result.size() == 1
								&& result.get(0) instanceof Boolean
								&& ((Boolean) result.get(0))) {
							accept = true;
						}
					}

					if (accept) {
						matchingFiles.add(file);
					}

					if (file.isDirectory()) {
						matchingFiles
								.addAll(getMathingFileOrDirectoryInDirectory(
										file, fileFilter, params, maxDeep,
										deepLevel + 1));
					}
				}
			}
		}

		return matchingFiles;
	}
}
