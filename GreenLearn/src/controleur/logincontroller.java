package controleur;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Connexion.Connexion;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;


public class logincontroller {
	@FXML
	private PasswordField tpassword;
	@FXML
	private TextField tusername;

	
    @FXML
    private Button btnsignup;

    @FXML
    private Button btnlogin;
	
	
	
    @FXML
    public void Actlogin(ActionEvent event) {
        if (tusername.getText().isEmpty() || tpassword.getText().isEmpty()) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setHeaderText("Erreur de validation de formulaire");
            alert.setContentText("Remplir tous les champs");
            alert.showAndWait();
        } else {
            Connection con = Connexion.getConnection();
            PreparedStatement stat;
            ResultSet rs;
            String sql = "SELECT * FROM user WHERE username=? AND password=?";
            try {
                stat = con.prepareStatement(sql);
                stat.setString(1, tusername.getText().toString());
                stat.setString(2, tpassword.getText().toString());
                rs = stat.executeQuery();
                if (rs.next()) {
                    System.out.println("Connecté en tant qu'utilisateur");
                    saveCredentialsToFile(tusername.getText(), tpassword.getText());
                    try {
                        Parent root = FXMLLoader.load(getClass().getResource("/view/home.fxml"));
                        Stage stage = new Stage();
                        stage.setTitle("Home");
                        stage.setScene(new Scene(root));
                        stage.show();

                        Stage stage1 = (Stage) btnlogin.getScene().getWindow();
                        stage1.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    String sqlA = "SELECT * FROM admin WHERE username=? AND password=?";
                    stat = con.prepareStatement(sqlA);
                    stat.setString(1, tusername.getText().toString());
                    stat.setString(2, tpassword.getText().toString());
                    rs = stat.executeQuery();
                    if (rs.next()) {
                        System.out.println("Connecté en tant qu'administrateur");
                        saveCredentialsToFile(tusername.getText(), tpassword.getText());
                        try {
                            Parent root = FXMLLoader.load(getClass().getResource("/view/home.fxml"));
                            Stage stage = new Stage();
                            stage.setTitle("Home");
                            stage.setScene(new Scene(root));
                            stage.show();

                            Stage stage1 = (Stage) btnlogin.getScene().getWindow();
                            stage1.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setHeaderText("Erreur de connexion");
                        alert.setContentText("Nom d'utilisateur ou mot de passe incorrect.");
                        alert.showAndWait();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Erreur : " + e.getMessage());
            }
        }
    }

	@FXML
	public void ActSignup(ActionEvent event) {

		try {
			 
			Parent root=FXMLLoader.load(getClass().getResource("/view/register.fxml"));
			Stage stage= new Stage();
			stage.setTitle("Page d'inscription");
			stage.setScene(new Scene(root));
			stage.show();
			
			
			Stage stage1 = (Stage) btnsignup.getScene().getWindow();
			
			stage1.close();

			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	private void saveCredentialsToFile(String username, String password) {
	    String directoryPath = "C:\\Fichiers"; // Remplacez par le chemin absolu du répertoire de votre choix
	    String filePath = directoryPath + File.separator + "login.txt";

	    try {
	        File directory = new File(directoryPath);
	        if (!directory.exists()) {
	            directory.mkdirs(); // Créer le répertoire s'il n'existe pas déjà
	        }

	        FileWriter fileWriter = new FileWriter(filePath);
	        fileWriter.write("Nom d'utilisateur : " + username + "\n");
	        fileWriter.write("Mot de passe : " + password + "\n");
	        fileWriter.close();
	        System.out.println("Les identifiants ont été sauvegardés dans le fichier.");
	    } catch (IOException e) {
	        e.printStackTrace();
	        System.out.println("Erreur lors de la sauvegarde des identifiants dans le fichier : " + e.getMessage());
	    }
	}
}
