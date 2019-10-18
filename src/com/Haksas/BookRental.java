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
import java.text.SimpleDateFormat;
import java.util.Date;

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
	
	JPanel pane1 = new JPanel();
	JPanel pane2 = new JPanel();
	
	DefaultTableModel model1 = null;
	JTable table1 = null;
	
	DefaultTableModel model2 = null;
	JTable table2 = null;
	
	private enum RentalStatus { BOOKSELECT, SEARCH2, LOAN, RETURN};
	
	private JTextField tf_hakbun = null;
	
	private JTextField tf_bookNo = null;
	
	private String Pivot = "";
	
	public BookRental(Connection conn) {
		// TODO Auto-generated constructor stub
		this.conn = conn;
		
		setLayout(null);
		this.add(pane1);
		//this.add(pane2);
		
		pane1.setBorder(new LineBorder(Color.BLUE));
		
		
		pane1.setLayout(new FlowLayout());
		pane1.setLocation(0, 0);
		pane1.setSize(260, 430);
		pane1.setVisible(true);
		pane2.setLayout(new FlowLayout());
		pane2.setLocation(260, 0);
		pane2.setSize(260, 430);
		pane2.setVisible(true);
		
	
		//pane1.setBorder(new LineBorder(Color.RED));
		//pane2.setBorder(new LineBorder(Color.GREEN));

		JLabel label_hakbun = new JLabel("학번");
		pane1.add(label_hakbun);
		
		tf_hakbun = new JTextField(12);
		pane1.add(tf_hakbun);
		
		JButton btnSelect1 = new JButton("검색");
		
		pane1.add(btnSelect1);
		
		JLabel label_name = new JLabel("이름");
		pane1.add(label_name);
		
		JTextField tf_name = new JTextField(12);
		pane1.add(tf_name);
		
		JButton btnList1 = new JButton("목록");
		btnList1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//query(RentalStatus.SEARCH1, 0, null);
			}
		});
		pane1.add(btnList1);
		
		
		
		String colName1 [] = {"책번호", "책이름", "반납여부"};
		model1 = new DefaultTableModel(colName1, 0);
		table1 = new JTable(model1);
		// ���̺� ������
		table1.setPreferredScrollableViewportSize(new Dimension(230, 300));
		pane1.add(new JScrollPane(table1));
		
		
		JLabel label_bookNo = new JLabel("책번호");
		pane2.add(label_bookNo);
		
		tf_bookNo = new JTextField(10);
		pane2.add(tf_bookNo);
		
		JButton btnSelect2 = new JButton("검색");
		
		pane2.add(btnSelect2);
		
		JLabel label_bookName = new JLabel("책이름");
		pane2.add(label_bookName);
		
		JTextField tf_bookName = new JTextField(10);
		pane2.add(tf_bookName);
		
		JButton btnList2 = new JButton("목록");
		btnList2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				query(RentalStatus.SEARCH2, 0, null);
			}
		});
		pane2.add(btnList2);
				
		String colName2 [] = {"책번호", "이름", "대여여부"};
		model2 = new DefaultTableModel(colName2, 0);
		table2 = new JTable(model2);
		// ���̺� ������
		table2.setPreferredScrollableViewportSize(new Dimension(230, 326));
		pane2.add(new JScrollPane(table2));

		
		
		btnSelect1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(!tf_hakbun.getText().equals("")) {
					//query(RentalStatus.SEARCH1, 1, tf_hakbun.getText());
				} else if(!tf_name.getText().equals("")) {
					//query(RentalStatus.SEARCH1, 2, tf_name.getText());
				}
			}
		});
		
		btnSelect2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(!tf_bookNo.getText().equals("")) {
					query(RentalStatus.SEARCH2, 1, tf_bookNo.getText());
				} else if(!tf_bookName.getText().equals("")) {
					query(RentalStatus.SEARCH2, 2, tf_bookName.getText());
				}
			}
		});
		
		table1.addMouseListener(new MouseListener() {
			
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
				table1 = (JTable)e.getComponent();
				model1 = (DefaultTableModel)table1.getModel();
				String id = (String)model1.getValueAt(table1.getSelectedRow(), 0);
				String name = (String)model1.getValueAt(table1.getSelectedRow(), 1);
				String dept = (String)model1.getValueAt(table1.getSelectedRow(), 2);
				
				tf_hakbun.setText(id);
				tf_name.setText(name);
				
			}
		});
		
		table2.addMouseListener(new MouseListener() {
			
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
				table2 = (JTable)e.getComponent();
				model2 = (DefaultTableModel)table2.getModel();
				String bookNo = (String)model2.getValueAt(table2.getSelectedRow(), 0);
				String bookName = (String)model2.getValueAt(table2.getSelectedRow(), 1);
				Pivot = (String)model2.getValueAt(table2.getSelectedRow(), 2);
				tf_bookNo.setText(bookNo);
				tf_bookName.setText(bookName);
			}
		});
		
		
		query(RentalStatus.BOOKSELECT, 0, null);
		//query(RentalStatus.SEARCH2, 0, null);

		
		JButton btnLoan = new JButton("대출");
		btnLoan.setBounds(185, 430, 60, 30);
		btnLoan.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(Pivot.equals("N"))
					JOptionPane.showMessageDialog(null, "이미 대출한 책입니다.", "Message", JOptionPane.ERROR_MESSAGE);
				else {
					int result = JOptionPane.showConfirmDialog(null, "대출하시겠습니까?", "Message", JOptionPane.YES_NO_OPTION);
					
					switch (result) {
					case JOptionPane.OK_OPTION:
						JOptionPane.showMessageDialog(null, "대출완료", "Message", JOptionPane.OK_OPTION);
						query(RentalStatus.LOAN, 0, null);
						model1.setNumRows(0);
						model2.setNumRows(0);
						tf_hakbun.setText("");
						tf_name.setText("");
						
						tf_bookNo.setText("");
						tf_bookName.setText("");
						break;
					case JOptionPane.NO_OPTION: return;
					default: break;
					}
				}
			}
		});
		add(btnLoan);
		
		JButton btnReturn = new JButton("반납");
		btnReturn.setBounds(274, 430, 60, 30);
		btnReturn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(Pivot.equals("Y"))
					JOptionPane.showMessageDialog(null, "이미 반납된 책입니다.", "Message", JOptionPane.ERROR_MESSAGE);
				else {
					int result = JOptionPane.showConfirmDialog(null, "반납하시겠습니까?", "Message", JOptionPane.YES_NO_OPTION);
					
					switch (result) {
					case JOptionPane.OK_OPTION:
						JOptionPane.showMessageDialog(null, "반납이 완료되었습니다", "Message", JOptionPane.OK_OPTION);
						query(RentalStatus.RETURN, 0, null);
						model1.setNumRows(0);
						model2.setNumRows(0);
						tf_hakbun.setText("");
						tf_name.setText("");
						
						tf_bookNo.setText("");
						tf_bookName.setText("");
						break;
					case JOptionPane.NO_OPTION: return;
					default: break;
					}
				}
			}
		});
		add(btnReturn);
		
		
		
		
		setSize(530, 470);
		//setBorder(new LineBorder(Color.BLACK));
		setVisible(true);
	}
	
	private void query(RentalStatus status, int index, String inValue) {
		ResultSet rs = null;
		switch(status) {
		case BOOKSELECT:
			model1.setNumRows(0);
			try {
				stmt = conn.createStatement();
				
				rs = stmt.executeQuery("Select * from Books");
//				else {
//					if(index == 1) {
//						rs = stmt.executeQuery("Select * from Student where Student_id = '" + inValue + "'");
//					} else if(index == 2) {
//						rs = stmt.executeQuery("Select * from Student where Student_name = '" + inValue + "'");
//					} else if(index == 3) {
//						rs = stmt.executeQuery("Select * from Student where Student_dept = '" + inValue + "'");
//					}
//				}
					
				while(rs.next()) {
					String [] row = new String[3];
					row[0] = rs.getString("Book_no");
					row[1] = rs.getString("Book_title");
					row[2] = rs.getString("Book_loan");
					model1.addRow(row);
				}
				rs.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		case SEARCH2:
			model2.setNumRows(0);
			try {
				stmt = conn.createStatement();
				if(inValue == null)
					rs = stmt.executeQuery("Select * from Books");
				else {
					if(index == 1) {
						rs = stmt.executeQuery("Select * from Books where Book_no = '" + inValue + "'");
					} else if(index == 2) {
						rs = stmt.executeQuery("Select * from Books where Book_title = '" + inValue + "'");
					}
				}
				while(rs.next()) {
					String [] row = new String[3];
					row[0] = rs.getString("Book_no");
					row[1] = rs.getString("Book_title");
					row[2] = rs.getString("Book_loan");
					model2.addRow(row);
				}
				rs.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case LOAN:
			SimpleDateFormat format = new SimpleDateFormat ( "yyyyMMdd");
			Date date = new Date();
			
			String time = format.format(date);
			
			try {
				stmt = conn.createStatement();
				if(inValue == null)
					stmt.executeUpdate("insert into BookRental(RentStudent_id, RentBook_No, BookRental_Date, BookRental_loan) values('"+ tf_hakbun.getText() +"','" + tf_bookNo.getText() + "','" + time + "', '대출')");
					stmt.executeUpdate("UPDATE Books SET Book_loan = 'N' WHERE Book_no = '" + tf_bookNo.getText() + "'");
			} catch (Exception e) {
					// TODO: handle exception
			}
			break;
		case RETURN:
			try {
				stmt = conn.createStatement();
				if(inValue == null)
					stmt.executeUpdate("UPDATE BookRental SET BookRental_loan = '반납' WHERE RentBook_No = '" + tf_bookNo.getText() + "'");
					stmt.executeUpdate("UPDATE Books SET Book_loan = 'Y' WHERE Book_no = '" + tf_bookNo.getText() + "'");
			} catch (Exception e) {
					// TODO: handle exception
			}
			break;
		default:
			break;
		}
		
	}

}
