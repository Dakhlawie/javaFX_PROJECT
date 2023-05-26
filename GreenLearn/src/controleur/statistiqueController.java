package controleur;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import Connexion.Connexion;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

public class statistiqueController implements Initializable{
	@FXML
	private TextField nombreRapportsTextField;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
        try {
            Connection con = Connexion.getConnection();
            PreparedStatement stat;
            ResultSet rs;
            String sql = "SELECT COUNT(*) AS total FROM rapport";
            stat = con.prepareStatement(sql);
            rs = stat.executeQuery();

            if (rs.next()) {
                int nombreRapports = rs.getInt("total");
                nombreRapportsTextField.setText(String.valueOf(nombreRapports));
            }

            rs.close();
            stat.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

	
}





