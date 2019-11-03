package com.Haksas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;




public class BookRental extends JPanel{
	private static final long serialVersionUID = 132L;
	Connection conn = null;
	Statement stmt = null;
	
	JPanel paneLeon_1 = new JPanel();
	JPanel paneLeon_2 = new JPanel();
	
	JPanel panereturn_1 = new JPanel();
	
	DefaultTableModel Leon_model1 = null;
	JTable Leon_table1 = null;
	
	DefaultTableModel Leon_model2 = null;
	JTable Leon_table2 = null;
	
	DefaultTableModel Return_model1 = null;
	JTable Return_table1 = null;
	
	private enum RentalStatus { BOOKSELECT, STUDENTSELECT, BOOKSELECT1, LOAN, RETURN};
	
	private JTextField tf_hakbun = null;
	
	private JTextField tf_name = null;
	
	
	private String Pivot = "";
	
	private String bookNo = "";
	
	public BookRental(Connection conn) {
		// TODO Auto-generated constructor stub
		this.conn = conn;
		
		setLayout(null);
		this.add(paneLeon_1);
		this.add(paneLeon_2);
		setBorder(new LineBorder(Color.BLACK));
		paneLeon_1.setBorder(new LineBorder(Color.BLACK));
		paneLeon_2.setBorder(new LineBorder(Color.BLACK));
		
		paneLeon_1.setLayout(new FlowLayout());
		paneLeon_1.setSize(280, 400);
		paneLeon_1.setVisible(false);
		paneLeon_2.setLayout(new FlowLayout());
		paneLeon_2.setSize(220, 265);
		paneLeon_2.setVisible(false);
		
	
		JButton btn_Loan = new JButton("대출");
		JButton btn_Return = new JButton("반납");
		btn_Loan.setBounds(150, 200, 100, 100);
		btn_Return.setBounds(340, 200, 100, 100);
		btn_Loan.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				btn_Loan.setVisible(false);
				btn_Return.setVisible(false);
				paneLeon_1.setVisible(true);
				paneLeon_1.setLocation(150, 70);
			}
		});
		this.add(btn_Loan);
		btn_Return.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		this.add(btn_Return);
		
		
		JButton btn_Back = new JButton("뒤로");
		btn_Back.setBounds(240, 30, 80, 30);
		
		this.add(btn_Back);
		
		
		JLabel label_hakbun = new JLabel("학번");
		paneLeon_1.add(label_hakbun);
		
		tf_hakbun = new JTextField(12);
		tf_hakbun.setEditable(false);
		paneLeon_1.add(tf_hakbun);
		
		
		
		JButton btnSelect1 = new JButton("검색");
		btnSelect1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String sql = "Select Student_id, Student_name from Student where Student_name ='" + tf_name.getText() + "'";
				query(RentalStatus.STUDENTSELECT, sql);
				
			}
		});
		paneLeon_1.add(btnSelect1);
		JLabel label_split = new JLabel("         ");
		
		paneLeon_1.add(label_split);
		
		JLabel label_name = new JLabel("이름");
		
		paneLeon_1.add(label_name);
		
		tf_name = new JTextField(12);
		paneLeon_1.add(tf_name);
		
		JLabel label_split1 = new JLabel("                               ");
		
		paneLeon_1.add(label_split1);
		

		String colName1 [] = {"책번호", "책이름", "반납여부"};
		Leon_model1 = new DefaultTableModel(colName1, 0) {
			private static final long serialVersionUID = 6435L;
			public boolean isCellEditable(int i, int c){ return false; } // 컬럼에서 수정불가
		};
		Leon_table1 = new JTable(Leon_model1);
		//컬럼 이동 막기
		Leon_table1.getTableHeader().setReorderingAllowed(false);
		Leon_table1.getTableHeader().setResizingAllowed(false);
				
		Leon_table1.setPreferredScrollableViewportSize(new Dimension(230, 300));
		paneLeon_1.add(new JScrollPane(Leon_table1));
		
		
		JLabel label_arrow = new JLabel("  ▶    ");
		
		paneLeon_1.add(label_arrow);
		
	
		
		JButton btnSelect2 = new JButton("대출");
		btnSelect2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(tf_name.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "회원을 찾지 않았습니다.", "Message", JOptionPane.ERROR_MESSAGE);
				} else {
					if(Pivot.equals("대출") && !Pivot.equals("")) {
						JOptionPane.showMessageDialog(null, "책이 없습니다.", "Message", JOptionPane.ERROR_MESSAGE);
					} else {
						String sql = "INSERT INTO BookRental(RentStudent_id, RentBook_No, BookRental_bDate, BookRental_aDate, BookRental_loan) VALUES('" + tf_hakbun.getText() + "', '" + bookNo + "', default, '', '대출');";
						query(RentalStatus.LOAN, sql);
						JOptionPane.showMessageDialog(null, "대출이 완료되었습니다.", "Message", JOptionPane.OK_OPTION);
						Leon_model2.setRowCount(0);
						paneLeon_2.setVisible(false);
						setLocation(150, 70);
					}
				}
			}
		});
		paneLeon_2.add(btnSelect2);
		JLabel label_split2 = new JLabel("|                                             |");
		
		paneLeon_2.add(label_split2);

		String colName2 [] = {"이름", "대출일", "반납일"};
		Leon_model2 = new DefaultTableModel(colName2, 0);
		Leon_table2 = new JTable(Leon_model2);
		
		Leon_table2.setPreferredScrollableViewportSize(new Dimension(210, 200));
		paneLeon_2.add(new JScrollPane(Leon_table2));

		
		
		Leon_table1.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {}
			
			@Override
			public void mousePressed(MouseEvent e) {}
			
			@Override
			public void mouseExited(MouseEvent e) {}
			
			@Override
			public void mouseEntered(MouseEvent e) {}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				Leon_model2.setRowCount(0);
				paneLeon_2.setVisible(true);
				paneLeon_1.setLocation(40, 70);
				paneLeon_2.setLocation(330, 150);
				Leon_table1 = (JTable)e.getComponent();
				Leon_model1 = (DefaultTableModel)Leon_table1.getModel();
				bookNo = (String)Leon_model1.getValueAt(Leon_table1.getSelectedRow(), 0);
				
				String sql = "SELECT Student.Student_name, BookRental.BookRental_bDate, BookRental.BookRental_aDate, BookRental.BookRental_loan FROM Student, Books, BookRental WHERE Books.Book_no = BookRental.RentBook_No AND BookRental.RentStudent_id = Student.Student_id AND Books.Book_no = '" + bookNo + "'";
				
				query(RentalStatus.BOOKSELECT1, sql);
		
			}
		});
		
		
		query(RentalStatus.BOOKSELECT, null);
		
		setOpaque(false);
		setLocation(0, 0);
		setSize(600, 600);
		setVisible(true);
		
		
	}
	
	private void query(RentalStatus status, String sSql) {
		ResultSet rs = null;
		switch(status) {
		case BOOKSELECT:
			Leon_model1.setNumRows(0);
			try {
				stmt = conn.createStatement();
				
				rs = stmt.executeQuery("Select * from Books");
				
				while(rs.next()) {
					String [] row = new String[3];
					row[0] = rs.getString("Book_no");
					row[1] = rs.getString("Book_title");
					row[2] = rs.getString("Book_loan");
					Leon_model1.addRow(row);
				}
				rs.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		case STUDENTSELECT:
			try {
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sSql);
				
				if(rs.next()) {
					tf_hakbun.setText(rs.getString("Student_id"));
					tf_name.setText(rs.getString("Student_name"));
				} else {
					JOptionPane.showMessageDialog(null, "학생이 없습니다.", "Message", JOptionPane.OK_OPTION);
				}
				rs.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case BOOKSELECT1:
			Pivot = "";
			try {
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sSql);
				
				while(rs.next()) {
					String [] row = new String[3];
					row[0] = rs.getString("Student_name");
					row[1] = rs.getString("BookRental_bDate");
					row[2] = rs.getString("BookRental_aDate");
					Pivot = rs.getString("BookRental_loan");
					Leon_model2.addRow(row);
				}
				rs.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case LOAN:
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate(sSql);
			} catch (Exception e) {
					// TODO: handle exception
			}
			break;
		default:
			break;
		}
		
	}

}
