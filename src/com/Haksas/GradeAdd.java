package com.Haksas;


import java.sql.Connection;
import java.sql.Statement;

import javax.swing.JPanel;


public class GradeAdd extends JPanel {
	private static final long serialVersionUID = 45L;
	Connection conn = null;
	Statement stmt = null;
	
	public GradeAdd(Connection conn) {
		// TODO Auto-generated constructor stub
		this.conn = conn;
		
		
		
		
		//setBorder(new LineBorder(Color.BLACK));
		setSize(584, 438);
		setVisible(true);
	}
	
}
