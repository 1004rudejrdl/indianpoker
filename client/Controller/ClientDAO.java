package client.Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.spi.DirStateFactory.Result;

import client.Model.ClientDB;
import server.Model.Client;

public class ClientDAO {
	public static List<ClientDB> dblist = new ArrayList<>();
	public static List<ClientDB> dbWinlist = new ArrayList<>();
	
	public static int insertClientData(ClientDB client) {
		int count = 0;
		StringBuffer insertClient = new StringBuffer();
		insertClient.append("insert into indianpoker ");
		insertClient.append("(id,password,name,resident,phoneNumber,gender,money,win,lose,imagepath) ");
		insertClient.append("values ");
		insertClient.append("(?,?,?,?,?,?,?,?,?,?) ");
		
		Connection con = null;
		
		PreparedStatement psmt = null;
		
		try {
			con = DBUtility.getConnection();
			psmt = con.prepareStatement(insertClient.toString());
			psmt.setString(1, client.getId());
			psmt.setString(2, client.getPassword());
			psmt.setString(3, client.getName());
			psmt.setString(4, client.getResident());
			psmt.setString(5, client.getPhoneNumber());
			psmt.setString(6, client.getGender());
			psmt.setString(7, client.getMoney());
			psmt.setString(8, client.getWin());
			psmt.setString(9, client.getLose());
			psmt.setString(10, client.getImagepath());
			count = psmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("���� ���д�");
			e.printStackTrace();
		} finally {
			try {
			if(psmt != null) {psmt.close();} 
			if(con != null) {con.close();} 
			}catch (SQLException e) {
				System.out.println("�ڿ� �ݱ� ����");
				e.printStackTrace();
			}
		}
		return count;
	}
	//������ ���̽����� ������ �������� �Լ�
	public static List<ClientDB> getClientTotaldata(){
		String selectClient = "select * from indianpoker";
		Connection con = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		try {
			con = DBUtility.getConnection();
			psmt = con.prepareStatement(selectClient);
			rs = psmt.executeQuery();
			if(rs == null) {
				System.out.println("������ ���� ����");
			}
			while(rs.next()) {
				ClientDB client = new ClientDB(
						rs.getString(1),
						rs.getString(2), 
						rs.getString(3),
						rs.getString(4), 
						rs.getString(5), 
						rs.getString(6), 
						rs.getString(7), 
						rs.getString(8), 
						rs.getString(9), 
						rs.getString(10));
				dblist.add(client);
			}
			
			
		} catch (SQLException e) {
			System.out.println("���� ���� ������ ���̽� ���Խ���");
			e.printStackTrace();
		}finally {
			
			try {
				if(psmt !=null) {psmt.close();}
				if(con !=null) {con.close();}
			} catch (SQLException e) {
				System.out.println("�ڿ� �ݱ� ����");
				e.printStackTrace();
			}
		}
		return dblist;
	}

	public static String getClientWin(String id){
		String returnId = null;
		String selectClient = "select * from indianpoker where id=?";
		Connection con = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		try {
			con = DBUtility.getConnection();
			psmt = con.prepareStatement(selectClient);
			psmt.setString(1, id);
			rs = psmt.executeQuery();
			if(rs == null) {
				System.out.println("������ ���� ����");
			}
			while(rs.next()) {
			
				returnId=rs.getString("win");
				System.out.println(rs.getString("win"));
						
			}
			
			
		} catch (SQLException e) {
			System.out.println("���� ���� ������ ���̽� ���Խ���");
			e.printStackTrace();
		}finally {
			
			try {
				if(psmt !=null) {psmt.close();}
				if(con !=null) {con.close();}
			} catch (SQLException e) {
				System.out.println("�ڿ� �ݱ� ����");
				e.printStackTrace();
			}
		}
		return returnId;
	}
	
	
	public static String getClientLose(String id){
		String returnId = null;
		String selectClient = "select * from indianpoker where id=?";
		Connection con = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		try {
			con = DBUtility.getConnection();
			psmt = con.prepareStatement(selectClient);
			psmt.setString(1, id);
			rs = psmt.executeQuery();
			if(rs == null) {
				System.out.println("������ ���� ����");
			}
			while(rs.next()) {
				
				returnId=rs.getString("lose");
				System.out.println(rs.getString("lose"));
				
			}
			
			
		} catch (SQLException e) {
			System.out.println("���� ���� ������ ���̽� ���Խ���");
			e.printStackTrace();
		}finally {
			
			try {
				if(psmt !=null) {psmt.close();}
				if(con !=null) {con.close();}
			} catch (SQLException e) {
				System.out.println("�ڿ� �ݱ� ����");
				e.printStackTrace();
			}
		}
		return returnId;
	}
	public static String getClientMoney(String id){
		String returnId = null;
		String selectClient = "select * from indianpoker where id=?";
		Connection con = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		try {
			con = DBUtility.getConnection();
			psmt = con.prepareStatement(selectClient);
			psmt.setString(1, id);
			rs = psmt.executeQuery();
			if(rs == null) {
				System.out.println("������ ���� ����");
			}
			while(rs.next()) {
				
				returnId=rs.getString("money");
				System.out.println(rs.getString("money"));
				
			}
			
			
		} catch (SQLException e) {
			System.out.println("���� ���� ������ ���̽� ���Խ���");
			e.printStackTrace();
		}finally {
			
			try {
				if(psmt !=null) {psmt.close();}
				if(con !=null) {con.close();}
			} catch (SQLException e) {
				System.out.println("�ڿ� �ݱ� ����");
				e.printStackTrace();
			}
		}
		return returnId;
	}
	public static String getClientImage(String id){
		String returnId = null;
		String selectClient = "select * from indianpoker where id=?";
		Connection con = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		try {
			con = DBUtility.getConnection();
			psmt = con.prepareStatement(selectClient);
			psmt.setString(1, id);
			rs = psmt.executeQuery();
			if(rs == null) {
				System.out.println("������ ���� ����");
			}
			while(rs.next()) {
				
				returnId=rs.getString("imagepath");
				System.out.println(rs.getString("imagepath"));
				
			}
			
			
		} catch (SQLException e) {
			System.out.println("���� ���� ������ ���̽� ���Խ���");
			e.printStackTrace();
		}finally {
			
			try {
				if(psmt !=null) {psmt.close();}
				if(con !=null) {con.close();}
			} catch (SQLException e) {
				System.out.println("�ڿ� �ݱ� ����");
				e.printStackTrace();
			}
		}
		return returnId;
	}
	
	
	
	public static int deleteClientData(String id) {
		int count = 0;
		String deleteClient="delete from indianpoker where id = ? ";
		Connection con = null;
		PreparedStatement psmt = null;
		
		try {
			con = DBUtility.getConnection();
			psmt = con.prepareStatement(deleteClient);
			psmt.setString(1, id);
			
			count = psmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("���� ����");
			e.printStackTrace();
		}finally {
			try {
				if(psmt !=null) {psmt.close();}	
				if(con !=null) {con.close();}	
				
			} catch (SQLException e) {
				System.out.println("�ڿ� �ݱ� ����");
				e.printStackTrace();
			}
		}
		return count;
		
	}
	//��й�ȣ ��ȭ��ȣ ���� , �����ݾ� ����, �׸� ����,�� �� ����
	//��й�ȣ ��ȭ��ȣ ���� 
	public static int updateClientData(ClientDB client) {
		int count = 0;
		
		StringBuffer updatePasswordTel = new StringBuffer();
		updatePasswordTel.append("update indianpoker set ");
		updatePasswordTel.append("password=?,phoneNumber=? where id=? ");
		Connection con = null;
		PreparedStatement psmt = null;
		try {
			con = DBUtility.getConnection();
			psmt = con.prepareStatement(updatePasswordTel.toString());
			psmt.setString(1, client.getPassword());
			psmt.setString(2, client.getPhoneNumber());
			psmt.setString(3, client.getId());
			
			count = psmt.executeUpdate();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		
		
		return count;
	}
	
	//�����ݾ� ����,
	public static int updateClientMoney(ClientDB client) {
		int count = 0;
		
		StringBuffer updatePasswordTel = new StringBuffer();
		updatePasswordTel.append("update indianpoker set ");
		updatePasswordTel.append("money=? where id=? ");
		Connection con = null;
		PreparedStatement psmt = null;
		try {
			con = DBUtility.getConnection();
			psmt = con.prepareStatement(updatePasswordTel.toString());
			psmt.setString(1, client.getMoney());
			psmt.setString(2, client.getId());
			
			count = psmt.executeUpdate();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		
		
		return count;
	}
	 //�׸� ����,
	public static int updateClientPicture(ClientDB client) {
		int count = 0;
		
		StringBuffer updatePasswordTel = new StringBuffer();
		updatePasswordTel.append("update indianpoker set ");
		updatePasswordTel.append("imagepath=? where id=? ");
		Connection con = null;
		PreparedStatement psmt = null;
		try {
			con = DBUtility.getConnection();
			psmt = con.prepareStatement(updatePasswordTel.toString());
			psmt.setString(1, client.getImagepath());
			psmt.setString(2, client.getId());
			
			count = psmt.executeUpdate();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		
		
		return count;
	}
	//�� 
	public static int updateClientWin(ClientDB client) {
		int count = 0;
		
		StringBuffer updatePasswordTel = new StringBuffer();
		updatePasswordTel.append("update indianpoker set ");
		updatePasswordTel.append("win=? where id=? ");
		Connection con = null;
		PreparedStatement psmt = null;
		try {
			con = DBUtility.getConnection();
			psmt = con.prepareStatement(updatePasswordTel.toString());
			psmt.setString(1, client.getWin());
			psmt.setString(2, client.getId());
			
			count = psmt.executeUpdate();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		
		
		return count;
	}
	
	// ��
	public static int updateClientLose(ClientDB client) {
		int count = 0;
		
		StringBuffer updatePasswordTel = new StringBuffer();
		updatePasswordTel.append("update indianpoker set ");
		updatePasswordTel.append("lose=? where id=? ");
		Connection con = null;
		PreparedStatement psmt = null;
		try {
			con = DBUtility.getConnection();
			psmt = con.prepareStatement(updatePasswordTel.toString());
			psmt.setString(1, client.getLose());
			psmt.setString(2, client.getId());
			
			count = psmt.executeUpdate();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		
		
		return count;
	}
	
}
