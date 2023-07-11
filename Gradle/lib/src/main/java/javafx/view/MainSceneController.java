package javafx.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class MainSceneController {

	@FXML
    private AnchorPane contentCharacter;

    @FXML
    private AnchorPane contentDynasty;

    @FXML
    private AnchorPane contentEvent;

    @FXML
    private AnchorPane contentFestival;

    @FXML
    private AnchorPane contentLocation;

    @FXML
    private BorderPane paneTabCharacter;

    @FXML
    private BorderPane paneTabDynasty;

    @FXML
    private BorderPane paneTabEvent;

    @FXML
    private BorderPane paneTabFestival;

    @FXML
    private BorderPane paneTabLocation;

    @FXML
    private Tab tabCharacter;

    @FXML
    private Tab tabDynasty;

    @FXML
    private Tab tabEvent;

    @FXML
    private Tab tabFestival;

    @FXML
    private Tab tabLocation;
    
    @FXML
    void mainLogOut(ActionEvent event) {

    }


    private void loadFXML(AnchorPane parent, String path) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        try {
            AnchorPane child = loader.load();
            parent.getChildren().add(child);
            
            AnchorPane.setTopAnchor(child, 0.0);
            AnchorPane.setBottomAnchor(child, 0.0);
            AnchorPane.setLeftAnchor(child, 0.0);
            AnchorPane.setRightAnchor(child, 0.0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
	@FXML
    public void initialize() {
		loadFXML(contentDynasty, "entity/DynastyScene.fxml");
		loadFXML(contentCharacter, "entity/CharacterScene.fxml");
		loadFXML(contentFestival, "entity/FestivalScene.fxml");
		loadFXML(contentLocation, "entity/LocationScene.fxml");
        
        tabDynasty.setGraphic(paneTabDynasty);
        tabCharacter.setGraphic(paneTabCharacter);
        tabEvent.setGraphic(paneTabEvent);
        tabFestival.setGraphic(paneTabFestival);
        tabLocation.setGraphic(paneTabLocation);
    }

}
