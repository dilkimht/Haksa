package com.Haksas;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class BookRentalList extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 354L;
	Connection conn = null;
	Statement stmt = null;
	DefaultTableModel model = null;
	JTable table = null;
	ArrayList<String> row = new ArrayList<String>();
	JComboBox<String> cb_dept = new JComboBox<String>();
	
	public BookRentalList(Connection conn) {
		// TODO Auto-generated constructor stub
		
		this.conn = conn;
		
	setLayout(new FlowLayout());
		
		JLabel label_hakbun = new JLabel("학번");
		
		add(label_hakbun);
		
		initList();
		
	
		
		
		add(cb_dept);
		
		cb_dept.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				@SuppressWarnings("unchecked")
				JComboBox<String> cb = (JComboBox<String>)e.getSource();
				int cb_index = cb.getSelectedIndex();
				
				if(cb_index == 0) {
					String query = "SELECT Student.Student_id, Student.Student_name, Majors.Major_name, Student.Student_address, BookRental.BookRental_bDate, BookRental.BookRental_aDate, BookRental.BookRental_loan FROM BookRental, Student, Books, Majors WHERE RentStudent_id = Student_id AND RentBook_No = Book_no AND Major_id = Student_dept";
					BookList(query);
				} else {
					
					String query = "SELECT Student.Student_id, Student.Student_name, Majors.Major_name, Student.Student_address, BookRental.BookRental_bDate, BookRental.BookRental_aDate, BookRental.BookRental_loan FROM BookRental, Student, Books, Majors WHERE RentStudent_id = Student_id AND RentBook_No = Book_no AND Major_id = Student_dept and "
							+ "Student.Student_dept = '" + row.get(cb_index) + "'";
					BookList(query);
				}
				
				
			}
		});
		
		
		
	
		
		String colName [] = {"학번", "이름", "학과", "주소", "대출일", "반납일", "대여여부"};
		model = new DefaultTableModel(colName, 0);
		table = new JTable(model);

		table.setPreferredScrollableViewportSize(new Dimension(560, 400));
		add(new JScrollPane(table));
		
		String query = "SELECT Student.Student_id, Student.Student_name, Majors.Major_name, Student.Student_address, BookRental.BookRental_bDate, BookRental.BookRental_aDate, BookRental.BookRental_loan FROM BookRental, Student, Books, Majors WHERE RentStudent_id = Student_id AND RentBook_No = Book_no AND Major_id = Student_dept";
		BookList(query);
		
		setLocation(8, 20);
		setSize(580, 500);
		setVisible(true);
	}
	
	public void initList() {
		
		try {
			ResultSet rs = null;
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT Majors.Major_name, Majors.Major_id FROM Student, Majors WHERE Majors.Major_id = Student.Student_dept GROUP BY Student_dept");
			
			row.add("전체");
			cb_dept.addItem("전체");
			while(rs.next()) {
				row.add(rs.getString("Major_id"));
				cb_dept.addItem(rs.getString("Major_name"));
			}
			
			rs.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		
//		for (int i = 0; i < row.size(); i++) {
//			cb_dept.addItem(row.get(i));
//		}
		
		
	}
	
	private void BookList(String query) {
		
		ResultSet rs = null;
		model.setNumRows(0);
		try {
			rs = stmt.executeQuery(query);
			stmt = conn.createStatement();
			
			while(rs.next()) {
				String [] row = new String[7];
				row[0] = rs.getString("Student_id");
				row[1] = rs.getString("Student_name");
				row[2] = rs.getString("Major_name");
				row[3] = rs.getString("Student_address");
				row[4] = rs.getString("BookRental_bDate");
				row[5] = rs.getString("BookRental_aDate");
				row[6] = rs.getString("BookRental_loan");
				model.addRow(row);
			}
			
			rs.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
	}
	
}
