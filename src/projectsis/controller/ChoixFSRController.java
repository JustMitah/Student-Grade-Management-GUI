/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectsis.controller;

import com.itextpdf.text.DocumentException;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import projectsis.Launcher;
import static projectsis.constructors.CreationPDFforMOYG.generateMoyG;
import projectsis.constructors.MyConnection;

/**
 * FXML Controller class
 *
 * @author Kazwell
 */
public class ChoixFSRController implements Initializable {

   
    @FXML
    private AnchorPane FSR;
    
    @FXML
    private JFXComboBox<String> csem;
    
    @FXML
    private JFXComboBox<String> cf;
    
    @FXML
    private JFXButton Checked;

    
    ObservableList <String> ComboFiliere = FXCollections.observableArrayList();
    ObservableList <String> ComboSemestre = FXCollections.observableArrayList();
    
    private List <String> ComboFiliere() throws Exception{
            MyConnection cnx = new MyConnection();    
            Connection con = cnx.getConnection();
            List <String> choice = new ArrayList<>();
            String query = "Select Nom_Filière from filière";
            try{
                PreparedStatement stat= (PreparedStatement) con.prepareStatement(query);
                ResultSet rslt = stat.executeQuery();
                while(rslt.next()){
                    choice.add(rslt.getString("Nom_Filière"));
                }
                stat.close();
                rslt.close();
                return choice;                   
            } catch (SQLException ex){
                Logger.getLogger(GestionFEController.class.getName()).log(Level.SEVERE,null,ex);
            }
            return null;
        }
        private List <String> ComboSemestre() throws Exception{
            MyConnection cnx = new MyConnection();    
            Connection con = cnx.getConnection();
            List <String> choice = new ArrayList<>();
            String query = "Select Nom_Semestre from semestre";
            try{
                PreparedStatement stat= (PreparedStatement) con.prepareStatement(query);
                ResultSet rslt = stat.executeQuery();
                while(rslt.next()){
                    choice.add(rslt.getString("Nom_Semestre"));
                }
                stat.close();
                rslt.close();
                return choice;                   
            } catch (SQLException ex){
                Logger.getLogger(GestionFEController.class.getName()).log(Level.SEVERE,null,ex);
            }
            return null;
        }
    
    
    private double xOffset = 0;
    private double yOffset = 0;
    private Stage stage;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        makeStageDragable();
        try{
                cf.setItems(FXCollections.observableArrayList(ComboFiliere()));
                csem.setItems(FXCollections.observableArrayList(ComboSemestre()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
    }    
    private void makeStageDragable() {
		FSR.setOnMousePressed((event) -> {
			xOffset=event.getSceneX();
			yOffset=event.getSceneY();
		});
		FSR.setOnMouseDragged((event)-> {
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			stage.setX(event.getScreenX()-xOffset);
			stage.setY(event.getScreenY()-yOffset);
			stage.setOpacity(0.95f);
		});
		FSR.setOnDragDone((event)-> {
			stage.setOpacity(1.0f);
		});
		FSR.setOnMouseReleased((event)->{
			stage.setOpacity(1.0f);
		});
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
	private void goBack(ActionEvent event) throws IOException {
		Parent backLaunch = FXMLLoader.load(getClass().getResource("/projectsis/scenes/LoginCController.fxml"));
		Scene main = new Scene(backLaunch);
		Stage Start = (Stage)((Node)event.getSource()).getScene().getWindow();
		Start.setScene(main);
		Start.show();
	}

    @FXML
    void goToSelectparIterv(ActionEvent event) throws IOException {
        JOptionPane.showMessageDialog(null,"Fonction en cours de maintenance");
        Parent InterfaceSPI = FXMLLoader.load(getClass().getResource("/projectsis/scenes/SéléctionParIntervalle.fxml"));
	Scene spi = new Scene(InterfaceSPI);
	Stage SPIchild = (Stage)((Node)event.getSource()).getScene().getWindow();
	SPIchild.setScene(spi);
	SPIchild.show();
    }
    @FXML
    private void Search(ActionEvent event) {
        JOptionPane.showMessageDialog(null,"Fonction en cours de maintenance");
    }
    
    @FXML
    void ValidFS(ActionEvent event) {
        JOptionPane.showMessageDialog(null,"Fonction en cours de maintenance");
        Checked.setDisable(true);
    }
    @FXML
    void Display(ActionEvent event) throws DocumentException, FileNotFoundException {
        generateMoyG();
        JOptionPane.showMessageDialog(null,"PDF généré dans le dossier constructor");
    }
}
