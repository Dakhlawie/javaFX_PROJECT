package controleur;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Connexion.Connexion;
import javafx.event.ActionEvent;

import javafx.scene.control.PasswordField;

import javafx.scene.control.Hyperlink;

public class registerController {
	@FXML
	private PasswordField tpassword;
	@FXML
	private PasswordField tpassword2;
	@FXML
	private TextField tsector;
	@FXML
	private Button btnregister;

	@FXML
	private Hyperlink btnsignin;
	@FXML
	private TextField tusername;

	// Event Listener on Button[#btnregister].onAction
	@FXML
	public void Actregister(ActionEvent event) {

		if (tusername.getText().isEmpty() || tpassword.getText().isEmpty() || tpassword2.getText().isEmpty()) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText("Error de validation de formulaire");
			alert.setContentText("Remplir tous les champs ");
			alert.showAndWait();
		} else if (!tpassword.getText().equals(tpassword2.getText())) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText("Error de validation de formulaire");
			alert.setContentText("les deux mots de passe ne sont pas identiques ");
			alert.showAndWait();
			tpassword.setText("");
			tpassword2.setText("");
	
		} else {
			Connection con = Connexion.getConnection();

		
			PreparedStatement stat;
			ResultSet rs;
			String sql = "insert into user (username,password) values (?,?)";
			try {
				stat = con.prepareStatement(sql);
				stat.setString(1, tusername.getText().toString());
				
				stat.setString(2, tpassword.getText().toString());

				int res = stat.executeUpdate();
				
			
				if (res>0) {
					try {
						 
						Parent root=FXMLLoader.load(getClass().getResource("/view/login.fxml"));
						Stage stage= new Stage();
						stage.setTitle("Page Login");
						stage.setScene(new Scene(root));
						stage.show();
						
						
						Stage stage1 = (Stage) btnsignin.getScene().getWindow();
						
						stage1.close();
						
						
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
				
			}
			catch (SQLException e) {
				e.printStackTrace();
				System.out.println("erreur " + e.getMessage());
			}
		}
	}
	
	
	
	
	@FXML
	public void Actsignin(ActionEvent event) {
		try {
			 
			Parent root=FXMLLoader.load(getClass().getResource("/view/login.fxml"));
			Stage stage= new Stage();
			stage.setTitle("Page de login");
			stage.setScene(new Scene(root));
			stage.show();
			
			// get a handle to the stage
			  Stage stage1 = (Stage) btnsignin.getScene().getWindow();
			  // do what you have to do
			  stage1.close();
			
			//Platform.exit();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
