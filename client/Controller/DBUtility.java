package client.Controller;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtility {
	public static Connection getConnection() {
		Connection con = null;
		
		try {
			//데이터 베이스 클래스 로드
			Class.forName("com.mysql.jdbc.Driver");
			
			//데이터 베이스 아이디 비밀번호 접속요청
			con = DriverManager.getConnection("jdbc:mysql://localhost/indianpokerdb","root","123456");
		} catch (Exception e) {
			System.out.println("DB연결 실패");
			e.printStackTrace();
			return null;
		}
		return con;
	}
	
}
