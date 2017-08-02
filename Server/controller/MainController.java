package controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import application.ServerMain;
import application.StatusTextDB;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import model.Client;

public class MainController implements Initializable{

	@FXML TableView<Client> clientsTableView;
	@FXML TableColumn<Client, Integer> identyfierColumn;
	@FXML TableColumn<Client, LocalDate> timeConnectionColumn;
	@FXML Button connectionBtn;
	@FXML Text statusTxt;
	
	private AcceptanceOfClientsConnection acceptanceOfClientsConnection;
	private ServerMain main;
	
	public void setMain(ServerMain main) {
		this.main = main;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		identyfierColumn.setCellValueFactory(new PropertyValueFactory<Client, Integer>("clientNumber"));
		timeConnectionColumn.setCellValueFactory(new PropertyValueFactory<Client, LocalDate>("timeConnection"));
	}
	
	public void setTable() {
		clientsTableView.setItems(main.getConnectedClients());
	}
	
	public void setConnection() throws IOException, InterruptedException {
		setServerOnline();
		acceptanceOfClientsConnection = new AcceptanceOfClientsConnection();
		acceptanceOfClientsConnection.setMain(main);
		acceptanceOfClientsConnection.start();
	}

	@FXML public void switchServerConnection() {
		
	}

	public void setServerOnline() {
		main.getServerDate().setServerStatusOnline();
		statusTxt.setText(StatusTextDB.ONLINE.get() + " " + main.getServerDate().getAuthorizationCode());
		connectionBtn.setText(StatusTextDB.SET_SERVER_OFF.get());
	}

	public void setServerOffline() {
		main.getServerDate().setServerStatusOffline();
		statusTxt.setText(StatusTextDB.OFFLINE.get());
		connectionBtn.setText(StatusTextDB.SET_SERVER_ON.get());
	}

}
