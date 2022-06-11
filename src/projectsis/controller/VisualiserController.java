/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectsis.controller;

import com.jfoenix.controls.JFXTextField;
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
import projectsis.constructors.ModelTableMoy;
import projectsis.constructors.MyConnection;

/**
 * FXML Controller class
 *
 * @author Kazwell
 */
public class VisualiserController implements Initializable {

    @FXML
    private AnchorPane Visualise;
    
    
    @FXML
    private TableColumn<ModelTableMoy,String> col_Nmoy;

    @FXML
    private TableColumn<ModelTableMoy,String> col_Valid;
    
    @FXML
    private TableView<ModelTableMoy> Table_TP;

    @FXML
    private TableColumn<ModelTableMoy,String> col_CNE;

    @FXML
    private TableColumn<ModelTableMoy,String> col_Nom;

    @FXML
    private TableColumn<ModelTableMoy,String> col_Prénom;

    @FXML
    private TableColumn<ModelTableMoy,String> col_Filière;
    
    
  
    
    @FXML
    private JFXTextField ChosenStudCNE;
    
    

    @FXML
    private void goBack(ActionEvent event) throws IOException 
    {
	Parent backLaunch = FXMLLoader.load(getClass().getResource("/projectsis/scenes/ChoisirMéthode.fxml"));
	Scene main = new Scene(backLaunch);
	Stage Start = (Stage)((Node)event.getSource()).getScene().getWindow();
	Start.setScene(main);
	Start.show();
    }   
    
    @FXML
    private double xOffset = 0;
    private double yOffset = 0;
    private Stage stage;
    
    private void makeStageDragable() {
		Visualise.setOnMousePressed((event) -> {
			xOffset=event.getSceneX();
			yOffset=event.getSceneY();
		});
		Visualise.setOnMouseDragged((event)-> {
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			stage.setX(event.getScreenX()-xOffset);
			stage.setY(event.getScreenY()-yOffset);
			stage.setOpacity(0.95f);
		});
		Visualise.setOnDragDone((event)-> {
			stage.setOpacity(1.0f);
		});
		Visualise.setOnMouseReleased((event)->{
			stage.setOpacity(1.0f);
		});
	}
   
    
    
	ObservableList <ModelTableMoy> oblist = FXCollections.observableArrayList();
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		makeStageDragable();
                try {
            MyConnection cnx = new MyConnection();
            Connection con = cnx.getConnection();
            ResultSet rs = con.createStatement().executeQuery("SELECT CNE_Étudiant,Nom_Étudiant,Prénom_Étudiant,Nom_Filière,Moyenne_Générale,Validation FROM project.filière f join project.étudiant e on f.id_Filière = e.id_Filière;");
            while (rs.next()) {
                oblist.add(new ModelTableMoy(rs.getInt("CNE_Étudiant"),rs.getString("Nom_Étudiant"),rs.getString("Prénom_Étudiant"),
                        rs.getString("Nom_Filière"),rs.getFloat("Moyenne_Générale"),rs.getString("Validation")));
                //JOptionPane.showMessageDialog(null,rs.getFloat("MoyG"));
                
                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(SaisirNoteTPController.class.getName()).log(Level.SEVERE, null, ex);
        }
            col_CNE.setCellValueFactory(new PropertyValueFactory<>("cne"));
            col_Nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
            col_Prénom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
            col_Filière.setCellValueFactory(new PropertyValueFactory<>("filiere"));
            col_Nmoy.setCellValueFactory(new PropertyValueFactory<>("moyG"));
            col_Valid.setCellValueFactory(new PropertyValueFactory<>("val"));
            Table_TP.setItems(oblist);   
            
	}	  
 @FXML
    void TableClickedDetails(MouseEvent event) {
        ModelTableMoy Md = Table_TP.getSelectionModel().getSelectedItem();
            int cne = Md.getCne();
            //String nom = Md.getNom();
            //String prenom = Md.getPrenom();
            //String filiere = Md.getFiliere();
            //float moyG = Md.getMoyG();
            //String val = Md.getVal();
            //ChosenStudSurname.setText(prenom);
            //ChosenStudName.setText(nom); 
            ChosenStudCNE.setText(String.valueOf(cne));
    }
    @FXML
    void AfficherDétails(ActionEvent event) throws IOException {
        if (ChosenStudCNE.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Choisissez un élève du tableau!");
        } else{        
           FXMLLoader loader = new FXMLLoader(getClass().getResource("/projectsis/scenes/NoteDétails.fxml"));
           try{
               Parent SaisirNE = (Parent) loader.load();
                Scene NP = new Scene(SaisirNE);
                Stage NPchild = (Stage)((Node)event.getSource()).getScene().getWindow();
                NPchild.setScene(NP);
                NPchild.show();
                NoteDétailsController ndc = loader.getController();
                ndc.getCNE(Integer.parseInt(ChosenStudCNE.getText()));
                
                              
       } catch (IOException e){
            JOptionPane.showMessageDialog(null,e);
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
        
    
}
