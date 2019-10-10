package com.Haksas;


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
import javax.swing.table.DefaultTableModel;


public class MemberList  extends JPanel {
	private static final long serialVersionUID = 1L;
	Connection conn = null;
	Statement stmt = null;
	DefaultTableModel model = null;
	JTable table = null;
	
	JTextField tf_id = null;
	JTextField tf_name = null;
	JTextField tf_dept = null;
	JTextField tf_address = null;
	
	private enum CRUD { SELECT, INSERT, UPDATE, DELETE };
	
	public MemberList(Connection conn) {
		// TODO Auto-generated constructor stub
		
		this.conn = conn;
		
		
		setLayout(new FlowLayout());
		
	

		add(new JLabel("학번"));
		tf_id = new JTextField(23);
		add(tf_id);
		
		JButton btnSelect = new JButton("검색");
		btnSelect.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(	tf_id.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "학번을 입력하세요.", "Message", JOptionPane.ERROR_MESSAGE);
					tf_name.setText("");
					tf_dept.setText("");
					tf_address.setText("");
					
				} else {
					list(CRUD.SELECT, tf_id.getText());
				}
			}
		});
		add(btnSelect);
		
		
		add(new JLabel("이름"));
		tf_name = new JTextField(29);
		add(tf_name);
		
		add(new JLabel("학과"));
		tf_dept = new JTextField(29);
		add(tf_dept);
		
		add(new JLabel("주소"));
		tf_address = new JTextField(29);
		add(tf_address);
		
		
		
		String colName [] = {"학번", "이름", "학과", "주소"};
		model = new DefaultTableModel(colName, 0);
		table = new JTable(model);
		// ���̺� ������
		table.setPreferredScrollableViewportSize(new Dimension(350, 270));
		add(new JScrollPane(table));
		
		table.addMouseListener(new MouseListener() {
			
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
				table = (JTable)e.getComponent();
				model = (DefaultTableModel)table.getModel();
				String id = (String)model.getValueAt(table.getSelectedRow(), 0);
				String name = (String)model.getValueAt(table.getSelectedRow(), 1);
				String dept = (String)model.getValueAt(table.getSelectedRow(), 2);
				String address = (String)model.getValueAt(table.getSelectedRow(), 3);
				
				tf_id.setText(id);
				tf_name.setText(name);
				tf_dept.setText(dept);
				tf_address.setText(address);
				
			}
		});
		
		
		JButton btnInsert = new JButton("등록");
		btnInsert.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				if(	tf_id.getText().equals("") || 
					tf_name.getText().equals("") ||
					tf_dept.getText().equals("") ||
					tf_address.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "전부 입력되지 않았습니다.", "Message", JOptionPane.ERROR_MESSAGE);
				} else {
					
					JOptionPane.showMessageDialog(null, "등록되었습니다.", "Message", JOptionPane.YES_OPTION);
					list(CRUD.INSERT, null);
					list(CRUD.SELECT, null);
					
				}
			}
		});
		add(btnInsert);
		JButton btnList = new JButton("목록");
		btnList.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				list(CRUD.SELECT, null);
				tf_id.setText("");
				tf_name.setText("");
				tf_dept.setText("");
				tf_address.setText("");
			}
		});
		add(btnList);
		JButton btnUpdate = new JButton("수정");
		btnUpdate.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int result = JOptionPane.showConfirmDialog(null, "수정하시겠습니까?", "Message", JOptionPane.YES_NO_OPTION);
				
				switch (result) {
				case JOptionPane.OK_OPTION:
					JOptionPane.showMessageDialog(null, "수정이완료되었습니다.", "Message", JOptionPane.OK_OPTION);
					list(CRUD.UPDATE, null);
					list(CRUD.SELECT, null);
					break;
				case JOptionPane.NO_OPTION: return;
				default: break;
				}
			}
		});
		add(btnUpdate);
		JButton btnDelete = new JButton("삭제");
		btnDelete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int result = JOptionPane.showConfirmDialog(null, "삭제하시겠습니까?", "Message", JOptionPane.YES_NO_OPTION);
				
				switch (result) {
				case JOptionPane.OK_OPTION:
					JOptionPane.showMessageDialog(null, "삭제가 완료되었습니다.", "Message", JOptionPane.OK_OPTION);
					list(CRUD.DELETE, null);
					list(CRUD.SELECT, null);
					break;
				case JOptionPane.NO_OPTION: return;
				default: break;
				}
			}
		});
		add(btnDelete);
		
		list(CRUD.SELECT, null);
		
		
		setSize(380, 520);
		setVisible(true);
	}
	
	public void list(CRUD type, String where) {
		
		ResultSet rs = null;
		model.setNumRows(0);
		try {
			stmt = conn.createStatement();
			
			switch(type) {
			case SELECT:
				if(where == null) {
					rs = stmt.executeQuery("Select * from Student");
					
					while(rs.next()) {
						String [] row = new String[4];
						row[0] = rs.getString("Student_id");
						row[1] = rs.getString("Student_name");
						row[2] = rs.getString("Student_dept");
						row[3] = rs.getString("Student_address");
						model.addRow(row);
					}
					
				} else {
					rs = stmt.executeQuery("SELECT * FROM Student WHERE Student_id = '" + where + "'");
					
					String [] row = new String[4];
					if(rs.next()) {
						row[0] = rs.getString("Student_id");
						row[1] = rs.getString("Student_name");
						row[2] = rs.getString("Student_dept");
						row[3] = rs.getString("Student_address");
						tf_id.setText(row[0]);
						tf_name.setText(row[1]);
						tf_dept.setText(row[2]);
						tf_address.setText(row[3]);
						model.addRow(row);
					}
				}
				break;
			case INSERT:
				try {
					rs = stmt.executeQuery("insert into Student values('"
							+ 	tf_id.getText() +"', '"+ tf_name.getText() + "', '" + tf_dept.getText() + "', '" + tf_address.getText() + "')");
				} catch (Exception e2) {
					// TODO: handle exception
				}
				break;
			case UPDATE:
				try {
					rs = stmt.executeQuery("update Student set Student_name = '"
							+ tf_name.getText() +"', Student_dept = '"+ tf_dept.getText() + "', Student_address = '" + tf_address.getText() + "' where Student_id = '" + tf_id.getText() + "'");
				} catch (Exception e2) {
					// TODO: handle exception
				}
				break;
			case DELETE:
				try {
					rs = stmt.executeQuery("delete from Student where Student_id = '" + tf_id.getText() + "'");
				} catch (Exception e2) {
					// TODO: handle exception
				}
				break;
				
			}
			
		
			
			rs.close();
		} catch (Exception e) {
			// TODO: handle exception\
			System.out.println(e.getMessage());
			e.getStackTrace();
		}
	}
	
}
