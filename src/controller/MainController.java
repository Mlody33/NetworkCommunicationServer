package controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Client;

public class MainController implements Initializable{

	@FXML TableView<Client> clientsTableView;
	@FXML TableColumn<Client, String> identyfierColumn;
	@FXML TableColumn<Client, LocalDate> timeConnectionColumn;
	
	private Main main;
	
	public void setMain(Main main) {
		this.main = main;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		identyfierColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("identyfier"));
		timeConnectionColumn.setCellValueFactory(new PropertyValueFactory<Client, LocalDate>("timeConnection"));
	}
	
	public void setTable() {
		LocalDate today = LocalDate.now().plusDays(1);
		Client client = new Client("id", today);
		
		clientsTableView.setItems(main.getConnectedClients());
	}
	
	public void setConnection() throws IOException {
		ConnectionThread connectionThread = new ConnectionThread();
		connectionThread.start();
	}

}
