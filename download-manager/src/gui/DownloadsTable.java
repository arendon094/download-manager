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
