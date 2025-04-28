package com.EmployeeManagement.System;

import javax.swing.*;
import javax.swing.JFrame;
import java.awt.event.*;
import java.awt.*;

public class Splash extends JFrame implements ActionListener  {
	    
	    Splash() {
	        
	        getContentPane().setBackground(Color.WHITE);
	        setLayout(null);
	        
	        JLabel heading = new JLabel("EMPLOYEE MANAGEMENT SYSTEM");
	        heading.setBounds(150, 30, 1200, 60);
	        heading.setFont(new Font("Garamond", Font.PLAIN, 50));
	        heading.setForeground(Color.RED);
	        add(heading);
	        
	        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/sh.jpg"));
	        Image i2 = i1.getImage().getScaledInstance(1100, 700, Image.SCALE_DEFAULT);
	        ImageIcon i3 = new ImageIcon(i2);
	        JLabel image = new JLabel(i3);
	        image.setBounds(50, 100, 1050, 500);
	        add(image);
	        
	        JButton clickhere = new JButton("CLICK HERE TO CONTINUE");
	        clickhere.setBounds(360, 400, 300, 70);
	        clickhere.setBackground(Color.BLACK);
	        clickhere.setForeground(Color.WHITE);
	        clickhere.addActionListener(this);
	        image.add(clickhere);
	        
	        
	        setSize(1170, 650);
	        setTitle("Employee ManageMent");
	        setLocation(200, 50);
	        setVisible(true);
	        setDefaultCloseOperation(EXIT_ON_CLOSE);
	        
//	        while(true) {
//	            heading.setVisible(false);
//	            try {
//	                Thread.sleep(500);
//	            } catch (Exception e){
//	                
//	            }
//	            
//	            heading.setVisible(true);
//            try {
//	                Thread.sleep(500);
//	            } catch (Exception e){
//	                
//	            }
//	        }
	    }
	    
	    public void actionPerformed(ActionEvent ae) {
	        setVisible(false);
	        new Login();
	    }
	    
	    public static void main(String args[]) {
	        new Splash();
	    }
}
