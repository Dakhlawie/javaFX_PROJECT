package controleur;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class homeController {
	  @FXML
	    private Button btnliste;

		@FXML
		public void Actliste(ActionEvent event) {
	    	try {
				 
				Parent root=FXMLLoader.load(getClass().getResource("/view/listeRapport.fxml"));
				Stage stage3= new Stage();
				stage3.setTitle("liste Rapport");
				stage3.setScene(new Scene(root));
				stage3.show();
				
				// get a handle to the stage
				Stage stage4 = (Stage) btnliste.getScene().getWindow();
				// do what you have to do
				stage4.close();
				
				//Platform.exit();			
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
}
