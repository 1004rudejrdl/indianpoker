package client.Controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import client.Model.ClientDB;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class ClientGameServerRootController implements Initializable{

	public Stage mainstage;
	
	@FXML private ImageView mainInformation;
	@FXML private Button mainExplanation;
	@FXML private Label mainMyMoney;
	@FXML private Label memGameChart;
	@FXML private Button mainLogout;
	@FXML private Button mainExit;
	@FXML private PieChart mainPieChart;
	@FXML private ImageView mainGameStart;
	@FXML private ImageView mainMoneyCharge;
	private int money;
	private File selectFile = null;
	public static File imageDirectory = new File("D:/images");
	private String fileName = "";
	private ClientDB indianpoker;
	//public int display;
	
	public void setMainstage(Stage mainstage) {
		this.mainstage = mainstage;
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		String image1 = ClientDAO.getClientImage(ClientRootController.id);
		if(!(image1==null)) {
			Image images = new Image("file:///"+imageDirectory.getAbsolutePath()+"/"+image1);
			mainInformation.setImage(images);
		}
		//1 �� ����â�� ���� �̹��� ��
		mainInformation.setOnMouseClicked(e->{mouseClickMainInformation();});
		//2 ������ �����ִ� ��ư
		mainExplanation.setOnAction(e->{explanationBtn();});
		//3 ���� ���� ���� �ݾ��� �����ִ� ��
		showMyMoney();
		//4�α׾ƿ����� �α��� ȭ������ �ǵ��� ����.
		mainLogout.setOnAction(e->{logoutBtn();});
		//5���� ��ư���� ������
		mainExit.setOnAction(e->{Platform.exit();});
		//6 �� �·��� ���� ��Ʈ�� �����ش�
		winOrLosePieChart();
		//7���� ���� �̹���
		mainGameStart.setOnMouseClicked(e->{gameStartImage(mainstage);});
		//8���� ������ ���� ������ �� �� �ִ� �̹���
		mainMoneyCharge.setOnMouseClicked(e->{chargeMoney();});
//		if(display==1) {
//			gameStartImage();
//			Platform.exit();
//		}
		
		
	}
	


	//1 �� ����â�� ���� �̹��� ��
	private void mouseClickMainInformation() {
		try {
			Stage informationStage = new Stage();
			FXMLLoader loder = new FXMLLoader(getClass().getResource("../View/information.fxml"));
			Parent root = loder.load();
			informationStage.initModality(Modality.WINDOW_MODAL);
			informationStage.initOwner(mainstage);
			
			ImageView infImageView = (ImageView)root.lookup("#infImageView");
			Label infTextID = (Label)root.lookup("#infTextID");
			Label infTextMoney = (Label)root.lookup("#infTextMoney");
			PasswordField infTextPassword = (PasswordField)root.lookup("#infTextPassword");
			PasswordField infTextPasswordRegister = (PasswordField)root.lookup("#infTextPasswordRegister");
			TextField infTel1 = (TextField)root.lookup("#infTel1");
			TextField infTel2 = (TextField)root.lookup("#infTel2");
			TextField infTel3 = (TextField)root.lookup("#infTel3");
			Button infChange = (Button)root.lookup("#infChange");
			Button infGiveUp = (Button)root.lookup("#infGiveUp");
			Button infClose = (Button)root.lookup("#infClose");
			String image1 = ClientDAO.getClientImage(ClientRootController.id);
			if(!(image1==null)) {
			Image images = new Image("file:///"+imageDirectory.getAbsolutePath()+"/"+image1);
			infImageView.setImage(images);
			}
			ClientRootController.inputDecimalFormat3(infTel1);
			ClientRootController.inputDecimalFormat4(infTel2);
			ClientRootController.inputDecimalFormat4(infTel3);
			
			infImageView.setOnMouseClicked(e->{
				String image = ClientDAO.getClientImage(ClientRootController.id);
				
				System.out.println(image+"       �̹�����");
				if(!(image==null)) {
					imageDelete();
				}
				handleT1BtnImageAction(infImageView);
				imageSave();
				ClientDB client = new ClientDB(ClientRootController.id, fileName);
				ClientDAO.updateClientPicture(client);
				String image2 = ClientDAO.getClientImage(ClientRootController.id);
				Image imagess = new Image("file:///"+imageDirectory.getAbsolutePath()+"/"+image2);
				infImageView.setImage(imagess);
				mainInformation.setImage(imagess);
				
			});
			
			
			
			
			infTextID.setText(ClientRootController.id);
			infTextMoney.setText("���� �Ӵ� : 		"+ClientRootController.money);
			infGiveUp.setOnAction(e->{
				int count = ClientDAO.deleteClientData(ClientRootController.id);
				if(count!=0) {
					System.out.println("���� �Ϸ�");
					ClientRootController.callAlert("ȸ�� Ż�� ��û : ȸ�� Ż�� ó�� �Ϸ�");
					logoutBtn();
				}
			});
			infChange.setOnAction(e->{
				if(infTextPassword.getText().trim().equals(infTextPasswordRegister.getText().trim())) {
					ClientDB client = new ClientDB(ClientRootController.id, infTextPassword.getText(), infTel1.getText()+infTel2.getText()+infTel3.getText());
					int count = ClientDAO.updateClientData(client);
					if(count!=0) {
						ClientRootController.callAlert("���� �Ϸ� : ������ �Ϸ� �Ǿ����ϴ�.");
					}
				}else {
					ClientRootController.callAlert("��й�ȣ ���� : ��й�ȣ�� Ȯ�ζ��� ������ Ȯ���Ͻÿ�");
				}
			});
			infClose.setOnAction(e->informationStage.close());
			
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("../application/app2.css").toString());
			informationStage.setScene(scene);
			informationStage.show();
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}
	
	


	//2 ������ �����ִ� ��ư
		private void explanationBtn() {
			
			try {
				Stage loginStage = new Stage();
				FXMLLoader loder = new FXMLLoader(getClass().getResource("../View/gamediscription.fxml"));
				Parent root = loder.load();
			
				
				
				
				Scene scene = new Scene(root);
				scene.getStylesheets().add(getClass().getResource("../application/app2.css").toString());
				loginStage.setScene(scene);
				loginStage.show();
				
				
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			
			
		}
		//������ ���̽����� ���� �����ͼ� �־�����Ѵ�.**************
		//3 ���� ���� ���� �ݾ��� �����ִ� ��
		private void showMyMoney() {
			String ClientMoney = ClientDAO.getClientMoney(ClientRootController.id);
			
			
			mainMyMoney.setText("�ݾ� : " + ClientMoney);
			
		}
		
		//4�α׾ƿ����� �α��� ȭ������ �ǵ��� ����.
		//***************************************************�ذ��ϱ�
		private void logoutBtn() {
			try {
				Stage loginStage = new Stage();
				FXMLLoader loder = new FXMLLoader(getClass().getResource("../View/root.fxml"));
				Parent root = loder.load();
				ClientRootController loginController = loder.getController();
				loginController.stage = loginStage;
				
				
				
				Scene scene = new Scene(root);
				scene.getStylesheets().add(getClass().getResource("../application/app.css").toString());
				loginStage.setScene(scene);
				mainstage.close();
				loginStage.show();
				
				
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		}

		//6 �� �·��� ���� ��Ʈ�� �����ش�
		//������ ���̽����� �·��� �����ͼ� ��Ʈ�� �־��ش�************
		private void winOrLosePieChart() {
			String wins = ClientDAO.getClientWin(ClientRootController.id);
			String lose = ClientDAO.getClientLose(ClientRootController.id);
			System.out.println(wins + "����" + lose);
			int i=Integer.parseInt(wins);
			int j=Integer.parseInt(lose);
			if( i>0 || j>0) {
				memGameChart.setText("");
				Map<String,Integer> hashMap = new HashMap<>();
				hashMap.put("��", i);
				hashMap.put("��", j);
				
				ObservableList list = FXCollections.observableArrayList();
				Set<String> set = hashMap.keySet();
				Iterator<String> iterator = set.iterator();
				while(iterator.hasNext()) {
					String strkey = iterator.next();
					list.add(new PieChart.Data(strkey,hashMap.get(strkey)));
					
				}
				mainPieChart.setData(list);
			}
			
			
			
			
		}
		
		//7���� ���� �̹���
		public void gameStartImage(Stage stage) {
			String takeMoney = ClientDAO.getClientMoney(ClientRootController.id);
			int mymoney=Integer.parseInt(takeMoney);
			if(mymoney<2000) {
				ClientRootController.callAlert("�ݾ� ���� : �ݾ��� �����ϰ� ������");
			}else {
				
				try {
					Stage mainStage = new Stage();
					FXMLLoader loder = new FXMLLoader(getClass().getResource("../View/gamestart1.fxml"));
					Parent root = loder.load();
					mainStage.initModality(Modality.WINDOW_MODAL);
					mainStage.initOwner(stage);
					GameStart gameController = loder.getController();
					gameController.gameStage = mainStage;
					
					
					Scene scene = new Scene(root);
					scene.getStylesheets().add(getClass().getResource("../application/app2.css").toString());
					mainStage.setResizable(false);
					mainStage.setScene(scene);
					stage.close();
					mainStage.show();
					
				
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
			
		}
		
		//8���� ������ ���� ������ �� �� �ִ� �̹���
		//������ ���̽����� ���� �����ͼ� �־�����Ѵ�.**************
		private void chargeMoney() {
			String ClientMoney = ClientDAO.getClientMoney(ClientRootController.id);
			money = Integer.parseInt(ClientMoney);
			
			if(money>2000) {
				callAlert("�ݾ��� ����մϴ�. : �ݾ��� 2000�� ���Ϻ��� ������ �����մϴ�.");
			}else {
				
				ClientRootController.money="10000";
				mainMyMoney.setText("�ݾ� : " + ClientRootController.money);
				ClientDB client = new ClientDB(ClientRootController.id, ClientRootController.money);
				ClientDAO.updateClientMoney(client);
				callAlert("�ݾ��� �����Ǿ����ϴ�. : �ݾ� 10000�� ����");
			}
			
		}
		//��Ÿ �˸�â "�������� : ���� ����� �Է��� �ּ���"  �߰��� �� :�� ������
				public static void callAlert(String contentText) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("�˸�");
					alert.setHeaderText(contentText.substring(0, contentText.lastIndexOf(":")));
					alert.setContentText(contentText.substring(contentText.lastIndexOf(":")+1));
					alert.showAndWait();
				}
				private void handleT1BtnImageAction(ImageView infImageView) {
					FileChooser fileChooser = new FileChooser();
					fileChooser.getExtensionFilters().add(new ExtensionFilter("Image File","*.png","*.jpg","*.gif"));
					selectFile = fileChooser.showOpenDialog(mainstage);
					String localURL = null;
					if(selectFile !=null) {
						try {
							localURL = selectFile.toURI().toURL().toString();
						} catch (MalformedURLException e1) {		}
					}
					infImageView.setImage(new Image(localURL,false));//����ȭ�� �������� �������°� �ƴϴ�.?��¥ ��ü�� �����´�
					fileName = selectFile.getName();//���õ� ���ϸ��� �ش�  fileName�� �ݵ�� �̹��� ������ ���������� ���� �����Ѵ�.
					//callAlert("���õ� �̹��� : " + fileName);
					
				}

				//��Ÿ �̹��� C:/images/student123412341234_���õ� ���ϸ����� �����Ѵ�.
				private void imageSave() {
					if(!imageDirectory.exists()) {
						imageDirectory.mkdir();   //���丮�� ������ �ȵǾ� ������ ������ �����.
					}
					FileInputStream fis=null;
					BufferedInputStream bis =null;
					FileOutputStream fos=null;
					BufferedOutputStream bos = null;
					//���õ� �̹����� c:/images/  ���õ� �̹��� �̸������� �����Ѵ�
					try {
						fis = new FileInputStream(selectFile);
						bis = new BufferedInputStream(fis);
						
						//FileChooser���� ���õ� ���ϸ��� C:/kkk/ppp/jjj/name.jpg
						//selecFile.getName()  -> name.jpg �� �����´�
						//���ο� ���� ���� ���� �ϴµ� -> student+12345678912_name.jpg
						//imageDirectory.getAbsolutePath()+"\\"+fileName 
						//-> C:/images/student+12345678912_name.jpg �̸����� �����Ѵ�
						//C:/kkk/ppp/jjj/name.jpg �о   C:/images/student+12345678912_name.jpg����� �����Ѵ�
						fileName ="indianpoker"+ System.currentTimeMillis()+"_"+selectFile.getName();
						fos = new FileOutputStream(imageDirectory.getAbsolutePath()+"\\"+fileName);
						bos = new BufferedOutputStream(fos);
						int data=-1;
						while((data = bis.read()) != -1) {
							bos.write(data);
							bos.flush();
						}
						
					} catch (Exception e) {
						callAlert("�̹��� ���� ���� : c/images/�������� ���� ���� �ٶ�");
					}finally {
						try {
						if(fis != null) {fis.close();}
						if(bis != null) {bis.close();}
						if(fos != null) {fos.close();}
						if(bos != null) {bos.close();}
						} catch (IOException e) {}
					}
						
				}
				//�̹�������
				private void imageDelete() {
					String image = ClientDAO.getClientImage(ClientRootController.id);
					boolean delFlag = false;
					File imageFile = new File(imageDirectory.getAbsolutePath()+"\\"+image);
					if(imageFile.exists() && imageFile.isFile()) {
						delFlag = imageFile.delete();
						if(delFlag == false) {callAlert("�̹��� ���� ����:���Ž��� ���˹ٶ�");}
					}
				}
				
				
				
}
//�α׾ƿ� ��ư �ذ��ϱ�