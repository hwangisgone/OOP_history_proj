package javafx.view.entity;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import database.DynastyDatabase;
import database.IDatabase;
import entity.Dynasty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import util.ExtraStringUtil;

public class DynastySceneController extends SearchController<Dynasty> implements Initializable{

    @FXML
    private TableView<Dynasty> DynastiesTableView;

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

    @Override
	public void refresh() {
        data = FXCollections.observableArrayList(dysData.load());

        // Bind the ObservableList to the TableView
	    DynastiesTableView.setItems(data);
    }

	@Override
	protected void initSearchMap() {
		searchMap = new HashMap<>();
		searchMap.put("Tên",		dynasty -> dynasty.getName());
		searchMap.put("Tên đầy đủ",	dynasty -> ExtraStringUtil.addComma(dynasty.getLongName()));
		searchMap.put("Tên khác",	dynasty -> ExtraStringUtil.addComma(dynasty.getNativeName()));
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
        // colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));

        colLongName.setCellValueFactory(celldata -> {
        	return new SimpleStringProperty(ExtraStringUtil.addComma(celldata.getValue().getLongName()));
        });
        colNativeName.setCellValueFactory(celldata -> {
        	return new SimpleStringProperty(ExtraStringUtil.addComma(celldata.getValue().getNativeName()));
        });
        colYearStart.setCellValueFactory(new PropertyValueFactory<>("yearEnd"));
        colYearEnd.setCellValueFactory(new PropertyValueFactory<>("yearEnd"));

        labelDescription.wrappingWidthProperty().bind(scrollText.widthProperty());
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

    @FXML
    private ImageView imageInfo;

    @FXML
    private ScrollPane scrollText;

    @FXML
    private Text labelDescription;

    @FXML
    private Label labelInfo;

    @FXML
    private Label labelTitle;

    @FXML
    private BorderPane paneInfo;

    @FXML
    private VBox paneTable;

    private void switchPane(Dynasty selectDynasty) {
    	if (selectDynasty == null) {
        	paneInfo.setVisible(false);
        	paneTable.setVisible(true);
    	} else {
        	paneInfo.setVisible(true);
        	paneTable.setVisible(false);

        	labelTitle.setText(selectDynasty.getName());
        	labelDescription.setText(selectDynasty.getDescription());
        	labelInfo.setText(selectDynasty.toString());
    	}
    }

    @FXML
    void btnActionReturn(ActionEvent event) {
    	switchPane(null);
    }

    @FXML
    void tableClick(MouseEvent event) {
    	// if (event.getClickCount() == 2) {
            Dynasty entity = DynastiesTableView.getSelectionModel().getSelectedItem();
            switchPane(entity);
        // }
    }
}
