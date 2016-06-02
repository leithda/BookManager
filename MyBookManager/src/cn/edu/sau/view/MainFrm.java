package cn.edu.sau.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JDesktopPane;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import javax.swing.JOptionPane;

import java.awt.Window.Type;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;

public class MainFrm extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrm frame = new MainFrm();
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
	public MainFrm() {

		// 设置最大化
		setExtendedState(JFrame.MAXIMIZED_BOTH);

		setTitle("\u56FE\u4E66\u7BA1\u7406\u7CFB\u7EDF\u4E3B\u754C\u9762");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		JDesktopPane table = new JDesktopPane();
		table.setBounds(0, 3, 1354, 666);
		contentPane.add(table);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mn_datamanager = new JMenu("\u57FA\u672C\u6570\u636E\u7EF4\u62A4");
		menuBar.add(mn_datamanager);

		JMenu menu = new JMenu("\u56FE\u4E66\u7C7B\u522B\u7BA1\u7406");
		mn_datamanager.add(menu);

		// 添加图书类别菜单项
		JMenuItem jmi_bookTypeAdd = new JMenuItem(
				"\u6DFB\u52A0\u56FE\u4E66\u7C7B\u522B");
		jmi_bookTypeAdd.setIcon(new ImageIcon(
				"D:\\java\\MyBookManager\\image\\add.png"));
		jmi_bookTypeAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BookTypeAddInterFrm bookTypeAddInterFrm = new BookTypeAddInterFrm();
				bookTypeAddInterFrm.setVisible(true);
				table.add(bookTypeAddInterFrm);
			}
		});
		menu.add(jmi_bookTypeAdd);
		// 图书类别维护菜单项
		JMenuItem jmi_bookTypeManage = new JMenuItem(
				"\u56FE\u4E66\u7C7B\u522B\u7EF4\u62A4");
		jmi_bookTypeManage.setIcon(new ImageIcon(
				"D:\\java\\MyBookManager\\image\\bookTypeManager.png"));
		jmi_bookTypeManage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BookTypeManageInterFrm bookTypeManageInterFrm = new BookTypeManageInterFrm();
				bookTypeManageInterFrm.setVisible(true);
				table.add(bookTypeManageInterFrm);
			}
		});
		menu.add(jmi_bookTypeManage);

		JMenu menu_1 = new JMenu("\u56FE\u4E66\u7BA1\u7406");
		mn_datamanager.add(menu_1);
		// 添加图书菜单项
		JMenuItem jmi_bookAdd = new JMenuItem("\u6DFB\u52A0\u56FE\u4E66");
		jmi_bookAdd.setIcon(new ImageIcon(
				"D:\\java\\MyBookManager\\image\\add.png"));
		jmi_bookAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BookAddInterFrm bookAddInterFrm = new BookAddInterFrm();
				bookAddInterFrm.setVisible(true);
				table.add(bookAddInterFrm);
			}
		});
		menu_1.add(jmi_bookAdd);
		// 图书维护菜单项
		JMenuItem jmi_bookManage = new JMenuItem("\u56FE\u4E66\u7EF4\u62A4");
		jmi_bookManage.setIcon(new ImageIcon(
				"D:\\java\\MyBookManager\\image\\bookManager.png"));
		jmi_bookManage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BookManageInterFrm bookManageInterFrm = new BookManageInterFrm();
				bookManageInterFrm.setVisible(true);
				table.add(bookManageInterFrm);
			}
		});
		menu_1.add(jmi_bookManage);

		JMenuItem menu_2 = new JMenuItem("\u9000\u51FA");
		menu_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		mn_datamanager.add(menu_2);

		JMenu mn_lendreturn = new JMenu("\u501F\u9605\u7BA1\u7406");
		menuBar.add(mn_lendreturn);

		JMenuItem menuItem = new JMenuItem(
				"\u56FE\u4E66\u501F\u9605\u7BA1\u7406");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LendBookManageInterFrm lendBookManageInterFrm = new LendBookManageInterFrm();
				lendBookManageInterFrm.setVisible(true);
				table.add(lendBookManageInterFrm);
			}
		});
		mn_lendreturn.add(menuItem);

		JMenu mnNewMenu_2 = new JMenu("\u5173\u4E8E\u6211\u4EEC");
		menuBar.add(mnNewMenu_2);
		// 关于我们
		JMenuItem menuItem_1 = new JMenuItem("\u5173\u4E8E\u8F6F\u4EF6");
		menuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane
						.showMessageDialog(
								null,
								"**********************************************\n"
										+ " 编写者：沈阳航空航天大学2013级 起名真麻烦小队 \n"
										+ " 博客地址：http://www.cnblogs.com/qmzmfteam/ \n"
										+ " 初学者，不足之处希望大家能提出意见，谢谢！  \n"
										+ "**********************************************\n");
			}
		});
		mnNewMenu_2.add(menuItem_1);
	}
}
