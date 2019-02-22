package client.Controller;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.util.List;
import java.util.ResourceBundle;

import client.Model.ClientDB;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ClientRootController implements Initializable{

	
	@FXML private TextField roginIdTextField;
	@FXML private PasswordField roginPasswordTextField;
	@FXML private Button btnRogin;
	@FXML private Button btnClose;
	@FXML private Button btnIdChoose;
	@FXML private Button btnPasswordChoose;
	@FXML private Button btnMember;
	@FXML private ImageView speed1;
	@FXML private ImageView speed2;
	
	public Stage stage;
	//public static List<ClientDB> dbArrayList  = ClientDAO.getClientTotaldata();
	public static ObservableList<ClientDB> clientDBList = FXCollections.observableArrayList();

	public static String id;
	public static String password;
	public static String name;
	public static String resident;
	public static String phoneNumber;
	public static String gender;
	public static String money;
	public static String win;
	public static String lose;
	public static String imagepath;
	public static int count;
	
	public void setStage(Stage stage) {
		this.stage = stage;
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		//2. 회원 가입 창이 열려야 한다
		btnMember.setOnAction(e->{memberShip();});
		//3. 아이디 찾기 창이 열려야 한다.
		btnIdChoose.setOnAction(e->{btnIdChoose();});
		//4. 비밀번호 찾기 창이 열려야 한다.
		btnPasswordChoose.setOnAction(e->{btnPasswordChoose();});
		//5.로그인을 누르면 새로운 창이 열린다.
		btnRogin.setOnAction(e->btnRoginAnotherScreen());
		//6. 나가기 버튼을 누르면 나가진다.
		btnClose.setOnAction(e->Platform.exit());
		//번외 그림 누르면 자동 입력
		speed1.setOnMouseClicked(e->{speed1();});
		speed2.setOnMouseClicked(e->{speed2();});
	}
	

	private void speed2() {
		roginIdTextField.setText("플레이어2");
		roginPasswordTextField.setText("123");
	}


	private void speed1() {
		roginIdTextField.setText("플레이어1");
		roginPasswordTextField.setText("123");
	}


	//2. 회원 가입 창이 열려야 한다
	private void memberShip() {
		try {
			Stage memberStage = new Stage();
			memberStage.initModality(Modality.WINDOW_MODAL);
			memberStage.initOwner(stage);
			memberStage.setTitle("회원 가입");
			
			FXMLLoader loder = new FXMLLoader(getClass().getResource("../View/member.fxml"));
			Parent root = loder.load();
			
			TextField memTextId = (TextField)root.lookup("#memTextId");
			Button memBtnRegister = (Button)root.lookup("#memBtnRegister");
			PasswordField memTextPassword = (PasswordField)root.lookup("#memTextPassword");
			PasswordField memTextPasswordRegister = (PasswordField)root.lookup("#memTextPasswordRegister");
			TextField memTextName = (TextField)root.lookup("#memTextName");
			TextField memTextresident1 = (TextField)root.lookup("#memTextresident1");
			PasswordField memTextresident2 = (PasswordField)root.lookup("#memTextresident2");
			TextField memTextTel1 = (TextField)root.lookup("#memTextTel1");
			TextField memTextTel2 = (TextField)root.lookup("#memTextTel2");
			TextField memTextTel3 = (TextField)root.lookup("#memTextTel3");
			ComboBox memComboBox = (ComboBox)root.lookup("#memComboBox");
			Button memJoin = (Button)root.lookup("#memJoin");
			Button memCencel = (Button)root.lookup("#memCencel");
			
			
			//*****************************
			//이름데이터를 가져와서맞으면 눌려지고 안맞으면 그대로
			memBtnRegister.setOnAction(e->{
				List<ClientDB> dbArrayList  = ClientDAO.getClientTotaldata();
				for(ClientDB clientdb : dbArrayList) {
					String id=clientdb.getId();
					
					System.out.println(id);					
					if((memTextId.getText().trim().isEmpty()) || (memTextId.getText().trim().equals(id)||memTextId.getText().length()>40)) {
						callAlert("아이디 오류 : 아이디를 작성하지 않으셨거나 \n40자가 넘거나 \n이미 있는 아이디 입니다.");
						return;
					}
				}
				try {
					Stage idreagisterStage = new Stage();
					idreagisterStage.initModality(Modality.WINDOW_MODAL);
					idreagisterStage.initOwner(memberStage);
					FXMLLoader loder1 = new FXMLLoader(getClass().getResource("../View/idregister.fxml"));
					Parent root2 = loder1.load();
					Button idRegisterBtnUse = (Button)root2.lookup("#idRegisterBtnUse");
					Button idRegisterBtnCencel = (Button)root2.lookup("#idRegisterBtnCencel");
					
					idRegisterBtnUse.setOnAction(e2->{
						memTextId.setDisable(true);
						memBtnRegister.setDisable(true);
						idreagisterStage.close();});
					idRegisterBtnCencel.setOnAction(e2->idreagisterStage.close());
					
					Scene scene = new Scene(root2);
					scene.getStylesheets().add(getClass().getResource("../application/app2.css").toString());
					idreagisterStage.setResizable(false);
					idreagisterStage.setScene(scene);
					idreagisterStage.show();
					
				} catch (IOException e1) {
					
					e1.printStackTrace();
				}
			});
			
			inputDecimalFormat3(memTextTel1);
			inputDecimalFormat4(memTextTel2);
			inputDecimalFormat4(memTextTel3);
			inputDecimalFormat6(memTextresident1);
			inputDecimalFormat7(memTextresident2);
			
			ObservableList obComboBox = FXCollections.observableArrayList();
			
			obComboBox.add("남자");
			obComboBox.add("여자");
			
			memComboBox.setItems(obComboBox);
			
			/*if(!(memTextId.getText().trim().equals("")) && !(memTextPassword.getText().trim().equals("")) &&!(memTextPasswordRegister.getText().trim().equals(""))
					&&!(memTextName.getText().trim().equals("")) &&!(memTextresident1.getText().trim().equals("")) &&!(memTextresident2.getText().trim().equals(""))
					&&!(memTextTel1.getText().trim().equals("")) &&!(memTextTel2.getText().trim().equals("")) &&!(memTextTel3.getText().trim().equals(""))
					&&!(memComboBox.getSelectionModel().isEmpty())
					) {
				memJoin.setDisable(false);
				}else {
				memJoin.setDisable(true);
				}*/
			
			
			//2-1회원가입 버튼을 눌렀을때 회원 정보가 서버 클라이언트로 보내준다
			memJoin.setOnAction(e->{
				
				if(!(memBtnRegister.isDisable())) {
					callAlert("아이디확인 오류 : 아이디확인을 해 주세요");
					return;
				}else
				if((memTextId.getText().trim().equals(""))) {
					callAlert("아이디 오류 : 아이디를 쓰셨는지 확인해 주세요");
					return;
				}else
				if((memTextPassword.getText().trim().equals("")||memTextPassword.getText().length()>15)) {
					callAlert("비밀 번호 오류 : 비밀 번호가 15자가 넘거나 쓰셨는지 확인해 주세요");
					return;
				}else
				if((memTextPasswordRegister.getText().trim().equals("")||memTextPassword.getText().length()>15)) {
					callAlert("비밀 번호 오류 : 비밀 번호확인란이 15자가 넘거나 쓰셨는지 확인해 주세요");
					return;
				}else
				if(!(memTextPassword.getText().trim().equals(memTextPasswordRegister.getText()))) {
					callAlert("비밀 번호 오류 : 비밀 번호와 비밀번호 확인이 같은지 확인하세요");
					return;
				}else
				if((memTextName.getText().trim().equals(""))) {
					callAlert("이름 오류 : 이름란을 쓰셨는지 확인해 주세요");
					return;
				}else
				
				if((memTextresident1.getText().trim().equals("") || memTextresident2.getText().trim().equals("")||memTextresident1.getText().length()!=6 ||memTextresident2.getText().length()!=7  )) {
					callAlert("주민등록 번호 오류 : 주민 등록 번호란을 쓰셨는지 확인해 주세요");
					return;
				}else
				if((memTextTel1.getText().trim().equals("") || memTextTel2.getText().trim().equals("")|| memTextTel3.getText().trim().equals("")||memTextTel1.getText().length()!=3||memTextTel2.getText().length()!=4||memTextTel3.getText().length()!=4)) {
					callAlert("전화 번호 오류 : 전화 번호란을 쓰셨는지 확인해 주세요");
					return;
				}else
				if((memComboBox.getSelectionModel().isEmpty())) {
					callAlert("성별 오류 : 성별란을 누르셨는지 확인해 주세요");
					return;
				}else {
					ClientDB clientDB = new ClientDB(memTextId.getText(), memTextPassword.getText(),
							memTextName.getText(), memTextresident1.getText()+memTextresident2.getText(),
							memTextTel1.getText()+memTextTel2.getText()+memTextTel3.getText(),
							memComboBox.getSelectionModel().getSelectedItem().toString(), "10000", "0", "0");
					int count = ClientDAO.insertClientData(clientDB);
					if(count!=0) {
						System.out.println("성공");
						memberStage.close();
					}
				}
				
				
			});
			memCencel.setOnAction(e->memberStage.close());
			
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("../application/app2.css").toString());
			memberStage.setResizable(false);
			memberStage.setScene(scene);
			memberStage.show();
		} catch (IOException e) {
			
		}
	
	}
	


	//3. 아이디 찾기 창이 열려야 한다.
	private void btnIdChoose() {
		try {
			Stage idStage = new Stage();
			idStage.initModality(Modality.WINDOW_MODAL);
			idStage.initOwner(stage);
			idStage.setTitle("아이디 찾기");
			FXMLLoader loder = new FXMLLoader(getClass().getResource("../View/id.fxml"));
			Parent root = loder.load();
			TextField idTextName = (TextField)root.lookup("#idTextName");
			TextField idTextTel1 = (TextField)root.lookup("#idTextTel1");
			TextField idTextTel2 = (TextField)root.lookup("#idTextTel2");
			TextField idTextTel3 = (TextField)root.lookup("#idTextTel3");
			TextField idResident1 = (TextField)root.lookup("#idResident1");
			PasswordField idResident2 = (PasswordField)root.lookup("#idResident2");
			Button idRegister = (Button)root.lookup("#idRegister");
			
			
			inputDecimalFormat3(idTextTel1);
			inputDecimalFormat4(idTextTel2);
			inputDecimalFormat4(idTextTel3);
			inputDecimalFormat6(idResident1);
			inputDecimalFormat7(idResident2);
			
			idRegister.setOnAction(e->{
				List<ClientDB> dbArrayList  = ClientDAO.getClientTotaldata();
				int i=0;
				for(ClientDB client : dbArrayList) {
					String name = client.getName();
					String phoneNumber = client.getPhoneNumber();
					String resident = client.getResident();
					String id = client.getId();
					
					
					if(name.equals(idTextName.getText())&&phoneNumber.equals(idTextTel1.getText()+idTextTel2.getText()+idTextTel3.getText())&&resident.equals(idResident1.getText()+idResident2.getText())) {
						callAlert("아이디 찾기 완료 : 당신의 아이디는 "+id+"입니다.");
						idStage.close();
						i=1;
						break;
					}
					
				}if(i==0) {
					callAlert("아이디 찾기 오류 :이름 전화번호 주민번호가 틀립니다.");
					return;
				}
			});

			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("../application/app2.css").toString());
			idStage.setScene(scene);
			idStage.setResizable(false);
			idStage.show();
		} catch (IOException e) {
		}

		
	}
	
	//4. 비밀번호 찾기 창이 열려야 한다
		private void btnPasswordChoose() {
			
			try {
				Stage idStage = new Stage();
				idStage.initModality(Modality.WINDOW_MODAL);
				idStage.initOwner(stage);
				idStage.setTitle("비밀번호 찾기");
				FXMLLoader loder = new FXMLLoader(getClass().getResource("../View/password.fxml"));
				Parent root = loder.load();
				
				TextField pwTextId = (TextField)root.lookup("#pwTextId");
				TextField pwTextName = (TextField)root.lookup("#pwTextName");
				TextField pwTextTel1 = (TextField)root.lookup("#pwTextTel1");
				TextField pwTextTel2 = (TextField)root.lookup("#pwTextTel2");
				TextField pwTextTel3 = (TextField)root.lookup("#pwTextTel3");
				TextField pwTextResident1 = (TextField)root.lookup("#pwTextResident1");
				PasswordField pwTextResident2 = (PasswordField)root.lookup("#pwTextResident2");
				Button pwBtnRegister = (Button)root.lookup("#pwBtnRegister");
				
			
				
				inputDecimalFormat3(pwTextTel1);
				inputDecimalFormat4(pwTextTel2);
				inputDecimalFormat4(pwTextTel3);
				inputDecimalFormat6(pwTextResident1);
				inputDecimalFormat7(pwTextResident2);
				
				pwBtnRegister.setOnAction(e->{
					List<ClientDB> dbArrayList  = ClientDAO.getClientTotaldata();
					int i=0;
					for(ClientDB client : dbArrayList) {
						String name = client.getName();
						String phoneNumber = client.getPhoneNumber();
						String resident = client.getResident();
						String id = client.getId();
						String password=client.getPassword();
						
						
						if(name.equals(pwTextName.getText())&&phoneNumber.equals(pwTextTel1.getText()+pwTextTel2.getText()+pwTextTel3.getText())
								&&resident.equals(pwTextResident1.getText()+pwTextResident2.getText())&&id.equals(pwTextId.getText())) {
							callAlert("비밀번호 찾기 완료 : 당신의 패스워드는 "+password+"입니다.");
							idStage.close();
							i=1;
							break;
						}
						
					}if(i==0) {
						callAlert("비밀번호 찾기 오류 :아이디 이름 전화번호 주민번호가 틀립니다.");
						return;
					}
					/*int i=0;
					for(ClientDB client : dbArrayList) {
						String name = client.getName();
						String phoneNumber = client.getPhoneNumber();
						String resident = client.getResident();
						String id = client.getId();
						String password = client.getPassword();
						if(name.equals(pwTextName.getText())&&phoneNumber.equals(pwTextTel1.getText()+pwTextTel2.getText()+pwTextTel3.getText())
								&&resident.equals(pwTextResident1.getText()+pwTextResident2.getText())&&id.equals(pwTextId.getText())) {
							callAlert("비밀번호 찾기 완료 : 당신의 패스워드는 "+password+"입니다.");
							idStage.close();
							i=1;
							break;
						}
						
					}if(i==0) {
						callAlert("비밀번호 찾기 오류 :아이디 이름 전화번호 주민번호가 틀립니다.");
						return;
					}
					*/
				});
				
				
				Scene scene = new Scene(root);
				scene.getStylesheets().add(getClass().getResource("../application/app2.css").toString());
				idStage.setScene(scene);
				idStage.setResizable(false);
				idStage.show();
			} catch (IOException e) {
			}


		}
		
		//5.로그인을 누르면 새로운 창이 열린다.
		private void btnRoginAnotherScreen() {
			List<ClientDB> dbArrayList  = ClientDAO.getClientTotaldata();
			int i=0;
			for(ClientDB client : dbArrayList) {
				count++;
				name = client.getName();
				phoneNumber = client.getPhoneNumber();
				resident = client.getResident();
				id = client.getId();
				password=client.getPassword();
				money=client.getMoney();
				win=client.getWin();
				lose=client.getLose();
				imagepath=client.getImagepath();
				if(id.equals(roginIdTextField.getText()) && password.equals(roginPasswordTextField.getText())) {
					try {
						Stage mainStage = new Stage();
						FXMLLoader loder = new FXMLLoader(getClass().getResource("../View/main.fxml"));
						Parent root = loder.load();
						ClientGameServerRootController gameController = loder.getController();
						gameController.mainstage = mainStage;
						
						
						Scene scene = new Scene(root);
						//scene.getStylesheets().add(getClass().getResource("app.css").toString());
						scene.getStylesheets().add(getClass().getResource("../application/app2.css").toString());
						mainStage.setResizable(false);
						mainStage.setScene(scene);
						stage.close();
						mainStage.show();
						
					
					} catch (IOException e) {
						
						e.printStackTrace();
					}
					i=1;
					break;
				}
				
			}if(i==0) {
				callAlert("로그인 오류 :아이디 비밀번호가 틀립니다.");
				return;
			}
			
			
			
		
		}
		
		
	

		
		
		//기타 알림창 "오류정보 : 값을 제대로 입력해 주세요"  중간에 꼭 :을 적을것
		public static void callAlert(String contentText) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("경고");
			alert.setHeaderText(contentText.substring(0, contentText.lastIndexOf(":")));
			alert.setContentText(contentText.substring(contentText.lastIndexOf(":")+1));
			alert.showAndWait();
		}
	
		//전화번호 란에 4개만 입력 할 수 있게한다.
		public static void inputDecimalFormat4(TextField textField) {
			// 숫자만 입력(정수만 입력받음), 실수입력받고싶으면 new DecimalFormat("###.#");
			DecimalFormat format = new DecimalFormat("####");
			// 점수 입력시 길이 제한 이벤트 처리
			textField.setTextFormatter(new TextFormatter<>(event -> {  
				//입력받은 내용이 없으면 이벤트를 리턴함.  
			if (event.getControlNewText().isEmpty()) { return event; }
			//구문을 분석할 시작 위치를 지정함. 
			    	ParsePosition parsePosition = new ParsePosition(0);
				//입력받은 내용과 분석위치를 지정한지점부터 format 내용과 일치한지 분석함.
			    	Object object = format.parse(event.getControlNewText(), parsePosition); 
			//리턴값이 null 이거나, 입력한길이와 구문분석위치값이 적어버리면(다 분석하지못했음을 뜻함) 거나, 입력한길이가 4이면(3자리를 넘었음을 뜻함.) 이면 null 리턴함. 
			if (object == null || parsePosition.getIndex()<event.getControlNewText().length()
			      || event.getControlNewText().length() == 5) {
			     return null;    
			}else {
			     return event;    
			}   
			}));

		}
		//전화번호 란에 3개만 입력 할 수 있게한다.
		public static void inputDecimalFormat3(TextField textField) {
					// 숫자만 입력(정수만 입력받음), 실수입력받고싶으면 new DecimalFormat("###.#");
					DecimalFormat format = new DecimalFormat("###");
					// 점수 입력시 길이 제한 이벤트 처리
					textField.setTextFormatter(new TextFormatter<>(event -> {  
						//입력받은 내용이 없으면 이벤트를 리턴함.  
					if (event.getControlNewText().isEmpty()) { return event; }
					//구문을 분석할 시작 위치를 지정함. 
					    	ParsePosition parsePosition = new ParsePosition(0);
						//입력받은 내용과 분석위치를 지정한지점부터 format 내용과 일치한지 분석함.
					    	Object object = format.parse(event.getControlNewText(), parsePosition); 
					//리턴값이 null 이거나, 입력한길이와 구문분석위치값이 적어버리면(다 분석하지못했음을 뜻함) 거나, 입력한길이가 4이면(3자리를 넘었음을 뜻함.) 이면 null 리턴함. 
					if (object == null || parsePosition.getIndex()<event.getControlNewText().length()
					      || event.getControlNewText().length() == 4) {
					     return null;    
					}else {
					     return event;    
					}   
					}));

				}
				//주민등록번호 란에 6개만 입력 할 수 있게한다.
		public static void inputDecimalFormat6(TextField textField) {
					// 숫자만 입력(정수만 입력받음), 실수입력받고싶으면 new DecimalFormat("###.#");
					DecimalFormat format = new DecimalFormat("######");
					// 점수 입력시 길이 제한 이벤트 처리
					textField.setTextFormatter(new TextFormatter<>(event -> {  
						//입력받은 내용이 없으면 이벤트를 리턴함.  
					if (event.getControlNewText().isEmpty()) { return event; }
					//구문을 분석할 시작 위치를 지정함. 
					    	ParsePosition parsePosition = new ParsePosition(0);
						//입력받은 내용과 분석위치를 지정한지점부터 format 내용과 일치한지 분석함.
					    	Object object = format.parse(event.getControlNewText(), parsePosition); 
					//리턴값이 null 이거나, 입력한길이와 구문분석위치값이 적어버리면(다 분석하지못했음을 뜻함) 거나, 입력한길이가 4이면(3자리를 넘었음을 뜻함.) 이면 null 리턴함. 
					if (object == null || parsePosition.getIndex()<event.getControlNewText().length()
					      || event.getControlNewText().length() == 7) {
					     return null;    
					}else {
					     return event;    
					}   
					}));

				}//주민등록번호 란에 7개만 입력 할 수 있게한다.
		public static void inputDecimalFormat7(TextField textField) {
					// 숫자만 입력(정수만 입력받음), 실수입력받고싶으면 new DecimalFormat("###.#");
					DecimalFormat format = new DecimalFormat("#######");
					// 점수 입력시 길이 제한 이벤트 처리
					textField.setTextFormatter(new TextFormatter<>(event -> {  
						//입력받은 내용이 없으면 이벤트를 리턴함.  
					if (event.getControlNewText().isEmpty()) { return event; }
					//구문을 분석할 시작 위치를 지정함. 
					    	ParsePosition parsePosition = new ParsePosition(0);
						//입력받은 내용과 분석위치를 지정한지점부터 format 내용과 일치한지 분석함.
					    	Object object = format.parse(event.getControlNewText(), parsePosition); 
					//리턴값이 null 이거나, 입력한길이와 구문분석위치값이 적어버리면(다 분석하지못했음을 뜻함) 거나, 입력한길이가 4이면(3자리를 넘었음을 뜻함.) 이면 null 리턴함. 
					if (object == null || parsePosition.getIndex()<event.getControlNewText().length()
					      || event.getControlNewText().length() == 8) {
					     return null;    
					}else {
					     return event;    
					}   
					}));

				}
}


//DB에서 가져온 정보를 1 회원 가입에 준다
//2아이디 비밀번호 찾기에 준다
//3아이디 비밀번호 넣고 로그인 하기에 준다.