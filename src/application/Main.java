package application;
	
import java.io.IOException;

import controller.MainController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Client;


public class Main extends Application {
	
	private static final String TITLE = "SERWER";
	private Stage primaryStage;
	private BorderPane rootLayout;
	
	private ObservableList<Client> connectedClients = FXCollections.observableArrayList();

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		
		try {
			initMainView();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	private void initMainView() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("../view/view.fxml"));
		rootLayout = (BorderPane)loader.load();
		
		MainController controller = loader.getController();
		controller.setMain(this);
		controller.setTable();
		controller.setConnection();
		
		Scene scene = new Scene(rootLayout);
		scene.getStylesheets().add(Main.class.getResource("../view/style.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setTitle(TITLE);
		primaryStage.show();
		
	}

	public ObservableList<Client> getConnectedClients() {
		return connectedClients;
	}
}
