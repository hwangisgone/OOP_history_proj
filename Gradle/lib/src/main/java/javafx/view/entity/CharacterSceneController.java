package javafx.view.entity;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import database.CharacterDatabase;
import entity.Character;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;


public class CharacterSceneController extends SearchController<Character> implements Initializable {

    @FXML
    private TableView<Character> CharactersTableView;

    @FXML
    private TableColumn<Character, String> colBiography;

    @FXML
    private TableColumn<Character, String> colBirth;

    @FXML
    private TableColumn<Character, String> colDeath;

    @FXML
    private TableColumn<Character, String> colDynasty;

    @FXML
    private TableColumn<Character, String> colFather;

    @FXML
    private TableColumn<Character, String> colID;

    @FXML
    private TableColumn<Character, String> colMother;

    @FXML
    private TableColumn<Character, String> colFullName;

    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private TextField fieldSearch;

    @FXML
    private HBox hboxFeature;

    private ObservableList<Character> data;
    /*
        Here I don't return any database, just return directly the list of Character,
        which read from file: "src/main/resources/final/Character.json"
     */
    private List<Character> listCharacter = CharacterDatabase.getListCharacter();

    @Override
	public void refresh() {
        data = FXCollections.observableArrayList(listCharacter);

        // Bind the ObservableList to the TableView
	    CharactersTableView.setItems(data);
    }


	@Override
	protected void initSearchMap() {
		searchMap = new HashMap<>();
		searchMap.put("ID",			chara -> chara.getID());
		searchMap.put("Tên đầy đủ",	chara -> chara.getFullName());
		searchMap.put("Ngày sinh",	chara -> chara.getDateOfBirth());
		searchMap.put("Ngày mất",	chara -> chara.getDateOfDeath());
		searchMap.put("Tên bố",		chara -> chara.getFather());
		searchMap.put("Tên mẹ",		chara -> chara.getMother());
		searchMap.put("Triều đại",	chara -> chara.getDynasty());
		// Tiểu sử, mô tả

		ObservableList<String> itemsList = FXCollections.observableArrayList(searchMap.keySet());
        comboBox.setItems(itemsList);
        comboBox.setValue("ID");
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		 System.out.println("Character controller initialized");
	        // Add a default row
			refresh();
			initSearchMap();


	        colID.setCellValueFactory(new PropertyValueFactory<>("ID"));
	        // colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));

	        colFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
	        colBiography.setCellValueFactory(new PropertyValueFactory<>("biography"));
	        colBirth.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
	        colDeath.setCellValueFactory(new PropertyValueFactory<>("dateOfDeath"));
	        colDynasty.setCellValueFactory(new PropertyValueFactory<>("dynasty"));
	        colFather.setCellValueFactory(new PropertyValueFactory<>("father"));
	        colMother.setCellValueFactory(new PropertyValueFactory<>("mother"));

	        labelDescription.wrappingWidthProperty().bind(scrollText.widthProperty().subtract(20));
	        labelInfo.prefWidthProperty().bind(paneExtra.widthProperty());
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

        if (searchText.isEmpty()) {
            // If the search text is empty, revert to the original unfiltered list
        	CharactersTableView.setItems(data);
        } else {
		    CharactersTableView.setItems(getSearchData(searchText, searchOption));
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

    private void switchPane(Character selectCharacter) {
    	if (selectCharacter == null) {
        	paneInfo.setVisible(false);
        	paneTable.setVisible(true);
    	} else {
        	paneInfo.setVisible(true);
        	paneTable.setVisible(false);

        	labelTitle.setText(selectCharacter.getID());
        	labelDescription.setText(selectCharacter.getBiography());
        	labelInfo.setText(selectCharacter.toString());
    	}
    }

    @FXML
    void btnActionReturn(ActionEvent event) {
    	switchPane(null);
    }

    @FXML
    void tableClick(MouseEvent event) {
    	// if (event.getClickCount() == 2) {
            Character entity = CharactersTableView.getSelectionModel().getSelectedItem();
            switchPane(entity);
        // }
    }

}
