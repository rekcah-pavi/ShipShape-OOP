package shipshape;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*;

public class Employee_view extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Employee_view frame = new Employee_view();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void createTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS employees (" +
                     "employee_id INT PRIMARY KEY, " +
                     "employee_name VARCHAR(255) NOT NULL, " +
                     "job_role VARCHAR(255) NOT NULL, " +
                     "schedule VARCHAR(255) NOT NULL, " +
                     "work_location VARCHAR(50) NOT NULL, " +
                     "skills VARCHAR(255) NOT NULL, " +
                     "email VARCHAR(255) NOT NULL" +
                     ")";

        try (Connection conn = sql_connecter.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Employee_view() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 600);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(153, 153, 255));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new GridBagLayout());

        JButton btnMainPage = new JButton("HOME");
        btnMainPage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                Main_view frame = new Main_view();
                frame.setVisible(true);
            }
        });
        btnMainPage.setFont(new Font("Arial", Font.PLAIN, 16));
        GridBagConstraints gbc_btnMainPage = new GridBagConstraints();
        gbc_btnMainPage.insets = new Insets(0, 0, 5, 0);
        gbc_btnMainPage.gridx = 4;
        gbc_btnMainPage.gridy = 0;
        contentPane.add(btnMainPage, gbc_btnMainPage);

      
        JLabel titleLabel = new JLabel("Employee Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        GridBagConstraints gbc_titleLabel = new GridBagConstraints();
        gbc_titleLabel.gridwidth = 3;
        gbc_titleLabel.insets = new Insets(10, 10, 20, 10);
        gbc_titleLabel.gridx = 1;
        gbc_titleLabel.gridy = 1;
        contentPane.add(titleLabel, gbc_titleLabel);

       
        JLabel lblEmployeeId = new JLabel("Employee ID");
        lblEmployeeId.setFont(new Font("Arial", Font.PLAIN, 16));
        GridBagConstraints gbc_lblEmployeeId = new GridBagConstraints();
        gbc_lblEmployeeId.insets = new Insets(10, 10, 10, 10);
        gbc_lblEmployeeId.gridx = 0;
        gbc_lblEmployeeId.gridy = 2;
        contentPane.add(lblEmployeeId, gbc_lblEmployeeId);

        JTextArea textEmployeeId = new JTextArea();
        textEmployeeId.setFont(new Font("Arial", Font.PLAIN, 16));
        textEmployeeId.setPreferredSize(new Dimension(200, 30));
        GridBagConstraints gbc_textEmployeeId = new GridBagConstraints();
        gbc_textEmployeeId.insets = new Insets(10, 10, 10, 10);
        gbc_textEmployeeId.gridx = 1;
        gbc_textEmployeeId.gridy = 2;
        contentPane.add(textEmployeeId, gbc_textEmployeeId);

        JButton btnGetEmployeeDetails = new JButton("Get Details");
        btnGetEmployeeDetails.setFont(new Font("Arial", Font.PLAIN, 16));
        GridBagConstraints gbc_btnGetEmployeeDetails = new GridBagConstraints();
        gbc_btnGetEmployeeDetails.insets = new Insets(20, 10, 10, 10);
        gbc_btnGetEmployeeDetails.gridx = 3;
        gbc_btnGetEmployeeDetails.gridy = 2;
        contentPane.add(btnGetEmployeeDetails, gbc_btnGetEmployeeDetails);

        JButton btnDeleteEmployee = new JButton("Delete Employee");
        btnDeleteEmployee.setFont(new Font("Arial", Font.PLAIN, 16));
        GridBagConstraints gbc_btnDeleteEmployee = new GridBagConstraints();
        gbc_btnDeleteEmployee.insets = new Insets(20, 10, 10, 10);
        gbc_btnDeleteEmployee.gridx = 4;
        gbc_btnDeleteEmployee.gridy = 2;
        contentPane.add(btnDeleteEmployee, gbc_btnDeleteEmployee);
        
        btnDeleteEmployee.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String temp_id = textEmployeeId.getText();
                if (temp_id.equals("")) {
                    JOptionPane.showMessageDialog(null, "Error: missing employee ID");
                    return;
                }

                int employee_id;
                try {
                    employee_id = Integer.parseInt(temp_id);
                } catch (NumberFormatException e1) {
                    JOptionPane.showMessageDialog(null, "Error: Employee ID must be a number");
                    return;
                }

                String sql = "DELETE FROM employees WHERE employee_id = ?";

                try (Connection conn = sql_connecter.getConnection();
                     PreparedStatement pstmt = conn.prepareStatement(sql)) {

                    pstmt.setInt(1, employee_id);

                    int rowsAffected = pstmt.executeUpdate();
                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(null, "Employee Deleted Successfully");
                    } else {
                        JOptionPane.showMessageDialog(null, "Employee Not Found");
                    }

                } catch (SQLException e1) {
                    JOptionPane.showMessageDialog(null, "Error: " + e1.getMessage());
                }
            }
        });

  
        JLabel lblEmployeeName = new JLabel("Employee Name");
        lblEmployeeName.setFont(new Font("Arial", Font.PLAIN, 16));
        GridBagConstraints gbc_lblEmployeeName = new GridBagConstraints();
        gbc_lblEmployeeName.insets = new Insets(10, 10, 10, 10);
        gbc_lblEmployeeName.gridx = 0;
        gbc_lblEmployeeName.gridy = 3;
        contentPane.add(lblEmployeeName, gbc_lblEmployeeName);

        JTextArea textEmployeeName = new JTextArea();
        textEmployeeName.setFont(new Font("Arial", Font.PLAIN, 16));
        textEmployeeName.setPreferredSize(new Dimension(200, 30));
        GridBagConstraints gbc_textEmployeeName = new GridBagConstraints();
        gbc_textEmployeeName.insets = new Insets(10, 10, 10, 10);
        gbc_textEmployeeName.gridx = 1;
        gbc_textEmployeeName.gridy = 3;
        contentPane.add(textEmployeeName, gbc_textEmployeeName);

       
        JLabel lblJobRole = new JLabel("Job Role");
        lblJobRole.setFont(new Font("Arial", Font.PLAIN, 16));
        GridBagConstraints gbc_lblJobRole = new GridBagConstraints();
        gbc_lblJobRole.insets = new Insets(10, 10, 10, 10);
        gbc_lblJobRole.gridx = 0;
        gbc_lblJobRole.gridy = 4;
        contentPane.add(lblJobRole, gbc_lblJobRole);

        JTextArea textJobRole = new JTextArea();
        textJobRole.setFont(new Font("Arial", Font.PLAIN, 16));
        textJobRole.setPreferredSize(new Dimension(200, 30));
        GridBagConstraints gbc_textJobRole = new GridBagConstraints();
        gbc_textJobRole.insets = new Insets(10, 10, 10, 10);
        gbc_textJobRole.gridx = 1;
        gbc_textJobRole.gridy = 4;
        contentPane.add(textJobRole, gbc_textJobRole);

       
        JLabel lblSchedule = new JLabel("Schedule");
        lblSchedule.setFont(new Font("Arial", Font.PLAIN, 16));
        GridBagConstraints gbc_lblSchedule = new GridBagConstraints();
        gbc_lblSchedule.insets = new Insets(10, 10, 10, 10);
        gbc_lblSchedule.gridx = 0;
        gbc_lblSchedule.gridy = 5;
        contentPane.add(lblSchedule, gbc_lblSchedule);

        JTextArea textSchedule = new JTextArea();
        textSchedule.setFont(new Font("Arial", Font.PLAIN, 16));
        textSchedule.setPreferredSize(new Dimension(200, 30));
        GridBagConstraints gbc_textSchedule = new GridBagConstraints();
        gbc_textSchedule.insets = new Insets(10, 10, 10, 10);
        gbc_textSchedule.gridx = 1;
        gbc_textSchedule.gridy = 5;
        contentPane.add(textSchedule, gbc_textSchedule);

     
        JLabel lblWorkLocation = new JLabel("Work Location");
        lblWorkLocation.setFont(new Font("Arial", Font.PLAIN, 16));
        GridBagConstraints gbc_lblWorkLocation = new GridBagConstraints();
        gbc_lblWorkLocation.insets = new Insets(10, 10, 10, 10);
        gbc_lblWorkLocation.gridx = 0;
        gbc_lblWorkLocation.gridy = 6;
        contentPane.add(lblWorkLocation, gbc_lblWorkLocation);

        JTextArea textWorkLocation = new JTextArea();
        textWorkLocation.setFont(new Font("Arial", Font.PLAIN, 16));
        textWorkLocation.setPreferredSize(new Dimension(200, 30));
        GridBagConstraints gbc_textWorkLocation = new GridBagConstraints();
        gbc_textWorkLocation.insets = new Insets(10, 10, 10, 10);
        gbc_textWorkLocation.gridx = 1;
        gbc_textWorkLocation.gridy = 6;
        contentPane.add(textWorkLocation, gbc_textWorkLocation);

      
        JLabel lblSkills = new JLabel("Skills");
        lblSkills.setFont(new Font("Arial", Font.PLAIN, 16));
        GridBagConstraints gbc_lblSkills = new GridBagConstraints();
        gbc_lblSkills.insets = new Insets(10, 10, 10, 10);
        gbc_lblSkills.gridx = 0;
        gbc_lblSkills.gridy = 7;
        contentPane.add(lblSkills, gbc_lblSkills);

        JTextArea textSkills = new JTextArea();
        textSkills.setFont(new Font("Arial", Font.PLAIN, 16));
        textSkills.setPreferredSize(new Dimension(200, 30));
        GridBagConstraints gbc_textSkills = new GridBagConstraints();
        gbc_textSkills.insets = new Insets(10, 10, 10, 10);
        gbc_textSkills.gridx = 1;
        gbc_textSkills.gridy = 7;
        contentPane.add(textSkills, gbc_textSkills);

   
        JLabel lblEmail = new JLabel("Email");
        lblEmail.setFont(new Font("Arial", Font.PLAIN, 16));
        GridBagConstraints gbc_lblEmail = new GridBagConstraints();
        gbc_lblEmail.insets = new Insets(10, 10, 10, 10);
        gbc_lblEmail.gridx = 0;
        gbc_lblEmail.gridy = 8;
        contentPane.add(lblEmail, gbc_lblEmail);

        JTextArea textEmail = new JTextArea();
        textEmail.setFont(new Font("Arial", Font.PLAIN, 16));
        textEmail.setPreferredSize(new Dimension(200, 30));
        GridBagConstraints gbc_textEmail = new GridBagConstraints();
        gbc_textEmail.insets = new Insets(10, 10, 10, 10);
        gbc_textEmail.gridx = 1;
        gbc_textEmail.gridy = 8;
        contentPane.add(textEmail, gbc_textEmail);
        
        JButton btnAddUpdateEmployee = new JButton("Add/Update Employee");
        
        
                
                JCheckBox sendEmailCheckbox = new JCheckBox("Send Email Notification");
                sendEmailCheckbox.setFont(new Font("Arial", Font.PLAIN, 16));
                GridBagConstraints gbc_sendEmailCheckbox = new GridBagConstraints();
                gbc_sendEmailCheckbox.insets = new Insets(10, 10, 10, 10);
                gbc_sendEmailCheckbox.gridx = 3;
                gbc_sendEmailCheckbox.gridy = 8;
                contentPane.add(sendEmailCheckbox, gbc_sendEmailCheckbox);
        btnAddUpdateEmployee.setFont(new Font("Arial", Font.PLAIN, 16));
        GridBagConstraints gbc_btnAddUpdateEmployee = new GridBagConstraints();
        gbc_btnAddUpdateEmployee.insets = new Insets(0, 0, 0, 5);
        gbc_btnAddUpdateEmployee.gridx = 1;
        gbc_btnAddUpdateEmployee.gridy = 11;
        contentPane.add(btnAddUpdateEmployee, gbc_btnAddUpdateEmployee);
        
        JButton btnGetAllOrders = new JButton("Get All Employess");
        btnGetAllOrders.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		String sql = "SELECT employee_id, employee_name, job_role, schedule, work_location, skills, email FROM employees";

        		try (Connection conn = sql_connecter.getConnection();
        		     Statement stmt = conn.createStatement();
        		     ResultSet rs = stmt.executeQuery(sql)) {

        		    StringBuilder sb = new StringBuilder();
        		    while (rs.next()) {
        		        int employeeId = rs.getInt("employee_id");
        		        String employeeName = rs.getString("employee_name");
        		        String jobRole = rs.getString("job_role");
        		        String schedule = rs.getString("schedule");
        		        String workLocation = rs.getString("work_location");
        		        String skills = rs.getString("skills");
        		        String email = rs.getString("email");

        		        sb.append(String.format("Employee ID: %d\nName: %s\nJob Role: %s\nSchedule: %s\nWork Location: %s\nSkills: %s\nEmail: %s\n\n",
        		                employeeId, employeeName, jobRole, schedule, workLocation, skills, email));
        		    }

        		    JTextArea textArea = new JTextArea(sb.toString());
        		    textArea.setFont(new Font("Arial", Font.PLAIN, 16));
        		    textArea.setEditable(false);
        		    JScrollPane scrollPane = new JScrollPane(textArea);
        		    scrollPane.setPreferredSize(new Dimension(500, 400));
        		    JOptionPane.showMessageDialog(null, scrollPane, "All Employees", JOptionPane.INFORMATION_MESSAGE);

        		} catch (SQLException e1) {
        		    JOptionPane.showMessageDialog(null, "Error: " + e1.getMessage());
        		}
        		
        	}
        });
        btnGetAllOrders.setFont(new Font("Arial", Font.PLAIN, 16));
        GridBagConstraints gbc_btnGetAllOrders = new GridBagConstraints();
        gbc_btnGetAllOrders.insets = new Insets(0, 0, 0, 5);
        gbc_btnGetAllOrders.gridx = 3;
        gbc_btnGetAllOrders.gridy = 11;
        contentPane.add(btnGetAllOrders, gbc_btnGetAllOrders);
        
        

 
        btnGetEmployeeDetails.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String temp_id = textEmployeeId.getText();
                if (temp_id.equals("")) {
                    JOptionPane.showMessageDialog(null, "Error: missing employee ID");
                    return;
                }

                int employee_id;
                try {
                    employee_id = Integer.parseInt(temp_id);
                } catch (NumberFormatException e1) {
                    JOptionPane.showMessageDialog(null, "Error: Employee ID must be a number");
                    return;
                }

                String sql = "SELECT * FROM employees WHERE employee_id = ?";

                try (Connection conn = sql_connecter.getConnection();
                     PreparedStatement pstmt = conn.prepareStatement(sql)) {

                    pstmt.setInt(1, employee_id);

                    try (ResultSet rs = pstmt.executeQuery()) {
                        if (rs.next()) {
                            textEmployeeName.setText(rs.getString("employee_name"));
                            textJobRole.setText(rs.getString("job_role"));
                            textSchedule.setText(rs.getString("schedule"));
                            textWorkLocation.setText(rs.getString("work_location"));
                            textSkills.setText(rs.getString("skills"));
                            textEmail.setText(rs.getString("email"));
                        } else {
                            JOptionPane.showMessageDialog(null, "Employee Not Found");
                        }
                    }

                } catch (SQLException e1) {
                    JOptionPane.showMessageDialog(null, "Error: " + e1.getMessage());
                }
            }
        });


        createTableIfNotExists();
    }
}
