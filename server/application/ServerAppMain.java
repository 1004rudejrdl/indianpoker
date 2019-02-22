package server.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ServerAppMain extends Application {

	public static void main(String[] args) {
		launch(args);
		
	}

	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader loder = new FXMLLoader(getClass().getResource("../View/root.fxml")); 
		Parent root = loder.load();
		
		
		Scene scene = new Scene(root);
		stage.setTitle("서버 메인창");
		stage.setResizable(false);
		stage.setScene(scene);
		stage.show();
	}

}
