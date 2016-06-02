package cn.edu.sau.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import cn.edu.sau.model.Book;
import cn.edu.sau.model.User;
import cn.edu.sau.util.StringUtil;

public class UserDao {
	// 用户登录验证
	public User login(Connection con, User user) throws Exception {
		User returnUser = null;
		String sql = "select * from t_user where userName=? and password=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, user.getUserName());
		pstmt.setString(2, user.getUserPassword());
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			returnUser = new User(rs.getInt("id"), rs.getString("userName"),
					rs.getString("password"), rs.getInt("role"),
					rs.getString("pname"));
		}
		return returnUser;
	}

	// 借书
	public int lendBook(Connection con, int userId, int bookId)
			throws Exception {
		String sql = "insert into t_lendbook values(null,?,?)";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, userId);
		pstmt.setInt(2, bookId);
		return pstmt.executeUpdate();
	}

	// 还书
	public int returnBook(Connection con, String id) throws Exception {
		String sql = "delete from t_lendbook where id=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, id);
		return pstmt.executeUpdate();
	}

	// 获取借阅列表
	public ResultSet lendBookList(Connection con, User user, Book book)
			throws Exception {
		StringBuffer sb = new StringBuffer("select * from t_lendbook ");
		if (user != null && user.getId() != -1) {
			sb.append(" and userid = " + user.getId());
		}
		if (book != null && book.getId() != -1) {
			sb.append(" and bookid =" + book.getId());
		}
		PreparedStatement pstmt = con.prepareStatement(sb.toString()
				.replaceFirst("and", "where"));
		return pstmt.executeQuery();
	}

	// 根据id获取用户名
	public String getPnameById(Connection con, int userId) throws Exception {
		String sql = "select * from t_user where id=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, userId);
		ResultSet rs = pstmt.executeQuery();
		if (rs.next())
			return rs.getString("pname");
		else
			return null;
	}
}
