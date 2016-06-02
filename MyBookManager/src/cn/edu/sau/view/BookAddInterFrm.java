package cn.edu.sau.view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import cn.edu.sau.dao.BookDao;
import cn.edu.sau.dao.BookTypeDao;
import cn.edu.sau.model.Book;
import cn.edu.sau.model.BookType;
import cn.edu.sau.util.DbUtil;
import cn.edu.sau.util.StringUtil;
import javax.swing.ImageIcon;

public class BookAddInterFrm extends JInternalFrame {
	DbUtil dbUtil = new DbUtil();
	BookTypeDao bookTypeDao = new BookTypeDao();
	BookDao bookDao = new BookDao();

	private JTextField bookNameTxt;
	private JTextField bookAuthorTxt;
	private JTextField bookPriceTxt;
	private JComboBox jcb_bookType;
	private JTextArea bookDescTxt;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BookAddInterFrm frame = new BookAddInterFrm();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public BookAddInterFrm() {

		setClosable(true);
		setIconifiable(true);
		setTitle("\u56FE\u4E66\u6DFB\u52A0");
		setBounds(100, 100, 474, 443);
		setLocation(500, 100);
		getContentPane().setLayout(null);

		init();
		fillBookType();

	}

	private void init() {
		JLabel lb_bookName = new JLabel("\u56FE\u4E66\u540D\u79F0\uFF1A");
		lb_bookName.setBounds(29, 39, 72, 15);
		getContentPane().add(lb_bookName);

		bookNameTxt = new JTextField();
		bookNameTxt.setBounds(112, 36, 106, 21);
		getContentPane().add(bookNameTxt);
		bookNameTxt.setColumns(10);

		JLabel lb_bookAuthor = new JLabel("\u56FE\u4E66\u4F5C\u8005\uFF1A");
		lb_bookAuthor.setBounds(236, 39, 72, 15);
		getContentPane().add(lb_bookAuthor);

		bookAuthorTxt = new JTextField();
		bookAuthorTxt.setBounds(304, 36, 101, 21);
		getContentPane().add(bookAuthorTxt);
		bookAuthorTxt.setColumns(10);

		JLabel lb_bookPrice = new JLabel("\u56FE\u4E66\u4EF7\u683C\uFF1A");
		lb_bookPrice.setBounds(29, 100, 72, 15);
		getContentPane().add(lb_bookPrice);

		bookPriceTxt = new JTextField();
		bookPriceTxt.setBounds(112, 97, 106, 21);
		getContentPane().add(bookPriceTxt);
		bookPriceTxt.setColumns(10);

		JLabel lb_bookType = new JLabel("\u56FE\u4E66\u7C7B\u522B\uFF1A");
		lb_bookType.setBounds(236, 100, 74, 15);
		getContentPane().add(lb_bookType);

		jcb_bookType = new JComboBox();
		jcb_bookType.setBounds(304, 97, 101, 21);
		getContentPane().add(jcb_bookType);

		JLabel lb_bookDesc = new JLabel("\u56FE\u4E66\u7B80\u4ECB\uFF1A");
		lb_bookDesc.setBounds(28, 176, 73, 15);
		getContentPane().add(lb_bookDesc);

		bookDescTxt = new JTextArea();
		bookDescTxt.setBounds(112, 172, 297, 127);
		getContentPane().add(bookDescTxt);
		// 添加图书按钮
		JButton jb_bookAdd = new JButton("\u6DFB\u52A0");
		jb_bookAdd.setIcon(new ImageIcon(
				"D:\\java\\MyBookManager\\image\\add.png"));
		jb_bookAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String bookName = bookNameTxt.getText();
				String bookAuthor = bookAuthorTxt.getText();
				String bookPrice = bookPriceTxt.getText();
				String bookDesc = bookDescTxt.getText();
				if (StringUtil.isEmpty(bookName)) {
					JOptionPane.showMessageDialog(null, "图书名称不能为空");
					return;
				}
				if (StringUtil.isEmpty(bookAuthor)) {
					JOptionPane.showMessageDialog(null, "图书作者不能为空");
					return;
				}
				if (StringUtil.isEmpty(bookPrice)) {
					JOptionPane.showMessageDialog(null, "图书价格不能为空");
					return;
				}
				BookType bookType = (BookType) jcb_bookType.getSelectedItem();
				int bookTypeId = bookType.getId();
				Book book = new Book(-1, bookName, bookAuthor, Float
						.parseFloat(bookPrice), bookDesc, bookTypeId);
				Connection con = null;
				try {
					con = dbUtil.getCon();
					int addNum = BookDao.bookAdd(con, book);
					if (1 == addNum) {
						JOptionPane.showMessageDialog(null, "添加成功");
						resetValue();
					} else {
						JOptionPane.showMessageDialog(null, "添加失败");
					}
				} catch (Exception ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, "添加失败");
				} finally {
					try {
						con.close();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		jb_bookAdd.setBounds(115, 339, 93, 23);
		getContentPane().add(jb_bookAdd);
		// 重置按钮
		JButton jb_bookReset = new JButton("\u91CD\u7F6E");
		jb_bookReset.setIcon(new ImageIcon(
				"D:\\java\\MyBookManager\\image\\reset.png"));
		jb_bookReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetValue();
			}
		});
		jb_bookReset.setBounds(264, 339, 93, 23);
		getContentPane().add(jb_bookReset);
	}

	// 填充下拉菜单
	private void fillBookType() {
		Connection con = null;
		BookType bookType = null;
		try {
			con = dbUtil.getCon();
			ResultSet rs = bookTypeDao.bookTypeList(con, new BookType("", ""));
			while (rs.next()) {
				bookType = new BookType();
				bookType.setId(rs.getInt("id"));
				bookType.setBookTypeName(rs.getString("bookTypeName"));
				jcb_bookType.addItem(bookType);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private void resetValue() {
		bookNameTxt.setText("");
		bookAuthorTxt.setText("");
		bookPriceTxt.setText("");
		jcb_bookType.setSelectedIndex(0);
		bookDescTxt.setText("");
	}
}
