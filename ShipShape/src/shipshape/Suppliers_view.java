package shipshape;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*;

public class Suppliers_view extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Suppliers_view frame = new Suppliers_view();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void createTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS suppliers (" +
                "supplier_id INT PRIMARY KEY, " +
                "supplier_name VARCHAR(255) NOT NULL, " +
                "contact_info VARCHAR(255) NOT NULL" +
                ")";

        try (Connection conn = sql_connecter.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Suppliers_view() {
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

    
        JLabel titleLabel = new JLabel("Manage Suppliers");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        GridBagConstraints gbc_titleLabel = new GridBagConstraints();
        gbc_titleLabel.gridwidth = 3;
        gbc_titleLabel.insets = new Insets(10, 10, 20, 10);
        gbc_titleLabel.gridx = 1;
        gbc_titleLabel.gridy = 1;
        contentPane.add(titleLabel, gbc_titleLabel);

     
        JLabel lblSupplierId = new JLabel("Supplier ID");
        lblSupplierId.setFont(new Font("Arial", Font.PLAIN, 16));
        GridBagConstraints gbc_lblSupplierId = new GridBagConstraints();
        gbc_lblSupplierId.insets = new Insets(10, 10, 10, 10);
        gbc_lblSupplierId.gridx = 0;
        gbc_lblSupplierId.gridy = 2;
        contentPane.add(lblSupplierId, gbc_lblSupplierId);

        JTextArea textSupplierId = new JTextArea();
        textSupplierId.setFont(new Font("Arial", Font.PLAIN, 16));
        textSupplierId.setPreferredSize(new Dimension(200, 30));
        GridBagConstraints gbc_textSupplierId = new GridBagConstraints();
        gbc_textSupplierId.insets = new Insets(10, 10, 10, 10);
        gbc_textSupplierId.gridx = 1;
        gbc_textSupplierId.gridy = 2;
        contentPane.add(textSupplierId, gbc_textSupplierId);

        JButton btnGetSupplierDetails = new JButton("Get Details");
        btnGetSupplierDetails.setFont(new Font("Arial", Font.PLAIN, 16));
        GridBagConstraints gbc_btnGetSupplierDetails = new GridBagConstraints();
        gbc_btnGetSupplierDetails.insets = new Insets(20, 10, 10, 10);
        gbc_btnGetSupplierDetails.gridx = 3;
        gbc_btnGetSupplierDetails.gridy = 2;
        contentPane.add(btnGetSupplierDetails, gbc_btnGetSupplierDetails);

        

        JButton btnDeleteSupplier = new JButton("Delete Supplier");
        btnDeleteSupplier.setFont(new Font("Arial", Font.PLAIN, 16));
        GridBagConstraints gbc_btnDeleteSupplier = new GridBagConstraints();
        gbc_btnDeleteSupplier.insets = new Insets(20, 10, 10, 10);
        gbc_btnDeleteSupplier.gridx = 4;
        gbc_btnDeleteSupplier.gridy = 2;
        contentPane.add(btnDeleteSupplier, gbc_btnDeleteSupplier);

        btnDeleteSupplier.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String tsupplier_id = textSupplierId.getText();
                if (tsupplier_id.equals("")) {
                    JOptionPane.showMessageDialog(null, "Error: missing supplier ID");
                    return;
                }

                int supplier_id;
                try {
                    supplier_id = Integer.parseInt(tsupplier_id);
                } catch (NumberFormatException e1) {
                    JOptionPane.showMessageDialog(null, "Error: supplier ID must be a number");
                    return;
                }

                String sql = "DELETE FROM suppliers WHERE supplier_id = ?";

                try (Connection conn = sql_connecter.getConnection();
                     PreparedStatement pstmt = conn.prepareStatement(sql)) {

                    pstmt.setInt(1, supplier_id);

                    int rowsAffected = pstmt.executeUpdate();
                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(null, "Supplier Deleted Successfully");
                    } else {
                        JOptionPane.showMessageDialog(null, "Supplier Not Found");
                    }

                } catch (SQLException e1) {
                    JOptionPane.showMessageDialog(null, "Error: " + e1.getMessage());
                }
            }
        });

       
        JLabel lblSupplierName = new JLabel("Supplier Name");
        lblSupplierName.setFont(new Font("Arial", Font.PLAIN, 16));
        GridBagConstraints gbc_lblSupplierName = new GridBagConstraints();
        gbc_lblSupplierName.insets = new Insets(10, 10, 10, 10);
        gbc_lblSupplierName.gridx = 0;
        gbc_lblSupplierName.gridy = 3;
        contentPane.add(lblSupplierName, gbc_lblSupplierName);

        JTextArea textSupplierName = new JTextArea();
        textSupplierName.setFont(new Font("Arial", Font.PLAIN, 16));
        textSupplierName.setPreferredSize(new Dimension(200, 30));
        GridBagConstraints gbc_textSupplierName = new GridBagConstraints();
        gbc_textSupplierName.insets = new Insets(10, 10, 10, 10);
        gbc_textSupplierName.gridx = 1;
        gbc_textSupplierName.gridy = 3;
        contentPane.add(textSupplierName, gbc_textSupplierName);

    
        JLabel lblContactInfo = new JLabel("Email");
        lblContactInfo.setFont(new Font("Arial", Font.PLAIN, 16));
        GridBagConstraints gbc_lblContactInfo = new GridBagConstraints();
        gbc_lblContactInfo.insets = new Insets(10, 10, 10, 10);
        gbc_lblContactInfo.gridx = 0;
        gbc_lblContactInfo.gridy = 4;
        contentPane.add(lblContactInfo, gbc_lblContactInfo);

        JTextArea textContactInfo = new JTextArea();
        textContactInfo.setFont(new Font("Arial", Font.PLAIN, 16));
        textContactInfo.setPreferredSize(new Dimension(200, 30));
        GridBagConstraints gbc_textContactInfo = new GridBagConstraints();
        gbc_textContactInfo.insets = new Insets(10, 10, 10, 10);
        gbc_textContactInfo.gridx = 1;
        gbc_textContactInfo.gridy = 4;
        contentPane.add(textContactInfo, gbc_textContactInfo);
        
             
                JButton btnAddSupplier = new JButton("Add/Update Supplier");
                btnAddSupplier.setFont(new Font("Arial", Font.PLAIN, 16));
                GridBagConstraints gbc_btnAddSupplier = new GridBagConstraints();
                gbc_btnAddSupplier.insets = new Insets(20, 10, 10, 10);
                gbc_btnAddSupplier.gridx = 1;
                gbc_btnAddSupplier.gridy = 6;
                contentPane.add(btnAddSupplier, gbc_btnAddSupplier);
                
                      
                        btnAddSupplier.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                createTableIfNotExists();
                
                                String tsupplier_id = textSupplierId.getText();
                                if (tsupplier_id.equals("")) {
                                    JOptionPane.showMessageDialog(null, "Error: missing supplier ID");
                                    return;
                                }
                
                                int supplier_id;
                                try {
                                    supplier_id = Integer.parseInt(tsupplier_id);
                                } catch (NumberFormatException e1) {
                                    JOptionPane.showMessageDialog(null, "Error: supplier ID must be a number");
                                    return;
                                }
                
                                String supplier_name = textSupplierName.getText();
                                if (supplier_name.equals("")) {
                                    JOptionPane.showMessageDialog(null, "Error: missing supplier name");
                                    return;
                                }
                
                                String contact_info = textContactInfo.getText();
                                if (contact_info.equals("")) {
                                    JOptionPane.showMessageDialog(null, "Error: missing contact info");
                                    return;
                                }
                
                                String sql = "REPLACE INTO suppliers (supplier_id, supplier_name, contact_info) VALUES (?, ?, ?)";
                
                                try (Connection conn = sql_connecter.getConnection();
                                     PreparedStatement pstmt = conn.prepareStatement(sql)) {
                
                                    pstmt.setInt(1, supplier_id);
                                    pstmt.setString(2, supplier_name);
                                    pstmt.setString(3, contact_info);
                
                                    pstmt.executeUpdate();
                                    JOptionPane.showMessageDialog(null, "Supplier Added Successfully");
                
                                } catch (SQLException e1) {
                                    JOptionPane.showMessageDialog(null, "Error: " + e1.getMessage());
                                }
                            }
                        });

        JButton btnGetAllSuppliers = new JButton("Get All Suppliers");
        btnGetAllSuppliers.setFont(new Font("Arial", Font.PLAIN, 16));
        GridBagConstraints gbc_btnGetAllSuppliers = new GridBagConstraints();
        gbc_btnGetAllSuppliers.insets = new Insets(20, 10, 10, 10);
        gbc_btnGetAllSuppliers.gridx = 2;
        gbc_btnGetAllSuppliers.gridy = 6;
        gbc_btnGetAllSuppliers.gridwidth = 2;
        contentPane.add(btnGetAllSuppliers, gbc_btnGetAllSuppliers);
        
        
        btnGetSupplierDetails.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String tsupplier_id = textSupplierId.getText();
                if (tsupplier_id.equals("")) {
                    JOptionPane.showMessageDialog(null, "Error: missing supplier ID");
                    return;
                }

                int supplier_id;
                try {
                    supplier_id = Integer.parseInt(tsupplier_id);
                } catch (NumberFormatException e1) {
                    JOptionPane.showMessageDialog(null, "Error: supplier ID must be a number");
                    return;
                }

                String sql = "SELECT supplier_id, supplier_name, contact_info FROM suppliers WHERE supplier_id = " + supplier_id;

                try (Connection conn = sql_connecter.getConnection();
                     Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery(sql)) {

                    if (rs.next()) {
                        int supplierId = rs.getInt("supplier_id");
                        String supplierName = rs.getString("supplier_name");
                        String contactInfo = rs.getString("contact_info");
                        
                        textContactInfo.setText(contactInfo);
                        textSupplierName.setText(supplierName);
                        
                        
                    } else {
                        JOptionPane.showMessageDialog(null, "Supplier not found");
                    }

                } catch (SQLException e1) {
                    JOptionPane.showMessageDialog(null, "Error: " + e1.getMessage());
                }
            }
        });

        btnGetAllSuppliers.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String sql = "SELECT supplier_id, supplier_name, contact_info FROM suppliers";

                try (Connection conn = sql_connecter.getConnection();
                     Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery(sql)) {

                    StringBuilder sb = new StringBuilder();
                    while (rs.next()) {
                        int supplierId = rs.getInt("supplier_id");
                        String supplierName = rs.getString("supplier_name");
                        String contactInfo = rs.getString("contact_info");

                        sb.append(String.format("Supplier ID: %d\nSupplier Name: %s\nContact Info: %s\n\n",
                                supplierId, supplierName, contactInfo));
                    }

                    JTextArea textArea = new JTextArea(sb.toString());
                    textArea.setFont(new Font("Arial", Font.PLAIN, 16));
                    textArea.setEditable(false);
                    JScrollPane scrollPane = new JScrollPane(textArea);
                    scrollPane.setPreferredSize(new Dimension(500, 400));
                    JOptionPane.showMessageDialog(null, scrollPane, "All Suppliers", JOptionPane.INFORMATION_MESSAGE);

                } catch (SQLException e1) {
                    JOptionPane.showMessageDialog(null, "Error: " + e1.getMessage());
                }
            }
        });
    }
}
