package fr.ogama.jfsql.query;

public enum Status {
	NONE("none", 0),
	READABLE("readable", 1),
	WRITABLE("writable", 2),
	EXECUTABLE("executable", 3);
	
	private String label;
	private int level;
	
	private Status(String label, int level) {
		this.label = label;
		this.level = level;
	}
	
	public static Status getStatusByLabel(String label) {
		if (label == null) {
			return null;
		}
		
		for (Status status : Status.values()) {
			if (status.getLabel().equals(label.toLowerCase())) {
				return status;
			}
		}
		
		return null;
	}
	
	public String getLabel() {
		return label;
	}
	
	public int getLevel() {
		return level;
	}
}
