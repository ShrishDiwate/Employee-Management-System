package com.EmployeeManagement.System;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import net.proteanit.sql.DbUtils;

public class AdminLeaveApproval extends JFrame implements ActionListener {
    JTable table;
    JButton approve, reject, back, assignSubstitute;
    JComboBox<String> substituteEmployees;
    JLabel selectedLeaveInfo, substituteLabel;
    String selectedLeaveId;
    String selectedEmployeeName;
    String selectedEmployeeId;
    JCheckBox filterBox;

    AdminLeaveApproval() {
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        JLabel heading = new JLabel("Leave Requests");
        heading.setBounds(320, 20, 200, 30);
        heading.setFont(new Font("Serif", Font.BOLD, 20));
        add(heading);

        // Add filter for pending requests
        filterBox = new JCheckBox("Show only pending requests");
        filterBox.setBounds(50, 50, 200, 30);
        filterBox.setSelected(true);
        filterBox.addActionListener(e -> loadTableData());
        add(filterBox);

        table = new JTable();
        JScrollPane jsp = new JScrollPane(table);
        jsp.setBounds(50, 80, 700, 200);
        add(jsp);

        // Selected leave information panel
        selectedLeaveInfo = new JLabel("No leave request selected");
        selectedLeaveInfo.setBounds(50, 290, 700, 30);
        selectedLeaveInfo.setFont(new Font("Serif", Font.ITALIC, 14));
        add(selectedLeaveInfo);

        // Substitute employee section
        substituteLabel = new JLabel("Assign Substitute Employee:");
        substituteLabel.setBounds(50, 330, 200, 30);
        add(substituteLabel);

        substituteEmployees = new JComboBox<>();
        substituteEmployees.setBounds(250, 330, 250, 30);
        substituteEmployees.setEnabled(false);
        add(substituteEmployees);

        assignSubstitute = new JButton("Assign Substitute");
        assignSubstitute.setBounds(520, 330, 150, 30);
        assignSubstitute.addActionListener(this);
        assignSubstitute.setEnabled(false);
        add(assignSubstitute);

        
        approve = new JButton("Approve Leave");
        approve.setBounds(100, 380, 150, 40);
        approve.addActionListener(this);
        approve.setEnabled(false);
        add(approve);

        reject = new JButton("Reject Leave");
        reject.setBounds(270, 380, 150, 40);
        reject.addActionListener(this);
        reject.setEnabled(false);
        add(reject);

        back = new JButton("Back");
        back.setBounds(440, 380, 150, 40);
        back.addActionListener(this);
        add(back);

        loadTableData();

        // Add selection listener to the table or table row selection 
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = table.getSelectedRow();    
                if (selectedRow != -1) { 
                    selectedLeaveId = table.getValueAt(selectedRow, 0).toString();
                    selectedEmployeeId = table.getValueAt(selectedRow, 1).toString();
                    selectedEmployeeName = table.getValueAt(selectedRow, 2).toString();
                    updateSelectedLeaveInfo(selectedRow);
                    
                    // Enable buttons only for pending requests
                    String status = table.getValueAt(selectedRow, 6).toString();
                    boolean isPending = status.equals("Pending");
                    approve.setEnabled(isPending);
                    reject.setEnabled(isPending);
                    substituteEmployees.setEnabled(isPending);
                    assignSubstitute.setEnabled(isPending);
                    
                    // Refresh substitute employee list for the selected employee
                    populateSubstituteEmployees(selectedEmployeeId);
                }
            }
        });

        setSize(800, 500);
        setLocation(300, 200);
        setVisible(true);
    }

    private void updateSelectedLeaveInfo(int row) {
        try {
            String empId = table.getValueAt(row, 1).toString();
            String empName = table.getValueAt(row, 2).toString();
            String startDate = table.getValueAt(row, 3).toString();
            String endDate = table.getValueAt(row, 4).toString();
            String reason = table.getValueAt(row, 5).toString();
            String status = table.getValueAt(row, 6).toString();
            
            // Get substitute info if assigned
            String substituteInfo = "";
            if (row < table.getRowCount() && table.getColumnCount() > 7) {
                Object substituteObj = table.getValueAt(row, 7);
                if (substituteObj != null && !substituteObj.toString().equals("Not Assigned")) {
                    substituteInfo = " | Substitute: " + substituteObj.toString();
                }
            }
            
            selectedLeaveInfo.setText("<html>Selected: <b>" + empName + " (ID: " + empId + ")</b> | " +
                                     "From: " + startDate + " To: " + endDate + " | " +
                                     "Status: <b>" + status + "</b> | " +
                                     "Reason: " + reason + substituteInfo + "</html>");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadTableData() {
        try {
            Conn c = new Conn();
            String query = "SELECT lr.id, lr.empId, e.name, lr.start_date, lr.end_date, lr.reason, lr.status, " +
                          "IFNULL(s.name, 'Not Assigned') as substitute " +
                          "FROM leave_requests lr " +
                          "JOIN employees e ON lr.empId = e.empId " +
                          "LEFT JOIN employees s ON lr.substitute_empId = s.empId ";
                          
           
            if (filterBox.isSelected()) {
                query += "WHERE lr.status = 'Pending' ";
            }
            
            query += "ORDER BY lr.start_date";
            ResultSet rs = c.s.executeQuery(query);
            table.setModel(DbUtils.resultSetToTableModel(rs));
            
            // Format the table header
            table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
            
            // Set custom column widths
            if (table.getColumnCount() >= 8) {
                table.getColumnModel().getColumn(0).setPreferredWidth(30);  // ID
                table.getColumnModel().getColumn(1).setPreferredWidth(40);  // empId
                table.getColumnModel().getColumn(2).setPreferredWidth(100); // name
                table.getColumnModel().getColumn(3).setPreferredWidth(80);  // start_date
                table.getColumnModel().getColumn(4).setPreferredWidth(80);  // end_date
                table.getColumnModel().getColumn(5).setPreferredWidth(150); // reason
                table.getColumnModel().getColumn(6).setPreferredWidth(60);  // status
                table.getColumnModel().getColumn(7).setPreferredWidth(100); // substitute
            }
            
            // Reset selection
            selectedLeaveId = null;
            selectedEmployeeId = null;
            selectedEmployeeName = null;
            selectedLeaveInfo.setText("No leave request selected");
            approve.setEnabled(false);
            reject.setEnabled(false);
            substituteEmployees.setEnabled(false);
            assignSubstitute.setEnabled(false);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void populateSubstituteEmployees(String employeeId) {
        try {
            substituteEmployees.removeAllItems();
            substituteEmployees.addItem("-- Select Substitute --");
            
            Conn c = new Conn();
            // Query to get employees with the same or similar designation
            String query = "SELECT e1.empId, e1.name, e1.designation FROM employees e1 " +
                          "JOIN employees e2 ON e1.empId != " + employeeId + " " +
                          "WHERE e2.empId = " + employeeId + " " +
                          "ORDER BY CASE WHEN e1.designation = e2.designation THEN 0 ELSE 1 END, e1.name";
            ResultSet rs = c.s.executeQuery(query);
            
            while (rs.next()) {
                String id = rs.getString("empId");
                String name = rs.getString("name");
                String designation = rs.getString("designation");
                substituteEmployees.addItem(id + " - " + name + " (" + designation + ")");
            }
            
            // Check if a substitute is already assigned
            if (selectedLeaveId != null) {
                String checkQuery = "SELECT substitute_empId FROM leave_requests WHERE id = " + selectedLeaveId;
                ResultSet checkRs = c.s.executeQuery(checkQuery);
                
                if (checkRs.next() && checkRs.getObject("substitute_empId") != null) {
                    int substituteId = checkRs.getInt("substitute_empId");
                    
                    // Find and select the current substitute in the combo box
                    for (int i = 0; i < substituteEmployees.getItemCount(); i++) {
                        String item = substituteEmployees.getItemAt(i);
                        if (item.startsWith(substituteId + " - ")) {
                            substituteEmployees.setSelectedIndex(i);
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == back) {
            setVisible(false);
            new Home();
            return;
        }

        if (selectedLeaveId == null) {
            JOptionPane.showMessageDialog(null, "Select a leave request first.");
            return;
        }

        try {
            Conn c = new Conn();
            
            if (ae.getSource() == assignSubstitute) {
                String selected = (String) substituteEmployees.getSelectedItem();
                if (selected == null || selected.equals("-- Select Substitute --")) {
                    JOptionPane.showMessageDialog(null, "Please select a substitute employee.");
                    return;
                }
                
                // Extract employee ID from selection
                String substituteEmpId = selected.split(" - ")[0];
                
                // Check if substitute is the same as the employee requesting leave
                if (substituteEmpId.equals(selectedEmployeeId)) {
                    JOptionPane.showMessageDialog(null, "Cannot assign the same employee as substitute.");
                    return;
                }
                
                // Check if the substitute is already on leave for the same period
                String checkQuery = "SELECT COUNT(*) AS overlap FROM leave_requests lr1 " +
                                   "JOIN leave_requests lr2 ON lr1.id = " + selectedLeaveId + " " +
                                   "WHERE lr2.empId = " + substituteEmpId + " " +
                                   "AND lr2.status = 'Approved' " +
                                   "AND ((lr2.start_date <= lr1.end_date AND lr2.end_date >= lr1.start_date) " +
                                   "OR (lr1.start_date <= lr2.end_date AND lr1.end_date >= lr2.start_date))";
                ResultSet rsCheck = c.s.executeQuery(checkQuery);
                
                if (rsCheck.next() && rsCheck.getInt("overlap") > 0) {
                    JOptionPane.showMessageDialog(null, "This substitute employee has an approved leave that overlaps with this period.");
                    return;
                }
                
                // Update the database
                String updateQuery = "UPDATE leave_requests SET substitute_empId = " + substituteEmpId + 
                                    ", substitute_assigned = TRUE WHERE id = " + selectedLeaveId;
                c.s.executeUpdate(updateQuery);
                
                JOptionPane.showMessageDialog(null, "Substitute employee assigned successfully!");
                
            } else if (ae.getSource() == approve) {
                // Check if substitute is assigned for multi-day leaves
                boolean needsConfirmation = false;
                String message = "";
                
                String checkDaysQuery = "SELECT DATEDIFF(end_date, start_date) as days, substitute_assigned " +
                                       "FROM leave_requests WHERE id = " + selectedLeaveId;
                ResultSet rs = c.s.executeQuery(checkDaysQuery);
                
                if (rs.next()) {
                    int days = rs.getInt("days");
                    boolean hasSubstitute = rs.getBoolean("substitute_assigned");
                    
                    if (days > 2 && !hasSubstitute) {
                        needsConfirmation = true;
                        message = "This is a long leave (" + (days + 1) + " days) without a substitute assigned. Approve anyway?";
                    }
                }
                
                // If confirmation is needed, show dialog
                if (needsConfirmation) {
                    int confirm = JOptionPane.showConfirmDialog(null, message, "Confirm Approval", JOptionPane.YES_NO_OPTION);
                    if (confirm != JOptionPane.YES_OPTION) {
                        return;
                    }
                }
                
                // If no confirmation needed or user confirmed, approve the leave
                c.s.executeUpdate("UPDATE leave_requests SET status = 'Approved' WHERE id = " + selectedLeaveId);
                JOptionPane.showMessageDialog(null, "Leave Approved!");
                
            } else if (ae.getSource() == reject) {
                // Ask for rejection reason
                String rejectionReason = JOptionPane.showInputDialog(this, "Enter reason for rejection (optional):");
                
                // Update with rejection reason if provided
                String updateQuery;
                if (rejectionReason != null && !rejectionReason.trim().isEmpty()) {
                    updateQuery = "UPDATE leave_requests SET status = 'Rejected', rejection_reason = '" + 
                                  rejectionReason.replace("'", "''") + "' WHERE id = " + selectedLeaveId;
                } else {
                    updateQuery = "UPDATE leave_requests SET status = 'Rejected' WHERE id = " + selectedLeaveId;
                }
                
                c.s.executeUpdate(updateQuery);
                JOptionPane.showMessageDialog(null, "Leave Rejected!");
            }
            
            // Refresh the table data
            loadTableData();
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new AdminLeaveApproval();
    }
}