package cn.edu.sau.view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import cn.edu.sau.dao.UserDao;
import cn.edu.sau.model.User;
import cn.edu.sau.util.DbUtil;
import cn.edu.sau.util.StringUtil;
import javax.swing.ImageIcon;

public class LogonFrm extends JFrame {

	private JPanel contentPane;
	private JTextField userNameTxt;
	private JPasswordField userPasswordTxt;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LogonFrm frame = new LogonFrm();
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
	public LogonFrm() {
		setTitle("\u56FE\u4E66\u7BA1\u7406\u7CFB\u7EDF");
		UserDao userDao = new UserDao();
		DbUtil dbUtil = new DbUtil();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		// 设置frame居中
		this.setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		init();
	}

	private void reset() {
		userNameTxt.setText("");
		userPasswordTxt.setText("");
	}

	private void init() {
		DbUtil dbUtil = new DbUtil();
		UserDao userDao = new UserDao();
		JLabel lb_userName = new JLabel("\u7528\u6237\u540D\uFF1A");
		lb_userName.setBounds(102, 75, 54, 15);
		contentPane.add(lb_userName);

		userNameTxt = new JTextField();
		userNameTxt.setBounds(166, 72, 152, 21);
		contentPane.add(userNameTxt);
		userNameTxt.setColumns(10);

		JLabel lb_userPassword = new JLabel("\u5BC6  \u7801\uFF1A");
		lb_userPassword.setBounds(102, 129, 54, 15);
		contentPane.add(lb_userPassword);

		// 登录按钮
		JButton jb_login = new JButton("\u767B\u5F55");
		jb_login.setIcon(new ImageIcon(
				"D:\\java\\MyBookManager\\image\\login.png"));
		jb_login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String userName = userNameTxt.getText();
				String userPassword = new String(userPasswordTxt.getPassword());
				if (StringUtil.isEmpty(userName)) {
					JOptionPane.showMessageDialog(null, "用户名不能为空");
					return;
				}
				if (StringUtil.isEmpty(userPassword)) {
					JOptionPane.showMessageDialog(null, "密码不能为空");
					return;
				}
				User user = new User(userName, userPassword);
				Connection con = null;

				try {
					con = dbUtil.getCon();
					User reUser = userDao.login(con, user);
					if (reUser != null) {
						dispose();// 销毁登录框
						if (1 == reUser.getRole()) {
							new MainFrm().setVisible(true);// 创建管理员主界面Firme
						} else if (0 == reUser.getRole()) {
							new UserMainFrm(reUser).setVisible(true); // 创建读者界面
						}
					} else {
						JOptionPane.showMessageDialog(null, "用户名或密码不正确");
					}
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "登录失败");
					e1.printStackTrace();
				} finally {
					try {
						con.close();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}

			}
		});
		jb_login.setBounds(102, 191, 93, 23);
		contentPane.add(jb_login);
		// 重置按钮
		JButton jb_reset = new JButton("\u91CD\u7F6E");
		jb_reset.setIcon(new ImageIcon(
				"D:\\java\\MyBookManager\\image\\reset.png"));
		jb_reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reset();
			}
		});
		jb_reset.setBounds(225, 191, 93, 23);
		contentPane.add(jb_reset);

		userPasswordTxt = new JPasswordField();
		userPasswordTxt.setBounds(166, 126, 152, 21);
		contentPane.add(userPasswordTxt);

		JLabel lblNewLabel = new JLabel(" ");
		lblNewLabel.setIcon(new ImageIcon(
				"D:\\java\\MyBookManager\\image\\logo.png"));
		lblNewLabel.setBounds(182, 10, 99, 52);
		contentPane.add(lblNewLabel);
	}
}
