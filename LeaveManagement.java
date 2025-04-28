package com.EmployeeManagement.System;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LeaveManagement extends JFrame implements ActionListener {
    JTextField tfStartDate, tfEndDate;
    JTextArea taReason;
    JButton applyLeave, back;
    String empId;
    JComboBox<String> cbSubstitute;
    JLabel lblEmployeeName, lblSubstitute;

    LeaveManagement(String empId) {
        this.empId = empId;
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        JLabel heading = new JLabel("Apply for Leave");
        heading.setBounds(150, 20, 300, 30);
        heading.setFont(new Font("Serif", Font.BOLD, 20));
        add(heading);

        JLabel lblSelectedEmployee = new JLabel("Employee ID:");
        lblSelectedEmployee.setBounds(50, 70, 150, 30);
        add(lblSelectedEmployee);

        lblEmployeeName = new JLabel(empId);
        lblEmployeeName.setBounds(220, 70, 200, 30);
        add(lblEmployeeName);

        JLabel lblStartDate = new JLabel("Start Date (YYYY-MM-DD):");
        lblStartDate.setBounds(50, 120, 200, 30);
        add(lblStartDate);

        tfStartDate = new JTextField();
        tfStartDate.setBounds(220, 120, 150, 30);
        add(tfStartDate);

        JLabel lblEndDate = new JLabel("End Date (YYYY-MM-DD):");
        lblEndDate.setBounds(50, 170, 200, 30);
        add(lblEndDate);

        tfEndDate = new JTextField();
        tfEndDate.setBounds(220, 170, 150, 30);
        add(tfEndDate);

        lblSubstitute = new JLabel("Suggested Substitute:");
        lblSubstitute.setBounds(50, 220, 200, 30);
        add(lblSubstitute);

        cbSubstitute = new JComboBox<>();
        cbSubstitute.setBounds(220, 220, 200, 30);
        cbSubstitute.addItem("-- Optional --");
        populateSubstituteComboBox();
        add(cbSubstitute);

        JLabel lblReason = new JLabel("Reason:");
        lblReason.setBounds(50, 270, 100, 30);
        add(lblReason);

        taReason = new JTextArea();
        taReason.setLineWrap(true);
        taReason.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(taReason);
        scrollPane.setBounds(220, 270, 200, 100);
        add(scrollPane);

        applyLeave = new JButton("Apply Leave");
        applyLeave.setBounds(100, 400, 150, 40);
        applyLeave.addActionListener(this);
        add(applyLeave);

        back = new JButton("Back");
        back.setBounds(270, 400, 150, 40);
        back.addActionListener(this);
        add(back);

        setSize(500, 500);
        setLocation(490, 200);
        setVisible(true);
    }

    private void populateSubstituteComboBox() {
        try {
            cbSubstitute.removeAllItems();
            cbSubstitute.addItem("-- Optional --");

            Conn conn = new Conn();
            String query = "SELECT empId, name, designation FROM employees WHERE empId != '" + empId + "' ORDER BY name";
            ResultSet rs = conn.s.executeQuery(query);

            while (rs.next()) {
                String id = rs.getString("empId");
                String name = rs.getString("name");
                String desig = rs.getString("designation");
                cbSubstitute.addItem(id + " - " + name + " (" + desig + ")");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == applyLeave) {
            String startDate = tfStartDate.getText().trim();
            String endDate = tfEndDate.getText().trim();
            String reason = taReason.getText().trim();

            if (startDate.isEmpty() || endDate.isEmpty() || reason.isEmpty()) {
                JOptionPane.showMessageDialog(null, "All fields are required!");
                return;
            }

            if (!isValidDateFormat(startDate) || !isValidDateFormat(endDate)) {
                JOptionPane.showMessageDialog(null, "Use YYYY-MM-DD format for dates");
                return;
            }

            if (!isEndDateAfterStartDate(startDate, endDate)) {
                JOptionPane.showMessageDialog(null, "End date must be after or same as start date");
                return;
            }

            try {
                String substituteEmpId = null;
                if (cbSubstitute.getSelectedIndex() > 0) {
                    String selected = (String) cbSubstitute.getSelectedItem();
                    substituteEmpId = selected.split(" - ")[0];
                }

                Conn conn = new Conn();
                String query;
                if (substituteEmpId != null) {
                    query = "INSERT INTO leave_requests (empId, start_date, end_date, reason, status, substitute_empId, substitute_assigned) VALUES ('"
                            + empId + "', '" + startDate + "', '" + endDate + "', '" + reason + "', 'Pending', '" + substituteEmpId + "', TRUE)";
                } else {
                    query = "INSERT INTO leave_requests (empId, start_date, end_date, reason, status) VALUES ('"
                            + empId + "', '" + startDate + "', '" + endDate + "', '" + reason + "', 'Pending')";
                }

                conn.s.executeUpdate(query);
                JOptionPane.showMessageDialog(null, "Leave Request Submitted");
                setVisible(false);
                new EmployeeHome(empId);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            }
        } else {
            setVisible(false);
            new EmployeeHome(empId);
        }
    }

    private boolean isValidDateFormat(String date) {
        return date.matches("\\d{4}-\\d{2}-\\d{2}");
    }

    private boolean isEndDateAfterStartDate(String start, String end) {
        try {
            java.sql.Date startDate = java.sql.Date.valueOf(start);
            java.sql.Date endDate = java.sql.Date.valueOf(end);
            return !endDate.before(startDate);
        } catch (Exception e) {
            return false;
        }
    }

    public static void main(String[] args) {
        new LeaveManagement("101"); // Example
    }
}
