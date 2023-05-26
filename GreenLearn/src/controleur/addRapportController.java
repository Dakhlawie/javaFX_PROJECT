package controleur;

import javafx.fxml.FXML;

import javafx.fxml.Initializable;


import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import Connexion.Connexion;
import javafx.event.ActionEvent;

public class addRapportController {
	@FXML
	private TextField ttype;
	@FXML
	private TextField Etudiant;
	@FXML
	private TextField classe;
	
	@FXML
	private Button btnajouter;
	
	

	  @FXML
	  public void Actajouter(ActionEvent event) {
	      if (ttype.getText().isEmpty() || Etudiant.getText().isEmpty()||classe.getText().isEmpty()) {
	          Alert alert = new Alert(AlertType.ERROR);
	          alert.setTitle("Form Error!");
	          alert.setContentText("Please fill in all the fields.");
	          alert.showAndWait();
	      } else {
	          Connection con = Connexion.getConnection();
	          PreparedStatement stat;
	          String sql = "INSERT INTO rapport (type, filePath,nomEtudiant,classe) VALUES (?, ?, ?,?)";

	          try {
	              stat = con.prepareStatement(sql);
	              stat.setString(1, ttype.getText());
	              
	              stat.setString(3, Etudiant.getText());
	              stat.setString(4, classe.getText());
	              

	              // File import
	              FileChooser fileChooser = new FileChooser();
	              fileChooser.setTitle("Choose a PDF file");
	              fileChooser.getExtensionFilters().add(new ExtensionFilter("PDF Files", "*.pdf"));
	              File selectedFile = fileChooser.showOpenDialog(btnajouter.getScene().getWindow());
	              if (selectedFile != null) {
	                  String filePath = selectedFile.getAbsolutePath();
	                  stat.setString(2, filePath);
	              } else {
	                  Alert alert = new Alert(AlertType.WARNING);
	                  alert.setTitle("No File Selected");
	                  alert.setContentText("Please select a PDF file.");
	                  alert.showAndWait();
	                  return; // Return without further execution
	              }

	              int res = stat.executeUpdate();

	              if (res > 0) {
	                  // Move the selected file to the desired folder
	            	  String destinationFolder = "C:\\Users\\merie\\pdf"; // Replace with the actual destination folder path
	            	  File destinationFile = new File(destinationFolder + "\\" + selectedFile.getName());

	                  Files.move(selectedFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

	                  Alert alert = new Alert(AlertType.INFORMATION);
	                  alert.setTitle("Success");
	                  alert.setContentText("Report added successfully");
	                  alert.showAndWait();

	                  // Close the window
	                  Stage stage = (Stage) btnajouter.getScene().getWindow();
	                  stage.close();
	              }
	          } catch (SQLException e) {
	              e.printStackTrace();
	              System.out.println("Error: " + e.getMessage());
	          } catch (IOException e) {
	              e.printStackTrace();
	              System.out.println("Error while moving the file: " + e.getMessage());
	          }
	      }
	  }

	
		

}
