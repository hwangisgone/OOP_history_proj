package javafx.view.entity;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import database.IDatabase;
import database.LocationDatabase;
import entity.Location;
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

public class LocationSceneController extends SearchController<Location> implements Initializable {

    @FXML
    private TableView<Location> LocationsTableView;

    @FXML
    private TableColumn<Location, String> colGrade;

    @FXML
    private TableColumn<Location, String> colGradeType;

    @FXML
    private TableColumn<Location, String> colID;

    @FXML
    private TableColumn<Location, String> colLocated;

    @FXML
    private TableColumn<Location, String> colOtherNames;

    @FXML
    private TableColumn<Location, String> colPosition;

    @FXML
    private TableColumn<Location, String> colWorship;


    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private TextField fieldSearch;

    @FXML
    private HBox hboxFeature;


    private IDatabase<Location> locsData = new LocationDatabase();

    @Override
	public void refresh() {
        data = FXCollections.observableArrayList(locsData.load());

        // Bind the ObservableList to the TableView
	    LocationsTableView.setItems(data);
    }

	@Override
	protected void initSearchMap() {
		searchMap = new HashMap<>();
		searchMap.put("Tên",		loc -> loc.getName());
		searchMap.put("Tên khác",	loc -> loc.getOtherNames().toString());
		searchMap.put("Địa điểm",	loc -> loc.getLocated());
		searchMap.put("Vị trí",		loc -> loc.getPosition());
		searchMap.put("Xếp hạng",	loc -> loc.getGrade());
		searchMap.put("Loại xếp hạng",		loc -> loc.getGradeType());
		searchMap.put("Tôn thờ",	loc -> loc.getWorship());
		// Tiểu sử, mô tả

		ObservableList<String> itemsList = FXCollections.observableArrayList(searchMap.keySet());
        comboBox.setItems(itemsList);
        comboBox.setValue(itemsList.get(0));
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		 System.out.println("Location controller initialized");
	        // Add a default row
		refresh();
		initSearchMap();

        colID.setCellValueFactory(new PropertyValueFactory<>("name"));
        // colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));

        colOtherNames.setCellValueFactory(new PropertyValueFactory<>("otherNames"));
        colLocated.setCellValueFactory(new PropertyValueFactory<>("located"));
        colPosition.setCellValueFactory(new PropertyValueFactory<>("position"));
        colGrade.setCellValueFactory(new PropertyValueFactory<>("grade"));
        colGradeType.setCellValueFactory(new PropertyValueFactory<>("gradeType"));
        colWorship.setCellValueFactory(new PropertyValueFactory<>("worship"));

        labelDescription.wrappingWidthProperty().bind(scrollText.widthProperty());
	}
//	@Override
//	public void initialize() {
//
//
//        // Bind the columns to the corresponding properties in MyDataModel
//        colID.setCellValueFactory(new PropertyValueFactory<>("publicationID"));
//        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
//        colPublicationDate.setCellValueFactory(new PropertyValueFactory<>("releaseDate"));
//        colCountry.setCellValueFactory(new PropertyValueFactory<>("country"));
//        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
//
//        colAuthors.setCellValueFactory(new PropertyValueFactory<>("authors"));
//        colPublisher.setCellValueFactory(new PropertyValueFactory<>("publisher"));
//        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
//        colReissue.setCellValueFactory(new PropertyValueFactory<>("reissue"));
//	}

    @FXML
    void inputSearch(KeyEvent event) {
        String searchText = fieldSearch.getText();
        String searchOption = comboBox.getValue();
        SearchData(searchText, searchOption);
    }

    private void SearchData(String searchText, String searchOption) {
        if (searchText.isEmpty()) {
            // If the search text is empty, revert to the original unfiltered list
        	LocationsTableView.setItems(data);
        } else {
		    LocationsTableView.setItems(getSearchData(searchText, searchOption));
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

    private void switchPane(Location selectLocation) {
    	if (selectLocation == null) {
        	paneInfo.setVisible(false);
        	paneTable.setVisible(true);
    	} else {
        	paneInfo.setVisible(true);
        	paneTable.setVisible(false);

//        	URLConnection connection;
//			try {
//				String url = selectLocation.getImage();
//				connection = new URL(url.replace('\\', '/')).openConnection();
//	        	connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
//	        	Image image = new Image(connection.getInputStream());
//	            imageInfo.setImage(image);
//			} catch (MalformedURLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}

        	labelTitle.setText(selectLocation.getName());
        	labelDescription.setText(selectLocation.getDescription());
        	labelInfo.setText(selectLocation.toString());
    	}
    }

    @FXML
    void btnActionReturn(ActionEvent event) {
    	switchPane(null);
    }

    @FXML
    void tableClick(MouseEvent event) {
        Location entity = LocationsTableView.getSelectionModel().getSelectedItem();
        switchPane(entity);
    }
}
