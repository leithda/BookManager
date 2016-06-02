package cn.edu.sau.util;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * 数据库连接工具类
 * */
public class DbUtil {
	private String dbUrl = "jdbc:mysql://localhost:3306/db_book";

	private String dbUserName = "root";

	private String dbUserPassword = "123456";

	private String jdbcName = "com.mysql.jdbc.Driver";

	/**
	 * 获取数据库的连接
	 * 
	 * @return 数据库连接对象
	 * @throws Exception
	 */
	public Connection getCon() throws Exception {
		Class.forName(jdbcName);
		Connection con = DriverManager.getConnection(dbUrl, dbUserName,
				dbUserPassword);
		return con;
	}

	/**
	 * 关闭数据库连接
	 * 
	 * @param con
	 * @throws Exception
	 */
	public void closeCon(Connection con) throws Exception {
		if (con != null) {
			con.close();
		}
	}

	public static void main(String[] args) {
		DbUtil dbUtil = new DbUtil();
		try {
			dbUtil.getCon();
			System.out.println("数据库连接成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
