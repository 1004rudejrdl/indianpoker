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
		//1 내 정보창을 들어가는 이미지 뷰
		mainInformation.setOnMouseClicked(e->{mouseClickMainInformation();});
		//2 설명을 보여주는 버튼
		mainExplanation.setOnAction(e->{explanationBtn();});
		//3 내가 가진 돈의 금액을 보여주는 것
		showMyMoney();
		//4로그아웃으로 로그인 화면으로 되돌아 간다.
		mainLogout.setOnAction(e->{logoutBtn();});
		//5종료 버튼으로 끝난다
		mainExit.setOnAction(e->{Platform.exit();});
		//6 내 승률을 파이 차트로 보여준다
		winOrLosePieChart();
		//7게임 시작 이미지
		mainGameStart.setOnMouseClicked(e->{gameStartImage(mainstage);});
		//8돈이 없을때 돈을 충전해 줄 수 있는 이미지
		mainMoneyCharge.setOnMouseClicked(e->{chargeMoney();});
//		if(display==1) {
//			gameStartImage();
//			Platform.exit();
//		}
		
		
	}
	


	//1 내 정보창을 들어가는 이미지 뷰
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
				
				System.out.println(image+"       이미지다");
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
			infTextMoney.setText("보유 머니 : 		"+ClientRootController.money);
			infGiveUp.setOnAction(e->{
				int count = ClientDAO.deleteClientData(ClientRootController.id);
				if(count!=0) {
					System.out.println("삭제 완료");
					ClientRootController.callAlert("회원 탈퇴 요청 : 회원 탈퇴 처리 완료");
					logoutBtn();
				}
			});
			infChange.setOnAction(e->{
				if(infTextPassword.getText().trim().equals(infTextPasswordRegister.getText().trim())) {
					ClientDB client = new ClientDB(ClientRootController.id, infTextPassword.getText(), infTel1.getText()+infTel2.getText()+infTel3.getText());
					int count = ClientDAO.updateClientData(client);
					if(count!=0) {
						ClientRootController.callAlert("수정 완료 : 수정이 완료 되었습니다.");
					}
				}else {
					ClientRootController.callAlert("비밀번호 오류 : 비밀번호와 확인란이 같은지 확인하시오");
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
	
	


	//2 설명을 보여주는 버튼
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
		//데이터 베이스에서 돈을 가져와서 넣어줘야한다.**************
		//3 내가 가진 돈의 금액을 보여주는 것
		private void showMyMoney() {
			String ClientMoney = ClientDAO.getClientMoney(ClientRootController.id);
			
			
			mainMyMoney.setText("금액 : " + ClientMoney);
			
		}
		
		//4로그아웃으로 로그인 화면으로 되돌아 간다.
		//***************************************************해결하기
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

		//6 내 승률을 파이 차트로 보여준다
		//데이터 베이스에서 승률을 가져와서 차트에 넣어준다************
		private void winOrLosePieChart() {
			String wins = ClientDAO.getClientWin(ClientRootController.id);
			String lose = ClientDAO.getClientLose(ClientRootController.id);
			System.out.println(wins + "승패" + lose);
			int i=Integer.parseInt(wins);
			int j=Integer.parseInt(lose);
			if( i>0 || j>0) {
				memGameChart.setText("");
				Map<String,Integer> hashMap = new HashMap<>();
				hashMap.put("승", i);
				hashMap.put("패", j);
				
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
		
		//7게임 시작 이미지
		public void gameStartImage(Stage stage) {
			String takeMoney = ClientDAO.getClientMoney(ClientRootController.id);
			int mymoney=Integer.parseInt(takeMoney);
			if(mymoney<2000) {
				ClientRootController.callAlert("금액 부족 : 금액을 충전하고 오세요");
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
		
		//8돈이 없을때 돈을 충전해 줄 수 있는 이미지
		//데이터 베이스에서 돈을 가져와서 넣어줘야한다.**************
		private void chargeMoney() {
			String ClientMoney = ClientDAO.getClientMoney(ClientRootController.id);
			money = Integer.parseInt(ClientMoney);
			
			if(money>2000) {
				callAlert("금액이 충분합니다. : 금액이 2000원 이하부터 충전이 가능합니다.");
			}else {
				
				ClientRootController.money="10000";
				mainMyMoney.setText("금액 : " + ClientRootController.money);
				ClientDB client = new ClientDB(ClientRootController.id, ClientRootController.money);
				ClientDAO.updateClientMoney(client);
				callAlert("금액이 충전되었습니다. : 금액 10000원 충전");
			}
			
		}
		//기타 알림창 "오류정보 : 값을 제대로 입력해 주세요"  중간에 꼭 :을 적을것
				public static void callAlert(String contentText) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("알림");
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
					infImageView.setImage(new Image(localURL,false));//바탕화면 배경색으로 가져오는게 아니다.?진짜 본체로 가져온다
					fileName = selectFile.getName();//선택된 파일명을 준다  fileName은 반드시 이미지 파일을 선택했을때 값을 유지한다.
					//callAlert("선택된 이미지 : " + fileName);
					
				}

				//기타 이미지 C:/images/student123412341234_선택된 파일명으로 저장한다.
				private void imageSave() {
					if(!imageDirectory.exists()) {
						imageDirectory.mkdir();   //디렉토리가 생성이 안되어 있으면 폴더를 만든다.
					}
					FileInputStream fis=null;
					BufferedInputStream bis =null;
					FileOutputStream fos=null;
					BufferedOutputStream bos = null;
					//선택된 이미지를 c:/images/  선택된 이미지 이름명으로 저장한다
					try {
						fis = new FileInputStream(selectFile);
						bis = new BufferedInputStream(fis);
						
						//FileChooser에서 선택된 파일명이 C:/kkk/ppp/jjj/name.jpg
						//selecFile.getName()  -> name.jpg 만 가져온다
						//새로운 파일 명을 규정 하는데 -> student+12345678912_name.jpg
						//imageDirectory.getAbsolutePath()+"\\"+fileName 
						//-> C:/images/student+12345678912_name.jpg 이름으로 저장한다
						//C:/kkk/ppp/jjj/name.jpg 읽어서   C:/images/student+12345678912_name.jpg여기로 저장한다
						fileName ="indianpoker"+ System.currentTimeMillis()+"_"+selectFile.getName();
						fos = new FileOutputStream(imageDirectory.getAbsolutePath()+"\\"+fileName);
						bos = new BufferedOutputStream(fos);
						int data=-1;
						while((data = bis.read()) != -1) {
							bos.write(data);
							bos.flush();
						}
						
					} catch (Exception e) {
						callAlert("이미지 저장 에러 : c/images/저장파일 에러 점검 바람");
					}finally {
						try {
						if(fis != null) {fis.close();}
						if(bis != null) {bis.close();}
						if(fos != null) {fos.close();}
						if(bos != null) {bos.close();}
						} catch (IOException e) {}
					}
						
				}
				//이미지삭제
				private void imageDelete() {
					String image = ClientDAO.getClientImage(ClientRootController.id);
					boolean delFlag = false;
					File imageFile = new File(imageDirectory.getAbsolutePath()+"\\"+image);
					if(imageFile.exists() && imageFile.isFile()) {
						delFlag = imageFile.delete();
						if(delFlag == false) {callAlert("이미지 제거 실패:제거실패 점검바람");}
					}
				}
				
				
				
}
//로그아웃 버튼 해결하기