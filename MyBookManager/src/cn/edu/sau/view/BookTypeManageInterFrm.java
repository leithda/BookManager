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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import cn.edu.sau.dao.BookTypeDao;
import cn.edu.sau.model.BookType;
import cn.edu.sau.util.DbUtil;
import cn.edu.sau.util.StringUtil;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;

public class BookTypeManageInterFrm extends JInternalFrame {
	DbUtil dbUtil = new DbUtil();
	BookTypeDao bookTypeDao = new BookTypeDao();

	private JTextField s_bookTypeNameTxt;
	private JTextField idTxt;
	private JTextField bookTypeNameTxt;
	private JTable bookTypeTable;
	private JTextArea bookTypeDescTxt;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BookTypeManageInterFrm frame = new BookTypeManageInterFrm();
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
	public BookTypeManageInterFrm() {
		setClosable(true);
		setIconifiable(true);
		setTitle("\u56FE\u4E66\u7C7B\u522B\u7EF4\u62A4");
		setBounds(100, 100, 547, 549);
		setLocation(500, 100);
		getContentPane().setLayout(null);

		init();
		fillTable(new BookType("", ""));
	}

	// 填充表格
	private void fillTable(BookType bookType) {
		DefaultTableModel dtm = (DefaultTableModel) bookTypeTable.getModel();
		dtm.setRowCount(0);
		Connection con = null;
		try {
			con = dbUtil.getCon();
			ResultSet res = bookTypeDao.bookTypeList(con, bookType);
			while (res.next()) {
				Vector v = new Vector();
				v.add(res.getString("id"));
				v.add(res.getString("bookTypeName"));
				v.add(res.getString("bookTypeDesc"));
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
		JLabel lb_bookTypeName = new JLabel(
				"\u56FE\u4E66\u7C7B\u522B\u540D\u79F0\uFF1A");
		lb_bookTypeName.setBounds(31, 61, 93, 15);
		getContentPane().add(lb_bookTypeName);

		s_bookTypeNameTxt = new JTextField();
		s_bookTypeNameTxt.setBounds(134, 58, 185, 21);
		getContentPane().add(s_bookTypeNameTxt);
		s_bookTypeNameTxt.setColumns(10);
		// 搜索按钮
		JButton jb_search = new JButton("\u67E5\u8BE2");
		jb_search.setIcon(new ImageIcon(
				"D:\\java\\MyBookManager\\image\\search.png"));
		jb_search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String bookTypeName = s_bookTypeNameTxt.getText();
				BookType bookType = new BookType();
				bookType.setBookTypeName(bookTypeName);
				fillTable(bookType);
			}
		});
		jb_search.setBounds(379, 57, 108, 23);
		getContentPane().add(jb_search);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {

			}
		});
		scrollPane.setViewportBorder(new LineBorder(new Color(0, 0, 0)));
		scrollPane.setBounds(31, 113, 456, 143);
		getContentPane().add(scrollPane);

		bookTypeTable = new JTable();
		bookTypeTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				int row = bookTypeTable.getSelectedRow();
				idTxt.setText((String) bookTypeTable.getValueAt(row, 0));
				bookTypeNameTxt.setText((String) bookTypeTable.getValueAt(row,
						1));
				bookTypeDescTxt.setText((String) bookTypeTable.getValueAt(row,
						2));
			}
		});
		bookTypeTable.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "\u7F16\u53F7",
						"\u56FE\u4E66\u7C7B\u522B\u540D\u79F0",
						"\u56FE\u4E66\u7C7B\u522B\u63CF\u8FF0" }) {
			boolean[] columnEditables = new boolean[] { false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		bookTypeTable.getColumnModel().getColumn(1).setPreferredWidth(107);
		bookTypeTable.getColumnModel().getColumn(2).setPreferredWidth(243);
		bookTypeTable.setToolTipText("\u56FE\u4E66\u7C7B\u522B");
		bookTypeTable.setSurrendersFocusOnKeystroke(true);
		scrollPane.setViewportView(bookTypeTable);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "\u8868\u5355\u64CD\u4F5C",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setToolTipText("\u8868\u5355\u64CD\u4F5C");
		panel.setBounds(31, 282, 456, 196);
		getContentPane().add(panel);
		panel.setLayout(null);

		JLabel label = new JLabel("\u7F16\u53F7\uFF1A");
		label.setBounds(10, 44, 50, 23);
		panel.add(label);

		idTxt = new JTextField();
		idTxt.setEditable(false);
		idTxt.setBounds(71, 45, 72, 21);
		panel.add(idTxt);
		idTxt.setColumns(10);

		JLabel label_1 = new JLabel(
				"\u56FE\u4E66\u7C7B\u522B\u540D\u79F0\uFF1A");
		label_1.setBounds(156, 48, 96, 15);
		panel.add(label_1);

		bookTypeNameTxt = new JTextField();
		bookTypeNameTxt.setBounds(246, 45, 181, 21);
		panel.add(bookTypeNameTxt);
		bookTypeNameTxt.setColumns(10);

		JLabel label_2 = new JLabel("\u63CF\u8FF0\uFF1A");
		label_2.setBounds(10, 77, 50, 15);
		panel.add(label_2);

		bookTypeDescTxt = new JTextArea();
		bookTypeDescTxt.setBounds(71, 73, 356, 73);
		panel.add(bookTypeDescTxt);
		// 修改按钮
		JButton jb_modify = new JButton("\u4FEE\u6539");
		jb_modify.setIcon(new ImageIcon(
				"D:\\java\\MyBookManager\\image\\modify.png"));
		jb_modify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String bookTypeName = bookTypeNameTxt.getText();
				String bookTypeDesc = bookTypeDescTxt.getText();
				if (StringUtil.isEmpty(bookTypeName)) {
					JOptionPane.showMessageDialog(null, "图书类别名称不能为空");
				}
				BookType bookType = new BookType(bookTypeName, bookTypeDesc);
				bookType.setId(Integer.parseInt(idTxt.getText()));
				Connection con = null;
				try {
					con = dbUtil.getCon();
					int re = bookTypeDao.bookTypeModify(con, bookType);
					if (1 == re) {
						JOptionPane.showMessageDialog(null, "修改成功");
						fillTable(new BookType("", ""));
						reset();
					} else {
						JOptionPane.showMessageDialog(null, "修改失败");
					}
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "修改失败");
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
		jb_modify.setBounds(71, 156, 90, 23);
		panel.add(jb_modify);
		// 删除按钮
		JButton jb_delete = new JButton("\u5220\u9664");
		jb_delete.setIcon(new ImageIcon(
				"D:\\java\\MyBookManager\\image\\delete.png"));
		jb_delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id = idTxt.getText();
				Connection con = null;
				try {
					con = dbUtil.getCon();
					int re = bookTypeDao.bookTypeDelete(con, id);
					if (1 == re) {
						JOptionPane.showMessageDialog(null, "删除成功");
						fillTable(new BookType("", ""));
						reset();
					} else {
						JOptionPane.showMessageDialog(null, "删除失败");
					}
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "删除失败");
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
		jb_delete.setBounds(215, 156, 90, 23);
		panel.add(jb_delete);
		getContentPane().setFocusTraversalPolicy(
				new FocusTraversalOnArray(new Component[] { lb_bookTypeName,
						s_bookTypeNameTxt, jb_search, scrollPane, panel, label,
						idTxt, label_1, bookTypeNameTxt, label_2,
						bookTypeDescTxt, jb_modify, jb_delete }));
	}

	private void reset() {
		idTxt.setText("");
		bookTypeNameTxt.setText("");
		bookTypeDescTxt.setText("");
	}
}
