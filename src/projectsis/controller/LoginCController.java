package projectsis.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import projectsis.Launcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import projectsis.constructors.MyConnection;

public class LoginCController implements Initializable {

    @FXML
    private JFXButton Login;
    
    @FXML
    private AnchorPane LoginC;
    
    
    @FXML
	private void goBack(ActionEvent event) throws IOException {
		Parent backLaunch = FXMLLoader.load(getClass().getResource("/projectsis/scenes/Launcher.fxml"));
		Scene main = new Scene(backLaunch);
		Stage Start = (Stage)((Node)event.getSource()).getScene().getWindow();
		Start.setScene(main);
		Start.show();
	}
   @FXML
    private JFXTextField Username;
    @FXML
    private JFXPasswordField Password;
    
    
    PreparedStatement pst;
    ResultSet rs;
    
    @FXML
    void login(ActionEvent event) throws ClassNotFoundException, IOException {
        String username = Username.getText();
        String password = Password.getText();
        
        if (username.equals("") || password.equals(""))
        {
            JOptionPane.showMessageDialog(null,"Username or Password needed");
            Username.setText("");
            Password.setText(""); 
            Username.requestFocus();
        }
        else
        {
            try {
                MyConnection cnx = new MyConnection();    
                Connection con = cnx.getConnection();
                pst = con.prepareStatement("select * from coordonateur where Nom_Coordonateur=? and Password_Coordonateur=?");
                pst.setString(1,username);
                pst.setString(2,password);
                rs = pst.executeQuery();
                if (rs.next()){
                    //JOptionPane.showMessageDialog(null,"Login Success");
                    Parent ChildFE = FXMLLoader.load(getClass().getResource("/projectsis/scenes/ChoixFilièreSemestreRésultat.fxml"));
                    Scene main = new Scene(ChildFE);
                    Stage Start = (Stage)((Node)event.getSource()).getScene().getWindow();
                    Start.setScene(main);
                    Start.show();
                }
                else{
                    JOptionPane.showMessageDialog(null,"Username or Password invalid");
                    Username.setText("");
                    Password.setText(""); 
                    Username.requestFocus();
                }
            } 
            catch (SQLException ex) {
                Logger.getLogger(LoginEController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }

    }   
    @FXML
 	private void handleClose(MouseEvent event) {
 		System.exit(0);
 	}
 	
 	@FXML
 	private void handleMini(MouseEvent event) {
 		Launcher.stage.setIconified(true);
 	}
 	@FXML
    private double xOffset = 0;
	private double yOffset = 0;
	private Stage stage;
	
 	private void makeStageDragable() {
		LoginC.setOnMousePressed((event) -> {
			xOffset=event.getSceneX();
			yOffset=event.getSceneY();
		});
		LoginC.setOnMouseDragged((event)-> {
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			stage.setX(event.getScreenX()-xOffset);
			stage.setY(event.getScreenY()-yOffset);
			stage.setOpacity(0.95f);
		});
		LoginC.setOnDragDone((event)-> {
			stage.setOpacity(1.0f);
		});
		LoginC.setOnMouseReleased((event)->{
			stage.setOpacity(1.0f);
		});
	}
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		makeStageDragable();
	}
        
        @FXML
	void loginEnter(KeyEvent event) throws IOException {
        if(event.getCode()== KeyCode.ENTER) {
            String username = Username.getText();
        String password = Password.getText();
        
        if (username.equals("") || password.equals(""))
        {
            JOptionPane.showMessageDialog(null,"Username or Password needed");
            Username.setText("");
            Password.setText(""); 
            Username.requestFocus();
        }
        else
        {
            try {
                MyConnection cnx = new MyConnection();    
                Connection con = cnx.getConnection();
                pst = con.prepareStatement("select * from coordonateur where Nom_Coordonateur=? and Password_Coordonateur=?");
                pst.setString(1,username);
                pst.setString(2,password);
                rs = pst.executeQuery();
                if (rs.next()){
                    //JOptionPane.showMessageDialog(null,"Login Success");
                    Parent ChildFE = FXMLLoader.load(getClass().getResource("/projectsis/scenes/ChoixFilièreSemestreRésultat.fxml"));
                    Scene main = new Scene(ChildFE);
                    Stage Start = (Stage)((Node)event.getSource()).getScene().getWindow();
                    Start.setScene(main);
                    Start.show();
                }
                else{
                    JOptionPane.showMessageDialog(null,"Username or Password invalid");
                    Username.setText("");
                    Password.setText(""); 
                    Username.requestFocus();
                }
            } 
            catch (SQLException ex) {
                Logger.getLogger(LoginEController.class.getName()).log(Level.SEVERE, null, ex);
            }
          }
       }
    }
}


