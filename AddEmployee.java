package com.EmployeeManagement.System;

import java.awt.*;
import javax.swing.*;
import com.toedter.calendar.JDateChooser;
import java.awt.event.*;

public class AddEmployee extends JFrame implements ActionListener {

    JTextField tfname, tfaddress, tfsalary, tfdesignation, tfphone, tfemail, tfusername, tfpassword, tfeducation;
    JDateChooser dcdob;
    JButton add, back;
    JComboBox cbeducation;

    AddEmployee() {
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        JLabel heading = new JLabel("Add Employee Detail");
        heading.setBounds(320, 30, 500, 50);
        heading.setFont(new Font("SAN_SERIF", Font.BOLD, 25));
        add(heading);

        JLabel labelname = new JLabel("Name");
        labelname.setBounds(50, 150, 150, 30);
        labelname.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelname);

        tfname = new JTextField();
        tfname.setBounds(200, 150, 150, 30);
        add(tfname);

        JLabel labeldob = new JLabel("Date of Birth");
        labeldob.setBounds(400, 150, 150, 30);
        labeldob.setFont(new Font("serif", Font.PLAIN, 20));
        add(labeldob);

        dcdob = new JDateChooser();
        dcdob.setBounds(600, 150, 150, 30);
        add(dcdob);

        JLabel labeladdress = new JLabel("Address");
        labeladdress.setBounds(50, 200, 150, 30);
        labeladdress.setFont(new Font("serif", Font.PLAIN, 20));
        add(labeladdress);

        tfaddress = new JTextField();
        tfaddress.setBounds(200, 200, 150, 30);
        add(tfaddress);

        JLabel labelsalary = new JLabel("Salary");
        labelsalary.setBounds(400, 200, 150, 30);
        labelsalary.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelsalary);

        tfsalary = new JTextField();
        tfsalary.setBounds(600, 200, 150, 30);
        add(tfsalary);

        JLabel labeldesignation = new JLabel("Designation");
        labeldesignation.setBounds(50, 250, 150, 30);
        labeldesignation.setFont(new Font("serif", Font.PLAIN, 20));
        add(labeldesignation);

        tfdesignation = new JTextField();
        tfdesignation.setBounds(200, 250, 150, 30);
        add(tfdesignation);

        JLabel labelphone = new JLabel("Phone");
        labelphone.setBounds(400, 250, 150, 30);
        labelphone.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelphone);

        tfphone = new JTextField();
        tfphone.setBounds(600, 250, 150, 30);
        add(tfphone);

        JLabel labelemail = new JLabel("Email");
        labelemail.setBounds(50, 300, 150, 30);
        labelemail.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelemail);

        tfemail = new JTextField();
        tfemail.setBounds(200, 300, 150, 30);
        add(tfemail);

        JLabel labeleducation = new JLabel("Education");
        labeleducation.setBounds(400, 300, 150, 30);
        labeleducation.setFont(new Font("serif", Font.PLAIN, 20));
        add(labeleducation);
        
        String courses[] = {"BBA", "BCA", "BA", "BSC", "B.COM", "BTech", "MBA", "MCA", "MA", "MTech", "MSC", "PHD"};
        cbeducation = new JComboBox(courses);
        cbeducation.setBackground(Color.WHITE);
        cbeducation.setBounds(600, 300, 150, 30);
        add(cbeducation);

//        tfeducation = new JTextField();
//        tfeducation.setBounds(600, 300, 150, 30);
//        add(tfeducation);

        JLabel labelusername = new JLabel("Username");
        labelusername.setBounds(50, 350, 150, 30);
        labelusername.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelusername);

        tfusername = new JTextField();
        tfusername.setBounds(200, 350, 150, 30);
        add(tfusername);

        JLabel labelpassword = new JLabel("Password");
        labelpassword.setBounds(400, 350, 150, 30);
        labelpassword.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelpassword);

        tfpassword = new JTextField();
        tfpassword.setBounds(600, 350, 150, 30);
        add(tfpassword);

        add = new JButton("Add Details");
        add.setBounds(250, 510, 150, 40);
        add.setBackground(Color.BLACK);
        add.setForeground(Color.WHITE);
        add.addActionListener(this);
        add(add);

        back = new JButton("Back");
        back.setBounds(450, 510, 150, 40);
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        back.addActionListener(this);
        add(back);

        setSize(900, 650);
        setLocation(300, 70);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == add) {
            String name = tfname.getText().trim();
            String dob = ((JTextField) dcdob.getDateEditor().getUiComponent()).getText().trim();
            String address = tfaddress.getText().trim();
            String salary = tfsalary.getText().trim();
            String designation = tfdesignation.getText().trim();
            String phone = tfphone.getText().trim();
            String email = tfemail.getText().trim();
            String education = (String) cbeducation.getSelectedItem();
            String username = tfusername.getText().trim();
            String password = tfpassword.getText().trim();

            // Validate required fields
            if (name.isEmpty() || dob.isEmpty() || address.isEmpty() || salary.isEmpty() ||
                designation.isEmpty() || phone.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "All fields are required!");
                return;
            }

            // Validate email format
            if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
                JOptionPane.showMessageDialog(null, "Invalid email address!");
                return;
            }

            // Validate phone number
            if (!phone.matches("\\d{10}")) {
                JOptionPane.showMessageDialog(null, "Phone number must be exactly 10 digits!");
                return;
            }

            try {
                Conn conn = new Conn();
                String query = "INSERT INTO employees(name, dob, address, email, phone, salary, designation, education, username, password) VALUES ('" 
                        + name + "', '" + dob + "', '" + address + "', '" + email + "', '" + phone + "', '" + salary + "', '" + designation + "', '" + education + "', '" + username + "', '" + password + "')";
                conn.s.executeUpdate(query);
                JOptionPane.showMessageDialog(null, "Employee Details added successfully");
                setVisible(false);
                new Home();
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            }
        } else {
            setVisible(false);
            new Home();
        }
    }

   

    public static void main(String[] args) {
        new AddEmployee();
    }
}
