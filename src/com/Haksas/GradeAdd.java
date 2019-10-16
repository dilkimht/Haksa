package com.Haksas;


import java.awt.Color;
import java.sql.Connection;
import java.sql.Statement;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;


public class GradeAdd extends JPanel {
	private static final long serialVersionUID = 45L;
	Connection conn = null;
	Statement stmt = null;
	
	public GradeAdd(Connection conn) {
		// TODO Auto-generated constructor stub
		this.conn = conn;
		
		
		
		
		setBorder(new LineBorder(Color.BLACK));
		setSize(584, 439);
		setVisible(true);
	}
	
}
