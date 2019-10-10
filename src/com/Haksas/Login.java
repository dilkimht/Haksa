package com.Haksas;

import java.awt.FlowLayout;
import java.sql.Connection;
import java.sql.Statement;

import javax.swing.JPanel;

public class Login extends JPanel{
	private static final long serialVersionUID = 176L;
	Connection conn = null;
	Statement stmt = null;
	
	public Login(Connection conn) {
		// TODO Auto-generated constructor stub
		this.conn = conn;
		
		setLayout(new FlowLayout());
		
		
		
	}
	
	
	
}
