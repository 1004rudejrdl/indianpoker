package client.Model;

public class ClientDB {
	private String id;
	private String password;
	private String name;
	private String resident;
	private String phoneNumber;
	private String gender;
	private String money;
	private String win;
	private String lose;
	private String imagepath;
	
	
	public ClientDB(String id, String money) {
		super();
		this.id = id;
		this.money = money;
		this.win = money;
		this.lose = money;
		this.imagepath = money;
		
	}
	public ClientDB(String id, String password, String phoneNumber) {
		super();
		this.id = id;
		this.password = password;
		this.phoneNumber = phoneNumber;
	}
	
	
	public ClientDB(String imagepath) {
		super();
		this.imagepath = imagepath;
	}
	
	
	public ClientDB(String id, String password, String name, String resident, String phoneNumber, String gender,
			String money, String win, String lose) {
		super();
		this.id = id;
		this.password = password;
		this.name = name;
		this.resident = resident;
		this.phoneNumber = phoneNumber;
		this.gender = gender;
		this.money = money;
		this.win = win;
		this.lose = lose;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getResident() {
		return resident;
	}
	public void setResident(String resident) {
		this.resident = resident;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getWin() {
		return win;
	}
	public void setWin(String win) {
		this.win = win;
	}
	public String getLose() {
		return lose;
	}
	public void setLose(String lose) {
		this.lose = lose;
	}
	public String getImagepath() {
		return imagepath;
	}
	public void setImagepath(String imagepath) {
		this.imagepath = imagepath;
	}
	public ClientDB(String id, String password, String name, String resident, String phoneNumber, String gender,
			String money, String win, String lose, String imagepath) {
		super();
		this.id = id;
		this.password = password;
		this.name = name;
		this.resident = resident;
		this.phoneNumber = phoneNumber;
		this.gender = gender;
		this.money = money;
		this.win = win;
		this.lose = lose;
		this.imagepath = imagepath;
	}
	
}
