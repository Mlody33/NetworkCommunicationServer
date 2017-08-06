package controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import application.ServerMain;
import application.ServerStatuses;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import model.Client;

public class ServerController implements Initializable {

	@FXML private TableView<Client> clientsTableView;
	@FXML private TableColumn<Client, Integer> identyfierColumn;
	@FXML private TableColumn<Client, Boolean> authorizationColumn;
	@FXML private TableColumn<Client, Integer> signalColumn;
	@FXML private TableColumn<Client, LocalDateTime> timeConnectionColumn;
	@FXML private Button connectionBtn;
	@FXML private Text statusTxt;
	@FXML private Text authorizationCodeTxt;
	
	private AcceptanceOfClientsConnection acceptanceOfClientsConnection;
	private ServerMain main;
	
	public void setMain(ServerMain main) {
		this.main = main;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		identyfierColumn.setCellValueFactory(new PropertyValueFactory<Client, Integer>("clientNumber"));
		authorizationColumn.setCellValueFactory(new PropertyValueFactory<Client, Boolean>("authorized"));
		signalColumn.setCellValueFactory(new PropertyValueFactory<Client, Integer>("signalToCommunicationWithServer"));
		timeConnectionColumn.setCellValueFactory(new PropertyValueFactory<Client, LocalDateTime>("timeConnection"));
	}
	
	public void setTable() {
		clientsTableView.setItems(main.getServerDate().getConnectedClients());
	}
	
	public void setConnection() throws IOException, InterruptedException {
		setServerUIStatusOnline();
		acceptanceOfClientsConnection = new AcceptanceOfClientsConnection();
		Thread acceptanceThread = new Thread(acceptanceOfClientsConnection);
		acceptanceOfClientsConnection.setMain(main);
		acceptanceThread.setName(ServerStatuses.ACCEPTANCE_THREAD.get());
		acceptanceThread.start();
	}

	@FXML public void switchServerConnection() throws IOException, InterruptedException {
		if(main.getServerDate().isServerOnline()) {
			setServerOffline();
		} else {
			setServerOnline();
		}
	}

	private void setServerOnline() throws IOException, InterruptedException {
		main.getServerDate().generateAuthorizationCode();
		setConnection();
	}

	public void setServerOffline() {
		if(main.getServerDate().getConnectedClients().isEmpty()) {
			main.getServerDate().setServerStatusOffline();
			setServerUIStatusOffline();
			acceptanceOfClientsConnection.closeConnection();
		} else {
			statusTxt.setFill(Color.RED);
			statusTxt.setText(ServerStatuses.SERVER_NOT_EMPTY.get());
		}
	}
	
	private void setServerUIStatusOnline() {
		main.getServerDate().setServerStatusOnline();
		statusTxt.setFill(Color.BLACK);
		statusTxt.setText(ServerStatuses.ONLINE.get());
		authorizationCodeTxt.setText(String.valueOf(main.getServerDate().getAuthorizationCode()));
		connectionBtn.setText(ServerStatuses.SET_SERVER_OFFLINE.get());
	}

	private void setServerUIStatusOffline() {
		main.getServerDate().setServerStatusOffline();
		statusTxt.setFill(Color.BLACK);
		statusTxt.setText(ServerStatuses.OFFLINE.get());
		connectionBtn.setText(ServerStatuses.SET_SERVER_ONLINE.get());
	}
	
	public void setUINewClientConnection() {
		statusTxt.setFill(Color.BLACK);
		statusTxt.setText(ServerStatuses.NEW_CONNECTION.get());
	}
	
	public void setUINewClientDisconnection() {
		statusTxt.setFill(Color.BLACK);
		statusTxt.setText(ServerStatuses.NEW_DISCONNECTION.get());
	}
	
	public void setUINewClientAuthorization() {
		statusTxt.setFill(Color.BLACK);
		statusTxt.setText(ServerStatuses.NEW_AUTHORIZATION.get());
	}
	
	public void setUINewClientUpdate() {
		statusTxt.setFill(Color.BLACK);
		statusTxt.setText(ServerStatuses.NEW_UPDATE.get());
	}
	
	public void setImmediatelyServerOffline() {
		main.getServerDate().setServerStatusOffline();
		acceptanceOfClientsConnection.closeConnection();
	}

}