package controleur;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import Connexion.Connexion;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;

import model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

public class listeuserController implements Initializable {
	@FXML
	private JFXButton btnajouter;
	@FXML
	private JFXButton btnajouter1;
	@FXML
	private JFXButton tlogout;
	@FXML
	private Button btnchercher;
	@FXML
	private TextField tcherche;
	@FXML
	private Button btnconsulter;
	
	@FXML
	private JFXButton btnrapport;
	
	
    @FXML
    private TableView<User> table;
	
    @FXML
    private TableColumn<User, String> colnom;
    

    @FXML
    private TableColumn<User, String> colpassword;

    @FXML
    private TableColumn<User, Integer> colid;
    


    
	
	ObservableList<User> data = FXCollections.observableArrayList( );
	@FXML
	public void Actajouteruser(ActionEvent event) {
    	try {
			 
			Parent root=FXMLLoader.load(getClass().getResource("/view/adduser.fxml"));
			Stage stage= new Stage();
			stage.setTitle("Ajout utilisateur");
			stage.setScene(new Scene(root));
			stage.show();
			
				
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void ActRapport(ActionEvent event) {
    	try {
			 
			Parent root=FXMLLoader.load(getClass().getResource("/view/listeRapport.fxml"));
			Stage stage= new Stage();
			stage.setTitle("listeRapport");
			stage.setScene(new Scene(root));
			stage.show();
	
			Stage stage1 = (Stage) btnrapport.getScene().getWindow();
			
			stage1.close();
						
		} catch(Exception e) {
			e.printStackTrace();
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
			 
			Parent root=FXMLLoader.load(getClass().getResource("/view/login.fxml"));
			Stage stage= new Stage();
			stage.setTitle("page login");
			stage.setScene(new Scene(root));
			stage.show();
			
			
			Stage stage1 = (Stage) tlogout.getScene().getWindow();
		
			stage1.close();
				
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	

	@FXML
	public void Actchercher(ActionEvent event) {
	
		Connection con= Connexion.getConnection();
		PreparedStatement stat ;
		ResultSet rs;
	
		String ch = tcherche.getText();
		System.out.println(ch);
		String sql ="select * from user where username like '%"+ch+"%';";
		String st ="";
		
		try
		{
		stat=con.prepareStatement(sql);
		rs=stat.executeQuery();
		
		data.clear();
		while (rs.next()) {
			st += "";
			data.add(new User(rs.getInt("iduser"),rs.getString("username"),rs.getString("password")));
		}
		/***********************Remplir tableau avec Data***********************/
		table.setEditable(true);
		colid.setCellValueFactory(new PropertyValueFactory<User,Integer>("idUser"));
		colpassword.setCellValueFactory(new PropertyValueFactory<User,String>("password"));
		colnom.setCellValueFactory(new PropertyValueFactory<User,String>("username"));
		
    	table.setItems(data);
    	System.out.println(data);
		}
		
		catch(SQLException e)
			{
				e.printStackTrace();
				System.out.println("erreur "+ e.getMessage());
			}
	}
	
	
	
	
	@FXML
	public void Actconsulter(ActionEvent event) {
		
				Connection con= Connexion.getConnection();
				PreparedStatement stat ;
				ResultSet rs;
				
				String sql ="select * from user";
				String st ="";
				
				try
				{
				stat=con.prepareStatement(sql);
				rs=stat.executeQuery();
				
				data.clear();
				while (rs.next()) {
					st += "";
					data.add(new User(rs.getInt("iduser"),rs.getString("username"),rs.getString("password")));
				}
				System.out.println(data);
				/***********************Remplir tableau avec Data***********************/
				table.setEditable(true);
				colid.setCellValueFactory(new PropertyValueFactory<User,Integer>("idUser"));
				colpassword.setCellValueFactory(new PropertyValueFactory<User,String>("password"));
				colnom.setCellValueFactory(new PropertyValueFactory<User,String>("username"));
				
		    	table.setItems(data);
		    	System.out.println(data);
				}
				
				catch(SQLException e)
					{
						e.printStackTrace();
						System.out.println("erreur "+ e.getMessage());
					}
	}
	
	
	
	/*****************************************************************************************************************/	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		table.setPlaceholder( new Label("Le tableau des Rapport est vide !"));

		table.setEditable(true);
	
		table.setItems(data);
		colnom.setCellFactory(TextFieldTableCell.forTableColumn());
		colnom.setOnEditCommit(event -> {
		    User user = event.getRowValue();
		    user.setUsername(event.getNewValue());
		    updateUserInfoInDatabase(user); // Call a method to update the user info in the database
		});

		colpassword.setCellFactory(TextFieldTableCell.forTableColumn());
		colpassword.setOnEditCommit(event -> {
		    User user = event.getRowValue();
		    user.setPassword(event.getNewValue());
		    updateUserInfoInDatabase(user); // Call a method to update the user info in the database
		});
		

	}
	private void updateUserInfoInDatabase(User user) {
	    try {
	        Connection con = Connexion.getConnection();
	        String sql = "UPDATE user SET username = ?, password = ? WHERE iduser = ?";
	        PreparedStatement statement = con.prepareStatement(sql);
	        statement.setString(1, user.getUsername());
	        statement.setString(2, user.getPassword());
	        statement.setInt(3, user.getIdUser());
	        statement.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	        
	    }
	}
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
	
}
