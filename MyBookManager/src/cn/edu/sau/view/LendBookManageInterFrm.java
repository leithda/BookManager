package cn.edu.sau.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import cn.edu.sau.dao.BookDao;
import cn.edu.sau.dao.UserDao;
import cn.edu.sau.model.Book;
import cn.edu.sau.model.User;
import cn.edu.sau.util.DbUtil;
import cn.edu.sau.util.StringUtil;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LendBookManageInterFrm extends JInternalFrame {
	DbUtil dbUtil = new DbUtil();
	UserDao userDao = new UserDao();
	BookDao bookDao = new BookDao();

	private JTextField userIdTxt;
	private JTextField bookIdTxt;
	private JTable table;
	private JTextField s_idTxt;
	private JTextField s_userNameTxt;
	private JTextField s_bookNameTxt;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LendBookManageInterFrm frame = new LendBookManageInterFrm();
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
	public LendBookManageInterFrm() {
		setIconifiable(true);
		setClosable(true);
		setTitle("\u56FE\u4E66\u501F\u9605\u7BA1\u7406");
		setBounds(100, 100, 691, 448);
		getContentPane().setLayout(null);

		init();
		fillTable(null, null);
	}

	// 填充借书记录表格
	private void fillTable(User user, Book book) {
		DefaultTableModel dtm = (DefaultTableModel) table.getModel();
		dtm.setRowCount(0);
		Connection con = null;
		try {
			con = dbUtil.getCon();
			ResultSet rs = userDao.lendBookList(con, user, book);
			while (rs.next()) {
				Vector v = new Vector();
				v.add(rs.getInt("id"));
				v.add(userDao.getPnameById(con, rs.getInt("userid")) + "("
						+ rs.getInt("userid") + ")");
				v.add(bookDao.getBookNameById(con, rs.getInt("bookid")) + "("
						+ rs.getInt("bookid") + ")");
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
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "\u641C\u7D22\u6761\u4EF6",
				TitledBorder.LEADING, TitledBorder.TOP, null,
				new Color(0, 0, 0)));
		panel.setToolTipText("\u641C\u7D22\u6761\u4EF6");
		panel.setBounds(10, 24, 655, 58);
		getContentPane().add(panel);
		panel.setLayout(null);

		JLabel lblid = new JLabel("\u7528\u6237Id\uFF1A");
		lblid.setBounds(10, 20, 70, 15);
		panel.add(lblid);

		userIdTxt = new JTextField();
		userIdTxt.setBounds(78, 17, 150, 21);
		panel.add(userIdTxt);
		userIdTxt.setColumns(10);

		JLabel lblid_1 = new JLabel("\u56FE\u4E66Id\r\n\uFF1A");
		lblid_1.setBounds(238, 20, 70, 15);
		panel.add(lblid_1);

		bookIdTxt = new JTextField();
		bookIdTxt.setBounds(318, 17, 155, 21);
		panel.add(bookIdTxt);
		bookIdTxt.setColumns(10);

		// 搜索按钮
		JButton jb_search = new JButton("\u641C\u7D22");
		jb_search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection con = null;
				try {
					con = dbUtil.getCon();
					String userId = userIdTxt.getText();
					String bookId = bookIdTxt.getText();
					Book book = new Book();
					User user = new User();
					if (StringUtil.isNotEmpty(bookId)) {
						book.setId(Integer.parseInt(bookId));
					}
					if (StringUtil.isNotEmpty(userId))
						user.setId(Integer.parseInt(userId));
					fillTable(user, book);
				} catch (Exception e1) {
					e1.printStackTrace();
				} finally {
					try {
						dbUtil.closeCon(con);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		jb_search.setBounds(510, 16, 93, 23);
		panel.add(jb_search);
		panel.setFocusTraversalPolicy(new FocusTraversalOnArray(
				new Component[] { lblid, userIdTxt, lblid_1, bookIdTxt,
						jb_search }));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 92, 655, 174);
		getContentPane().add(scrollPane);

		// 借书记录表格
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				int row = table.getSelectedRow();
				s_idTxt.setText(table.getValueAt(row, 0) + "");

				String userName = table.getValueAt(row, 1) + "";
				s_userNameTxt.setText(userName.replaceAll("\\(\\d*\\)", ""));
				String bookName = table.getValueAt(row, 2) + "";
				s_bookNameTxt.setText(bookName.replaceAll("\\(\\d*\\)", ""));

			}
		});
		table.setModel(new DefaultTableModel(new Object[][] { { null, null,
				null }, }, new String[] { "\u7F16\u53F7", "\u501F\u9605\u4EBA",
				"\u501F\u9605\u56FE\u4E66" }));
		table.getColumnModel().getColumn(0).setPreferredWidth(83);
		table.getColumnModel().getColumn(1).setPreferredWidth(162);
		table.getColumnModel().getColumn(2).setPreferredWidth(187);
		scrollPane.setViewportView(table);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "\u8868\u5355\u64CD\u4F5C",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(10, 276, 655, 101);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);

		JLabel label_2 = new JLabel("\u7F16\u53F7\uFF1A");
		label_2.setBounds(10, 33, 60, 15);
		panel_1.add(label_2);

		s_idTxt = new JTextField();
		s_idTxt.setBounds(91, 30, 66, 21);
		panel_1.add(s_idTxt);
		s_idTxt.setColumns(10);

		JLabel label_3 = new JLabel("\u501F\u9605\u4EBA\uFF1A");
		label_3.setBounds(195, 33, 65, 15);
		panel_1.add(label_3);

		s_userNameTxt = new JTextField();
		s_userNameTxt.setBounds(270, 30, 132, 21);
		panel_1.add(s_userNameTxt);
		s_userNameTxt.setColumns(10);

		JLabel label_4 = new JLabel("\u56FE\u4E66\u540D\u79F0\uFF1A");
		label_4.setBounds(432, 33, 71, 15);
		panel_1.add(label_4);

		s_bookNameTxt = new JTextField();
		s_bookNameTxt.setBounds(513, 30, 132, 21);
		panel_1.add(s_bookNameTxt);
		s_bookNameTxt.setColumns(10);

		// 还书按钮
		JButton jb_return = new JButton("\u5F52\u8FD8");
		jb_return.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection con = null;
				try {
					con = dbUtil.getCon();
					String id = s_idTxt.getText();
					String bookName = s_bookNameTxt.getText();
					if (StringUtil.isEmpty(bookName)) {
						JOptionPane.showMessageDialog(null, "请选择要归还书籍记录");
						return;
					}
					userDao.returnBook(con, id);
					bookDao.bookReturn(con, bookName);
					fillTable(null, null);
				} catch (Exception e1) {
					e1.printStackTrace();
				} finally {
					try {
						dbUtil.closeCon(con);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}

			}
		});
		jb_return.setBounds(10, 68, 635, 23);
		panel_1.add(jb_return);
	}
}
