package server.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class GameUseClient extends Thread{
	public Socket socket;
	public char no;
	public BufferedReader br;
	public PrintWriter pw;
	public GameUseClient other;
	public GameUseClient me;
	public int number,number2;
	private char order1;
	private char order2;
	
	public GameUseClient(Socket socket,char no) throws IOException {
		super();
		this.socket = socket;
		this.no= no;
		
		//나의 소켓 인풋스트림
		InputStream is = socket.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		br = new BufferedReader(isr);
		
		//상대방 소켓아웃풋 스트림
		OutputStream os = socket.getOutputStream();
		pw = new PrintWriter(os,true);
		
		pw.println("START "+no);
		//pw.println("START" +no);
		
		
	}



	@Override
	public void run() {
		try {
			pw.println("GAMES" + "경기자 연결 완료");
			
				while(true) {
					
					String client = br.readLine();
					if(client.startsWith("CLIENT")) {
						System.out.println(client);
						String message = client.substring(7);
						
						other.pw.println("IDSEN "+message);
						
					}else
					if(client.startsWith("MESSAGE")) {
						System.out.println(client);
						String message = client.substring(8);
						me.pw.println("MMESS "+message);
						other.pw.println("YMESS "+message);
						
					}else
					if(client.startsWith("START")) {
						System.out.println("스타트");
						System.out.println(client.charAt(6));
						if(client.charAt(6)=='1') {
							
							pw.println("START "+'1');
						}else {
							
							pw.println("START "+'2');
						}
						pw.println("GAMES");
						//ServerRootController.mainThread.start();
						System.out.println("마지막");
					//number = (int)(Math.random()*(10-1+1))+1;
					//number2 = (int)(Math.random()*(10-1+1))+1;
					/*int order = (int)(Math.random()*(2-1+1))+1;
					if(order==1) {
						order1 = '1';
						order2 = '2';
						
					}else {
						order1 = '2';
						order2 = '1';
					}*/
					
//						pw.println("START"+"시작하였습니다.");
//						other.pw.println("START"+"시작하였습니다.");
						//pw.println("FIRST"+"선택해 주세요"+number);
						//other.pw.println("SECOT");
					
						//pw.println("START"+"시작하였습니다.");
						//other.pw.println("START"+"시작하였습니다.");
						//other.pw.println("FIRSA"+"선택해 주세요");
						//other.pw.println("SECON"+number);
						
//						pw.println("FIRST"+"선택해 주세요"+number);
//						other.pw.println("SECON"+number2);
						
						/*String client = br.readLine();
						if(client.startsWith("FIRST")) {
							String str = client.substring(7);
							int i=Integer.parseInt(str);
							number=i;
						}else if(client.startsWith("SECON")) {
							String str = client.substring(7);
							int i=Integer.parseInt(str);
							number2=i;
						}*/
						
					
					//System.out.println(number+"---"+number2);
					
					
					
						
					}/*else
						if(client.startsWith("SECON")) {
							String str = client.substring(6);
							number2 = Integer.parseInt(str);
							pw.println("YTURN"+ "기다려 주세요");
							System.out.println(number2+"1번");
						}else if(client.startsWith("FIRST")) {
							String str = client.substring(6);
							number2 = Integer.parseInt(str);
							System.out.println(number2+"2번");
							pw.println("MTURN"+ "선택해 주십시오");
							
						}*/else if(client.startsWith("RETURN")) {
							System.out.println(client);
							String number = client.substring(7);
								other.pw.println("SAMET"+number);
								System.out.println(number);
						}else if(client.startsWith("BATING")) {
							
							String str = client.substring(7);
							System.out.println(str);
							other.pw.println("MTURN"+str);
							
						}else if(client.startsWith("OPEN")){
							System.out.println(client);
							String number = client.substring(5);
								other.pw.println("SAMEC"+number);
								
								System.out.println(number);
						}else if(client.equals("GIVEUP")){
							me.pw.println("GOVER"+"2포기 하셨습니다.");
							other.pw.println("GOVER"+"1상대방이 포기 하였습니다.");
							
						}
						/*switch (client) {
						case "BATING" : 
							pw.println("YTURN"+ "기다려 주세요");
							other.pw.println("MTURN"+ "상대방이 배팅했습니다.");
							break;
							
						case "OPEN" : 
							if(number==number2) {
								other.pw.println("SAMEC"+"비겼습니다."+number);
								pw.println("SAMEC"+"비겼습니다."+number2);
							}
							else if(number>number2) {
								other.pw.println("CNTIU"+"당신의 승리."+number);
								pw.println("CNTIU"+"당신의 패배."+number2);
							}else {
								other.pw.println("CNTIU"+"당신의 패배."+number);
								pw.println("CNTIU"+"당신의 승리."+number2);
								
							}
							
							
							
							break;
						case "GIVEUP" : 
							pw.println("GOVER"+"포기 하셨습니다.");
							other.pw.println("GOVER"+"상대방이 포기 하였습니다.");
							break;
							
							
						}*/
					
					
				
			}

		}catch(Exception e) {

		}finally {
			if(!socket.isClosed()) {try {
				socket.close();
			} catch (IOException e1) {
				
			}}
		}
		
	}

}
