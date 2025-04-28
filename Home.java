package com.EmployeeManagement.System;

import javax.swing.*;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.*;

public class Home extends JFrame implements ActionListener {
    JLabel heading;
    JButton view, add, update, remove, leaveRequests, logout;

    Home() {
        setLayout(null);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/Home.jpg"));
        Image i2 = i1.getImage().getScaledInstance(1150, 650, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(0, 0, 1120, 630);
        add(image);

        heading = new JLabel("Employee Management System");
        heading.setBounds(620, 20, 400, 40);
        heading.setFont(new Font("Raleway", Font.BOLD, 25));
        image.add(heading);

        add = new JButton("Add Employee");
        add.setBounds(650, 80, 150, 40);
        add.addActionListener(this);
        image.add(add);

        view = new JButton("View Employees");
        view.setBounds(820, 80, 150, 40);
        view.addActionListener(this);
        image.add(view);

        update = new JButton("Update Employee");
        update.setBounds(650, 140, 150, 40);
        update.addActionListener(this);
        image.add(update);

        remove = new JButton("Remove Employee");
        remove.setBounds(820, 140, 150, 40);
        remove.addActionListener(this);
        image.add(remove);

        leaveRequests = new JButton("Leave Requests");
        leaveRequests.setBounds(735, 200, 150, 40);
        leaveRequests.addActionListener(this);
        image.add(leaveRequests);

        logout = new JButton("Logout");
        logout.setBounds(735, 260, 150, 40);
        logout.addActionListener(this);
        image.add(logout);

        setSize(1120, 630);
        setTitle("Employee Management System");
        setLocation(250, 100);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == add) {
            setVisible(false);
            new AddEmployee();
        } else if (ae.getSource() == view) {
            setVisible(false);
            new ViewEmployee();
        } else if (ae.getSource() == update) {
            setVisible(false);
            new ViewEmployee();
        } else if (ae.getSource() == remove) {
            setVisible(false);
            new RemoveEmployee();
        } else if (ae.getSource() == leaveRequests) {
            setVisible(false);
            new AdminLeaveApproval();
        } else if (ae.getSource() == logout) {
            setVisible(false);
            new Login();
        }
    }

    public static void main(String[] args) {
        new Home();
    }
}
