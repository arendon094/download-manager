package gui;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.github.axet.wget.Downloader;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class DownloadsTable extends VBox{
	
	private Downloader downloader;
	private TableView<DownloadingFile> downloadsTable;
	
	public DownloadsTable(Downloader downloader) {
		this.downloader = downloader;
	}

	public void createTable() {
		this.getStylesheets().add("style/metroLight.css");
		this.getStyleClass().add("background");
		// Need to add type to tableview once we create
		// class for data
		this.downloadsTable = new TableView();
		TableColumn downloads = new TableColumn("Downloads");
		
		TableColumn fileName = new TableColumn("File Name");
		TableColumn size = new TableColumn("Size");
		TableColumn status = new TableColumn("Status");
		TableColumn speed = new TableColumn("Speed");
		TableColumn remaining = new TableColumn("Time Remaining");
		TableColumn created = new TableColumn("Created");
		TableColumn encrypted = new TableColumn("Encrypted?");
		
		downloads.getColumns().addAll(fileName, size, status, speed, created, encrypted);
		downloadsTable.getColumns().addAll(downloads);
		
		this.setSpacing(5);
		this.setPadding(new Insets(10, 10, 10, 10));
		this.getChildren().addAll(downloadsTable);
		
		fileName.setCellValueFactory(new PropertyValueFactory<>("name"));
		size.setCellValueFactory(new PropertyValueFactory<>("size"));
		speed.setCellValueFactory(new PropertyValueFactory<>("speed"));
		
        
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
	
	public void refresh() {
		File[] dir = new File(downloader.getDirectory()).listFiles();
		if(dir.length != 0) {
		ObservableList<DownloadingFile> list = FXCollections.observableArrayList();
		
		for (File file : dir ) {
			int dot = file.getName().lastIndexOf(".");
			if (file.getName().substring(dot + 1).equals("tif")) {
				
				String speed;
				String size = getCurrentSize(file.getName()) + " kb";
				
				if (downloader.getURL() != null && downloader.getURL().equals(file.getName())) {
					size = String.valueOf(file.length() / 1024) + " kb";
					speed = Integer.toString(downloader.getSpeedInfo().getAverageSpeed());
					
					if (Integer.parseInt(speed) > 0.1 * 1024 * 1024 * 1024) {
			            float f = Integer.parseInt(speed) / 1024f / 1024f / 1024f;
			            speed =  String.format("%.1f GB", f);
			        } else if (Integer.parseInt(speed) > 0.1 * 1024 * 1024) {
			            float f = Integer.parseInt(speed) / 1024f / 1024f;
			            speed = String.format("%.1f MB", f);
			        } else {
			            float f = Integer.parseInt(speed) / 1024f;
			            speed =  String.format("%.1f kb", f);
			        }
				} else {
					speed = "--";
				}
				
				list.add(new DownloadingFile(file.getName(), speed, size));
			}
		}
		
		downloadsTable.setItems(list);
		}
	}
		
	private String getCurrentSize(String name) {
		File[] dir = new File(downloader.getDirectory()).listFiles();
		
		ArrayList<File> allMatching = new ArrayList();	
		
		for (File file : dir) {
			if (file.getName().substring(0, file.getName().length() - 4).contains(name.substring(0, name.length() - 4))) {
				allMatching.add(file);
				System.out.println(file.getName());
			}
		}
		return String.valueOf(allMatching.get(allMatching.size() - 1).length() / 1024);
	}
}
