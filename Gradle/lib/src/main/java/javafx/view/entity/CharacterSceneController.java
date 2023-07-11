package javafx.view.entity;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Function;

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

import entity.Character;
import database.IDatabase;
import database.CharacterDatabase;


public class CharacterSceneController implements Initializable {

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

    @FXML
    private AnchorPane paneAdd;

    @FXML
    private VBox paneMain;

    private ObservableList<Character> data;
    /*
        Here I don't return any database, just return directly the list of Character,
        which read from file: "src/main/resources/final/Character.json"
     */
    private List<Character> listCharacter = CharacterDatabase.getListCharacter();
    
    public void refresh() {
        data = FXCollections.observableArrayList(listCharacter);

        // Bind the ObservableList to the TableView
	    CharactersTableView.setItems(data);
    }
    
	
    Map<String, Function<Character, String>> searchMap;

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		 System.out.println("Character controller initialized");
	        // Add a default row
			refresh();

			searchMap = new HashMap<>();
			searchMap.put("ID",			chara -> chara.getName());
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
	        
	        colID.setCellValueFactory(new PropertyValueFactory<>("name"));
	        // colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
	        
	        colFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
	        colBiography.setCellValueFactory(new PropertyValueFactory<>("biography"));
	        colBirth.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
	        colDeath.setCellValueFactory(new PropertyValueFactory<>("dateOfDeath"));
	        colDynasty.setCellValueFactory(new PropertyValueFactory<>("dynasty"));
	        colFather.setCellValueFactory(new PropertyValueFactory<>("father"));
	        colMother.setCellValueFactory(new PropertyValueFactory<>("mother"));
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
            CharactersTableView.setItems(data);
        } else {
        	
            // Apply filtering based on the search text and option
            FilteredList<Character> filteredList = new FilteredList<>(data);

            filteredList.setPredicate(chara -> {
                Function<Character, String> fun = searchMap.get(searchOption);
                
                if (fun != null) {
                	String originalText = fun.apply(chara);
                	
                    if (searchOption.equals("ID")) {
                        return originalText.startsWith(searchText);
                    } else {
                        return originalText.toLowerCase().contains(searchText.toLowerCase());
                    }
                }

                return false;
            });

            CharactersTableView.setItems(filteredList);
        }
    }

}
