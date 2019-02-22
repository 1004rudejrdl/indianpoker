package client.Controller;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ClientserverConnect implements Runnable{
	private Socket socket;
	@Override
	public void run() {
		socket = new Socket();
		try {
			socket.connect(new InetSocketAddress("192.168.0.195", 5004));
			System.out.println("연결완료");
			
			
			
		} catch (IOException e) {
			if(socket.isClosed()) {
				try {
					socket.close();
				} catch (IOException e1) {	}
			}
			
		}
	}

}
