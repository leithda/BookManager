package cn.edu.sau.view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import cn.edu.sau.dao.BookTypeDao;
import cn.edu.sau.model.BookType;
import cn.edu.sau.util.DbUtil;
import cn.edu.sau.util.StringUtil;
import javax.swing.ImageIcon;

public class BookTypeAddInterFrm extends JInternalFrame {
	private JTextField bookTypeNameTxt;
	private JTextArea bookTypeDescTxt;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BookTypeAddInterFrm frame = new BookTypeAddInterFrm();
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
	public BookTypeAddInterFrm() {
		setIconifiable(true);
		setClosable(true);
		setResizable(true);
		setTitle("\u6DFB\u52A0\u56FE\u4E66\u7C7B\u522B");
		setBounds(100, 100, 480, 325);
		setLocation(500, 100);
		getContentPane().setLayout(null);
		init();

	}

	private void init() {
		BookTypeDao bookTypeDao = new BookTypeDao();
		DbUtil dbUtil = new DbUtil();
		JLabel lb_BookTypeName = new JLabel(
				"\u56FE\u4E66\u7C7B\u522B\u540D\u79F0\uFF1A");
		lb_BookTypeName.setBounds(53, 32, 99, 15);
		getContentPane().add(lb_BookTypeName);

		bookTypeNameTxt = new JTextField();
		bookTypeNameTxt.setBounds(162, 29, 150, 21);
		getContentPane().add(bookTypeNameTxt);
		bookTypeNameTxt.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel(
				"\u56FE\u4E66\u7C7B\u522B\u63CF\u8FF0\uFF1A");
		lblNewLabel_1.setBounds(53, 91, 99, 15);
		getContentPane().add(lblNewLabel_1);

		bookTypeDescTxt = new JTextArea();
		bookTypeDescTxt.setBounds(162, 87, 200, 121);
		getContentPane().add(bookTypeDescTxt);
		// 添加图书类别按钮
		JButton jb_bookTypeAdd = new JButton("\u6DFB\u52A0");
		jb_bookTypeAdd.setIcon(new ImageIcon(
				"D:\\java\\MyBookManager\\image\\add.png"));
		jb_bookTypeAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String bookTypeName = bookTypeNameTxt.getText();
				String bookTypeDesc = bookTypeDescTxt.getText();
				if (StringUtil.isEmpty(bookTypeName)) {
					JOptionPane.showMessageDialog(null, "图书类别名称不能为空");
				} else {
					Connection con = null;

					try {
						con = dbUtil.getCon();
						BookType bookType = new BookType(bookTypeName,
								bookTypeDesc);
						int ret = bookTypeDao.bookTypeAdd(con, bookType);
						if (1 == ret) {
							JOptionPane.showMessageDialog(null, "图书类别添加成功");
							reset();
						} else {
							JOptionPane.showMessageDialog(null, "图书类别添加失败");
						}
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, "图书类别添加失败");
						e1.printStackTrace();
					}
				}
			}
		});
		jb_bookTypeAdd.setBounds(53, 238, 99, 23);
		getContentPane().add(jb_bookTypeAdd);
		// 重置按钮
		JButton jb_bookTypeReset = new JButton("\u91CD\u7F6E");
		jb_bookTypeReset.setIcon(new ImageIcon(
				"D:\\java\\MyBookManager\\image\\reset.png"));
		jb_bookTypeReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reset();
			}
		});
		jb_bookTypeReset.setBounds(263, 238, 99, 23);
		getContentPane().add(jb_bookTypeReset);
	}

	private void reset() {
		bookTypeNameTxt.setText("");
		bookTypeDescTxt.setText("");
	}
}
