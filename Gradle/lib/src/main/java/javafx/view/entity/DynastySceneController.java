package javafx.view.entity;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import database.DynastyDatabase;
import database.IDatabase;
import entity.Dynasty;
import entity.Festival;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;

public class DynastySceneController extends SearchController<Dynasty> implements Initializable{

    @FXML
    private TableView<Dynasty> DynastiesTableView;

    @FXML
    private TableColumn<Dynasty, String> colDescription;
    
    @FXML
    private TableColumn<Dynasty, String> colID;

    @FXML
    private TableColumn<Dynasty, String> colLongName;

    @FXML
    private TableColumn<Dynasty, String> colNativeName;

    @FXML
    private TableColumn<Dynasty, String> colYearEnd;

    @FXML
    private TableColumn<Dynasty, String> colYearStart;

    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private TextField fieldSearch;

    @FXML
    private HBox hboxFeature;

    private IDatabase<Dynasty> dysData = new DynastyDatabase();
    
    public void refresh() {
        data = FXCollections.observableArrayList(dysData.load());
        
        // Bind the ObservableList to the TableView
	    DynastiesTableView.setItems(data);
    }
    
	@Override
	protected void initSearchMap() {
		searchMap = new HashMap<>();
		searchMap.put("Tên",		dynasty -> dynasty.getName());
		searchMap.put("Tên đầy đủ",	dynasty -> dynasty.getLongNameString());
		searchMap.put("Tên khác",	dynasty -> dynasty.getNativeNameString());
		// Mô tả
		
		ObservableList<String> itemsList = FXCollections.observableArrayList(searchMap.keySet());
        comboBox.setItems(itemsList);
        comboBox.setValue("Tên");
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		 System.out.println("Dynasty controller initialized");
	        // Add a default row
		refresh();
		initSearchMap();
        
        colID.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        
        colLongName.setCellValueFactory(new PropertyValueFactory<>("longNameString"));
        colNativeName.setCellValueFactory(new PropertyValueFactory<>("nativeNameString"));
        colYearStart.setCellValueFactory(new PropertyValueFactory<>("yearEnd"));
        colYearEnd.setCellValueFactory(new PropertyValueFactory<>("yearEnd"));
	}

    @FXML
    void inputSearch(KeyEvent event) {
        String searchText = fieldSearch.getText();
        String searchOption = comboBox.getValue();
        
        if (searchText.isEmpty()) {
            // If the search text is empty, revert to the original unfiltered list
        	DynastiesTableView.setItems(data);
        } else {
		    DynastiesTableView.setItems(getSearchData(searchText, searchOption));
		}
    }

}
