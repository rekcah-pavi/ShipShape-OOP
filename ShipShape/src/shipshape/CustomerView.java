package shipshape;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*;

public class CustomerView extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

  
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    CustomerView frame = new CustomerView();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void createTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS customers (" +
                     "customer_id INT PRIMARY KEY, " +
                     "name VARCHAR(255) NOT NULL, " +
                     "email VARCHAR(255) NOT NULL," +
                     "city VARCHAR(255) NOT NULL" +
                     ")";

        try (Connection conn = sql_connecter.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public CustomerView() {
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

      
        JLabel titleLabel = new JLabel("Customer Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        GridBagConstraints gbc_titleLabel = new GridBagConstraints();
        gbc_titleLabel.gridwidth = 3;
        gbc_titleLabel.insets = new Insets(10, 10, 20, 10);
        gbc_titleLabel.gridx = 1;
        gbc_titleLabel.gridy = 1;
        contentPane.add(titleLabel, gbc_titleLabel);

       
        JLabel lblCustomerId = new JLabel("Customer ID");
        lblCustomerId.setFont(new Font("Arial", Font.PLAIN, 16));
        GridBagConstraints gbc_lblCustomerId = new GridBagConstraints();
        gbc_lblCustomerId.insets = new Insets(10, 10, 10, 10);
        gbc_lblCustomerId.gridx = 0;
        gbc_lblCustomerId.gridy = 2;
        contentPane.add(lblCustomerId, gbc_lblCustomerId);

        JTextArea textCustomerId = new JTextArea();
        textCustomerId.setFont(new Font("Arial", Font.PLAIN, 16));
        textCustomerId.setPreferredSize(new Dimension(200, 30));
        GridBagConstraints gbc_textCustomerId = new GridBagConstraints();
        gbc_textCustomerId.insets = new Insets(10, 10, 10, 10);
        gbc_textCustomerId.gridx = 1;
        gbc_textCustomerId.gridy = 2;
        contentPane.add(textCustomerId, gbc_textCustomerId);

        JButton btnGetCustomerDetails = new JButton("Get Details");
        btnGetCustomerDetails.setFont(new Font("Arial", Font.PLAIN, 16));
        GridBagConstraints gbc_btnGetCustomerDetails = new GridBagConstraints();
        gbc_btnGetCustomerDetails.insets = new Insets(20, 10, 10, 10);
        gbc_btnGetCustomerDetails.gridx = 3;
        gbc_btnGetCustomerDetails.gridy = 2;
        contentPane.add(btnGetCustomerDetails, gbc_btnGetCustomerDetails);

        JButton btnDeleteCustomer = new JButton("Delete Customer");
        btnDeleteCustomer.setFont(new Font("Arial", Font.PLAIN, 16));
        GridBagConstraints gbc_btnDeleteCustomer = new GridBagConstraints();
        gbc_btnDeleteCustomer.insets = new Insets(20, 10, 10, 10);
        gbc_btnDeleteCustomer.gridx = 4;
        gbc_btnDeleteCustomer.gridy = 2;
        contentPane.add(btnDeleteCustomer, gbc_btnDeleteCustomer);

        btnDeleteCustomer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String temp_id = textCustomerId.getText();
                if (temp_id.equals("")) {
                    JOptionPane.showMessageDialog(null, "Error: missing customer ID");
                    return;
                }

                int customer_id;
                try {
                    customer_id = Integer.parseInt(temp_id);
                } catch (NumberFormatException e1) {
                    JOptionPane.showMessageDialog(null, "Error: Customer ID must be a number");
                    return;
                }

                String sql = "DELETE FROM customers WHERE customer_id = ?";

                try (Connection conn = sql_connecter.getConnection();
                     PreparedStatement pstmt = conn.prepareStatement(sql)) {

                    pstmt.setInt(1, customer_id);

                    int rowsAffected = pstmt.executeUpdate();
                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(null, "Customer Deleted Successfully");
                    } else {
                        JOptionPane.showMessageDialog(null, "Customer Not Found");
                    }

                } catch (SQLException e1) {
                    JOptionPane.showMessageDialog(null, "Error: " + e1.getMessage());
                }
            }
        });

     
        JLabel lblCustomerName = new JLabel("Customer Name");
        lblCustomerName.setFont(new Font("Arial", Font.PLAIN, 16));
        GridBagConstraints gbc_lblCustomerName = new GridBagConstraints();
        gbc_lblCustomerName.insets = new Insets(10, 10, 10, 10);
        gbc_lblCustomerName.gridx = 0;
        gbc_lblCustomerName.gridy = 3;
        contentPane.add(lblCustomerName, gbc_lblCustomerName);

        JTextArea textCustomerName = new JTextArea();
        textCustomerName.setFont(new Font("Arial", Font.PLAIN, 16));
        textCustomerName.setPreferredSize(new Dimension(200, 30));
        GridBagConstraints gbc_textCustomerName = new GridBagConstraints();
        gbc_textCustomerName.insets = new Insets(10, 10, 10, 10);
        gbc_textCustomerName.gridx = 1;
        gbc_textCustomerName.gridy = 3;
        contentPane.add(textCustomerName, gbc_textCustomerName);

       
        JLabel lblEmail = new JLabel("Email");
        lblEmail.setFont(new Font("Arial", Font.PLAIN, 16));
        GridBagConstraints gbc_lblEmail = new GridBagConstraints();
        gbc_lblEmail.insets = new Insets(10, 10, 10, 10);
        gbc_lblEmail.gridx = 0;
        gbc_lblEmail.gridy = 4;
        contentPane.add(lblEmail, gbc_lblEmail);

        JTextArea textEmail = new JTextArea();
        textEmail.setFont(new Font("Arial", Font.PLAIN, 16));
        textEmail.setPreferredSize(new Dimension(200, 30));
        GridBagConstraints gbc_textEmail = new GridBagConstraints();
        gbc_textEmail.insets = new Insets(10, 10, 10, 10);
        gbc_textEmail.gridx = 1;
        gbc_textEmail.gridy = 4;
        contentPane.add(textEmail, gbc_textEmail);

     
        JLabel lblCity = new JLabel("City");
        lblCity.setFont(new Font("Arial", Font.PLAIN, 16));
        GridBagConstraints gbc_lblCity = new GridBagConstraints();
        gbc_lblCity.insets = new Insets(10, 10, 10, 10);
        gbc_lblCity.gridx = 0;
        gbc_lblCity.gridy = 5;
        contentPane.add(lblCity, gbc_lblCity);

        JTextArea textCity = new JTextArea();
        textCity.setFont(new Font("Arial", Font.PLAIN, 16));
        textCity.setPreferredSize(new Dimension(200, 30));
        GridBagConstraints gbc_textCity = new GridBagConstraints();
        gbc_textCity.insets = new Insets(10, 10, 10, 10);
        gbc_textCity.gridx = 1;
        gbc_textCity.gridy = 5;
        contentPane.add(textCity, gbc_textCity);

      
        JButton btnAddUpdateCustomer = new JButton("Add/Update Customer");
        btnAddUpdateCustomer.setFont(new Font("Arial", Font.PLAIN, 16));
        GridBagConstraints gbc_btnAddUpdateCustomer = new GridBagConstraints();
        gbc_btnAddUpdateCustomer.insets = new Insets(20, 10, 10, 10);
        gbc_btnAddUpdateCustomer.gridx = 1;
        gbc_btnAddUpdateCustomer.gridy = 7;
        contentPane.add(btnAddUpdateCustomer, gbc_btnAddUpdateCustomer);

        JButton btnGetAllCustomers = new JButton("Get All Customers");
        btnGetAllCustomers.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String sql = "SELECT customer_id, name, email, city FROM customers";

                try (Connection conn = sql_connecter.getConnection();
                     Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery(sql)) {

                    StringBuilder sb = new StringBuilder();
                    while (rs.next()) {
                        int customerId = rs.getInt("customer_id");
                        String name = rs.getString("name");
                        String email = rs.getString("email");
                        String city = rs.getString("city");

                        sb.append(String.format("Customer ID: %d\nName: %s\nEmail: %s\nCity: %s\n\n", customerId, name, email, city));
                    }

                    JTextArea textArea = new JTextArea(sb.toString());
                    textArea.setFont(new Font("Arial", Font.PLAIN, 16));
                    textArea.setEditable(false);
                    JScrollPane scrollPane = new JScrollPane(textArea);
                    scrollPane.setPreferredSize(new Dimension(500, 400));
                    JOptionPane.showMessageDialog(null, scrollPane, "All Customers", JOptionPane.INFORMATION_MESSAGE);

                } catch (SQLException e1) {
                    JOptionPane.showMessageDialog(null, "Error: " + e1.getMessage());
                }
            }
        });
        btnGetAllCustomers.setFont(new Font("Arial", Font.PLAIN, 16));
        GridBagConstraints gbc_btnGetAllCustomers = new GridBagConstraints();
        gbc_btnGetAllCustomers.gridx = 4;
        gbc_btnGetAllCustomers.gridy = 7;
        contentPane.add(btnGetAllCustomers, gbc_btnGetAllCustomers);

        btnAddUpdateCustomer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String temp_id = textCustomerId.getText();
                if (temp_id.equals("")) {
                    JOptionPane.showMessageDialog(null, "Error: missing customer ID");
                    return;
                }

                int customer_id;
                try {
                    customer_id = Integer.parseInt(temp_id);
                } catch (NumberFormatException e1) {
                    JOptionPane.showMessageDialog(null, "Error: Customer ID must be a number");
                    return;
                }

                String name = textCustomerName.getText();
                String email = textEmail.getText();
                String city = textCity.getText();

                String sql = "REPLACE INTO customers (customer_id, name, email, city) VALUES (?, ?, ?, ?)";

                try (Connection conn = sql_connecter.getConnection();
                     PreparedStatement pstmt = conn.prepareStatement(sql)) {

                    pstmt.setInt(1, customer_id);
                    pstmt.setString(2, name);
                    pstmt.setString(3, email);
                    pstmt.setString(4, city);

                    pstmt.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Customer Added/Updated Successfully");

                } catch (SQLException e1) {
                    JOptionPane.showMessageDialog(null, "Error: " + e1.getMessage());
                }
            }
        });

        btnGetCustomerDetails.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String temp_id = textCustomerId.getText();
                if (temp_id.equals("")) {
                    JOptionPane.showMessageDialog(null, "Error: missing customer ID");
                    return;
                }

                int customer_id;
                try {
                    customer_id = Integer.parseInt(temp_id);
                } catch (NumberFormatException e1) {
                    JOptionPane.showMessageDialog(null, "Error: Customer ID must be a number");
                    return;
                }

                String sql = "SELECT name, email, city FROM customers WHERE customer_id = ?";

                try (Connection conn = sql_connecter.getConnection();
                     PreparedStatement pstmt = conn.prepareStatement(sql)) {

                    pstmt.setInt(1, customer_id);

                    ResultSet rs = pstmt.executeQuery();

                    if (rs.next()) {
                        textCustomerName.setText(rs.getString("name"));
                        textEmail.setText(rs.getString("email"));
                        textCity.setText(rs.getString("city"));
                    } else {
                        JOptionPane.showMessageDialog(null, "Customer Not Found");
                    }

                } catch (SQLException e1) {
                    JOptionPane.showMessageDialog(null, "Error: " + e1.getMessage());
                }
            }
        });

        createTableIfNotExists();
    }
}
