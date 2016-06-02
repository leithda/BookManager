package cn.edu.sau.view;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import cn.edu.sau.dao.BookDao;
import cn.edu.sau.dao.BookTypeDao;
import cn.edu.sau.model.Book;
import cn.edu.sau.model.BookType;
import cn.edu.sau.util.DbUtil;
import cn.edu.sau.util.StringUtil;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;

public class BookManageInterFrm extends JInternalFrame {
	DbUtil dbUtil = new DbUtil();
	BookTypeDao bookTypeDao = new BookTypeDao();
	BookDao bookDao = new BookDao();

	private JTextField s_bookNameTxt;
	private JTextField s_bookAuthorTxt;
	private JTable bookTable;
	private JTextField idTxt;
	private JTextField bookNameTxt;
	private JTextField bookAuthorTxt;
	private JTextField bookPriceTxt;
	private JTextArea bookDescTxt;
	private JScrollPane scrollPane;
	private JComboBox<BookType> s_jcbBookType;
	private JComboBox<BookType> jcb_bookType;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BookManageInterFrm frame = new BookManageInterFrm();
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
	public BookManageInterFrm() {
		setClosable(true);
		setIconifiable(true);
		setTitle("\u56FE\u4E66\u7BA1\u7406");
		setBounds(100, 100, 812, 618);
		setLocation(300, 20);
		getContentPane().setLayout(null);

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
				this.s_jcbBookType.addItem(bookType);
			}
			while (rs.next()) {
				bookType = new BookType();
				bookType.setId(rs.getInt("id"));
				bookType.setBookTypeName(rs.getString("bookTypeName"));
				if ("search".equals(type)) {
					this.s_jcbBookType.addItem(bookType);
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
		DefaultTableModel dtm = (DefaultTableModel) bookTable.getModel();
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
		scrollPane = new JScrollPane();
		scrollPane.setToolTipText("");
		scrollPane.setBounds(30, 91, 732, 178);
		getContentPane().add(scrollPane);

		bookTable = new JTable();
		bookTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				int row = bookTable.getSelectedRow();
				idTxt.setText((Integer) bookTable.getValueAt(row, 0) + "");
				bookNameTxt.setText((String) bookTable.getValueAt(row, 1));
				bookAuthorTxt.setText((String) bookTable.getValueAt(row, 2));
				bookPriceTxt.setText((Float) bookTable.getValueAt(row, 3) + "");
				bookDescTxt.setText(bookTable.getValueAt(row, 4) + "");
				String bookTypeName = (String) bookTable.getValueAt(row, 5);
				int n = jcb_bookType.getItemCount();
				for (int i = 0; i < n; i++) {
					BookType item = (BookType) jcb_bookType.getItemAt(i);
					if (item.getBookTypeName().equals(bookTypeName)) {
						jcb_bookType.setSelectedIndex(i);
					}
				}
			}
		});
		bookTable.setToolTipText("\u56FE\u4E66");
		bookTable.setSurrendersFocusOnKeystroke(true);
		bookTable.setModel(new DefaultTableModel(new Object[][] { { null, null,
				null, null, null, null }, }, new String[] {
				"\u56FE\u4E66\u7F16\u53F7", "\u56FE\u4E66\u540D\u79F0",
				"\u56FE\u4E66\u4F5C\u8005", "\u56FE\u4E66\u4EF7\u683C",
				"\u56FE\u4E66\u63CF\u8FF0", "\u56FE\u4E66\u7C7B\u522B" }) {
			boolean[] columnEditables = new boolean[] { true, false, false,
					false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		bookTable.getColumnModel().getColumn(1).setPreferredWidth(100);
		bookTable.getColumnModel().getColumn(2).setPreferredWidth(92);
		bookTable.getColumnModel().getColumn(3).setPreferredWidth(94);
		bookTable.getColumnModel().getColumn(4).setPreferredWidth(164);
		bookTable.getColumnModel().getColumn(5).setPreferredWidth(97);
		scrollPane.setViewportView(bookTable);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "\u641C\u7D22\u6761\u4EF6",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setToolTipText("\u641C\u7D22\u6761\u4EF6");
		panel.setBounds(31, 14, 732, 68);
		getContentPane().add(panel);
		panel.setLayout(null);

		JLabel label = new JLabel("\u56FE\u4E66\u7C7B\u522B\uFF1A\r\n");
		label.setBounds(410, 29, 72, 15);
		panel.add(label);

		JLabel lblNewLabel_1 = new JLabel("\u56FE\u4E66\u4F5C\u8005\uFF1A");
		lblNewLabel_1.setBounds(209, 29, 72, 15);
		panel.add(lblNewLabel_1);

		JLabel lblNewLabel = new JLabel("\u56FE\u4E66\u540D\u79F0\uFF1A");
		lblNewLabel.setBounds(10, 29, 67, 15);
		panel.add(lblNewLabel);

		s_bookNameTxt = new JTextField();
		s_bookNameTxt.setBounds(84, 26, 114, 21);
		panel.add(s_bookNameTxt);
		s_bookNameTxt.setColumns(10);

		s_bookAuthorTxt = new JTextField();
		s_bookAuthorTxt.setBounds(285, 26, 114, 21);
		panel.add(s_bookAuthorTxt);
		s_bookAuthorTxt.setColumns(10);
		// 搜索按钮
		JButton jb_search = new JButton("\u67E5\u8BE2");
		jb_search.setIcon(new ImageIcon(
				"D:\\java\\MyBookManager\\image\\search.png"));
		jb_search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Book book = new Book();
				String bookName = s_bookNameTxt.getText();
				String bookAuthor = s_bookAuthorTxt.getText();

				BookType bookType = (BookType) s_jcbBookType.getSelectedItem();
				int bookTypeId = bookType.getId();

				book.setBookName(bookName);
				book.setBookAuthor(bookAuthor);
				book.setBookTypeId(bookTypeId);
				fillTable(book);
			}
		});
		jb_search.setBounds(629, 25, 93, 23);
		panel.add(jb_search);

		s_jcbBookType = new JComboBox<BookType>();
		s_jcbBookType.setBounds(472, 26, 131, 21);
		panel.add(s_jcbBookType);
		panel.setFocusTraversalPolicy(new FocusTraversalOnArray(
				new Component[] { label, lblNewLabel_1, lblNewLabel,
						s_bookNameTxt, s_bookAuthorTxt, jb_search,
						s_jcbBookType }));

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "\u8868\u5355\u64CD\u4F5C",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setToolTipText("\u8868\u5355\u64CD\u4F5C");
		panel_1.setBounds(32, 283, 732, 274);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);

		JLabel label_1 = new JLabel("\u7F16\u53F7\uFF1A");
		label_1.setBounds(10, 41, 57, 15);
		panel_1.add(label_1);

		idTxt = new JTextField();
		idTxt.setEditable(false);
		idTxt.setBounds(96, 38, 118, 21);
		panel_1.add(idTxt);
		idTxt.setColumns(10);

		JLabel label_2 = new JLabel("\u56FE\u4E66\u540D\u79F0\uFF1A");
		label_2.setBounds(10, 81, 76, 15);
		panel_1.add(label_2);

		bookNameTxt = new JTextField();
		bookNameTxt.setBounds(96, 78, 118, 21);
		panel_1.add(bookNameTxt);
		bookNameTxt.setColumns(10);

		JLabel label_3 = new JLabel("\u56FE\u4E66\u4F5C\u8005\uFF1A");
		label_3.setBounds(224, 81, 65, 15);
		panel_1.add(label_3);

		bookAuthorTxt = new JTextField();
		bookAuthorTxt.setColumns(10);
		bookAuthorTxt.setBounds(299, 78, 118, 21);
		panel_1.add(bookAuthorTxt);

		JLabel label_4 = new JLabel("\u56FE\u4E66\u4EF7\u683C\uFF1A");
		label_4.setBounds(10, 160, 76, 15);
		panel_1.add(label_4);

		bookPriceTxt = new JTextField();
		bookPriceTxt.setBounds(96, 157, 117, 21);
		panel_1.add(bookPriceTxt);
		bookPriceTxt.setColumns(10);

		JLabel label_5 = new JLabel("\u56FE\u4E66\u7C7B\u522B\uFF1A");
		label_5.setBounds(224, 160, 65, 15);
		panel_1.add(label_5);

		jcb_bookType = new JComboBox<BookType>();
		jcb_bookType.setBounds(299, 157, 118, 21);
		panel_1.add(jcb_bookType);

		JLabel label_6 = new JLabel("\u56FE\u4E66\u63CF\u8FF0\uFF1A");
		label_6.setBounds(439, 81, 65, 15);
		panel_1.add(label_6);

		bookDescTxt = new JTextArea();
		bookDescTxt.setBounds(514, 77, 208, 98);
		panel_1.add(bookDescTxt);
		// 修改按钮
		JButton jb_modify = new JButton("\u4FEE\u6539");
		jb_modify.setIcon(new ImageIcon(
				"D:\\java\\MyBookManager\\image\\modify.png"));
		jb_modify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id = idTxt.getText();
				if (StringUtil.isEmpty(id)) {
					JOptionPane.showMessageDialog(null, "请选择要修改的记录！");
					return;
				}

				String bookName = bookNameTxt.getText();
				String author = bookAuthorTxt.getText();
				String price = bookPriceTxt.getText();
				String bookDesc = bookDescTxt.getText();

				if (StringUtil.isEmpty(bookName)) {
					JOptionPane.showMessageDialog(null, "图书名称不能为空！");
					return;
				}
				if (StringUtil.isEmpty(author)) {
					JOptionPane.showMessageDialog(null, "图书作者不能为空！");
					return;
				}
				if (StringUtil.isEmpty(price)) {
					JOptionPane.showMessageDialog(null, "图书价格不能为空！");
					return;
				}

				BookType bookType = (BookType) jcb_bookType.getSelectedItem();
				int bookTypeId = bookType.getId();

				Book book = new Book(Integer.parseInt(id), bookName, author,
						Float.parseFloat(price), bookDesc, bookTypeId);
				Connection con = null;
				try {
					con = dbUtil.getCon();
					int modifyNum = bookDao.bookModify(con, book);
					if (modifyNum == 1) {
						JOptionPane.showMessageDialog(null, "修改成功");
						resetValue();
						fillTable(new Book());
					} else {
						JOptionPane.showMessageDialog(null, "修改失败");
					}
				} catch (Exception ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, "修改失败");
				} finally {
					try {
						dbUtil.closeCon(con);
					} catch (Exception ec) {
						ec.printStackTrace();
					}
				}
			}
		});
		jb_modify.setBounds(66, 225, 93, 23);
		panel_1.add(jb_modify);
		// 删除按钮
		JButton jb_delete = new JButton("\u5220\u9664");
		jb_delete.setIcon(new ImageIcon(
				"D:\\java\\MyBookManager\\image\\delete.png"));
		jb_delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id = idTxt.getText();
				if (StringUtil.isEmpty(id)) {
					JOptionPane.showMessageDialog(null, "请选择要删除的记录！");
					return;
				}
				int n = JOptionPane.showConfirmDialog(null, "确定要删除这条记录吗？");
				if (n == 0) {
					Connection con = null;
					try {
						con = dbUtil.getCon();
						int re = bookDao.bookDelete(con, id);
						if (1 == re) {
							JOptionPane.showMessageDialog(null, "删除成功");
							resetValue();
							fillTable(new Book());
						} else
							JOptionPane.showMessageDialog(null, "删除失败");
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, "删除失败");
						e1.printStackTrace();
					}
				}
			}
		});
		jb_delete.setBounds(257, 225, 93, 23);
		panel_1.add(jb_delete);
	}

	private void resetValue() {
		this.idTxt.setText("");
		this.bookNameTxt.setText("");
		this.bookAuthorTxt.setText("");
		this.bookPriceTxt.setText("");
		this.bookDescTxt.setText("");
		if (jcb_bookType.getItemCount() > 0) {
			jcb_bookType.setSelectedIndex(0);
		}
	}
}
