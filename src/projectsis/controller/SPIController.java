/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectsis.controller;

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
import projectsis.Launcher;

/**
 * FXML Controller class
 *
 * @author Kazwell
 */
public class SPIController implements Initializable {

    @FXML
    private AnchorPane SPI;

    @FXML
    private double xOffset = 0;
    private double yOffset = 0;
    private Stage stage;
    
    private void makeStageDragable() {
		SPI.setOnMousePressed((event) -> {
			xOffset=event.getSceneX();
			yOffset=event.getSceneY();
		});
		SPI.setOnMouseDragged((event)-> {
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			stage.setX(event.getScreenX()-xOffset);
			stage.setY(event.getScreenY()-yOffset);
			stage.setOpacity(0.95f);
		});
		SPI.setOnDragDone((event)-> {
			stage.setOpacity(1.0f);
		});
		SPI.setOnMouseReleased((event)->{
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
    private void goBack(ActionEvent event) throws IOException 
    {
	Parent backLaunch = FXMLLoader.load(getClass().getResource("/projectsis/scenes/ChoixFilièreSemestreRésultat.fxml"));
	Scene main = new Scene(backLaunch);
	Stage Start = (Stage)((Node)event.getSource()).getScene().getWindow();
	Start.setScene(main);
	Start.show();
    }
    
}
