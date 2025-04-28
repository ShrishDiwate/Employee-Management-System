package com.EmployeeManagement.System;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class EmployeeHome extends JFrame implements ActionListener {
    JButton applyLeave, logout;
    String empId;

    EmployeeHome(String empId) {
        this.empId = empId;
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        JLabel heading = new JLabel("Employee Dashboard");
        heading.setBounds(100, 20, 200, 30);
        heading.setFont(new Font("Serif", Font.BOLD, 20));
        add(heading);

        applyLeave = new JButton("Apply Leave");
        applyLeave.setBounds(120, 80, 150, 40);
        applyLeave.addActionListener(this);
        add(applyLeave);

        logout = new JButton("Logout");
        logout.setBounds(120, 150, 150, 40);
        logout.addActionListener(this);
        add(logout);

        setSize(400, 300);
        setLocation(500, 250);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == applyLeave) {
            setVisible(false);
            new LeaveManagement(empId);
        } else if (ae.getSource() == logout) {
            setVisible(false);
            new Login();
        }
    }

    public static void main(String[] args) {
        new EmployeeHome("101"); // Example employee ID
    }
}
