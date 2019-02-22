package server.Controller;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import server.Model.Client;

public class ServerRootController implements Initializable{
	@FXML private TextArea textArea;
	@FXML private TableView<Client> tableView;
	private ServerSocket serversocket;
	ObservableList<Client> tableViewList = FXCollections.observableArrayList();
	public Thread mainThread;
	List<GameUseClient> list = new Vector<>();
	private char order1;
	private char order2;
	private String[] message = new String[5];
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		//���� ������ �Ѵ�
		serverStartEvent();
		

		
	}
	
	
	//1���̺� �並 ���� ���� �ش�
	
	//���� ������ �Ѵ�
	private void serverStartEvent() {
		
		try {
			//���� ������ ���� �� �����Ǹ� �־��ش�
			serversocket = new ServerSocket();
			serversocket.bind(new InetSocketAddress("192.168.0.195", 5004));

			Runnable runnable = new Runnable() {
				public void run() {
					Platform.runLater(()->{
						textArea.setText("���� ����");
					});
					//���� �����Ŀ� Ŭ���̾�Ʈ�� �ް� �����带 ������
					while(true) {
						try {
							Socket clientSocket1 = serversocket.accept();
							Platform.runLater(()->{
								textArea.appendText("���� �Ǿ����ϴ�.");//�̺κп� ȸ�� ���̵� �־���� �Ѵ�
								
							});
							//Ŭ���̾�Ʈ �湮�� Ŭ������ ���� �� ����Ʈ�� �߰�
							int order = (int)(Math.random()*(2-1+1))+1;
							if(order==1) {
								order1 = '1';
								order2 = '2';
								
							}else {
								order1 = '2';
								order2 = '1';
							}
							GameUseClient client = new GameUseClient(clientSocket1,'1');
							list.add(client);
							Socket clientSocket2 = serversocket.accept();
							GameUseClient client2 = new GameUseClient(clientSocket2,'2');
							list.add(client2);
							Platform.runLater(()->{
								textArea.appendText("���ᰳ��" +list.size());
							});
							client.other = client2;
							client2.other = client;
							client.me = client;
							client2.me = client2;
							
							client.start();
							client2.start();
							
						
							
							//*******************************
							//���̵� ��й�ȣ �̸� ����� �����ͼ� �ƿ�ǲ��Ʈ��������Ѵ�
							/*Runnable runnable3 = new Runnable() {
								
								@Override
								public void run() {
									
									
								}
							};*/
							
							
						} catch (IOException e) {
							if(!serversocket.isClosed()) {
								try {
								serversocket.close();
							} catch (IOException e1) {}}
						}
						
						
						
					}
					
					
					
				}
			};
			
			mainThread = new Thread(runnable);
			mainThread.start();
			
			
			
			
		} catch (IOException e) {
			//������ ���� ������ �ݾ��ش�
			if(!serversocket.isClosed()) {
				try {
				serversocket.close();
			} catch (IOException e1) {}}
		}
		//������� Client�� ������ ���� �����带 �����ش�.
		
		
		
		
	}		
	
}

//1DB���� ������ ������ �ش�
