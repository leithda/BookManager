package cn.edu.sau.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import cn.edu.sau.model.Book;
import cn.edu.sau.model.BookType;
import cn.edu.sau.util.StringUtil;

public class BookDao {
	public static int bookAdd(Connection con, Book book) throws Exception {
		String sql = "insert into t_book values(null,?,?,?,?,?)";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, book.getBookName());
		pstmt.setString(2, book.getBookAuthor());
		pstmt.setFloat(3, book.getBookPrice());
		pstmt.setString(4, book.getBookDesc());
		pstmt.setInt(5, book.getBookTypeId());
		return pstmt.executeUpdate();
	}

	// 获取图书列表
	public ResultSet bookList(Connection con, Book book) throws Exception {
		StringBuffer sb = new StringBuffer(
				"select * from t_book b,t_bookType bt where b.bookTypeId=bt.id");
		if (StringUtil.isNotEmpty(book.getBookName())) {
			sb.append(" and b.bookName like '%" + book.getBookName() + "%'");
		}
		if (StringUtil.isNotEmpty(book.getBookAuthor())) {
			sb.append(" and b.author like '%" + book.getBookAuthor() + "%'");
		}

		if (book.getBookTypeId() != -1) {
			sb.append(" and b.bookTypeId = " + book.getBookTypeId());
		}
		PreparedStatement pstmt = con.prepareStatement(sb.toString());
		return pstmt.executeQuery();
	}

	public int bookDelete(Connection con, String id) throws Exception {
		String sql = "delete from t_book where id=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, id);
		return pstmt.executeUpdate();
	}

	// 修改图书
	public int bookModify(Connection con, Book book) throws Exception {
		String sql = "update t_book set bookName=?,author=?,price=?,bookDesc=?,bookTypeId=? where id=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, book.getBookName());
		pstmt.setString(2, book.getBookAuthor());
		pstmt.setFloat(3, book.getBookPrice());
		pstmt.setString(4, book.getBookDesc());
		pstmt.setInt(5, book.getBookTypeId());
		pstmt.setInt(6, book.getId());
		return pstmt.executeUpdate();
	}

	// 获取当前类别是否有图书
	public boolean getBookByBookTypeId(Connection con, String bookTypeId)
			throws Exception {
		String sql = "select * from t_book where bookTypeId=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, bookTypeId);
		ResultSet rs = pstmt.executeQuery();
		return rs.next();
	}

	// 借书
	public int bookLend(Connection con, int bookId) throws Exception {
		String sql = "update t_book set state=? where id=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, 1);
		pstmt.setInt(2, bookId);
		return pstmt.executeUpdate();
	}

	// 还书
	public int bookReturn(Connection con, String bookName) throws Exception {
		String sql = "update t_book set state=? where bookname=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, 0);
		pstmt.setString(2, bookName);
		return pstmt.executeUpdate();
	}

	// 根据id获取图书名称
	public String getBookNameById(Connection con, int bookId) throws Exception {
		String sql = "select * from t_book where id=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, bookId);
		ResultSet rs = pstmt.executeQuery();
		if (rs.next())
			return rs.getString("bookname");
		else
			return null;
	}

}
