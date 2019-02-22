package client.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import client.Model.ClientDB;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GameStart implements Initializable {
	public Stage gameStage;
	private static Thread thread;
	private Socket socket;
	private BufferedReader br;
	private PrintWriter pw;
	private char me, other;
	public int number ;
	@FXML
	private Label gamePoker;
	@FXML
	private Button gameClose;
	@FXML
	private ImageView gameClientImage;
	@FXML
	private Label gameClientID;
	@FXML
	private ImageView gameMyCard;
	@FXML
	private Button gamePluse;
	@FXML
	private Button gameSet;
	@FXML
	private Button gameGiveUp;
	@FXML
	private ImageView gameClientCard;
	@FXML
	private ImageView gameClientImage2;
	@FXML
	private Label gameClientID2;
	@FXML
	private ImageView gameCemterCard1;
	@FXML
	private ImageView gameCemterCard2;
	@FXML
	private TextField gameProTextField;
	@FXML
	private TextField gameMoneyTextField;
	@FXML
	private Label gameMyMoney;
	@FXML
	private Label gameClientMoney;
	@FXML
	private TextArea gameTextArea;
	@FXML
	private TextField gameTextFild;
	@FXML
	private Button gameSendBtn;
	private boolean state = false;
	
	 private String totalMoney = "1000";
	 
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		gameClientID.setText(ClientRootController.id);
		
		// 1. 클라이언트 서버 연결
		clientServerConnect();
		// 3. 나가기버튼
		gameClose.setOnAction(e -> {
			mainStageBack();
		});
		gameSendBtn.setVisible(false);
	}

	// 1. 클라이언트 서버 연결
	private void clientServerConnect() {

		Runnable runnable = new Runnable() {

			private int battingMoney=0;
			private int mymoney;
			private int money;
			private int recevieMoney=0;
			private String otherName;
			private String clientID;
			private String lastTotalMoney;
			private String clientMoney;
			private int openmoney;
			private String ClientMoney;
			@Override
			public void run() {
				
					socket = new Socket();
					try {
						socket.connect(new InetSocketAddress("192.168.0.195", 5004));
						System.out.println("연결완료");
						InputStream is = socket.getInputStream();
						InputStreamReader isr = new InputStreamReader(is);
						br = new BufferedReader(isr);
						OutputStream os = socket.getOutputStream();
						pw = new PrintWriter(os, true);

						// 2. 클라이언트 게임 시작
						while (true) {
							
							String serverMessage = br.readLine();
							String protocol = serverMessage.substring(0, 5);
							switch (protocol) {
							case "IDSEN" : 
								clientID = serverMessage.substring(6,serverMessage.lastIndexOf(":"));
								clientMoney = serverMessage.substring(serverMessage.lastIndexOf(":")+1);
								String image = ClientDAO.getClientImage(clientID);
								Image images = new Image("file:///"+ClientGameServerRootController.imageDirectory.getAbsolutePath()+"/"+image);
								gameClientImage2.setImage(images);
								ClientMoney = ClientDAO.getClientMoney(clientID);
								
								
								System.out.println("rrrrrrrrrrrrrr      "+clientMoney);
								Platform.runLater(()->{
									gameClientID2.setText(clientID);
									gameClientMoney.setText(clientID+"님 보유 금액 : "+ClientMoney);
								});
								System.out.println(clientID);
								break;
							case "MMESS" : 
								String message = serverMessage.substring(5);
								gameTextArea.appendText(message+"\n");
								break;
							case "YMESS" : 
								String message1 = serverMessage.substring(5);
								gameTextArea.appendText(message1+"\n");
								otherName = message1.substring(0, message1.lastIndexOf(":"));
								System.out.println(otherName);
								break;
							case "START":
								String takeMoney = ClientDAO.getClientMoney(ClientRootController.id);
								mymoney=Integer.parseInt(takeMoney);
								if(mymoney<500) {
									mainStageBack1();
								}else {
									
									String image1 = ClientDAO.getClientImage(ClientRootController.id);
									Image imagess = new Image("file:///"+ClientGameServerRootController.imageDirectory.getAbsolutePath()+"/"+image1);
									gameClientImage.setImage(imagess);
									pw.println("CLIENT " + ClientRootController.id+":"+mymoney);
									gameSendBtn.setOnAction(e->{sendMessage();});
									gameTextFild.setOnKeyPressed(new EventHandler<KeyEvent>() {
										
										@Override
										public void handle(KeyEvent event) {
											if(event.getCode().equals(KeyCode.ENTER)) {
												sendMessage();
												gameTextFild.clear();
											}
											
										}
									});
									String str = serverMessage.substring(6);
									me = serverMessage.charAt(6);
									other = (me == '1')?('2'):('1');
									gameTextArea.appendText(serverMessage+"\n");
									totalMoney = "1000";
									gameProTextField.setText(totalMoney);
									battingMoney=0;
									
									
									money = mymoney-500;
									Platform.runLater(()->{gameMyMoney.setText("보유 머니 : "+money+"원");});
									ClientDAO.updateClientMoney(new ClientDB(ClientRootController.id, String.valueOf(money)));
									gameMyCard.setImage(new Image(getClass().getResource("../images/card.png").toString()));
									gamePluse.setVisible(false);
									gameSet.setVisible(false);
									gameGiveUp.setVisible(false);
									
									//gameProTextField.setText(serverMessage.substring(5));
								
									
								}
								break;
							
							case "GAMES" : 
								if(me=='1') {
									state=true;
									gamePluse.setVisible(true);
									gameSet.setVisible(true);
									gameGiveUp.setVisible(true);
								}else {
									gamePluse.setVisible(false);
									gameSet.setVisible(false);
									gameGiveUp.setVisible(false);
								}
								gameSendBtn.setVisible(true);
								gameCemterCard1.setVisible(true);
								gameCemterCard2.setVisible(true);
								gameCemterCard1.setImage(new Image(getClass().getResource("../images/card.png").toString()));
								gameCemterCard2.setImage(new Image(getClass().getResource("../images/card.png").toString()));
								Thread.sleep(800);
								gameCemterCard1.setVisible(false);
								gameCemterCard2.setVisible(false);
								number = (int)(Math.random()*(10-1+1))+1;
								gameClientCard.setImage(new Image(getClass().getResource("../images/viewcard"+number+".png").toString()));
								if(state==true) {
									//1-2 배팅 버튼을 눌렀을때
									gamePluse.setOnAction(e->{battingBtn();});
									//1-3 오픈 버튼을 눌렀을때
									gameSet.setOnAction(e->{openBtn();});
									//1-4 포기 버튼을 눌렀을때
									gameGiveUp.setOnAction(e->{giveUpBtn();});
									//pw.print("FIRST  "+string);
									state=false;
									
								}
								break;
							case "MTURN" : 
								String string = serverMessage.substring(5);
								recevieMoney = Integer.parseInt(string);
								
								String ClientMoney1 = ClientDAO.getClientMoney(clientID);
								Platform.runLater(()->{
									gameClientID2.setText(clientID);
									gameClientMoney.setText(clientID+"님 보유 금액 : "+ClientMoney1);
								});
								
								
								int totalmoney = Integer.parseInt(totalMoney);
								totalmoney = recevieMoney+totalmoney;
								totalMoney = String.valueOf(totalmoney);
								gameProTextField.setText(totalMoney);
								state=true;
								gamePluse.setVisible(true);
								gameSet.setVisible(true);
								gameGiveUp.setVisible(true);
								//1-2 배팅 버튼을 눌렀을때
								gamePluse.setOnAction(e->{battingBtn();});
								//1-3 오픈 버튼을 눌렀을때
								gameSet.setOnAction(e->{openBtn();});
								//1-4 포기 버튼을 눌렀을때
								gameGiveUp.setOnAction(e->{giveUpBtn();});
								
								gameTextArea.appendText("상대방이"+ recevieMoney+"원 배팅 했습니다.\n");
							
								break;
							case "SAMEC" :
								System.out.println("serverMessage"+serverMessage);
								String str2 = serverMessage.substring(5,serverMessage.lastIndexOf(":"));
								lastTotalMoney = serverMessage.substring(serverMessage.lastIndexOf(":")+1);
								System.out.println("str2"+str2);
								gameProTextField.setText(lastTotalMoney);
								int number2 = Integer.parseInt(str2);
								System.out.println("number2"+number2);
								gameMyCard.setImage(new Image(getClass().getResource("../images/viewcard"+number2+".png").toString()));
								char i=0;
								if(number2==number) {
									gameTextArea.appendText("비겼습니다.\n");
									i='2';
								}else if(number2>number) {
									gameTextArea.appendText("당신이 이겼습니다.\n");//자기이름쓰기
									i='1';
									
									
								}else if(number2<number) {
									gameTextArea.appendText("상대방이 이겼습니다.\n");//상대방 아이디 쓰기
									i='2';
									
									
								}
								recevieMoney=0;
								pw.println("RETURN "+number);
								Thread.sleep(4000);
								pw.println("START "+i);
								break;
							case "SAMET" :
								int totalmoney5 = Integer.parseInt(totalMoney);
								gameProTextField.setText(String.valueOf(totalmoney5+openmoney));
								System.out.println("serverMessage"+serverMessage);
								String str3 = serverMessage.substring(5);
								System.out.println("str2"+str3);
								int number3 = Integer.parseInt(str3);
								System.out.println("number2"+number3);
								gameMyCard.setImage(new Image(getClass().getResource("../images/viewcard"+number3+".png").toString()));
								char j=0;
								if(number3==number) {
									int totalmoney1 = Integer.parseInt(gameProTextField.getText());
									String takeMoney1 = ClientDAO.getClientMoney(ClientRootController.id);
									mymoney=Integer.parseInt(takeMoney1);
									money = mymoney+(totalmoney1/2);
									//ClientDAO.updateClientMoney(new ClientDB(ClientRootController.id, String.valueOf(money)));
									Platform.runLater(()->{gameMyMoney.setText("보유 머니 : "+money+"원");});
									String takeMoney2 = ClientDAO.getClientMoney(clientID);
									mymoney=Integer.parseInt(takeMoney2);
									money = mymoney+(totalmoney1/2);
									ClientDAO.updateClientMoney(new ClientDB(clientID, String.valueOf(money)));
									
									
									
									
									//ClientRootController.money = String.valueOf(money);
									gameTextArea.appendText("비겼습니다.\n");
									j='1';
								}else if(number3>number) {
									int totalmoney1 = Integer.parseInt(gameProTextField.getText());
									//money = money+totalmoney1+recevieMoney;
									/*ClientRootController.money = String.valueOf(money);
									Platform.runLater(()->{gameMyMoney.setText("보유 머니 : "+money+"원");});*/
									gameTextArea.appendText(ClientRootController.id +" 당신이 이겼습니다.\n");//자기이름쓰기
									String takeMoney1 = ClientDAO.getClientMoney(ClientRootController.id);
									mymoney=Integer.parseInt(takeMoney1);
									//int totalmoney2 = Integer.parseInt(gameProTextField.getText());
									money = mymoney+totalmoney1;
									ClientDAO.updateClientMoney(new ClientDB(ClientRootController.id, String.valueOf(money)));
									//Platform.runLater(()->{gameMyMoney.setText("보유 머니 : "+money+"원");});
									
									
									
									
									/*String wins=null;
									List<ClientDB> dbArrayList  = ClientDAO.getClientTotaldata();
									
									for(ClientDB client : dbArrayList) {
										String id = client.getId();
										System.out.println("idididididididid"+id);
										wins = client.getWin();
										if(ClientRootController.id.equals(id)) {
											break;
										}
									
									}	
									
									System.out.println("wins  위"+wins+"위  wins");*/
									String wins = ClientDAO.getClientWin(ClientRootController.id);
									System.out.println(wins);
									int win = Integer.parseInt(wins);
									String winner = String.valueOf(win+1);
									ClientDB client = new ClientDB(ClientRootController.id, winner);
									ClientDAO.updateClientWin(client);
									
									
									
									
									
									
									
									
									
									/*String loses=null;
									List<ClientDB> dbArrayList1  = ClientDAO.getClientTotaldata();
									
									for(ClientDB client2 : dbArrayList1) {
										String id = client2.getId();
										System.out.println("idididididididid"+id);
										loses = client2.getLose();
										if(clientID.equals(id)) {
											break;
										}
									
									}	
									
									System.out.println("LLLLLLLL위위위"+loses);
									System.out.println(clientID);*/
									String loses = ClientDAO.getClientLose(clientID);
									System.out.println(loses);
									int lose = Integer.parseInt(loses);
									String loser = String.valueOf(lose+1);
									ClientDB client1 = new ClientDB(clientID, loser);
									ClientDAO.updateClientLose(client1);
									
									j='1';
								}else if(number3<number) {
									int totalmoney1 = Integer.parseInt(gameProTextField.getText());
									//money = money-recevieMoney;
									//ClientRootController.money = String.valueOf(money);
									gameTextArea.appendText("상대방이 이겼습니다.\n");//상대방 아이디 쓰기
									String takeMoney1 = ClientDAO.getClientMoney(clientID);
									mymoney=Integer.parseInt(takeMoney1);
									//int totalmoney2 = Integer.parseInt(gameProTextField.getText());
									money = mymoney+totalmoney1;
									ClientDAO.updateClientMoney(new ClientDB(clientID, String.valueOf(money)));
									//Platform.runLater(()->{gameMyMoney.setText("보유 머니 : "+money+"원");});
									
									
									
									/*String loses=null;
									List<ClientDB> dbArrayList  = ClientDAO.getClientTotaldata();
									
									for(ClientDB client : dbArrayList) {
										String id = client.getId();
										System.out.println("idididididididid"+id);
										loses = client.getLose();
										if(ClientRootController.id.equals(id)) {
											break;
										}
									
									}	
									
									System.out.println("loses  아래00"+loses+"00아래  loses");*/
									String loses = ClientDAO.getClientLose(ClientRootController.id);
									System.out.println(loses);
									int lose = Integer.parseInt(loses);
									String loser = String.valueOf(lose+1);
									ClientDB client = new ClientDB(ClientRootController.id, loser);
									ClientDAO.updateClientLose(client);
									
									
									
									
									/*String wins=null;
									List<ClientDB> dbArrayList1  = ClientDAO.getClientTotaldata();
									
									for(ClientDB client2 : dbArrayList1) {
										String id = client2.getId();
										System.out.println("idididididididid"+id);
										wins = client2.getWin();
										if(clientID.equals(id)) {
											break;
										}
									
									}	
									System.out.println(clientID);
									System.out.println("WWWWWWWWWWWWW아래"+wins);*/
									String wins = ClientDAO.getClientWin(clientID);
									System.out.println(wins);
									int win = Integer.parseInt(wins);
									String winner = String.valueOf(win+1);
									ClientDB client2 = new ClientDB(clientID, winner);
									ClientDAO.updateClientWin(client2);
									
									
									j='2';
								}
								recevieMoney=0;
								Thread.sleep(4000);
								pw.println("START "+j);
								break;
							case "GOVER" : 
								String string9 = serverMessage.substring(6);
								gameTextArea.appendText(string9+"\n");
								char string10 = serverMessage.charAt(5);
								//System.out.println("끝:끝");
								Thread.sleep(4000);
								pw.println("START "+string10);
								//ClientRootController.callAlert("끝:끝");
								
								/*gameStage.close();
								ClientGameServerRootController controller = new ClientGameServerRootController();
								controller.gameStartImage(gameStage);*/
								//pw.println("START");
								recevieMoney=0;
								break;
							}
						}

					} catch (Exception e) {
						
					}finally {
						if (!socket.isClosed()) {
							try {
								socket.close();
							} catch (IOException e1) {
							}
						}
					}

				}
			//1-2 배팅 버튼을 눌렀을때
			private void battingBtn() {
				if(gameMoneyTextField.getText().trim().isEmpty()) {
					ClientRootController.callAlert("배팅 오류 : 금액이 비어있습니다.");
				}else {
					int battingMoney = Integer.parseInt(gameMoneyTextField.getText());
					ClientMoney = ClientDAO.getClientMoney(clientID);
					int clientmoney = Integer.parseInt(ClientMoney);
					if(battingMoney<=recevieMoney || battingMoney>money||battingMoney>clientmoney) {
						ClientRootController.callAlert("배팅 오류 : 금액이 맞지 않습니다.");
					}else {
						String takeMoney = ClientDAO.getClientMoney(ClientRootController.id);
						mymoney=Integer.parseInt(takeMoney);
						money=mymoney-battingMoney;
						
						int totalmoney = Integer.parseInt(totalMoney);
						totalmoney = battingMoney+totalmoney;
						this.battingMoney = this.battingMoney+battingMoney;
						System.out.println(this.battingMoney);
						totalMoney = String.valueOf(totalmoney); 
						gameProTextField.setText(totalMoney);
						gameMoneyTextField.clear();
						Platform.runLater(()->{gameMyMoney.setText("보유 머니 : "+money+"원");});
						ClientDAO.updateClientMoney(new ClientDB(ClientRootController.id, String.valueOf(money)));
						
						
						pw.println("BATING "+battingMoney);
						gamePluse.setVisible(false);
						gameSet.setVisible(false);
						gameGiveUp.setVisible(false);
					}
				}
				
			
			}
			
			//1-3 오픈 버튼을 눌렀을때
			private void openBtn() {
				int totalmoney = Integer.parseInt(gameProTextField.getText());
				openmoney = totalmoney-1000-battingMoney-battingMoney;
				totalmoney = totalmoney+openmoney;
				String takeMoney = ClientDAO.getClientMoney(ClientRootController.id);
				mymoney=Integer.parseInt(takeMoney);
				money = mymoney-openmoney;
				Platform.runLater(()->{gameMyMoney.setText("보유 머니 : "+money+"원");});
				ClientDAO.updateClientMoney(new ClientDB(ClientRootController.id, String.valueOf(money)));

				String num = String.valueOf(number);
				pw.println("OPEN " + num+":"+totalmoney);
				
				System.out.println(number);
				gamePluse.setVisible(false);
				gameSet.setVisible(false);
				gameGiveUp.setVisible(false);
			}
			
			//1-4 포기 버튼을 눌렀을때
			private void giveUpBtn() {
				int totalmoney1 = Integer.parseInt(gameProTextField.getText());
				gamePluse.setVisible(false);
				gameSet.setVisible(false);
				gameGiveUp.setVisible(false);
				String takeMoney1 = ClientDAO.getClientMoney(clientID);
				mymoney=Integer.parseInt(takeMoney1);
				//int totalmoney2 = Integer.parseInt(gameProTextField.getText());
				money = mymoney+totalmoney1;
				ClientDAO.updateClientMoney(new ClientDB(clientID, String.valueOf(money)));
				String loses = ClientDAO.getClientLose(ClientRootController.id);
				System.out.println(loses);
				int lose = Integer.parseInt(loses);
				String loser = String.valueOf(lose+1);
				ClientDB client = new ClientDB(ClientRootController.id, loser);
				ClientDAO.updateClientLose(client);
				String wins = ClientDAO.getClientWin(clientID);
				System.out.println(wins);
				int win = Integer.parseInt(wins);
				String winner = String.valueOf(win+1);
				ClientDB client2 = new ClientDB(clientID, winner);
				ClientDAO.updateClientWin(client2);
				pw.println("GIVEUP");
			}

			//메세지를 보낼때 사용하는 함수
			private void sendMessage() {
				pw.println("MESSAGE "+ClientRootController.id+ " : "+gameTextFild.getText());
				gameTextFild.clear();
				System.out.println("MESSAGE "+ClientRootController.id+gameTextFild.getText());
			}
			
			private void mainStageBack1() {
				try {
					Stage mainStage = new Stage();
					FXMLLoader loder = new FXMLLoader(getClass().getResource("../View/main.fxml"));
					Parent root = loder.load();
					ClientGameServerRootController loginController = loder.getController();
					loginController.mainstage = mainStage;
					//loginController.display = 1;			
					socket.close();
					Scene scene = new Scene(root);
					mainStage.setResizable(false);
					mainStage.setScene(scene);
					gameStage.close();
					mainStage.show();

				} catch (Exception e) {

					e.printStackTrace();
				}

			}
			
			
		};
		thread = new Thread(runnable);
		thread.start();
		
	}
	
	
	

	// 3. 나가기버튼
	private void mainStageBack() {
		try {
			Stage mainStage = new Stage();
			FXMLLoader loder = new FXMLLoader(getClass().getResource("../View/main.fxml"));
			Parent root = loder.load();
			ClientGameServerRootController loginController = loder.getController();
			loginController.mainstage = mainStage;
			//loginController.display = 1;			
			socket.close();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("../application/app2.css").toString());
			mainStage.setResizable(false);
			mainStage.setScene(scene);
			gameStage.close();
			mainStage.show();

		} catch (Exception e) {

			e.printStackTrace();
		}

	}
	/*public void gameStartImage(Stage stage) {
		try {
			Stage mainStage = new Stage();
			FXMLLoader loder = new FXMLLoader(getClass().getResource("../View/gamestart1.fxml"));
			Parent root = loder.load();
			mainStage.initModality(Modality.WINDOW_MODAL);
			mainStage.initOwner(gameStage);
			GameStart gameController = loder.getController();
			gameController.gameStage = mainStage;
			
			
			Scene scene = new Scene(root);
			mainStage.setResizable(false);
			mainStage.setScene(scene);
			gameStage.close();
			mainStage.show();
			
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}*/
	

}
