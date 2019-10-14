package com.Haksas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

public class Registration extends JPanel{
	private static final long serialVersionUID = 187L;
	Connection conn = null;
	Statement stmt = null;
	Haksa myThis = null;
	DefaultTableModel beforeModel = null;
	JTable beforeTable = null;
	DefaultTableModel AfterModel = null;
	JTable AfterTable = null;
	
	
	public Registration(Haksa myThis, Connection conn) {
		// TODO Auto-generated constructor stub
		this.myThis = myThis;
		this.conn = conn;
		setLayout(new FlowLayout(FlowLayout.LEFT));
		
		JLabel label_MemberNo = new JLabel("학번");
		add(label_MemberNo);
		JTextField tf_MemberNo = new JTextField(10);
		add(tf_MemberNo);
		JLabel label_Re1 = new JLabel("                                                    ");
		add(label_Re1);
		JButton btn_Select = new JButton("검색");
		btn_Select.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		add(btn_Select);
		JLabel label_MemberName = new JLabel("이름");
		add(label_MemberName);
		JTextField tf_MemberName = new JTextField(10);
		add(tf_MemberName);
		JLabel label_Re2 = new JLabel("                                                                  ");
		add(label_Re2);
		JLabel label_Memberdept = new JLabel("학과");
		add(label_Memberdept);
		JTextField tf_Memberdept = new JTextField(15);
		tf_Memberdept.setEditable(false);
		add(tf_Memberdept);
		JLabel label_Re3 = new JLabel("                          ");
		add(label_Re3);
		JButton btn_Regist = new JButton("수강신청");
		btn_Regist.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		add(btn_Regist);
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
		beforeTable.setPreferredScrollableViewportSize(new Dimension(160, 340));
		add(new JScrollPane(beforeTable));
		
		JButton btn_next = new JButton("◀");
		btn_next.setMargin(new Insets(0, 0, 0, 0));
		btn_next.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		add(btn_next);
		JButton btn_prov = new JButton("▶");
		btn_prov.setMargin(new Insets(0, 0, 0, 0));
		btn_prov.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		add(btn_prov);
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
		AfterTable.setPreferredScrollableViewportSize(new Dimension(160, 340));
		add(new JScrollPane(AfterTable));
		setBorder(new LineBorder(Color.BLACK));
		setSize(384, 460);
		setVisible(true);
	}
	
	
}
