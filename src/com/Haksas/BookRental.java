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
	
	JPanel pane1 = new JPanel();
	JPanel pane2 = new JPanel();
	
	DefaultTableModel model1 = null;
	JTable table1 = null;
	
	DefaultTableModel model2 = null;
	JTable table2 = null;
	
	private enum RentalStatus { BOOKSELECT, STUDENTSELECT, BOOKSELECT1, LOAN, RETURN};
	
	private JTextField tf_hakbun = null;
	
	private JTextField tf_name = null;
	
	
	private String Pivot = "";
	
	private String bookNo = "";
	
	public BookRental(Connection conn) {
		// TODO Auto-generated constructor stub
		this.conn = conn;
		
		setLayout(null);
		this.add(pane1);
		this.add(pane2);
		
		pane1.setBorder(new LineBorder(Color.BLACK));
		
		
		pane1.setLayout(new FlowLayout());
		pane1.setLocation(0, 0);
		pane1.setSize(280, 400);
		pane1.setVisible(true);
		pane2.setLayout(new FlowLayout());
		pane2.setLocation(290, 90);
		pane2.setSize(220, 265);
		pane2.setVisible(false);
		
	
		//pane1.setBorder(new LineBorder(Color.RED));
		pane2.setBorder(new LineBorder(Color.BLACK));

		JLabel label_hakbun = new JLabel("학번");
		pane1.add(label_hakbun);
		
		tf_hakbun = new JTextField(12);
		tf_hakbun.setEditable(false);
		pane1.add(tf_hakbun);
		
		
		
		JButton btnSelect1 = new JButton("검색");
		btnSelect1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String sql = "Select Student_id, Student_name from Student where Student_name ='" + tf_name.getText() + "'";
				query(RentalStatus.STUDENTSELECT, sql);
				
			}
		});
		pane1.add(btnSelect1);
		JLabel label_split = new JLabel("         ");
		
		pane1.add(label_split);
		
		JLabel label_name = new JLabel("이름");
		
		pane1.add(label_name);
		
		tf_name = new JTextField(12);
		pane1.add(tf_name);
		
		JLabel label_split1 = new JLabel("                               ");
		
		pane1.add(label_split1);
		

		String colName1 [] = {"책번호", "책이름", "반납여부"};
		model1 = new DefaultTableModel(colName1, 0) {
			private static final long serialVersionUID = 6435L;
			public boolean isCellEditable(int i, int c){ return false; } // 컬럼에서 수정불가
		};
		table1 = new JTable(model1);
		//컬럼 이동 막기
		table1.getTableHeader().setReorderingAllowed(false);
		table1.getTableHeader().setResizingAllowed(false);
				
		table1.setPreferredScrollableViewportSize(new Dimension(230, 300));
		pane1.add(new JScrollPane(table1));
		
		
		JLabel label_arrow = new JLabel("  ▶    ");
		
		pane1.add(label_arrow);
		
	
		
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
						model2.setRowCount(0);
						pane2.setVisible(false);
						setLocation(150, 70);
					}
				}
			}
		});
		pane2.add(btnSelect2);
		JLabel label_split2 = new JLabel("|                                             |");
		
		pane2.add(label_split2);

		String colName2 [] = {"이름", "대출일", "반납일"};
		model2 = new DefaultTableModel(colName2, 0);
		table2 = new JTable(model2);
		
		table2.setPreferredScrollableViewportSize(new Dimension(210, 200));
		pane2.add(new JScrollPane(table2));

		
		
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
				model2.setRowCount(0);
				pane2.setVisible(true);
				setLocation(40, 50);
				table1 = (JTable)e.getComponent();
				model1 = (DefaultTableModel)table1.getModel();
				bookNo = (String)model1.getValueAt(table1.getSelectedRow(), 0);
				
				String sql = "SELECT Student.Student_name, BookRental.BookRental_bDate, BookRental.BookRental_aDate, BookRental.BookRental_loan FROM Student, Books, BookRental WHERE Books.Book_no = BookRental.RentBook_No AND BookRental.RentStudent_id = Student.Student_id AND Books.Book_no = '" + bookNo + "'";
				
				query(RentalStatus.BOOKSELECT1, sql);
		
			}
		});
		
		
		query(RentalStatus.BOOKSELECT, null);
		
		setOpaque(false);
		setLocation(150, 70);
		setSize(530, 420);
		setVisible(true);
		
		
	}
	
	private void query(RentalStatus status, String sSql) {
		ResultSet rs = null;
		switch(status) {
		case BOOKSELECT:
			model1.setNumRows(0);
			try {
				stmt = conn.createStatement();
				
				rs = stmt.executeQuery("Select * from Books");
				
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
					model2.addRow(row);
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
