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
import org.controlsfx.control.tableview2.TableView2;
import projectsis.Launcher;

/**
 * FXML Controller class
 *
 * @author Kazwell
 */
public class CoordonnateurDeModuleController implements Initializable {

    @FXML
    private AnchorPane InterfaceCM;
    @FXML
    private TableView2<?> TableMatiereNote;
    
    private double xOffset = 0;
    private double yOffset = 0;
    private Stage stage;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        makeStageDragable();
    }    
    private void makeStageDragable() {
		InterfaceCM.setOnMousePressed((event) -> {
			xOffset=event.getSceneX();
			yOffset=event.getSceneY();
		});
		InterfaceCM.setOnMouseDragged((event)-> {
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			stage.setX(event.getScreenX()-xOffset);
			stage.setY(event.getScreenY()-yOffset);
			stage.setOpacity(0.95f);
		});
		InterfaceCM.setOnDragDone((event)-> {
			stage.setOpacity(1.0f);
		});
		InterfaceCM.setOnMouseReleased((event)->{
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
		Parent backLaunch = FXMLLoader.load(getClass().getResource("/projectsis/scenes/GestionFE.fxml"));
		Scene main = new Scene(backLaunch);
		Stage Start = (Stage)((Node)event.getSource()).getScene().getWindow();
		Start.setScene(main);
		Start.show();
	}	
    
}
