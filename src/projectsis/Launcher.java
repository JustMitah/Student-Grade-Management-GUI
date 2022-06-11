package projectsis;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;


public class Launcher extends Application {
	public static Stage stage = null;
	@Override
	public void start(Stage stage) throws Exception {
			Parent root = FXMLLoader.load(getClass().getResource("/projectsis/scenes/Launcher.fxml"));
			stage.initStyle(StageStyle.UNDECORATED);			
			Scene scene = new Scene(root);
			stage.setScene(scene);
			Launcher.stage = stage;
			stage.show();
			
	}
	
}
