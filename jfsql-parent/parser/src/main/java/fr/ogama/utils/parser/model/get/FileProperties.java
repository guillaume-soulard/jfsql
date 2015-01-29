package fr.ogama.utils.parser.model.get;

public enum FileProperties {
	FILE("file"),
	NAME("name"),
	PATH("path"),
	PARENT("parent"),
	SIZE("size"),
	CONTENT("content"),
	OWNER("owner"),
	TYPE("type"),
	STATUS("status"),
	CREATION_DATE("creation_date"),
	LAST_ACCESS_DATE("last_access_date"),
	LAST_UPDATE_DATE("last_update_date");
	
	private String label;
	
	private FileProperties(String label) {
		this.label = label;
	}
		
	public String getLabel() {
		return label;
	}
}
