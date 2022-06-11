/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectsis.controller;

import com.itextpdf.text.DocumentException;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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



/**
 * FXML Controller class
 *
 * @author Kazwell
 */
public class ChoisirMéthodeController implements Initializable {

    @FXML
    private AnchorPane ChoixVI;
    
    @FXML
    private JFXCheckBox CheckV;

    @FXML
    private JFXCheckBox CheckI;
    
    @FXML
    private JFXButton print;

    @FXML
    private void goBack(MouseEvent event) throws IOException 
    {
	Parent backLaunch = FXMLLoader.load(getClass().getResource("/projectsis/scenes/GestionFE.fxml"));
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
		ChoixVI.setOnMousePressed((event) -> {
			xOffset=event.getSceneX();
			yOffset=event.getSceneY();
		});
		ChoixVI.setOnMouseDragged((event)-> {
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			stage.setX(event.getScreenX()-xOffset);
			stage.setY(event.getScreenY()-yOffset);
			stage.setOpacity(0.95f);
		});
		ChoixVI.setOnDragDone((event)-> {
			stage.setOpacity(1.0f);
		});
		ChoixVI.setOnMouseReleased((event)->{
			stage.setOpacity(1.0f);
		});
	}
	
    @Override
    public void initialize(URL url, ResourceBundle rb) {
		makeStageDragable();
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
    void printORshow(ActionEvent event) throws IOException, DocumentException {
        if (!(CheckV.isSelected() ^ CheckI.isSelected())){
            JOptionPane.showMessageDialog(null,"Sélectionez une et une seule option");
        } else if (CheckV.isSelected()) {
            //JOptionPane.showMessageDialog(null,"Visualisation");
            Parent Visualise = FXMLLoader.load(getClass().getResource("/projectsis/scenes/Visualiser.fxml"));
            Scene Vis= new Scene(Visualise);
            Stage VisChild = (Stage)((Node)event.getSource()).getScene().getWindow();
            VisChild.setScene(Vis);
            VisChild.show();   
        } else{
            JOptionPane.showMessageDialog(null,"PDF généré dans le dossier constructor");
            generateMoyG();
                 
        }

    }
    
}
