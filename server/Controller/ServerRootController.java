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
		
		//서버 시작을 한다
		serverStartEvent();
		

		
	}
	
	
	//1테이블 뷰를 연동 시켜 준다
	
	//서버 시작을 한다
	private void serverStartEvent() {
		
		try {
			//서버 소켓을 만들어서 내 아이피를 넣어준다
			serversocket = new ServerSocket();
			serversocket.bind(new InetSocketAddress("192.168.0.195", 5004));

			Runnable runnable = new Runnable() {
				public void run() {
					Platform.runLater(()->{
						textArea.setText("서버 시작");
					});
					//서버 시작후에 클라이언트를 받고 쓰레드를 돌린다
					while(true) {
						try {
							Socket clientSocket1 = serversocket.accept();
							Platform.runLater(()->{
								textArea.appendText("연결 되었습니다.");//이부분에 회원 아이디를 넣어줘야 한다
								
							});
							//클라이언트 방문자 클래스를 만든 후 리스트에 추가
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
								textArea.appendText("연결개수" +list.size());
							});
							client.other = client2;
							client2.other = client;
							client.me = client;
							client2.me = client2;
							
							client.start();
							client2.start();
							
						
							
							//*******************************
							//아이디 비밀번호 이름 등등을 가져와서 아웃풋스트림해줘야한다
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
			//오류가 나면 소켓을 닫아준다
			if(!serversocket.isClosed()) {
				try {
				serversocket.close();
			} catch (IOException e1) {}}
		}
		//쓰레드로 Client가 왔을때 마다 쓰레드를 돌려준다.
		
		
		
		
	}		
	
}

//1DB에서 정보를 가져와 준다
