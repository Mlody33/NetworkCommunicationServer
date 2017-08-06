package application;
	
import java.io.IOException;

import controller.ServerController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Client;
import model.Server;


public class ServerMain extends Application {
	
	private Stage primaryStage;
	private BorderPane rootLayout;
	
	private Server serverDate = new Server();
	private ObservableList<Client> connectedClients = FXCollections.observableArrayList();
	private ServerController controller;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		try {
			initMainView();
		} catch(IOException e) {
			e.printStackTrace();
		} catch(InterruptedException e1) {
			e1.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	private void initMainView() throws IOException, InterruptedException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(ServerMain.class.getResource("../view/ServerView.fxml"));
		rootLayout = (BorderPane)loader.load();
		
		controller = loader.getController();
		controller.setMain(this);
		controller.setTable();
		controller.setConnection();
		
		Scene scene = new Scene(rootLayout);
		scene.getStylesheets().add(ServerMain.class.getResource("../view/style.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setTitle(ServerStatuses.SERVER_TITLE.get());
		primaryStage.show();
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent arg0) {
				controller.setImmediatelyServerOffline();
			}
		});
	}

	public ObservableList<Client> getConnectedClients() {
		return connectedClients;
	}
	
	public Server getServerDate() {
		return this.serverDate;
	}
	
}