/**
 * 
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.
 * 
 * This code is produced by the California State University of Los Angeles for
 * the Jet Propulsion Laboratory (JPL).
 */
package gui;

import javafx.scene.image.Image;

/**
 * This class handles the meta data and thumbnail for an GeoTIFF file from the S3 bucket.
 * 
 * @author Rowan Edge
 * @author Mariah Martinez
 * 
 */

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
