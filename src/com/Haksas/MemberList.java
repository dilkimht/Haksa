package com.Haksas;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;


public class MemberList  extends JPanel {
	private static final long serialVersionUID = 1L;
	Connection conn = null;
	Statement stmt = null;
	Haksa myThis = null;
	
	JPanel myPanel = new JPanel();
	
	DefaultTableModel model = null;
	JTable table = null;
	
	DefaultTableModel majorModel = null;
	JTable majorTable = null;
	
	JTextField tf_id = null;
	JTextField tf_name = null;
	JTextField tf_dept = null;
	JTextField tf_address = null;
	
	String majorNumber = "";
	
	private enum CRUD { SELECT, INSERT, UPDATE, DELETE };
	
	public MemberList(Haksa myThis, Connection conn) {
		// TODO Auto-generated constructor stub
		
		this.conn = conn;
		this.myThis = myThis;
		
		setLayout(null);
		setBackground(Color.WHITE);
		myPanel.setLayout(new FlowLayout());
		
		myPanel.setSize(380, 520);
		myPanel.setLocation(10, 25);
		myPanel.setOpaque(false);
		myPanel.setVisible(true);

		myPanel.add(new JLabel("학번"));
		tf_id = new JTextField(23);
		myPanel.add(tf_id);
		
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
		myPanel.add(btnSelect);
		
		
		myPanel.add(new JLabel("이름"));
		tf_name = new JTextField(21);
		myPanel.add(tf_name);
		
		DialogMajors dMajor = new DialogMajors(myThis, this, "학과", conn);
		JButton btnMajor = new JButton("학과검색");
		btnMajor.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dMajor.tf_major.setText("");
				majorModel.setNumRows(0);
				dMajor.majorList();
				dMajor.setVisible(true);
			}
		});
		myPanel.add(btnMajor);
		
		myPanel.add(new JLabel("학과"));
		tf_dept = new JTextField(29);
		tf_dept.setEditable(false);
		myPanel.add(tf_dept);
		
		myPanel.add(new JLabel("주소"));
		tf_address = new JTextField(29);
		myPanel.add(tf_address);
		
		
		
		String colName [] = {"학번", "이름", "학과", "주소", ""};
		model = new DefaultTableModel(colName, 0) {
			private static final long serialVersionUID = 6635L;
			public boolean isCellEditable(int i, int c){ return false; } // 컬럼에서 수정불가
		};
		table = new JTable(model);
		// 컬럼 숨기기
		table.getColumnModel().getColumn(4).setWidth(0);
		table.getColumnModel().getColumn(4).setMinWidth(0);
		table.getColumnModel().getColumn(4).setMaxWidth(0);
		//컬럼 이동 막기
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setResizingAllowed(false);
		table.setPreferredScrollableViewportSize(new Dimension(350, 270));
		myPanel.add(new JScrollPane(table));
		
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
				majorNumber = (String)model.getValueAt(table.getSelectedRow(), 4);
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
		myPanel.add(btnInsert);
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
		myPanel.add(btnList);
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
		myPanel.add(btnUpdate);
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
		myPanel.add(btnDelete);
		
		add(myPanel);
		
		list(CRUD.SELECT, null);
		
		setLocation(90, 20);
		setSize(400, 500);
		setVisible(true);
	}
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		ImageIcon iconImg = new ImageIcon("img/curlyleafframe.png");
		Image originImg = iconImg.getImage(); 
		
		Image changedImg= originImg.getScaledInstance(400, 500, Image.SCALE_SMOOTH );
		
		ImageIcon Icon = new ImageIcon(changedImg);
		
		g.drawImage(Icon.getImage(), 0, 0, null);
		setOpaque(false);
		super.paintComponent(g);
	}
	
	public void list(CRUD type, String where) {
		
		ResultSet rs = null;
		model.setNumRows(0);
		try {
			stmt = conn.createStatement();
			
			switch(type) {
			case SELECT:
				if(where == null) {
					rs = stmt.executeQuery("SELECT Student.Student_id, Student.Student_name, Majors.Major_name, Student.Student_address, Majors.Major_id FROM Student, Majors WHERE Student.Student_dept = Majors.Major_id");
					
					while(rs.next()) {
						String [] row = new String[5];
						row[0] = rs.getString("Student_id");
						row[1] = rs.getString("Student_name");
						row[2] = rs.getString("Major_name");
						row[3] = rs.getString("Student_address");
						row[4] = rs.getString("Major_id");
						model.addRow(row);
					}
					
				} else {
					rs = stmt.executeQuery("SELECT Student.Student_id, Student.Student_name, Majors.Major_name, Student.Student_address, Majors.Major_id FROM Student, Majors WHERE Student.Student_dept = Majors.Major_id AND Student_id = '" + where + "'");
					
					String [] row = new String[5];
					if(rs.next()) {
						row[0] = rs.getString("Student_id");
						row[1] = rs.getString("Student_name");
						row[2] = rs.getString("Major_name");
						row[3] = rs.getString("Student_address");
						row[4] = rs.getString("Major_id");
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
					stmt.executeUpdate("INSERT INTO Student(Student_id, Student_name, Student_dept, Student_address) VALUES ('"
							+ 	tf_id.getText() +"', '"+ tf_name.getText() + "', '" + majorNumber + "', '" + tf_address.getText() + "')");
				} catch (Exception e2) {
					// TODO: handle exception
				}
				break;
			case UPDATE:
				try {
					stmt.executeUpdate("UPDATE Student SET Student_name = '" + tf_name.getText() + "', Student_dept = '" + majorNumber + "', Student_address = '" + tf_address.getText() + "' WHERE Student_id = '" + tf_id.getText() + "'");
					
				} catch (Exception e2) {
					// TODO: handle exception
				}
				break;
			case DELETE:
				try {
					rs = stmt.executeQuery("CALL delete_Student('" + tf_id.getText() + "')");
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
	
	
	class DialogMajors extends JDialog {
		private static final long serialVersionUID = 342L;
		JPanel dialogPanel = null;
		Connection conn = null;
		Statement stmt = null;
		
		JTextField tf_major = null;
		
		public DialogMajors(Haksa haksaThis, MemberList MemberThis, String title, Connection conn) {
			// TODO Auto-generated constructor stub
			super(myThis, title, true);
			this.conn = conn;
			setTitle(title);
			setLayout(null);
			dialogPanel = new JPanel();
			dialogPanel.setLayout(null);
			dialogPanel.setSize(280, 280);
			//sdialogPanel.setBorder(new LineBorder(Color.BLACK));
			
			JLabel Label_major = new JLabel("학과");
			Label_major.setBounds(10, 10, 36, 22);
			//Label_major.setBorder(new LineBorder(Color.BLACK));
			dialogPanel.add(Label_major);
			tf_major = new JTextField(28);
			tf_major.setBounds(40, 10, 170, 22);
			tf_major.setEditable(false);
			dialogPanel.add(tf_major);
			
			String colName [] = {"학과번호", "학과"};
			majorModel = new DefaultTableModel(colName, 0){
				private static final long serialVersionUID = 6605L;
				public boolean isCellEditable(int i, int c){ return false; }
			};
			majorTable = new JTable(majorModel);
			// 컬럼 크기 변경 불가
			majorTable.getTableHeader().setReorderingAllowed(false);
			majorTable.getTableHeader().setResizingAllowed(false);
			majorTable.setPreferredScrollableViewportSize(new Dimension(265, 200));
			
			majorTable.addMouseListener(new MouseListener() {
				
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
					// TODO Auto-generated method stub
					JTable table = (JTable)e.getComponent();
					DefaultTableModel model = (DefaultTableModel)table.getModel();
					majorNumber = (String)model.getValueAt(table.getSelectedRow(), 0);
					String name = (String)model.getValueAt(table.getSelectedRow(), 1);
					tf_major.setText(name);
				}
			});
			
			JScrollPane tableSP = new JScrollPane(majorTable);
			tableSP.setBounds(10, 50, 265, 200);
			dialogPanel.add(tableSP);
			
		
			JButton btnSelect = new JButton("선택");
			btnSelect.setBounds(215, 8, 60, 25);
			btnSelect.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					if(tf_major.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "학과가 선택되지 않았습니다.", "Message", JOptionPane.ERROR_MESSAGE);
					} else {
						MemberThis.tf_dept.setText(tf_major.getText());
						
						setVisible(false);
					}
					
				}
			});
			dialogPanel.add(btnSelect);
			
			
			add(dialogPanel);
			setSize(300, 300);
		}
		
		public void majorList() {
			try {
				stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM Majors");
				
				while(rs.next()) {
					String [] row = new String[2];
					row[0] = rs.getString("Major_id");
					row[1] = rs.getString("Major_name");
					majorModel.addRow(row);
				}
				rs.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
}
