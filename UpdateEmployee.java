package com.EmployeeManagement.System;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class UpdateEmployee extends JFrame implements ActionListener {
    JTextField tfEducation, tfAddress, tfPhone, tfEmail, tfSalary, tfDesignation;
    JLabel lblEmpId, lblName, lblDob;
    JButton update, back;
    String empId;

    UpdateEmployee(String empId) {
        this.empId = empId;
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        JLabel heading = new JLabel("Update Employee Detail");
        heading.setBounds(320, 30, 500, 50);
        heading.setFont(new Font("SAN_SERIF", Font.BOLD, 25));
        add(heading);

        JLabel labelName = new JLabel("Name");
        labelName.setBounds(50, 150, 150, 30);
        labelName.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelName);

        lblName = new JLabel();
        lblName.setBounds(200, 150, 150, 30);
        add(lblName);

        JLabel labelDob = new JLabel("Date of Birth");
        labelDob.setBounds(50, 200, 150, 30);
        labelDob.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelDob);

        lblDob = new JLabel();
        lblDob.setBounds(200, 200, 150, 30);
        add(lblDob);

        JLabel labelSalary = new JLabel("Salary");
        labelSalary.setBounds(400, 200, 150, 30);
        labelSalary.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelSalary);

        tfSalary = new JTextField();
        tfSalary.setBounds(600, 200, 150, 30);
        add(tfSalary);

        JLabel labelAddress = new JLabel("Address");
        labelAddress.setBounds(50, 250, 150, 30);
        labelAddress.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelAddress);

        tfAddress = new JTextField();
        tfAddress.setBounds(200, 250, 150, 30);
        add(tfAddress);

        JLabel labelPhone = new JLabel("Phone");
        labelPhone.setBounds(400, 250, 150, 30);
        labelPhone.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelPhone);

        tfPhone = new JTextField();
        tfPhone.setBounds(600, 250, 150, 30);
        add(tfPhone);

        JLabel labelEmail = new JLabel("Email");
        labelEmail.setBounds(50, 300, 150, 30);
        labelEmail.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelEmail);

        tfEmail = new JTextField();
        tfEmail.setBounds(200, 300, 150, 30);
        add(tfEmail);

        JLabel labelEducation = new JLabel("Highest Education");
        labelEducation.setBounds(400, 300, 150, 30);
        labelEducation.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelEducation);

        tfEducation = new JTextField();
        tfEducation.setBounds(600, 300, 150, 30);
        add(tfEducation);

        JLabel labelDesignation = new JLabel("Designation");
        labelDesignation.setBounds(50, 350, 150, 30);
        labelDesignation.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelDesignation);

        tfDesignation = new JTextField();
        tfDesignation.setBounds(200, 350, 150, 30);
        add(tfDesignation);

        try {
            Conn c = new Conn();
            String query = "SELECT empId, name, dob, address, salary, phone, email, designation, education FROM employees WHERE empId = '" + empId + "'";
            ResultSet rs = c.s.executeQuery(query);
            if (rs.next()) {
                lblName.setText(rs.getString("name"));
                lblDob.setText(rs.getString("dob"));
                tfAddress.setText(rs.getString("address"));
                tfSalary.setText(rs.getString("salary"));
                tfPhone.setText(rs.getString("phone"));
                tfEmail.setText(rs.getString("email"));
                tfEducation.setText(rs.getString("education"));
                tfDesignation.setText(rs.getString("designation"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        update = new JButton("Update Details");
        update.setBounds(250, 500, 150, 40);
        update.addActionListener(this);
        update.setBackground(Color.BLACK);
        update.setForeground(Color.WHITE);
        add(update);

        back = new JButton("Back");
        back.setBounds(450, 500, 150, 40);
        back.addActionListener(this);
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        add(back);

        setSize(900, 600);
        setLocation(300, 70);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == update) {
            String salary = tfSalary.getText();
            String address = tfAddress.getText();
            String phone = tfPhone.getText();
            String email = tfEmail.getText();
            String education = tfEducation.getText();
            String designation = tfDesignation.getText();

            try {
                Conn conn = new Conn();
                String query = "UPDATE employees SET salary = '" + salary + "', address = '" + address + "', phone = '" + phone + "', email = '" + email + "', designation = '" + designation + "', education = '" + education + "' WHERE empId = '" + empId + "'";
                conn.s.executeUpdate(query);
                JOptionPane.showMessageDialog(null, "Details updated successfully");
                setVisible(false);
                new Home();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            setVisible(false);
            new Home();
        }
    }

    public static void main(String[] args) {
        new UpdateEmployee("");
    }
}