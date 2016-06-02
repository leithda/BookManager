package cn.edu.sau.model;

/**
 * 用户类
 * */
public class User {
	private int id;
	private String userName;
	private String userPassword;
	private int role = 0; // 默认为0->读者 、 1->图书管理员
	private String pname;

	public User() {
		this.id = -1;
	}

	public User(int id, String userName) {
		super();
		this.id = id;
		this.userName = userName;
	}

	public User(String userName, String userPassword) {
		super();
		this.userName = userName;
		this.userPassword = userPassword;
	}

	public User(int id, String userName, String userPassword, int role,
			String pname) {
		super();
		this.id = id;
		this.userName = userName;
		this.userPassword = userPassword;
		this.role = role;
		this.pname = pname;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

}
