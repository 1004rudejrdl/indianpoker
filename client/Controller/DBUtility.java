package client.Controller;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtility {
	public static Connection getConnection() {
		Connection con = null;
		
		try {
			//������ ���̽� Ŭ���� �ε�
			Class.forName("com.mysql.jdbc.Driver");
			
			//������ ���̽� ���̵� ��й�ȣ ���ӿ�û
			con = DriverManager.getConnection("jdbc:mysql://localhost/indianpokerdb","root","123456");
		} catch (Exception e) {
			System.out.println("DB���� ����");
			e.printStackTrace();
			return null;
		}
		return con;
	}
	
}
