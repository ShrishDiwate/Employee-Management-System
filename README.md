# Employee-Management-System
Developed a comprehensive Java Swing application for managing employee data, including a secure login system with role-based access for administrators and employees. Implemented robust CRUD operations for employee records with real-time database integration using JDBC. Engineered a leave management system with approval workflows, substitute employee assignment functionality, and date validation. Designed an intuitive user interface with responsive form validation, search capabilities, and filtering options. Demonstrated proficiency in object-oriented programming, MVC architecture, event handling, and database design.
An intuitive desktop application built with Java Swing and MySQL for comprehensive employee and leave management.

✨ Features
👤 User Authentication

Role-based access control (Admin and Employee)
Secure login system

👥 Employee Management

Add new employees with comprehensive details
View, update, and remove employee records
Search functionality by employee ID

📅 Leave Management System

Apply for leave with date validation
Suggest substitute employees
Admin approval workflow
Leave status tracking

🔄 Admin Dashboard

Comprehensive view of all employees
Leave request management
Substitute employee assignment
Data filtering options

🖼️ Screenshots
[Add screenshots of your application here]
🛠️ Technology Stack

Frontend: Java Swing for GUI
Backend: Java
Database: MySQL
Database Connectivity: JDBC
External Libraries: JDateChooser, Rs2xml

📋 Prerequisites

Java Development Kit (JDK) 8 or higher
MySQL Server 8.0 or higher
MySQL Connector/J (JDBC Driver)

🚀 Installation & Setup

Clone the repository
bashgit clone https://github.com/yourusername/employee-management-system.git
cd employee-management-system

Database Setup
sql-- Run the SQL scripts from db.txt to create necessary tables

Configure Database Connection

Update the database connection parameters in Conn.java if needed:

javac = DriverManager.getConnection("jdbc:mysql://localhost:3306/employeeManagementSystem_db", "root", "your_password");

Compile and Run
bashjavac -cp ".:./lib/*" com/EmployeeManagement/System/*.java
java -cp ".:./lib/*" com.EmployeeManagement.System.Splash


📱 Usage
Admin Operations

Login with admin credentials
Add, view, update, or remove employees
Manage leave requests
Assign substitute employees

Employee Operations

Login with provided username and password
Apply for leave
Suggest substitute employees
View leave status

🧩 Project Structure
com.EmployeeManagement.System/
├── AddEmployee.java       # Add employee details
├── AdminLeaveApproval.java # Leave approval workflow
├── Conn.java              # Database connection
├── EmployeeHome.java      # Employee dashboard
├── Home.java              # Admin dashboard
├── LeaveManagement.java   # Leave application
├── Login.java             # Authentication
├── RemoveEmployee.java    # Delete employee records
├── Splash.java            # Welcome screen
├── UpdateEmployee.java    # Edit employee details
└── ViewEmployee.java      # View employee records
