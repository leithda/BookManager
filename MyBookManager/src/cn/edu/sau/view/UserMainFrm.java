package cn.edu.sau.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import cn.edu.sau.dao.BookDao;
import cn.edu.sau.dao.BookTypeDao;
import cn.edu.sau.dao.UserDao;
import cn.edu.sau.model.Book;
import cn.edu.sau.model.BookType;
import cn.edu.sau.model.User;
import cn.edu.sau.util.DbUtil;
import java.awt.Window.Type;
import javax.swing.ImageIcon;

public class UserMainFrm extends JFrame {
	private User user;
	DbUtil dbUtil = new DbUtil();
	BookDao bookDao = new BookDao();
	BookTypeDao bookTypeDao = new BookTypeDao();
	UserDao userDao = new UserDao();

	private JPanel contentPane;
	private JPanel panel;
	private JTextField bookNameTxt;
	private JTextField bookAuthorTxt;
	private JComboBox<BookType> jcb_bookType;
	private JButton jb_search;
	private JTable table;
	private JTextField s_idTxt;
	private JButton jb_lendBook;
	private JTextField s_bookNameTxt;

	/**
	 * Launch the application.
	 */
	/*
	 * public static void main(String[] args) { EventQueue.invokeLater(new
	 * Runnable() { public void run() { try {
	 * 
	 * UserMainFrm frame = new UserMainFrm(); frame.setVisible(true); } catch
	 * (Exception e) { e.printStackTrace(); } } }); }
	 */

	/**
	 * Create the frame.
	 */
	public UserMainFrm(User user) {

		setTitle("\u501F\u9605\u7CFB\u7EDF");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 835, 445);
		this.user = user;
		JOptionPane.showMessageDialog(null, "欢迎" + user.getPname() + "使用我们的系统");

		init();
		fillTable(new Book());
		this.fillBookType("search");
		this.fillBookType("modify");
	}

	// 填充下拉菜单
	private void fillBookType(String type) {
		Connection con = null;
		BookType bookType = null;
		try {
			con = dbUtil.getCon();
			ResultSet rs = bookTypeDao.bookTypeList(con, new BookType());
			if ("search".equals(type)) {
				bookType = new BookType();
				bookType.setBookTypeName("请选择...");
				bookType.setId(-1);
				this.jcb_bookType.addItem(bookType);
			}
			while (rs.next()) {
				bookType = new BookType();
				bookType.setId(rs.getInt("id"));
				bookType.setBookTypeName(rs.getString("bookTypeName"));
				if ("search".equals(type)) {
					this.jcb_bookType.addItem(bookType);
				} else if ("modify".equals(type)) {
					this.jcb_bookType.addItem(bookType);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// 填充表格
	private void fillTable(Book book) {
		DefaultTableModel dtm = (DefaultTableModel) table.getModel();
		dtm.setRowCount(0);
		Connection con = null;
		try {
			con = dbUtil.getCon();
			ResultSet rs = bookDao.bookList(con, book);
			while (rs.next()) {
				Vector v = new Vector();
				v.add(rs.getInt("id"));
				v.add(rs.getString("bookName"));
				v.add(rs.getString("author"));
				v.add(rs.getFloat("price"));
				v.add(rs.getString("bookDesc"));
				v.add(rs.getString("bookTypeName"));
				String state = rs.getInt("state") == 1 ? "借阅中" : "正常";
				v.add(state);
				dtm.addRow(v);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void init() {
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		panel = new JPanel();
		panel.setLayout(null);
		panel.setToolTipText("\u641C\u7D22\u6761\u4EF6");
		panel.setBorder(new TitledBorder(null, "\u641C\u7D22\u6761\u4EF6",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(43, 28, 732, 68);
		contentPane.add(panel);

		JLabel label = new JLabel("\u56FE\u4E66\u7C7B\u522B\uFF1A\r\n");
		label.setBounds(410, 29, 72, 15);
		panel.add(label);

		JLabel label_1 = new JLabel("\u56FE\u4E66\u4F5C\u8005\uFF1A");
		label_1.setBounds(209, 29, 72, 15);
		panel.add(label_1);

		JLabel label_2 = new JLabel("\u56FE\u4E66\u540D\u79F0\uFF1A");
		label_2.setBounds(10, 29, 67, 15);
		panel.add(label_2);

		bookNameTxt = new JTextField();
		bookNameTxt.setColumns(10);
		bookNameTxt.setBounds(84, 26, 114, 21);
		panel.add(bookNameTxt);

		bookAuthorTxt = new JTextField();
		bookAuthorTxt.setColumns(10);
		bookAuthorTxt.setBounds(285, 26, 114, 21);
		panel.add(bookAuthorTxt);
		// 搜索按钮
		jb_search = new JButton("\u67E5\u8BE2");
		jb_search.setIcon(new ImageIcon(
				"D:\\java\\MyBookManager\\image\\search.png"));
		jb_search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Book book = new Book();
				String bookName = bookNameTxt.getText();
				String bookAuthor = bookAuthorTxt.getText();

				BookType bookType = (BookType) jcb_bookType.getSelectedItem();
				int bookTypeId = bookType.getId();

				book.setBookName(bookName);
				book.setBookAuthor(bookAuthor);
				book.setBookTypeId(bookTypeId);
				fillTable(book);
			}
		});
		jb_search.setBounds(629, 25, 93, 23);
		panel.add(jb_search);

		jcb_bookType = new JComboBox<BookType>();
		jcb_bookType.setBounds(472, 26, 131, 21);
		panel.add(jcb_bookType);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setToolTipText("");
		scrollPane.setBounds(43, 120, 732, 178);
		contentPane.add(scrollPane);

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				int row = table.getSelectedRow();
				s_idTxt.setText((Integer) table.getValueAt(row, 0) + "");
				s_bookNameTxt.setText(table.getValueAt(row, 1) + "");
			}
		});
		table.setModel(new DefaultTableModel(new Object[][] { { null, null,
				null, null, null, null, null }, }, new String[] {
				"\u56FE\u4E66\u7F16\u53F7", "\u56FE\u4E66\u540D\u79F0",
				"\u56FE\u4E66\u4F5C\u8005", "\u56FE\u4E66\u4EF7\u683C",
				"\u56FE\u4E66\u63CF\u8FF0", "\u56FE\u4E66\u7C7B\u522B",
				"\u56FE\u4E66\u72B6\u6001" }));
		table.setToolTipText("\u56FE\u4E66");
		table.setSurrendersFocusOnKeystroke(true);
		scrollPane.setViewportView(table);
		// 借书按钮
		jb_lendBook = new JButton("\u501F\u4E66");
		jb_lendBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				String state = table.getValueAt(row, 6) + "";
				if ("借阅中".equals(state)) {
					JOptionPane.showMessageDialog(null, "该书已经被借走，请选择其他书籍");
				} else {
					Connection con = null;

					try {
						con = dbUtil.getCon();
						int bookId = (Integer) table.getValueAt(row, 0);

						int ret = userDao.lendBook(con, user.getId(), bookId);
						if (1 == ret) {
							fillTable(new Book());
							JOptionPane.showMessageDialog(null, "借阅成功");
							bookDao.bookLend(con, bookId);
						} else
							JOptionPane.showMessageDialog(null, "借阅失败");
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, "借阅失败");
						e1.printStackTrace();
					} finally {
						try {
							dbUtil.closeCon(con);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		});
		jb_lendBook.setBounds(615, 322, 160, 23);
		contentPane.add(jb_lendBook);

		JLabel label_3 = new JLabel("\u56FE\u4E66\u7F16\u53F7\uFF1A");
		label_3.setBounds(43, 326, 66, 15);
		contentPane.add(label_3);

		s_idTxt = new JTextField();
		s_idTxt.setEnabled(false);
		s_idTxt.setBounds(119, 323, 135, 21);
		contentPane.add(s_idTxt);
		s_idTxt.setColumns(10);

		JLabel label_4 = new JLabel("\u56FE\u4E66\u540D\u79F0\uFF1A");
		label_4.setBounds(340, 326, 66, 15);
		contentPane.add(label_4);

		s_bookNameTxt = new JTextField();
		s_bookNameTxt.setEnabled(false);
		s_bookNameTxt.setColumns(10);
		s_bookNameTxt.setBounds(427, 323, 135, 21);
		contentPane.add(s_bookNameTxt);
	}
}
