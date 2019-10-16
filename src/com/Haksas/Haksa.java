package com.Haksas;


import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;



public class Haksa extends JFrame{
	private static final long serialVersionUID = 12L;
	Connection conn = null;
	Background background = null;
	JPanel mainPanel = new JPanel();
	BufferedImage img = null;
	Container cPane = null;
	
	public Haksa() {
		// TODO Auto-generated constructor stub
		this.setTitle("학사관리");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		cPane = getContentPane();
		try {
			img = ImageIO.read(new File("img/background.jpg"));
		} catch (Exception e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, "이미지 불러오기 실패");
			System.exit(0);
		}
		ImageIcon iconImg = new ImageIcon("img/background.jpg");
		JLabel mains = new JLabel(iconImg);
		cPane.add(mains);
		//background = new Background(img);
		
		//add(background);
		//background.add(mainPanel);
		
		cPane.add(mainPanel);
		
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
		
		
		CreateMenu(this, mainPanel);
		
		
		setResizable(false);
		setSize(600, 600);
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
	
	private void CreateMenu(Haksa myThis, JPanel mainPanel) {
		JMenuBar mb = new JMenuBar();
		JMenu studentMenu = new JMenu("학생관리");
		JMenuItem item_hakseng = new JMenuItem("학생");
		
		item_hakseng.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				mainPanel.removeAll();
				mainPanel.revalidate();
				mainPanel.repaint();
				mainPanel.setLayout(null);
				
				mainPanel.add(new MemberList(myThis, conn));
				//setSize(400, 520);
				setSize(600, 600);
			}
		});
		studentMenu.add(item_hakseng);
		JMenuItem item_Reg = new JMenuItem("수강신청");
		studentMenu.add(item_Reg);
		item_Reg.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				mainPanel.removeAll();
				mainPanel.revalidate();
				mainPanel.repaint();
				mainPanel.setLayout(null);
				
				mainPanel.add(new Registration(myThis, conn));
				//setSize(400, 520);
				setSize(600, 600);
			}
		});
		
		JMenu rentalMenu = new JMenu("도서관리");
		
		JMenuItem item_dechul = new JMenuItem("대출/반납");
		item_dechul.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				mainPanel.removeAll();
				mainPanel.revalidate();
				mainPanel.repaint(); 
				mainPanel.setLayout(null);
				
				mainPanel.add(new BookRental(conn));
				
				myThis.setSize(600, 600);
				
			}
		});
		rentalMenu.add(item_dechul);
		JMenuItem item_dechulInfo = new JMenuItem("대출관리");
		item_dechulInfo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				mainPanel.removeAll();
				mainPanel.revalidate();
				mainPanel.repaint(); 
				mainPanel.setLayout(null);
		
				
				//mainPanel.setBorder(new LineBorder(Color.RED));
				mainPanel.add(new BookRentalList(conn));
				
				myThis.setSize(600, 600);
			}
		});
		
		rentalMenu.add(item_dechulInfo);
		
		
		JMenu GradeMenu = new JMenu("학점매뉴");
		JMenuItem item_GradeAdd = new JMenuItem("학점등록");
		item_GradeAdd.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				mainPanel.removeAll();
				mainPanel.revalidate();
				mainPanel.repaint(); 
				mainPanel.setLayout(null);
		
				
				//mainPanel.setBorder(new LineBorder(Color.RED));
				mainPanel.add(new GradeAdd(conn));
				
				myThis.setSize(600, 600);
			}
		});
		GradeMenu.add(item_GradeAdd);
		JMenuItem item_GradeList = new JMenuItem("학점현황");
		
		GradeMenu.add(item_GradeList);
		mb.add(studentMenu);
		mb.add(rentalMenu);
		mb.add(GradeMenu);
		
		setJMenuBar(mb);
	}
	
	class Background extends JPanel {
		private static final long serialVersionUID = 1L;
		BufferedImage img = null;
		
		public Background(BufferedImage imgs) {
			// TODO Auto-generated constructor stub
			this.img = imgs;
			setLayout(new FlowLayout());
			
			add(new JLabel("학번"));
			
			
			
			setSize(600, 600);
			setVisible(true);
		}
		
		@Override
		public void paint(Graphics g) {
			// TODO Auto-generated method stub
			g.drawImage(img, 0, 0, 600, 600, null);
			super.paint(g);
		}
		
	}
	public static void main(String[] args) {
		new Haksa();
	}
}
