package com.Haksas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;


public class Registration extends JPanel{
	private static final long serialVersionUID = 187L;
	Connection conn = null;
	Statement stmt = null;
	Haksa myThis = null;
	
	JPanel myPanel = new JPanel();
	
	DefaultTableModel beforeModel = null;
	JTable beforeTable = null;
	DefaultTableModel AfterModel = null;
	JTable AfterTable = null;
	
	JTextField tf_MemberNo = null;
	JTextField tf_MemberName = null;
	JTextField tf_Memberdept = null;
	
	private enum SUBJ { SELECT, STUDENTLIST, REGIST, DELETE };
	
	public Registration(Haksa myThis, Connection conn) {
		// TODO Auto-generated constructor stub
		this.myThis = myThis;
		this.conn = conn;
		
		
		setLayout(null);
		myPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		myPanel.setSize(384, 460);
		myPanel.setLocation(30, 20);
		myPanel.setOpaque(false);
		myPanel.setVisible(true);
		
		JLabel label_MemberNo = new JLabel("학번");
		myPanel.add(label_MemberNo);
		tf_MemberNo = new JTextField(10);
		myPanel.add(tf_MemberNo);
		JButton btn_Clear = new JButton("비우기");
		btn_Clear.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				tf_MemberNo.setText("");
				tf_MemberName.setText("");
				tf_Memberdept.setText("");
				beforeModel.setNumRows(0);
				AfterModel.setNumRows(0);
			}
		});
		myPanel.add(btn_Clear);
		JLabel label_Re1 = new JLabel("                           ");
		myPanel.add(label_Re1);
		JButton btn_Select = new JButton("검색");
		btn_Select.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				if(	tf_MemberNo.getText().equals("") && tf_MemberName.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "전부 입력되지 않았습니다.", "Message", JOptionPane.ERROR_MESSAGE);
				} else {
					String sql = "CALL Select_Subject('" + tf_MemberNo.getText() + "', '" + tf_MemberName.getText() + "')";
					query(SUBJ.SELECT, sql);
					
				}
				
			}
		});
		myPanel.add(btn_Select);
		JLabel label_MemberName = new JLabel("이름");
		myPanel.add(label_MemberName);
		tf_MemberName = new JTextField(10);
		myPanel.add(tf_MemberName);
		
		JLabel label_Re2 = new JLabel("                                                                      ");
		myPanel.add(label_Re2);
		JLabel label_Memberdept = new JLabel("학과");
		myPanel.add(label_Memberdept);
		tf_Memberdept = new JTextField(15);
		tf_Memberdept.setEditable(false);
		myPanel.add(tf_Memberdept);
		JLabel label_Re3 = new JLabel("                          ");
		myPanel.add(label_Re3);
		JButton btn_Regist = new JButton("수강신청");
		btn_Regist.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int result = JOptionPane.showConfirmDialog(null, "이대로 수강신청을 하시겠습니까?", "Message", JOptionPane.YES_NO_OPTION);
				switch(result) {
				case JOptionPane.OK_OPTION:
					JOptionPane.showMessageDialog(null, "수강신청이 완료되었습니다.", "Message", JOptionPane.OK_OPTION);
					
					System.out.println("asdasdsad");
					int numRows = AfterTable.getRowCount();
					
					// 여기 에러 찾아봐야됨
					 for(int i=0; i<numRows ; i++ ) {
						String row = (String)AfterModel.getValueAt(i, 1);
						
						String sql = "INSERT INTO Grades(Grade_studentId, Grade_SubjId, Grade_rank) VALUES ('" + tf_MemberNo.getText() + "', '" + row + "', DEFAULT);";
						query(SUBJ.REGIST, sql);
					
					 }
					 AfterModel.setNumRows(0);
					 beforeModel.setNumRows(0);
					break;
				case JOptionPane.NO_OPTION: return;
				}
			}
		});
		
		myPanel.add(btn_Regist);
		String colName1 [] = {"학과", ""};
		beforeModel = new DefaultTableModel(colName1, 0) {
			private static final long serialVersionUID = 6635L;
			public boolean isCellEditable(int i, int c){ return false; } // 컬럼에서 수정불가
		};
		beforeTable = new JTable(beforeModel);
		// 컬럼 숨기기
		beforeTable.getColumnModel().getColumn(1).setWidth(0);
		beforeTable.getColumnModel().getColumn(1).setMinWidth(0);
		beforeTable.getColumnModel().getColumn(1).setMaxWidth(0);
		//컬럼 이동 막기
		beforeTable.getTableHeader().setReorderingAllowed(false);
		beforeTable.getTableHeader().setResizingAllowed(false);
		
		beforeTable.setRowSelectionAllowed(true);
		beforeTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		beforeTable.setPreferredScrollableViewportSize(new Dimension(160, 340));
		myPanel.add(new JScrollPane(beforeTable));
		
		JButton btn_next = new JButton("◀");
		btn_next.setMargin(new Insets(0, 0, 0, 0));
		btn_next.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int numRows = AfterTable.getSelectedRows().length;
				 
				 for(int i=0; i<numRows ; i++ ) {
					String [] row = new String[2];
					row[0] = (String)AfterModel.getValueAt(AfterTable.getSelectedRow(), 0);
					row[1] = (String)AfterModel.getValueAt(AfterTable.getSelectedRow(), 1);
					beforeModel.addRow(row);
					AfterModel.removeRow(AfterTable.getSelectedRow());
				 }
			}
		});
		myPanel.add(btn_next);
		JButton btn_prov = new JButton("▶");
		btn_prov.setMargin(new Insets(0, 0, 0, 0));
		btn_prov.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
 
				int numRows = beforeTable.getSelectedRows().length;
				 
				for(int i=0; i<numRows ; i++ ) {
					String [] row = new String[2];
					row[0] = (String)beforeModel.getValueAt(beforeTable.getSelectedRow(), 0);
					row[1] = (String)beforeModel.getValueAt(beforeTable.getSelectedRow(), 1);
					AfterModel.addRow(row);
					beforeModel.removeRow(beforeTable.getSelectedRow());
				 }

			}
		});
		
		myPanel.add(btn_prov);
		String colName2 [] = {"등록", ""};
		AfterModel = new DefaultTableModel(colName2, 0) {
			private static final long serialVersionUID = 6635L;
			public boolean isCellEditable(int i, int c){ return false; } // 컬럼에서 수정불가
		};
		AfterTable = new JTable(AfterModel);
		// 컬럼 숨기기
		AfterTable.getColumnModel().getColumn(1).setWidth(0);
		AfterTable.getColumnModel().getColumn(1).setMinWidth(0);
		AfterTable.getColumnModel().getColumn(1).setMaxWidth(0);
		//컬럼 이동 막기
		AfterTable.getTableHeader().setReorderingAllowed(false);
		AfterTable.getTableHeader().setResizingAllowed(false);
		
		AfterTable.setRowSelectionAllowed(true);
		AfterTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		AfterTable.setPreferredScrollableViewportSize(new Dimension(160, 340));
		myPanel.add(new JScrollPane(AfterTable));

		add(myPanel);
		setBorder(new LineBorder(Color.BLACK));
		setLocation(65, 20);
		setSize(450, 500);
		setVisible(true);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		ImageIcon iconImg = new ImageIcon("img/curlyleafframe.png");
		Image originImg = iconImg.getImage(); 
		
		Image changedImg= originImg.getScaledInstance(450, 500, Image.SCALE_SMOOTH );
		
		ImageIcon Icon = new ImageIcon(changedImg);
		
		g.drawImage(Icon.getImage(), 0, 0, null);
		//setOpaque(false);
		super.paintComponent(g);
	}
	
	public void query(SUBJ vSubj , String sql) {
		
		ResultSet rs = null;
		try {	
			stmt = conn.createStatement();
			
			switch(vSubj) {
			case SELECT:
				rs = stmt.executeQuery(sql);
				while(rs.next()) {
					String [] row = new String[2];
					row[0] = rs.getString("Subject_name");
					row[1] = rs.getString("Subject_id");
					beforeModel.addRow(row);
				}
				ResultSet rs1 = null;
				stmt = conn.createStatement();
				if(!tf_MemberNo.getText().equals("")) {
					rs1 = stmt.executeQuery("Select Student.Student_id, Student.Student_name, Majors.Major_name from Student, Majors where Student.Student_dept = Majors.Major_id and Student_id = '" + tf_MemberNo.getText() + "'");
				} else if (!tf_MemberName.getText().equals("")) {
					rs1 = stmt.executeQuery("Select Student.Student_id, Student.Student_name, Majors.Major_name from Student, Majors where Student.Student_dept = Majors.Major_id and Student_name = '" + tf_MemberName.getText() + "'");
				}
				rs1.next();
				
				tf_MemberNo.setText(rs1.getString("Student_id")); 
				tf_MemberName.setText(rs1.getString("Student_name"));
				tf_Memberdept.setText(rs1.getString("Major_name"));
				
				rs1.close();
				break;
			case STUDENTLIST:
				
				
				break;
			case REGIST:
				stmt.executeUpdate(sql);
				break;
			case DELETE:
				break;
			default:
				break;
			}
		rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
