package javafx.view.entity;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Function;

import database.FestivalDatabase;
import database.IDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import entity.Festival;

public class FestivalSceneController extends SearchController<Festival> implements Initializable {

    @FXML
    private TableView<Festival> FestivalsTableView;

    @FXML
    private TableColumn<Festival, String> colDate;

    @FXML
    private TableColumn<Festival, String> colDescription;

    @FXML
    private TableColumn<Festival, String> colID;

    @FXML
    private TableColumn<Festival, String> colLocation;


    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private TextField fieldSearch;

    @FXML
    private HBox hboxFeature;

    private IDatabase<Festival> festsData = new FestivalDatabase();
    
    public void refresh() {
        data = FXCollections.observableArrayList(festsData.load());
        
        // Bind the ObservableList to the TableView
	    FestivalsTableView.setItems(data);
    }
    
	@Override
	protected void initSearchMap() {
		searchMap = new HashMap<>();
		searchMap.put("Tên",		loc -> loc.getName());
		searchMap.put("Địa điểm",	loc -> loc.getLocation());
		searchMap.put("Thời gian",	loc -> loc.getDate());
		// Mô tả
		
		ObservableList<String> itemsList = FXCollections.observableArrayList(searchMap.keySet());
        comboBox.setItems(itemsList);
        comboBox.setValue("Tên");
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		 System.out.println("Festival controller initialized");
	        // Add a default row
		refresh();
		initSearchMap();
        
        colID.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        
        colLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
	}

    @FXML
    void inputSearch(KeyEvent event) {
        String searchText = fieldSearch.getText();
        String searchOption = comboBox.getValue();
        
        if (searchText.isEmpty()) {
            // If the search text is empty, revert to the original unfiltered list
        	FestivalsTableView.setItems(data);
        } else {
		    FestivalsTableView.setItems(getSearchData(searchText, searchOption));
		}
    }
}
