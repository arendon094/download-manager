package gui;

public class DownloadingFile {
	String name;
	String speed;
	String size;

	public DownloadingFile(String name, String speed, String size) {
		this.name = name;
		this.size = size;
		this.speed = speed;
	}

	public String getName() {
		return this.name;	
	}

	public String getSpeed() {
		return this.speed;	
	}

	public String getSize() {
		return this.size;	
	}

	public void setName() {
		this.name = name;	
	}

	public void setSpeed() {
		this.speed = speed;	
	}

	public void setSize() {
		this.size = size;	
	}

}