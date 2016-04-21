package gui;

import javafx.beans.property.SimpleStringProperty;

public class DownloadableFile {
	
	private final String name;
	private final String size;
    private final String created;
    private final String encrypted;
     
     public DownloadableFile(String name, String size, String created, String encrypted) {
    	 this.name = name;
    	 this.size = size;
    	 this.created = created;
    	 this.encrypted = encrypted;
     }

	public String getName() {
		return name;
	}

	public String getSize() {
		return size;
	}

	public String getCreated() {
		return created;
	}

	public String getEncrypted() {
		return encrypted;
	}
}