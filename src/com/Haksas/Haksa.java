package com.Haksas;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;


import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;



public class Haksa extends JFrame{
	private static final long serialVersionUID = 12L;
	Connection conn = null;
	JPanel mainPanel = new JPanel();
	
	HashMap<String, Object> myHaksa = new HashMap<String, Object>();
	
	public Haksa() {
		// TODO Auto-generated constructor stub
		this.setTitle("학사관리");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(mainPanel);
		
		
		
		System.out.println();
		
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://hdante.synology.me:5600/Haksa", "grien", "890715");
			//Class.forName("oracle.jdbc.driver.OracleDriver");
			//conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:myoracle", "ora_user", "hong");
			System.out.println("연결완료");
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		//createInstance();
		
		
		CreateMenu(this, mainPanel, myHaksa);
		
		
		
		setSize(400, 520);
		setVisible(true);
		
		this.addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e) {}
			
			@Override
			public void windowIconified(WindowEvent e) {}
			
			@Override
			public void windowDeiconified(WindowEvent e) {}
			
			@Override
			public void windowDeactivated(WindowEvent e) {}
			
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					conn.close();
				} catch (Exception e2) {
					// TODO: handle exception
					e2.getStackTrace();
				}
			}
			
			@Override
			public void windowClosed(WindowEvent e) {}
			
			@Override
			public void windowActivated(WindowEvent e) {}
		});
	}
	
	private void CreateMenu(Haksa myThis, JPanel mainPanel, HashMap<String, Object> myHaksas) {
		JMenuBar mb = new JMenuBar();
		JMenu studentMenu = new JMenu("학생관리");
		JMenuItem item_hakseng = new JMenuItem("학생");
		
		item_hakseng.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				mainPanel.removeAll();// ��� ������Ʈ �����
				mainPanel.revalidate();// �ٽ� Ȱ��ȭ
				mainPanel.repaint(); // �ٽñ׸���
				mainPanel.setLayout(null);
				
				mainPanel.add(new MemberList(conn));
				setSize(400, 520);
			}
		});
		studentMenu.add(item_hakseng);
		
		studentMenu.add(new JMenuItem("수강신청"));
		
		JMenu rentalMenu = new JMenu("도서관리");
		
		JMenuItem item_dechul = new JMenuItem("대출/반납");
		item_dechul.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				mainPanel.removeAll();// ��� ������Ʈ �����
				mainPanel.revalidate();// �ٽ� Ȱ��ȭ
				mainPanel.repaint(); // �ٽñ׸���
				mainPanel.setLayout(null);
				
				mainPanel.add(new BookRental(conn));
				
				myThis.setSize(550, 540);
				
			}
		});
		rentalMenu.add(item_dechul);
		JMenuItem item_dechulInfo = new JMenuItem("대출관리");
		item_dechulInfo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//System.out.println("�������");
				mainPanel.removeAll();// ��� ������Ʈ �����
				mainPanel.revalidate();// �ٽ� Ȱ��ȭ
				mainPanel.repaint(); // �ٽñ׸���
				mainPanel.setLayout(null);
				//BookRental bookRental = (BookRental)myHaksas.get("bookRental");
				//bookRental.initList();
				
				//mainPanel.setBorder(new LineBorder(Color.RED));
				mainPanel.add(new BookRentalList(conn));
				
				myThis.setSize(535, 535);
			}
		});
		
		rentalMenu.add(item_dechulInfo);
		JMenuItem item_hyunhuang = new JMenuItem("학점현황");
		
		JMenu hakjoumMenu = new JMenu("학점매뉴");
		
		
		mb.add(studentMenu);
		mb.add(rentalMenu);
		mb.add(hakjoumMenu);
		
		setJMenuBar(mb);
	}
	
	public static void main(String[] args) {
		new Haksa();
	}
}
