package javafx.view;

import java.io.File;

import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("MainScene.fxml"));
			primaryStage.setTitle("Group 12");
			primaryStage.getIcons().add(new Image( new File("/Users/trinhdiemquynh/Documents/gui/GUI/src/image/group.png").toURI().toString()));
			
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("primer-light.css").toExternalForm());
			primaryStage.setScene(scene);

			primaryStage.setMaximized(false);
			primaryStage.setWidth(1122);
			primaryStage.setHeight(768);
			primaryStage.centerOnScreen();
			primaryStage.show();	
		} catch(Exception e) {
            e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
