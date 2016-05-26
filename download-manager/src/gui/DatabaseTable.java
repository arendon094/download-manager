package gui;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.thoughtworks.xstream.XStream;

import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.geometry.Insets;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

import com.github.axet.wget.Downloader;
import com.github.axet.wget.info.DownloadInfo;
import com.github.axet.wget.info.URLInfo;
import com.github.axet.wget.info.DownloadInfo.Part.States;


public class DatabaseTable extends VBox {
	private TableView<DownloadableFile> dbTable;
	private ObservableList<DownloadableFile> data;
	private ObservableList<DownloadableFile> filteredData;
	private MenuBarNode menuBar;
	private ObservableMap<DownloadingFile,Thread> threadMap;
	private SummaryNode summaryNode;
	
	public DatabaseTable(SummaryNode summaryNode) throws Exception {
		this.summaryNode = summaryNode;
		Map<DownloadingFile,Thread> threadTMap= new HashMap<DownloadingFile,Thread>();
		this.threadMap = FXCollections.observableMap(threadTMap);
		createTable();
	}

	private void createTable() throws Exception{
		// Need to add type to tableview once we create
		// class for data
		
		data = FXCollections.observableArrayList();
		filteredData = FXCollections.observableArrayList();
		dbTable = new TableView<DownloadableFile>();

		TableColumn database = new TableColumn("JPL");

		TableColumn fileName = new TableColumn("File Name");
		fileName.setCellValueFactory(new PropertyValueFactory<>("name"));

		TableColumn size = new TableColumn("Size (mb)");
		size.setCellValueFactory(new PropertyValueFactory<>("size"));
		
		TableColumn created = new TableColumn("Created");
		created.setCellValueFactory(new PropertyValueFactory<>("created"));

		TableColumn encrypted = new TableColumn("Encrypted?");
		encrypted.setCellValueFactory(new PropertyValueFactory<>("encrypted"));

		dbTable.getColumns().addAll(database);
		database.getColumns().addAll(fileName, size, created, encrypted);

		this.setSpacing(5);
		database.prefWidthProperty().bind(dbTable.widthProperty());
		this.setPadding(new Insets(10, 10, 10, 10));
		this.getChildren().addAll(dbTable);	
		
//		this.summaryNode.getDownloadButton().setOnAction((event) -> {
//			downloadFile(dbTable.getSelectionModel().getSelectedItem());
//		});
		XStream xstream = new XStream();
		xstream.ignoreUnknownElements();
		xstream.processAnnotations(Contents.class);
		xstream.processAnnotations(ListBucketResult.class);
		
		dbTable.setRowFactory( tv -> {
		    TableRow<DownloadableFile> row = new TableRow<>();
		    row.setOnMouseClicked(event -> {
		    	if (event.getClickCount() == 1 && ! row.isEmpty()) {
		    		Downloader imageDownloader = new Downloader();
		    		imageDownloader.setDirectory(menuBar.getDirectory());
		    		imageDownloader.setURL(row.getItem().getName() + ".thumb");
		    		
		    		try {		    			
		    			File file = null;
		    			Thread t2 = new Thread(imageDownloader);		
		    			t2.start();

		    				String filename = imageDownloader.getURL();
		    				filename = filename.replace(".tif.thumb", "-thumb.tif");
			    			file = new File(menuBar.getDirectory() + "/" + filename);
			    			while (t2.isAlive()) {
			    				file = new File(menuBar.getDirectory() + "/" + filename);
			    			}
		    			
		    			summaryNode.showSummary(row.getItem());
		    			
		    			if(file.exists()) {
		    				summaryNode.updateImage(file);
		    			} else {
		    				summaryNode.updateImage(new File("images/default.png"));
		    			}
		    			
		    			
		    		} catch(Exception e) {
		    			e.printStackTrace();
		    		}
		    				    	}
		        if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
		        	downloadFile(row.getItem());
		        }
		    });
		    return row ;
		});
		
		URL cf = new URL("http://djhrn44g26er2.cloudfront.net");
		String results = getResults(cf);

		ListBucketResult bucket = (ListBucketResult) xstream.fromXML(results);
		
		for (int i = 0; i < bucket.contents.size(); i++) {
			Contents c = (Contents) bucket.contents.get(i);
			
			double fileSize = Double.parseDouble(c.fileSize);
			fileSize /= 1000000;
			 
			 String sizeRounded = String.format("%.2f", fileSize);
			
			if (!c.fileName.contains("thumb")) {
				data.add(new DownloadableFile(c.fileName.toString(), sizeRounded, "04-05-2016", "no", new Image("images/default.png")));
			}
		}
		
		dbTable.setItems(data);
	}
	
	private void downloadFile(DownloadableFile item) {
		DownloadableFile rowData = item;
        Downloader tempDownloader = new Downloader();
        tempDownloader.setURL(rowData.getName());
        tempDownloader.setDirectory(menuBar.getDirectory());
        Thread t1 = new Thread(tempDownloader);
        //Try this first to see if this will work.
        this.threadMap.put(new DownloadingFile(rowData.getName(), null, rowData.getSize(), tempDownloader, Double.parseDouble(rowData.getSize())), t1);
        t1.start();	
	}

	private String getResults(URL url) throws IOException {
		StringBuilder result = new StringBuilder();

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line;
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		rd.close();
		return result.toString();
	}

	public void updateFilter(String input) {
		filteredData.clear();
		for(DownloadableFile file : data) {
			if(matches(file, input)) {
				filteredData.add(file);
			}
		}
		ArrayList<TableColumn<DownloadableFile, ?>> order = new ArrayList<>(dbTable.getSortOrder());
        dbTable.getSortOrder().clear();
        dbTable.getSortOrder().addAll(order);
		dbTable.setItems(filteredData);
	}
	
	private Boolean matches(DownloadableFile file, String input) {
		String lowerCaseFilter = input.toLowerCase();
		if(input == null || input.isEmpty()) {
			return true;
		}	
		if (file.getName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
			return true;
		}
		else if (file.getSize().toLowerCase().indexOf(lowerCaseFilter) != -1) {
			return true;
		}
		return false;
	}
	
	public void setMenuBar(MenuBarNode menuBar) {
		this.menuBar = menuBar;
	}
	
	public ObservableMap<DownloadingFile, Thread> getThreadMap(){
		return this.threadMap;
	}
}
