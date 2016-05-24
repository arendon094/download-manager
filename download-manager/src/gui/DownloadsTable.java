package gui;

import java.io.File;
import java.nio.channels.InterruptedByTimeoutException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.github.axet.wget.Downloader;

import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Callback;

public class DownloadsTable extends VBox{
	
	private Downloader downloader;
	private TableView<DownloadingFile> downloadsTable;
	private DatabaseTable dbTable;
	private ObservableMap<DownloadingFile, Thread> threadMap;
	
	public DownloadsTable(Downloader downloader, DatabaseTable db) {
		this.downloader = downloader;
		this.dbTable = db;
		this.threadMap = this.dbTable.getThreadMap();
		this.threadMap.addListener( new MapChangeListener() {
            @Override
            public void onChanged(MapChangeListener.Change change) {
        		ObservableList<DownloadingFile> list = FXCollections.observableArrayList(threadMap.keySet());
        		
            	downloadsTable.setItems(list);
            }
        });
	}

	public void createTable() {
		this.getStylesheets().add("style/metroLight.css");
		this.getStyleClass().add("background");
		// Need to add type to tableview once we create
		// class for data
		this.downloadsTable = new TableView<DownloadingFile>();
		TableColumn<DownloadingFile,String>  downloads = new TableColumn<DownloadingFile, String>("Downloads");
		
		TableColumn<DownloadingFile,String>  fileName = new TableColumn<>("File Name");
		TableColumn<DownloadingFile,String>  size = new TableColumn<>("size");
		TableColumn<DownloadingFile,String>  status = new TableColumn<>("Status");
		TableColumn<DownloadingFile,String> speed = new TableColumn<>("speed");
		/*
		speed.setCellFactory(new Callback<TableColumn<DownloadingFile, String>, TableCell<DownloadingFile, String>>(){
			
			@Override
			public TableCell<DownloadingFile, String> call(
					TableColumn<DownloadingFile, String> param) {
				final TableCell cell = new TableCell<DownloadingFile, String>() {
				@Override
				protected void updateItem(String t, boolean bln) {
					super.updateItem(t, bln);
					if(bln){
						setText(null);
					} else {
						setText(t);
					}
				}
				};
				return null;
			}
			
		});
		*/
		//Everytime the cell value is for speed item in DownloadingFile update table
		speed.setCellValueFactory(new PropertyValueFactory<DownloadingFile, String>("speed"));
		//Set text 
		speed.setText("Speed");
		
		size.setCellValueFactory(new PropertyValueFactory<DownloadingFile, String>("size"));
		size.setText("Size");
		
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
		
        /*
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
		*/
	}
	
	/*
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
	}*/
	
	//For now set size to constant...update speed
	public void refresh() {
		
		for(Entry<DownloadingFile, Thread> entry : this.threadMap.entrySet()){

			String speed;
			String size = getCurrentSize(entry.getKey().getName()) + " kb";

			if (downloader.getURL() != null && downloader.getURL().equals(entry.getKey().getName())) {
				//size = String.valueOf(file.length() / 1024) + " kb";
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
			
			entry.getKey().setSpeed(speed);

		}
		
	}
		
	private String getCurrentSize(String name) {
		File[] dir = new File(downloader.getDirectory()).listFiles();
		
		ArrayList<File> allMatching = new ArrayList();	
		
		// not working correctly with multiple identical files
		for (File file : dir) {
			if (file.getName().substring(0, file.getName().length() - 4).contains(name.substring(0, name.length() - 4))) {
				allMatching.add(file);
			}
		}
		return String.valueOf(allMatching.get(allMatching.size() - 1).length() / 1024);
	}
	
	public synchronized void resumetItem() {
		if(this.dbTable != null){
			DownloadingFile file = downloadsTable.getSelectionModel().getSelectedItem();
			if(file != null && this.threadMap != null){
				for(Entry<DownloadingFile, Thread> entry : this.dbTable.getThreadMap().entrySet()){
					if(entry.getKey().getName().equals(file.getName())){
						try{	
							entry.getKey().getDownloader().setPause(false);
							entry.getValue().interrupt();
						} catch (Exception e) {
							//no-op
						}
					}
				}
			}
		}
	}
	
	public void pauseItem() {
		
		//Try just interrupting the thread.
		if(this.dbTable != null){
			DownloadingFile file = downloadsTable.getSelectionModel().getSelectedItem();
			if(file != null && this.dbTable.getThreadMap() != null){
				for(Entry<DownloadingFile, Thread> entry : this.dbTable.getThreadMap().entrySet()){
					if(entry.getKey().getName().equals(file.getName())){
						try{
							entry.getKey().getDownloader().setPause(true);
							entry.getValue().interrupt();
						} catch (Exception e) {
							//no-op
						}
					}
				}
			}
		}
		
	}

	public void deleteSelected() {
		DownloadingFile file = downloadsTable.getSelectionModel().getSelectedItem();
		if(file == null)
		{
			System.out.println("IS NULL");
		}
		File deleteFile = new File(downloader.getDirectory() + "/" + file.getName());
		System.out.println(deleteFile.getAbsolutePath());
		deleteFile.delete();
		
	}
}
