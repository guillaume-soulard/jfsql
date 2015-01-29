package fr.ogama.utils.parser.model.get;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.ogama.utils.parser.model.get.properties.Content;
import fr.ogama.utils.parser.model.get.properties.CreationDate;
import fr.ogama.utils.parser.model.get.properties.LastAccessDate;
import fr.ogama.utils.parser.model.get.properties.LastUpdateDate;
import fr.ogama.utils.parser.model.get.properties.Name;
import fr.ogama.utils.parser.model.get.properties.Parent;
import fr.ogama.utils.parser.model.get.properties.Path;
import fr.ogama.utils.parser.model.get.properties.Size;
import fr.ogama.utils.parser.model.get.properties.Status;
import fr.ogama.utils.parser.model.get.properties.Type;

public class SortByClause {
	private String property;
	private boolean isAscendingOrder;

	public List<File> sort(List<File> list) {

		Collections.sort(list, new Comparator<File>() {
			public int compare(File o1, File o2) {
				Comparable c1 = o1;
				Comparable c2 = o2;

				try {
					if (property != null) {
						Map<String, Comparable> params = new HashMap<String, Comparable>();

						if (FileProperties.NAME.getLabel().equalsIgnoreCase(
								property.toLowerCase())) {
							params.put("file", o1);
							c1 = new Name().execute(params).get(0);
							params.put("file", o2);
							c2 = new Name().execute(params).get(0);
						}

						if (FileProperties.PATH.getLabel().equalsIgnoreCase(
								property.toLowerCase())) {
							params.put("file", o1);
							c1 = new Path().execute(params).get(0);
							params.put("file", o2);
							c2 = new Path().execute(params).get(0);
						}

						if (FileProperties.CONTENT.getLabel().equalsIgnoreCase(
								property.toLowerCase())) {
							params.put("file", o1);
							c1 = new Content().execute(params).get(0);
							params.put("file", o2);
							c2 = new Content().execute(params).get(0);
						}

						if (FileProperties.CREATION_DATE.getLabel()
								.equalsIgnoreCase(property.toLowerCase())) {
							params.put("file", o1);
							c1 = new CreationDate().execute(params).get(0);
							params.put("file", o2);
							c2 = new CreationDate().execute(params).get(0);
						}

						if (FileProperties.LAST_ACCESS_DATE.getLabel()
								.equalsIgnoreCase(property.toLowerCase())) {
							params.put("file", o1);
							c1 = new LastAccessDate().execute(params).get(0);
							params.put("file", o2);
							c2 = new LastAccessDate().execute(params).get(0);
						}

						if (FileProperties.LAST_UPDATE_DATE.getLabel()
								.equalsIgnoreCase(property.toLowerCase())) {
							params.put("file", o1);
							c1 = new LastUpdateDate().execute(params).get(0);
							params.put("file", o2);
							c2 = new LastUpdateDate().execute(params).get(0);
						}

						if (FileProperties.PARENT.getLabel().equalsIgnoreCase(
								property.toLowerCase())) {
							params.put("file", o1);
							c1 = new Parent().execute(params).get(0);
							c2 = new Parent().execute(params).get(0);
						}

						if (FileProperties.STATUS.getLabel().equalsIgnoreCase(
								property.toLowerCase())) {
							params.put("file", o1);
							c1 = new Status().execute(params).get(0);
							params.put("file", o2);
							c2 = new Status().execute(params).get(0);
						}

						if (FileProperties.TYPE.getLabel().equalsIgnoreCase(
								property.toLowerCase())) {
							params.put("file", o1);
							c1 = new Type().execute(params).get(0);
							params.put("file", o2);
							c2 = new Type().execute(params).get(0);
						}
						
						if (FileProperties.SIZE.getLabel().equalsIgnoreCase(
								property.toLowerCase())) {
							params.put("file", o1);
							c1 = new Size().execute(params).get(0);
							params.put("file", o2);
							c2 = new Size().execute(params).get(0);
						}
					}
				} catch (Exception e) {
					throw new RuntimeException(e.getMessage(), e);
				}

				return isAscendingOrder ? c1.compareTo(c2) : c2.compareTo(c1);
			}
		});

		return list;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public boolean isAscendingOrder() {
		return isAscendingOrder;
	}

	public void setAscendingOrder(boolean isAscendingOrder) {
		this.isAscendingOrder = isAscendingOrder;
	}
}
