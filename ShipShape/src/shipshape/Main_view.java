package shipshape;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*;

public class Main_view extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Main_view frame = new Main_view();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    

    public Main_view() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 600);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(153, 153, 255));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new GridBagLayout());
                        
                        JButton btnQuickReport = new JButton("Quick Report");
                        btnQuickReport.addActionListener(new ActionListener() {
                        	public void actionPerformed(ActionEvent e) {
                        		StringBuilder report = new StringBuilder();
                        		try (Connection conn = sql_connecter.getConnection()) {

                        		    String totalCustomersSql = "SELECT COUNT(*) FROM customers";
                        		    try (PreparedStatement pstmt = conn.prepareStatement(totalCustomersSql);
                        		         ResultSet rs = pstmt.executeQuery()) {
                        		        if (rs.next()) {
                        		            int totalCustomers = rs.getInt(1);
                        		            report.append("Total Customers: ").append(totalCustomers).append("\n");
                        		        }
                        		    }
                                    
                        		    String mostOrderedCitySql = "SELECT city, COUNT(city) AS count FROM customers GROUP BY city ORDER BY count DESC LIMIT 1";
                        		    try (PreparedStatement pstmt = conn.prepareStatement(mostOrderedCitySql);
                        		         ResultSet rs = pstmt.executeQuery()) {
                        		        if (rs.next()) {
                        		            String city = rs.getString("city");
                        		            int count = rs.getInt("count");
                        		            report.append("\nMost Ordered Customer City: ").append(city)
                        		                  .append(" with ").append(count).append(" orders\n\n");
                        		        }
                        		    }

                        		    String totalEmployeesSql = "SELECT COUNT(*) FROM employees";
                        		    try (PreparedStatement pstmt = conn.prepareStatement(totalEmployeesSql);
                        		         ResultSet rs = pstmt.executeQuery()) {
                        		        if (rs.next()) {
                        		            int totalEmployees = rs.getInt(1);
                        		            report.append("Total Employees: ").append(totalEmployees).append("\n\n");
                        		        }
                        		    }

                        		    String lowQuantityItemsSql = "SELECT * FROM inventory WHERE quantity < ?";
                        		    try (PreparedStatement pstmt = conn.prepareStatement(lowQuantityItemsSql)) {
                        		        pstmt.setInt(1, 5);
                        		        try (ResultSet rs = pstmt.executeQuery()) {
                        		            report.append("Low Quantity Items:\n---------\n");
                        		            while (rs.next()) {
                        		                report.append(" Item ID: ").append(rs.getInt("item_id"))
                        		                      .append("\n Name: ").append(rs.getString("item_name"))
                        		                      .append("\n Quantity: ").append(rs.getInt("quantity"))
                        		                      .append("\n\n");
                        		            }
                        		        }
                        		    }

                        		    

           
                        		    String pendingOrdersSql = "SELECT * FROM orders WHERE order_status <> 'Ready'";
                        		    try (PreparedStatement pstmt = conn.prepareStatement(pendingOrdersSql);
                        		         ResultSet rs = pstmt.executeQuery()) {
                        		        report.append("Pending Orders:\n----------\n");
                        		        while (rs.next()) {
                        		            report.append(" Order ID: ").append(rs.getInt("order_id"))
                        		                  .append("\n Item ID: ").append(rs.getString("item_id"))
                        		                  .append("\n Order Status: ").append(rs.getString("order_status"))
                        		                  .append("\n Customer ID: ").append(rs.getInt("customer_id"))
                        		                  .append("\n\n");
                        		        }
                        		    }

                        		    JTextArea textArea = new JTextArea(report.toString());
                        		    JScrollPane scrollPane = new JScrollPane(textArea);
                        		    textArea.setLineWrap(true);
                        		    textArea.setWrapStyleWord(true);
                        		    scrollPane.setPreferredSize(new java.awt.Dimension(500, 500));

                        		    JOptionPane.showMessageDialog(null, scrollPane, "Quick Report", JOptionPane.INFORMATION_MESSAGE);

                        		} catch (SQLException e1) {
                        		    JOptionPane.showMessageDialog(null, "Error: " + e1.getMessage());
                        		}
                            }
                        		
                        	
                        });
                        btnQuickReport.setBackground(new Color(102, 153, 255));
                        GridBagConstraints gbc_btnQuickReport = new GridBagConstraints();
                        gbc_btnQuickReport.insets = new Insets(0, 0, 5, 0);
                        gbc_btnQuickReport.gridx = 5;
                        gbc_btnQuickReport.gridy = 0;
                        contentPane.add(btnQuickReport, gbc_btnQuickReport);
                
                        // Title
                        JLabel titleLabel = new JLabel("ShipShape OOP");
                        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
                        GridBagConstraints gbc_titleLabel = new GridBagConstraints();
                        gbc_titleLabel.gridwidth = 3;
                        gbc_titleLabel.insets = new Insets(10, 10, 20, 10);
                        gbc_titleLabel.gridx = 1;
                        gbc_titleLabel.gridy = 1;
                        contentPane.add(titleLabel, gbc_titleLabel);
        
          
                JButton btnAddSupplier = new JButton("Manage Orders");
                btnAddSupplier.setFont(new Font("Arial", Font.PLAIN, 16));
                GridBagConstraints gbc_btnAddSupplier = new GridBagConstraints();
                gbc_btnAddSupplier.insets = new Insets(20, 10, 10, 10);
                gbc_btnAddSupplier.gridx = 0;
                gbc_btnAddSupplier.gridy = 4;
                contentPane.add(btnAddSupplier, gbc_btnAddSupplier);
                
                       
                        btnAddSupplier.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                            	dispose();
                            	Order_view frame = new Order_view();
                                frame.setVisible(true);
                            }
                        });
        
                JButton btnGetAllSuppliers = new JButton("Manage Inventory");
                btnGetAllSuppliers.setFont(new Font("Arial", Font.PLAIN, 16));
                GridBagConstraints gbc_btnGetAllSuppliers = new GridBagConstraints();
                gbc_btnGetAllSuppliers.insets = new Insets(20, 10, 10, 10);
                gbc_btnGetAllSuppliers.gridx = 4;
                gbc_btnGetAllSuppliers.gridy = 4;
                gbc_btnGetAllSuppliers.gridwidth = 2;
                contentPane.add(btnGetAllSuppliers, gbc_btnGetAllSuppliers);
                                
                                JButton btnUpdateSupplier_1 = new JButton("Manage Customers");
                                btnUpdateSupplier_1.addActionListener(new ActionListener() {
                                	public void actionPerformed(ActionEvent e) {
                                		dispose();
                                		CustomerView frame = new CustomerView();
                                        frame.setVisible(true);
                                		
                                	}
                                });
                                btnUpdateSupplier_1.setFont(new Font("Arial", Font.PLAIN, 16));
                                GridBagConstraints gbc_btnUpdateSupplier_1 = new GridBagConstraints();
                                gbc_btnUpdateSupplier_1.insets = new Insets(0, 0, 5, 5);
                                gbc_btnUpdateSupplier_1.gridx = 2;
                                gbc_btnUpdateSupplier_1.gridy = 6;
                                contentPane.add(btnUpdateSupplier_1, gbc_btnUpdateSupplier_1);
                        
                                JButton btnUpdateSupplier = new JButton("Manage Suppliers");
                                btnUpdateSupplier.setFont(new Font("Arial", Font.PLAIN, 16));
                                GridBagConstraints gbc_btnUpdateSupplier = new GridBagConstraints();
                                gbc_btnUpdateSupplier.insets = new Insets(20, 10, 10, 10);
                                gbc_btnUpdateSupplier.gridx = 0;
                                gbc_btnUpdateSupplier.gridy = 7;
                                contentPane.add(btnUpdateSupplier, gbc_btnUpdateSupplier);
                                
                                JButton btnManageEmployees = new JButton("Manage Employees");
                                btnManageEmployees.addActionListener(new ActionListener() {
                                	public void actionPerformed(ActionEvent e) {
                                		dispose();
                                		Employee_view frame = new Employee_view();
                                        frame.setVisible(true);
                                	}
                                });
                                btnManageEmployees.setFont(new Font("Arial", Font.PLAIN, 16));
                                GridBagConstraints gbc_btnManageEmployees = new GridBagConstraints();
                                gbc_btnManageEmployees.gridx = 5;
                                gbc_btnManageEmployees.gridy = 7;
                                contentPane.add(btnManageEmployees, gbc_btnManageEmployees);
                        
                                btnUpdateSupplier.addActionListener(new ActionListener() {
                                    public void actionPerformed(ActionEvent e) {
                                    	dispose();
                                    	Suppliers_view frame = new Suppliers_view();
                                        frame.setVisible(true);
                                       
                                    }
                                });

        btnGetAllSuppliers.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	dispose();
            	Inventory_view frame = new Inventory_view();
                frame.setVisible(true);
            }
        });
    }
}
