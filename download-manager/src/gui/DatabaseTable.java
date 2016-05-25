package gui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import com.thoughtworks.xstream.XStream;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import com.github.axet.wget.Downloader;


public class DatabaseTable extends VBox {
	private Downloader downloader;
	private TableView<DownloadableFile> dbTable;
	private ObservableList<DownloadableFile> data;
	private ObservableList<DownloadableFile> filteredData;
	private MenuBarNode menuBar;
	public DatabaseTable(Downloader downloader) throws Exception {
		this.downloader = downloader;
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

		XStream xstream = new XStream();
		xstream.ignoreUnknownElements();
		xstream.processAnnotations(Contents.class);
		xstream.processAnnotations(ListBucketResult.class);
		
		dbTable.setRowFactory( tv -> {
		    TableRow<DownloadableFile> row = new TableRow<>();
		    row.setOnMouseClicked(event -> {
		        if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
		            DownloadableFile rowData = row.getItem();
		            downloader.setURL(rowData.getName());
		            downloader.setDirectory(menuBar.getDirectory());
		            Thread t1 = new Thread(downloader);
		            t1.start();
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
			
			data.add(new DownloadableFile(c.fileName.toString(), Double.toString(fileSize), "", ""));
		}
		
		dbTable.setItems(data);
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

	private void dumpData(ListBucketResult results) {

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
}
