package application;

import Controller.MainMenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class MainMenu extends Application {
	private Stage primaryStage;
	private Scene scene;
	private AnchorPane primaryLayout;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("CSCI 3300 project");
		loadMainWndow();
	}

	private void loadMainWndow() {
		// we need to create FXMLLoader object
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainMenu.class.getResource("/View/MainMenuView.fxml"));
		try {
			primaryLayout = loader.load();
			scene = new Scene(primaryLayout);
			// we need to give the controller access to the Main app.
			MainMenuController controller = new MainMenuController();
			controller.setMain(this);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
