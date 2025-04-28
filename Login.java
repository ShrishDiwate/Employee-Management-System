package com.EmployeeManagement.System;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Login extends JFrame implements ActionListener {
    JTextField tfusername;
    JPasswordField tfpassword;
    JButton login;
    
    Login() {
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        JLabel username = new JLabel("Username");
        username.setBounds(40, 40, 100, 30);
        username.setFont(new Font("Arial", Font.BOLD, 15));
        add(username);

        JLabel password = new JLabel("Password");
        password.setBounds(40, 90, 100, 30);
        password.setFont(new Font("Arial", Font.BOLD, 15));
        add(password);

        tfusername = new JTextField();
        tfusername.setBounds(150, 42, 150, 30);
        add(tfusername);

        tfpassword = new JPasswordField();
        tfpassword.setBounds(150, 92, 150, 30);
        add(tfpassword);
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/logg.jpeg"));
        Image i2 = i1.getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(350, 50, 200, 200);
        add(image);

        login = new JButton("LOGIN");
        login.setBounds(150, 140, 150, 30);
        login.setBackground(Color.BLACK);
        login.setForeground(Color.WHITE);
        login.addActionListener(this);
        add(login);

        setSize(600, 350);
        setTitle("Login");
        setVisible(true);
        setLocation(450, 200);
    }

    public void actionPerformed(ActionEvent ae) {
        try {
            String username = tfusername.getText();
            String password = new String(tfpassword.getPassword());

            Conn c = new Conn();
            String adminQuery = "SELECT * FROM admin WHERE username = '" + username + "' AND password = '" + password + "'";
            String empQuery = "SELECT * FROM employees WHERE username = '" + username + "' AND password = '" + password + "'";

            ResultSet rsAdmin = c.s.executeQuery(adminQuery);
            if (rsAdmin.next()) {
                setVisible(false);
                new Home(); // Admin panel
                return;
            }

            ResultSet rsEmp = c.s.executeQuery(empQuery);
            if (rsEmp.next()) {
                String empId = rsEmp.getString("empId");
                setVisible(false);
                new EmployeeHome(empId); // Employee panel
                return;
            }

            JOptionPane.showMessageDialog(null, "Invalid username or password");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Login();
    }
}
