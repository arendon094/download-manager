package gui;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.github.axet.wget.Downloader;

import javafx.beans.property.SimpleStringProperty;

public class DownloadingFile {
	private SimpleStringProperty name;
	private SimpleStringProperty speed;
	private SimpleStringProperty size;
	private Downloader downloader;
	
	public DownloadingFile(String name, String speed, String size, Downloader dl) {
		this.name = new SimpleStringProperty(name);
		this.size = new SimpleStringProperty(size);
		this.speed = new SimpleStringProperty(speed);
		this.downloader = dl;
		
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
	
	private void refresh() {
		this.speed.set(this.downloader.formatSpeed(this.downloader.getSpeedInfo().getCurrentSpeed()));
		this.size.set(String.format("%.2f",this.downloader.getSize()/1048576.0));
		this.name.set(this.downloader.getName());
	}

	public DownloadingFile(String name, String speed, String size) {
		this.name = new SimpleStringProperty(name);
		this.size = new SimpleStringProperty(size);
		this.speed = new SimpleStringProperty(speed);
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
	
	public SimpleStringProperty nameProperty(){
		return this.name;
	}
	
	public SimpleStringProperty sizeProperty(){
		return this.size;
	}
	
	public SimpleStringProperty speedProperty(){
		return this.speed;
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