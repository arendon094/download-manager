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

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.github.axet.wget.Downloader;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * This class handles the meta-data for an downloading GeoTIFF file
 * from the S3 bucket.  Additionally, each object of this class for each,
 * downloading file will spin off a thread which updates the meta-data once
 * a second.
 * 
 * @author Rowan Edge
 * @author Mariah Martinez
 * @author Gregory Miles
 */

public class DownloadingFile {
	private SimpleStringProperty name;
	private SimpleStringProperty speed;
	private SimpleStringProperty size;
	private Downloader downloader;
	private SimpleDoubleProperty status;
	private double trueSize;
	
	public DownloadingFile(String name, String speed, String size, Downloader dl, Double status) {
		this.name = new SimpleStringProperty(name);
		this.size = new SimpleStringProperty(size);
		this.speed = new SimpleStringProperty(speed);
		this.status = new SimpleDoubleProperty(status);
		this.downloader = dl;
		this.trueSize = Double.parseDouble(size);
		
		if(this.downloader != null){
			Runnable helloRunnable = new Runnable() {
				public void run() {
					try {
						refresh();
					}catch(Exception e) {
						e.printStackTrace();
						throw new RuntimeException(e);
					}
				}
			};

			ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
			executor.scheduleAtFixedRate(helloRunnable, 0, 1, TimeUnit.SECONDS);
		}
	}

    	/**
 	 * Called once a second by the runnable to change the 
 	 * associated PropertyValueFactory associated cell in
	 * a the DownloadsTable of the donwload-manager.
	 *
	 * @param  	void
	 * @return      void
	 */
	private void refresh() {
		this.speed.set(this.downloader.formatSpeed(this.downloader.getSpeedInfo().getCurrentSpeed()));
		this.size.set(String.format("%.2f",this.downloader.getSize()/1000000.0));
		this.status.set((Double.parseDouble(this.size.getValue()) / this.trueSize));
		this.name.set(this.downloader.getName());
	}
	
	public Downloader getDownloader(){
		return this.downloader;
	}

	public String getName() {
		return this.name.get();	
	}

	public String getSpeed() {
		return this.speed.get();	
	}

	public String getSize() {
		return this.size.get();	
	}

    // Required code format for PropertyValueFactory
    // to listen on changes for these values. <value>Property
    // must be the name of the function.
    
	public SimpleStringProperty nameProperty(){
		return this.name;
	}
	
	public SimpleStringProperty sizeProperty(){
		return this.size;
	}
	
	public SimpleStringProperty speedProperty(){
		return this.speed;
	}
	
	public SimpleDoubleProperty statusProperty(){
		return this.status;
	}

	public void setName() {
		this.name = name;	
	}

	public void setSpeed(String speed) {
		this.speed = new SimpleStringProperty(speed);	
	}

	public void setSize() {
		this.size = size;	
	}

}
