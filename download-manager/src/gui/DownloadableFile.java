package gui;

import javafx.scene.image.Image;

public class DownloadableFile {
	
	private final String name;
	private final String size;
    private final String created;
    private final String encrypted;
	private Image image;
     
     public DownloadableFile(String name, String size, String created, String encrypted, Image image) {
    	 this.name = name;
    	 this.size = size;
    	 this.created = created;
    	 this.encrypted = encrypted;
    	 this.image = image;
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

	public Image getImage() {
		// TODO Auto-generated method stub
		return this.image;
	}
}