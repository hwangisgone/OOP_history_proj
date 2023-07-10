package javafx.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;

public class MainSceneController {

    @FXML
    private Tab tabDynasty;

    @FXML
    private Tab tabFestival;

    @FXML
    private Tab tabLocation;

    @FXML
    private TabPane tabPane;

    @FXML
    private BorderPane paneTabCharacter;

    
    @FXML
    void mainLogOut(ActionEvent event) {

    }


	@FXML
    public void initialize() {
        tabDynasty.setGraphic(paneTabCharacter);
    }

}
