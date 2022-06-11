/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectsis.controller;

import com.itextpdf.text.DocumentException;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import projectsis.Launcher;
import static projectsis.constructors.CreationPDFforMOYD.generateMoyD;
import projectsis.constructors.ModelTableMoyDetails;
import projectsis.constructors.MyConnection;


/**
 * FXML Controller class
 *
 * @author Kazwell
 */
public class NoteDétailsController implements Initializable {

    @FXML
    private AnchorPane VisualiseDetails;
    @FXML
    private TableView<ModelTableMoyDetails> Table_TP;
    @FXML
    private TableColumn<ModelTableMoyDetails, String> col_CNE;
    @FXML
    private TableColumn<ModelTableMoyDetails, String> col_Nom;
    @FXML
    private TableColumn<ModelTableMoyDetails, String> col_Prénom;
    @FXML
    private TableColumn<ModelTableMoyDetails, String> col_ModNom;
    @FXML
    private TableColumn<ModelTableMoyDetails, String> col_ModMoy;
    @FXML
    private TableColumn<ModelTableMoyDetails, String> col_Valid;
    @FXML
    private JFXButton oneTime;
    @FXML
    private JFXTextField CNE;
    
    @FXML
    private JFXButton printDets;
	
        @Override
	public void initialize(URL url, ResourceBundle rb) {
		makeStageDragable();
                
            col_CNE.setCellValueFactory(new PropertyValueFactory<>("cne"));
            col_Nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
            col_Prénom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
            col_ModNom.setCellValueFactory(new PropertyValueFactory<>("nomMod"));
            col_ModMoy.setCellValueFactory(new PropertyValueFactory<>("noteMod"));
            col_Valid.setCellValueFactory(new PropertyValueFactory<>("val"));
            Table_TP.setItems(oblistd);   
            
	}	
        /*
         public void getPrenom(String nom,String prenom){
        name.setText(nom);
        surnom.setText(prenom);
    } */
        public void getCNE(int cne){
            CNE.setText(String.valueOf(cne));
        }
    
        ObservableList <ModelTableMoyDetails> oblistd = FXCollections.observableArrayList();
        private void lecture(){
             try {
            MyConnection cnx = new MyConnection();
            Connection con = cnx.getConnection();
            ResultSet rsGet;
           rsGet = con.createStatement().executeQuery("SELECT CNE_Étudiant,Nom_Étudiant,Prénom_Étudiant,Nom_Module,Note_Module,module_étudiant.Validation\n" +
"FROM project.module_étudiant join project.module join project.étudiant \n" +
"on étudiant_CNE_Étudiant = CNE_Étudiant and module.id_module = module_id_Module;");
            while (rsGet.next()) {
                
                if (rsGet.getInt("CNE_Étudiant") == Integer.parseInt(CNE.getText())){
                oblistd.add(new ModelTableMoyDetails(rsGet.getInt("CNE_Étudiant"),rsGet.getString("Nom_Étudiant"),rsGet.getString("Prénom_Étudiant"),
                        rsGet.getString("Nom_Module"),rsGet.getFloat("Note_Module"),rsGet.getString("Validation")));
                 
            }}}
         catch (SQLException ex) {
            Logger.getLogger(NoteDétailsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
     
    @FXML
    void Display(ActionEvent event) {
            lecture();    
            oneTime.setDisable(true);
    }
        
    @FXML
    void printD(ActionEvent event) throws DocumentException, FileNotFoundException {
        generateMoyD(Integer.parseInt(CNE.getText()));
        JOptionPane.showMessageDialog(null,"PDF généré dans le dossier constructor");
        printDets.setDisable(true);
    }
        
    private double xOffset = 0;
    private double yOffset = 0;
        
    private Stage stage;
	private void makeStageDragable() {
		VisualiseDetails.setOnMousePressed((event) -> {
			xOffset=event.getSceneX();
			yOffset=event.getSceneY();
		});
		VisualiseDetails.setOnMouseDragged((event)-> {
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			stage.setX(event.getScreenX()-xOffset);
			stage.setY(event.getScreenY()-yOffset);
			stage.setOpacity(0.95f);
		});
		VisualiseDetails.setOnDragDone((event)-> {
			stage.setOpacity(1.0f);
		});
		VisualiseDetails.setOnMouseReleased((event)->{
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
		Parent backLaunch = FXMLLoader.load(getClass().getResource("/projectsis/scenes/Visualiser.fxml"));
		Scene main = new Scene(backLaunch);
		Stage Start = (Stage)((Node)event.getSource()).getScene().getWindow();
		Start.setScene(main);
		Start.show();
	}

}
