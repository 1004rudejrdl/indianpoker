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
		
		//2. ȸ�� ���� â�� ������ �Ѵ�
		btnMember.setOnAction(e->{memberShip();});
		//3. ���̵� ã�� â�� ������ �Ѵ�.
		btnIdChoose.setOnAction(e->{btnIdChoose();});
		//4. ��й�ȣ ã�� â�� ������ �Ѵ�.
		btnPasswordChoose.setOnAction(e->{btnPasswordChoose();});
		//5.�α����� ������ ���ο� â�� ������.
		btnRogin.setOnAction(e->btnRoginAnotherScreen());
		//6. ������ ��ư�� ������ ��������.
		btnClose.setOnAction(e->Platform.exit());
		//���� �׸� ������ �ڵ� �Է�
		speed1.setOnMouseClicked(e->{speed1();});
		speed2.setOnMouseClicked(e->{speed2();});
	}
	

	private void speed2() {
		roginIdTextField.setText("�÷��̾�2");
		roginPasswordTextField.setText("123");
	}


	private void speed1() {
		roginIdTextField.setText("�÷��̾�1");
		roginPasswordTextField.setText("123");
	}


	//2. ȸ�� ���� â�� ������ �Ѵ�
	private void memberShip() {
		try {
			Stage memberStage = new Stage();
			memberStage.initModality(Modality.WINDOW_MODAL);
			memberStage.initOwner(stage);
			memberStage.setTitle("ȸ�� ����");
			
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
			//�̸������͸� �����ͼ������� �������� �ȸ����� �״��
			memBtnRegister.setOnAction(e->{
				List<ClientDB> dbArrayList  = ClientDAO.getClientTotaldata();
				for(ClientDB clientdb : dbArrayList) {
					String id=clientdb.getId();
					
					System.out.println(id);					
					if((memTextId.getText().trim().isEmpty()) || (memTextId.getText().trim().equals(id)||memTextId.getText().length()>40)) {
						callAlert("���̵� ���� : ���̵� �ۼ����� �����̰ų� \n40�ڰ� �Ѱų� \n�̹� �ִ� ���̵� �Դϴ�.");
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
			
			obComboBox.add("����");
			obComboBox.add("����");
			
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
			
			
			//2-1ȸ������ ��ư�� �������� ȸ�� ������ ���� Ŭ���̾�Ʈ�� �����ش�
			memJoin.setOnAction(e->{
				
				if(!(memBtnRegister.isDisable())) {
					callAlert("���̵�Ȯ�� ���� : ���̵�Ȯ���� �� �ּ���");
					return;
				}else
				if((memTextId.getText().trim().equals(""))) {
					callAlert("���̵� ���� : ���̵� ���̴��� Ȯ���� �ּ���");
					return;
				}else
				if((memTextPassword.getText().trim().equals("")||memTextPassword.getText().length()>15)) {
					callAlert("��� ��ȣ ���� : ��� ��ȣ�� 15�ڰ� �Ѱų� ���̴��� Ȯ���� �ּ���");
					return;
				}else
				if((memTextPasswordRegister.getText().trim().equals("")||memTextPassword.getText().length()>15)) {
					callAlert("��� ��ȣ ���� : ��� ��ȣȮ�ζ��� 15�ڰ� �Ѱų� ���̴��� Ȯ���� �ּ���");
					return;
				}else
				if(!(memTextPassword.getText().trim().equals(memTextPasswordRegister.getText()))) {
					callAlert("��� ��ȣ ���� : ��� ��ȣ�� ��й�ȣ Ȯ���� ������ Ȯ���ϼ���");
					return;
				}else
				if((memTextName.getText().trim().equals(""))) {
					callAlert("�̸� ���� : �̸����� ���̴��� Ȯ���� �ּ���");
					return;
				}else
				
				if((memTextresident1.getText().trim().equals("") || memTextresident2.getText().trim().equals("")||memTextresident1.getText().length()!=6 ||memTextresident2.getText().length()!=7  )) {
					callAlert("�ֹε�� ��ȣ ���� : �ֹ� ��� ��ȣ���� ���̴��� Ȯ���� �ּ���");
					return;
				}else
				if((memTextTel1.getText().trim().equals("") || memTextTel2.getText().trim().equals("")|| memTextTel3.getText().trim().equals("")||memTextTel1.getText().length()!=3||memTextTel2.getText().length()!=4||memTextTel3.getText().length()!=4)) {
					callAlert("��ȭ ��ȣ ���� : ��ȭ ��ȣ���� ���̴��� Ȯ���� �ּ���");
					return;
				}else
				if((memComboBox.getSelectionModel().isEmpty())) {
					callAlert("���� ���� : �������� �����̴��� Ȯ���� �ּ���");
					return;
				}else {
					ClientDB clientDB = new ClientDB(memTextId.getText(), memTextPassword.getText(),
							memTextName.getText(), memTextresident1.getText()+memTextresident2.getText(),
							memTextTel1.getText()+memTextTel2.getText()+memTextTel3.getText(),
							memComboBox.getSelectionModel().getSelectedItem().toString(), "10000", "0", "0");
					int count = ClientDAO.insertClientData(clientDB);
					if(count!=0) {
						System.out.println("����");
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
	


	//3. ���̵� ã�� â�� ������ �Ѵ�.
	private void btnIdChoose() {
		try {
			Stage idStage = new Stage();
			idStage.initModality(Modality.WINDOW_MODAL);
			idStage.initOwner(stage);
			idStage.setTitle("���̵� ã��");
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
						callAlert("���̵� ã�� �Ϸ� : ����� ���̵�� "+id+"�Դϴ�.");
						idStage.close();
						i=1;
						break;
					}
					
				}if(i==0) {
					callAlert("���̵� ã�� ���� :�̸� ��ȭ��ȣ �ֹι�ȣ�� Ʋ���ϴ�.");
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
	
	//4. ��й�ȣ ã�� â�� ������ �Ѵ�
		private void btnPasswordChoose() {
			
			try {
				Stage idStage = new Stage();
				idStage.initModality(Modality.WINDOW_MODAL);
				idStage.initOwner(stage);
				idStage.setTitle("��й�ȣ ã��");
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
							callAlert("��й�ȣ ã�� �Ϸ� : ����� �н������ "+password+"�Դϴ�.");
							idStage.close();
							i=1;
							break;
						}
						
					}if(i==0) {
						callAlert("��й�ȣ ã�� ���� :���̵� �̸� ��ȭ��ȣ �ֹι�ȣ�� Ʋ���ϴ�.");
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
							callAlert("��й�ȣ ã�� �Ϸ� : ����� �н������ "+password+"�Դϴ�.");
							idStage.close();
							i=1;
							break;
						}
						
					}if(i==0) {
						callAlert("��й�ȣ ã�� ���� :���̵� �̸� ��ȭ��ȣ �ֹι�ȣ�� Ʋ���ϴ�.");
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
		
		//5.�α����� ������ ���ο� â�� ������.
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
				callAlert("�α��� ���� :���̵� ��й�ȣ�� Ʋ���ϴ�.");
				return;
			}
			
			
			
		
		}
		
		
	

		
		
		//��Ÿ �˸�â "�������� : ���� ����� �Է��� �ּ���"  �߰��� �� :�� ������
		public static void callAlert(String contentText) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("���");
			alert.setHeaderText(contentText.substring(0, contentText.lastIndexOf(":")));
			alert.setContentText(contentText.substring(contentText.lastIndexOf(":")+1));
			alert.showAndWait();
		}
	
		//��ȭ��ȣ ���� 4���� �Է� �� �� �ְ��Ѵ�.
		public static void inputDecimalFormat4(TextField textField) {
			// ���ڸ� �Է�(������ �Է¹���), �Ǽ��Է¹ް������ new DecimalFormat("###.#");
			DecimalFormat format = new DecimalFormat("####");
			// ���� �Է½� ���� ���� �̺�Ʈ ó��
			textField.setTextFormatter(new TextFormatter<>(event -> {  
				//�Է¹��� ������ ������ �̺�Ʈ�� ������.  
			if (event.getControlNewText().isEmpty()) { return event; }
			//������ �м��� ���� ��ġ�� ������. 
			    	ParsePosition parsePosition = new ParsePosition(0);
				//�Է¹��� ����� �м���ġ�� �������������� format ����� ��ġ���� �м���.
			    	Object object = format.parse(event.getControlNewText(), parsePosition); 
			//���ϰ��� null �̰ų�, �Է��ѱ��̿� �����м���ġ���� ���������(�� �м������������� ����) �ų�, �Է��ѱ��̰� 4�̸�(3�ڸ��� �Ѿ����� ����.) �̸� null ������. 
			if (object == null || parsePosition.getIndex()<event.getControlNewText().length()
			      || event.getControlNewText().length() == 5) {
			     return null;    
			}else {
			     return event;    
			}   
			}));

		}
		//��ȭ��ȣ ���� 3���� �Է� �� �� �ְ��Ѵ�.
		public static void inputDecimalFormat3(TextField textField) {
					// ���ڸ� �Է�(������ �Է¹���), �Ǽ��Է¹ް������ new DecimalFormat("###.#");
					DecimalFormat format = new DecimalFormat("###");
					// ���� �Է½� ���� ���� �̺�Ʈ ó��
					textField.setTextFormatter(new TextFormatter<>(event -> {  
						//�Է¹��� ������ ������ �̺�Ʈ�� ������.  
					if (event.getControlNewText().isEmpty()) { return event; }
					//������ �м��� ���� ��ġ�� ������. 
					    	ParsePosition parsePosition = new ParsePosition(0);
						//�Է¹��� ����� �м���ġ�� �������������� format ����� ��ġ���� �м���.
					    	Object object = format.parse(event.getControlNewText(), parsePosition); 
					//���ϰ��� null �̰ų�, �Է��ѱ��̿� �����м���ġ���� ���������(�� �м������������� ����) �ų�, �Է��ѱ��̰� 4�̸�(3�ڸ��� �Ѿ����� ����.) �̸� null ������. 
					if (object == null || parsePosition.getIndex()<event.getControlNewText().length()
					      || event.getControlNewText().length() == 4) {
					     return null;    
					}else {
					     return event;    
					}   
					}));

				}
				//�ֹε�Ϲ�ȣ ���� 6���� �Է� �� �� �ְ��Ѵ�.
		public static void inputDecimalFormat6(TextField textField) {
					// ���ڸ� �Է�(������ �Է¹���), �Ǽ��Է¹ް������ new DecimalFormat("###.#");
					DecimalFormat format = new DecimalFormat("######");
					// ���� �Է½� ���� ���� �̺�Ʈ ó��
					textField.setTextFormatter(new TextFormatter<>(event -> {  
						//�Է¹��� ������ ������ �̺�Ʈ�� ������.  
					if (event.getControlNewText().isEmpty()) { return event; }
					//������ �м��� ���� ��ġ�� ������. 
					    	ParsePosition parsePosition = new ParsePosition(0);
						//�Է¹��� ����� �м���ġ�� �������������� format ����� ��ġ���� �м���.
					    	Object object = format.parse(event.getControlNewText(), parsePosition); 
					//���ϰ��� null �̰ų�, �Է��ѱ��̿� �����м���ġ���� ���������(�� �м������������� ����) �ų�, �Է��ѱ��̰� 4�̸�(3�ڸ��� �Ѿ����� ����.) �̸� null ������. 
					if (object == null || parsePosition.getIndex()<event.getControlNewText().length()
					      || event.getControlNewText().length() == 7) {
					     return null;    
					}else {
					     return event;    
					}   
					}));

				}//�ֹε�Ϲ�ȣ ���� 7���� �Է� �� �� �ְ��Ѵ�.
		public static void inputDecimalFormat7(TextField textField) {
					// ���ڸ� �Է�(������ �Է¹���), �Ǽ��Է¹ް������ new DecimalFormat("###.#");
					DecimalFormat format = new DecimalFormat("#######");
					// ���� �Է½� ���� ���� �̺�Ʈ ó��
					textField.setTextFormatter(new TextFormatter<>(event -> {  
						//�Է¹��� ������ ������ �̺�Ʈ�� ������.  
					if (event.getControlNewText().isEmpty()) { return event; }
					//������ �м��� ���� ��ġ�� ������. 
					    	ParsePosition parsePosition = new ParsePosition(0);
						//�Է¹��� ����� �м���ġ�� �������������� format ����� ��ġ���� �м���.
					    	Object object = format.parse(event.getControlNewText(), parsePosition); 
					//���ϰ��� null �̰ų�, �Է��ѱ��̿� �����м���ġ���� ���������(�� �м������������� ����) �ų�, �Է��ѱ��̰� 4�̸�(3�ڸ��� �Ѿ����� ����.) �̸� null ������. 
					if (object == null || parsePosition.getIndex()<event.getControlNewText().length()
					      || event.getControlNewText().length() == 8) {
					     return null;    
					}else {
					     return event;    
					}   
					}));

				}
}


//DB���� ������ ������ 1 ȸ�� ���Կ� �ش�
//2���̵� ��й�ȣ ã�⿡ �ش�
//3���̵� ��й�ȣ �ְ� �α��� �ϱ⿡ �ش�.