package javafx.view.entity;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import database.EventDatabase;
import database.IDatabase;
import entity.HistoricalEvent;
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

public class EventSceneController extends SearchController<HistoricalEvent> implements Initializable{

    @FXML
    private TableView<HistoricalEvent> HistoricalEventsTableView;

    @FXML
    private TableColumn<HistoricalEvent, String> colTime;

    @FXML
    private TableColumn<HistoricalEvent, String> colID;

    @FXML
    private TableColumn<HistoricalEvent, String> colLocation;

    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private TextField fieldSearch;

    @FXML
    private HBox hboxFeature;

    private IDatabase<HistoricalEvent> db = new EventDatabase();

    @Override
	public void refresh() {
        data = FXCollections.observableArrayList(db.load());

        // Bind the ObservableList to the TableView
	    HistoricalEventsTableView.setItems(data);
    }

	@Override
	protected void initSearchMap() {
		searchMap = new HashMap<>();
		searchMap.put("Tên",		hisevent -> hisevent.getID());
		searchMap.put("Thời gian",	hisevent -> hisevent.getTime());
		searchMap.put("Địa điểm",	hisevent -> hisevent.getLocation());
		// Mô tả

		ObservableList<String> itemsList = FXCollections.observableArrayList(searchMap.keySet());
        comboBox.setItems(itemsList);
        comboBox.setValue("Tên");
	}



	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		 System.out.println("HistoricalEvent controller initialized");
	        // Add a default row
		refresh();
		initSearchMap();

        colID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        // colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));

        colLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));

        labelDescription.wrappingWidthProperty().bind(scrollText.widthProperty().subtract(20));
        labelInfo.prefWidthProperty().bind(paneExtra.widthProperty());
	}

    @FXML
    void inputSearch(KeyEvent event) {
        String searchText = fieldSearch.getText();
        String searchOption = comboBox.getValue();

        if (searchText.isEmpty()) {
            // If the search text is empty, revert to the original unfiltered list
        	HistoricalEventsTableView.setItems(data);
        } else {
		    HistoricalEventsTableView.setItems(getSearchData(searchText, searchOption));
		}
    }

    @FXML
    private ImageView imageInfo;

    @FXML
    private ScrollPane scrollText;

    @FXML
    private VBox paneExtra;


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

    private void switchPane(HistoricalEvent selectHistoricalEvent) {
    	if (selectHistoricalEvent == null) {
        	paneInfo.setVisible(false);
        	paneTable.setVisible(true);
    	} else {
    		paneExtra.getChildren().remove(imageInfo);
        	paneInfo.setVisible(true);
        	paneTable.setVisible(false);


			StringBuilder sb = new StringBuilder();
			sb.append(selectHistoricalEvent.getDescription());
			ExtraStringUtil.appendNotNull(sb, "\n\n Sự kiện liên quan: \n - ", selectHistoricalEvent.getRelatedTo(), "\n - ");
			ExtraStringUtil.appendNotNull(sb, "\n\n Nhân vật liên quan: ", selectHistoricalEvent.getCharacters());

        	labelTitle.setText(selectHistoricalEvent.getID());
        	labelDescription.setText(sb.toString());
        	labelInfo.setText(selectHistoricalEvent.toString());
    	}
    }

    @FXML
    void btnActionReturn(ActionEvent event) {
    	switchPane(null);
    }

    @FXML
    void tableClick(MouseEvent event) {
    	// if (event.getClickCount() == 2) {
            HistoricalEvent entity = HistoricalEventsTableView.getSelectionModel().getSelectedItem();
            switchPane(entity);
        // }
    }
}
