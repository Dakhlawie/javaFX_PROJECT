package controleur;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.web.WebView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ResourceBundle;
import java.util.Scanner;

import com.jfoenix.controls.JFXButton;

import Connexion.Connexion;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import model.Rapport;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableView;

import javafx.scene.control.TableColumn;

public class listeRapportController implements Initializable {
    @FXML
    private JFXButton btnajouter;
    @FXML
	private JFXButton btnajouter1;
    @FXML
    private JFXButton tlogout;
    @FXML
    private WebView webView;
    @FXML
    private Button btnchercher;
    @FXML
    private TextField tcherche;
    @FXML
    private TableView<Rapport> table;
    @FXML
    private TableColumn<Rapport, Integer> colID;

    @FXML
    private TableColumn<Rapport, String> coletudiant;
    @FXML
    private TableColumn<Rapport, String> colclasse;
    @FXML
    private TableColumn<Rapport, String> colType;
   
    @FXML
    private Button btnconsulter;
    @FXML
    private JFXButton btnuser;

    // local Data
    ObservableList<Rapport> data = FXCollections.observableArrayList();
	@FXML
	public void Actajouteruser(ActionEvent event) {
		if (!isAdmin()) {
	        // Display an alert indicating that the user is not authorized
	        Alert alert = new Alert(Alert.AlertType.ERROR);
	        alert.setHeaderText("Accès refusé");
	        alert.setContentText("Vous n'avez pas les droits d'administrateur pour ajouter un utilisateur.");
	        alert.showAndWait();
	        return;
	    }else {
    	try {
			 
			Parent root=FXMLLoader.load(getClass().getResource("/view/adduser.fxml"));
			Stage stage= new Stage();
			stage.setTitle("Ajout utilisateur");
			stage.setScene(new Scene(root));
			stage.show();
			
				
		} catch(Exception e) {
			e.printStackTrace();
		}}
	}

    @FXML
    public void Actuser(ActionEvent event) {
        String username = getUsernameFromFile();
        String password = getPasswordFromFile();

        if (username.equals("admin") && password.equals("123")) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/view/listeuser.fxml"));
                Stage stage3 = new Stage();
                stage3.setTitle("Liste des utilisateurs");
                stage3.setScene(new Scene(root));
                stage3.show();

                Stage stage4 = (Stage) btnuser.getScene().getWindow();
                stage4.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // Afficher une alerte indiquant que l'utilisateur n'est pas autorisé
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Accès refusé");
            alert.setContentText("Vous n'avez pas les droits d'administrateur.");
            alert.showAndWait();
        }
    }
    @FXML
    public void Actajouter(ActionEvent event) {
        try {

            Parent root = FXMLLoader.load(getClass().getResource("/view/addRapport.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Ajouter Rapport");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void Actlogout(ActionEvent event) {
        clearCredentialsFromFile();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Login");
            stage.setScene(new Scene(root));
            stage.show();

            Stage stage1 = (Stage) tlogout.getScene().getWindow();

            stage1.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void Actchercher(ActionEvent event) {
        Connection con = Connexion.getConnection();
        PreparedStatement stat;
        ResultSet rs;

        String ch = tcherche.getText();
        System.out.println(ch);
        String sql = "SELECT *  from rapport where  classe LIKE '%" + ch + "%' OR type LIKE '%" + ch + "%'";

        try {
            stat = con.prepareStatement(sql);
            rs = stat.executeQuery();

            data.clear();
            while (rs.next()) {
                int idRapport = rs.getInt("idRapport");
               
                String type = rs.getString("type");
                String nomEtudiant = rs.getString("nomEtudiant");
                String classe = rs.getString("classe");
                String filePath = rs.getString("filePath");

                Rapport rapport = new Rapport(type,idRapport,filePath,nomEtudiant,classe);
                data.add(rapport);
            }

            // Remplir tableau avec Data
            table.setEditable(true);
            colType.setCellValueFactory(new PropertyValueFactory<>("Type"));
            colID.setCellValueFactory(new PropertyValueFactory<>("idRapport"));
            coletudiant.setCellValueFactory(new PropertyValueFactory<>("nometudiant"));
            colclasse.setCellValueFactory(new PropertyValueFactory<>("classe"));
           
            table.setItems(data);
            System.out.println(data);
         
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur: " + e.getMessage());
        }
    }

    @FXML
    public void Actconsulter(ActionEvent event) {
        Connection con = Connexion.getConnection();
        PreparedStatement stat;
        ResultSet rs;

        String sql = "SELECT * from rapport";

        try {
            stat = con.prepareStatement(sql);
            rs = stat.executeQuery();

            data.clear();
            while (rs.next()) {
                int idRapport = rs.getInt("idRapport");
                
                String type = rs.getString("type");
                String nomEtudiant = rs.getString("nomEtudiant");
                String classe = rs.getString("classe");
                String filePath = rs.getString("filePath");

                Rapport rapport = new Rapport(type,idRapport,filePath,nomEtudiant,classe);
                data.add(rapport);
            }

            // Remplir tableau avec Data
            table.setEditable(true);
            colType.setCellValueFactory(new PropertyValueFactory<>("Type"));
            colID.setCellValueFactory(new PropertyValueFactory<>("idRapport"));
            coletudiant.setCellValueFactory(new PropertyValueFactory<>("nometudiant"));
            colclasse.setCellValueFactory(new PropertyValueFactory<>("classe"));
        table.setItems(data);
            System.out.println(data);
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur: " + e.getMessage());
        }
    }


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    
        table.setPlaceholder(new Label("Le tableau des Rapport est vide !"));
     
        if (isAdmin()) {
            table.setEditable(true);

            // Set CellFactory for each editable column
            colType.setCellFactory(TextFieldTableCell.forTableColumn());
            colType.setOnEditCommit(event -> {
                event.getRowValue().setType(event.getNewValue());
                updateRapport(event.getRowValue());
            });

            colclasse.setCellFactory(TextFieldTableCell.forTableColumn());
            colclasse.setOnEditCommit(event -> {
                event.getRowValue().setClasse(event.getNewValue());
                updateRapport(event.getRowValue());
            });

            coletudiant.setCellFactory(TextFieldTableCell.forTableColumn());
            coletudiant.setOnEditCommit(event -> {
                event.getRowValue().setNometudiant(event.getNewValue());
                updateRapport(event.getRowValue());
            });

            // Add similar code for other editable columns if needed
        } else {
            table.setEditable(false);
        }
        
        table.setItems(data);}
    private void clearCredentialsFromFile() {
        String filePath = "C:\\Fichiers\\login.txt"; // Chemin absolu du fichier de sauvegarde

        try {
            FileWriter fileWriter = new FileWriter(filePath);
            fileWriter.write(""); // Écrase le contenu du fichier avec une chaîne vide
            fileWriter.close();
            System.out.println("Le contenu du fichier a été supprimé.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la suppression du contenu du fichier : " + e.getMessage());
        }
    }
    private String getUsernameFromFile() {
        String filePath = "C:\\Fichiers\\login.txt"; // Chemin absolu du fichier de sauvegarde
        String username = "";

        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.startsWith("Nom d'utilisateur")) {
                    username = line.split(":")[1].trim();
                    break;
                }
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la lecture du fichier : " + e.getMessage());
        }

        return username;
    }

    private String getPasswordFromFile() {
        String filePath = "C:\\Fichiers\\login.txt"; // Chemin absolu du fichier de sauvegarde
        String password = "";

        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.startsWith("Mot de passe")) {
                    password = line.split(":")[1].trim();
                    break;
                }
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la lecture du fichier : " + e.getMessage());
        }

        return password;
    }
        
    private boolean isAdmin() {
        String username = getUsernameFromFile();
        String password = getPasswordFromFile();

        return username.equals("admin") && password.equals("123");
    } 
    private void updateRapport(Rapport rapport)  {
    	try {
    	Connection con = Connexion.getConnection();
    	String updateSql = "UPDATE rapport SET type = ?, classe = ?, nomEtudiant = ? WHERE idRapport = ?";
    	PreparedStatement updateStmt = con.prepareStatement(updateSql);
    	updateStmt.setString(1, rapport.getType());
    	updateStmt.setString(2, rapport.getClasse());
    	updateStmt.setString(3, rapport.getNometudiant());
    	updateStmt.setInt(4, rapport.getIdRapport());
         updateStmt.executeUpdate();
    	   } catch (SQLException e) {
   	        e.printStackTrace();
   	        
   	    }
    }

   
 }
