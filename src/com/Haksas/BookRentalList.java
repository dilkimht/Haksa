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
					String query = "SELECT br.RentStudent_id, s.Student_name, b.Book_title, br.BookRental_Date, br.BookRental_loan FROM BookRental br, Student s, Books b WHERE br.RentStudent_id = s.Student_id AND br.RentBook_No = b.Book_no";
					BookList(query);
				} else {
					
					String query = "SELECT br.RentStudent_id, s.Student_name, b.Book_title, br.BookRental_Date, br.BookRental_loan FROM BookRental br, Student s, Books b WHERE br.RentStudent_id = s.Student_id AND br.RentBook_No = b.Book_no and "
							+ "s.Student_dept = '" + row.get(cb_index) + "'";
					BookList(query);
				}
				
				
			}
		});
		
		
		
	
		
		String colName [] = {"학번", "이름", "학과", "주소", "대여가능여부"};
		model = new DefaultTableModel(colName, 0);
		table = new JTable(model);
		// ���̺� ������
		table.setPreferredScrollableViewportSize(new Dimension(500, 400));
		add(new JScrollPane(table));
		
		String query = "SELECT br.RentStudent_id, s.Student_name, b.Book_title, br.BookRental_Date, BookRental_loan FROM BookRental br, Student s, Books b WHERE br.RentStudent_id = s.Student_id AND br.RentBook_No = b.Book_no";
		BookList(query);
		
		
		setSize(520, 500);
		setVisible(true);
	}
	
	public void initList() {
		
		try {
			ResultSet rs = null;
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT Student_dept FROM Student GROUP BY Student_dept; ");
			
			row.add("��ü");
			while(rs.next()) {
				row.add(rs.getString("Student_dept"));
			}
			
			rs.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		
		for (int i = 0; i < row.size(); i++) {
			cb_dept.addItem(row.get(i));
		}
		
		
	}
	
	private void BookList(String query) {
		
		ResultSet rs = null;
		model.setNumRows(0);
		try {
			rs = stmt.executeQuery(query);
			stmt = conn.createStatement();
			
			while(rs.next()) {
				String [] row = new String[5];
				row[0] = rs.getString("br.RentStudent_id");
				row[1] = rs.getString("s.Student_name");
				row[2] = rs.getString("b.Book_title");
				row[3] = rs.getString("br.BookRental_Date");
				row[4] = rs.getString("br.BookRental_loan");
				model.addRow(row);
			}
			
			rs.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
	}
	
}
