package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application  {
	public static void main(String[] args) {
		launch(args);
	}
	@Override
	public void start(Stage primaryStage) {
		try {
			//lire le fichier fxml et dessiner l'interface
			Parent root = FXMLLoader.load(getClass().getResource("../view/login.fxml"));
			//BorderPane root = new BorderPane();
			primaryStage.setTitle("GreenLearn ");
			Scene scene = new Scene(root/*,400,400*/);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
