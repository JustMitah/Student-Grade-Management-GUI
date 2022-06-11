package projectsis.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import projectsis.Launcher;
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
/**
 * FXML Controller class
 *
 * @author Kazwell
 */
public class LauncherController implements Initializable {
    @FXML
    private AnchorPane parent;
    private double xOffset = 0;
    private double yOffset = 0;
    private Stage stage;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
		makeStageDragable();
	}
		
    private void makeStageDragable() {
		parent.setOnMousePressed((event) -> {
			xOffset=event.getSceneX();
			yOffset=event.getSceneY();
		});
		parent.setOnMouseDragged((event)-> {
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			stage.setX(event.getScreenX()-xOffset);
			stage.setY(event.getScreenY()-yOffset);
			stage.setOpacity(0.95f);
		});
		parent.setOnDragDone((event)-> {
			stage.setOpacity(1.0f);
		});
		parent.setOnMouseReleased((event)->{
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
	private void goToEnseignant(ActionEvent event) throws IOException {
		Parent loginE = FXMLLoader.load(getClass().getResource("/projectsis/scenes/LoginEnseignant.fxml"));
		Scene LoginEpage = new Scene(loginE);
		Stage LoginEstage = (Stage)((Node)event.getSource()).getScene().getWindow();
		LoginEstage.setScene(LoginEpage);
		LoginEstage.show();
	}
	
	@FXML
	private void goToCoordonateur(ActionEvent event) throws IOException {
		Parent loginC = FXMLLoader.load(getClass().getResource("/projectsis/scenes/LoginCoordonnateur.fxml"));
		Scene LoginCpage = new Scene(loginC);
		Stage LoginCstage = (Stage)((Node)event.getSource()).getScene().getWindow();
		LoginCstage.setScene(LoginCpage);
		LoginCstage.show();
	}    
}
