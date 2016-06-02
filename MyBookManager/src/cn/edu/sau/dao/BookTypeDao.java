package cn.edu.sau.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import cn.edu.sau.model.BookType;
import cn.edu.sau.util.StringUtil;

public class BookTypeDao {
	// 添加图书类别
	public int bookTypeAdd(Connection con, BookType bookType) throws Exception {
		String sql = "insert into t_booktype values(null,?,?)";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, bookType.getBookTypeName());
		pstmt.setString(2, bookType.getBookTypeDesc());
		return pstmt.executeUpdate();
	}

	// 获取图书类别列表
	public ResultSet bookTypeList(Connection con, BookType bookType)
			throws Exception {
		StringBuffer sb = new StringBuffer("select * from t_booktype");
		String bookTypeName = bookType.getBookTypeName();
		if (!StringUtil.isEmpty(bookTypeName)) {
			sb.append(" and bookTypeName like '%" + bookType.getBookTypeName()
					+ "%'");
		}
		PreparedStatement pstmt = con.prepareStatement(sb.toString()
				.replaceAll("and", "where"));
		return pstmt.executeQuery();
	}

	// 更改图书类别
	public int bookTypeModify(Connection con, BookType bookType)
			throws Exception {
		String sql = "update t_bookType set booktypename=? , booktypedesc=? where id=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, bookType.getBookTypeName());
		pstmt.setString(2, bookType.getBookTypeDesc());
		pstmt.setInt(3, bookType.getId());
		return pstmt.executeUpdate();
	}

	// 删除图书类别
	public int bookTypeDelete(Connection con, String id) throws Exception {
		String sql = "delete from t_bookType where id=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, id);
		return pstmt.executeUpdate();
	}

	public String bookTypeNameById(Connection con, String id) throws Exception {
		String sql = "select * from t_bookType where id=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, id);
		return pstmt.executeQuery().getString("bookTypeName");
	}
}
