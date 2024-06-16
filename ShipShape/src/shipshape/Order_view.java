package shipshape;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*;

public class Order_view extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextArea textOrderId;
    private JTextArea textItemid;
    private JTextArea textCustomerId;

    private JRadioButton rbtnProcessing;
    private JRadioButton rbtnShipping;
    private JRadioButton rbtnReady;
    private ButtonGroup orderStatusGroup;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Order_view frame = new Order_view();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    

    public void createTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS orders (" +
                "order_id INT PRIMARY KEY, " +
                "item_id VARCHAR(255) NOT NULL, " +
                "order_status VARCHAR(255) NOT NULL, " +
                "customer_id INT NOT NULL" +
                ")";

        try (Connection conn = sql_connecter.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Order_view() {
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

    
        JLabel titleLabel = new JLabel("Place Order");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        GridBagConstraints gbc_titleLabel = new GridBagConstraints();
        gbc_titleLabel.gridwidth = 3;
        gbc_titleLabel.insets = new Insets(10, 10, 20, 10);
        gbc_titleLabel.gridx = 1;
        gbc_titleLabel.gridy = 2;
        contentPane.add(titleLabel, gbc_titleLabel);

       
        JLabel lblOrderId = new JLabel("Order ID");
        lblOrderId.setFont(new Font("Arial", Font.PLAIN, 16));
        GridBagConstraints gbc_lblOrderId = new GridBagConstraints();
        gbc_lblOrderId.insets = new Insets(10, 10, 10, 10);
        gbc_lblOrderId.gridx = 0;
        gbc_lblOrderId.gridy = 3;
        contentPane.add(lblOrderId, gbc_lblOrderId);

        textOrderId = new JTextArea();
        textOrderId.setFont(new Font("Arial", Font.PLAIN, 16));
        textOrderId.setPreferredSize(new Dimension(200, 30));
        GridBagConstraints gbc_textOrderId = new GridBagConstraints();
        gbc_textOrderId.insets = new Insets(10, 10, 10, 10);
        gbc_textOrderId.gridx = 1;
        gbc_textOrderId.gridy = 3;
        contentPane.add(textOrderId, gbc_textOrderId);

        JButton btnGetOrderDetails = new JButton("Get Details");
        btnGetOrderDetails.setFont(new Font("Arial", Font.PLAIN, 16));
        GridBagConstraints gbc_btnGetOrderDetails = new GridBagConstraints();
        gbc_btnGetOrderDetails.insets = new Insets(20, 10, 10, 10);
        gbc_btnGetOrderDetails.gridx = 3;
        gbc_btnGetOrderDetails.gridy = 3;
        contentPane.add(btnGetOrderDetails, gbc_btnGetOrderDetails);

        btnGetOrderDetails.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String torder_id = textOrderId.getText();
                if (torder_id.equals("")) {
                    JOptionPane.showMessageDialog(null, "Error: missing order ID");
                    return;
                }

                int order_id;
                try {
                    order_id = Integer.parseInt(torder_id);
                } catch (NumberFormatException e1) {
                    JOptionPane.showMessageDialog(null, "Error: Order ID must be a number");
                    return;
                }

                String sql = "SELECT order_id, item_id, order_status, customer_id FROM orders WHERE order_id = " + order_id;

                try (Connection conn = sql_connecter.getConnection();
                     Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery(sql)) {

                    if (rs.next()) {
                        int retrievedOrderId = rs.getInt("order_id");
                        String Itemid = rs.getString("item_id");
                        String orderStatus = rs.getString("order_status");
                        int customerId = rs.getInt("customer_id");

                     
                        textItemid.setText(Itemid);
                        textCustomerId.setText(String.valueOf(customerId));

                        switch (orderStatus) {
                            case "Processing":
                                rbtnProcessing.setSelected(true);
                                break;
                            case "Shipping":
                                rbtnShipping.setSelected(true);
                                break;
                            case "Ready":
                                rbtnReady.setSelected(true);
                                break;
                            default:
                                orderStatusGroup.clearSelection();
                                break;
                        }

                    } else {
                        JOptionPane.showMessageDialog(null, "Order not found");
                    }

                } catch (SQLException e1) {
                    JOptionPane.showMessageDialog(null, "Error: " + e1.getMessage());
                }
            }
        });

        JButton btnDeleteOrder = new JButton("Delete Order");
        btnDeleteOrder.setFont(new Font("Arial", Font.PLAIN, 16));
        GridBagConstraints gbc_btnDeleteOrder = new GridBagConstraints();
        gbc_btnDeleteOrder.insets = new Insets(20, 10, 10, 10);
        gbc_btnDeleteOrder.gridx = 4;
        gbc_btnDeleteOrder.gridy = 3;
        contentPane.add(btnDeleteOrder, gbc_btnDeleteOrder);

        btnDeleteOrder.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String toder_id = textOrderId.getText();
                if (toder_id.equals("")) {
                    JOptionPane.showMessageDialog(null, "Error: missing order ID");
                    return;
                }

                int order_id;
                try {
                    order_id = Integer.parseInt(toder_id);
                } catch (NumberFormatException e1) {
                    JOptionPane.showMessageDialog(null, "Error: Order ID must be a number");
                    return;
                }

                String sql = "DELETE FROM orders WHERE order_id = ?";

                try (Connection conn = sql_connecter.getConnection();
                     PreparedStatement pstmt = conn.prepareStatement(sql)) {

                    pstmt.setInt(1, order_id);

                    int rowsAffected = pstmt.executeUpdate();
                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(null, "Order Deleted Successfully");
                    } else {
                        JOptionPane.showMessageDialog(null, "Order Not Found");
                    }

                } catch (SQLException e1) {
                    JOptionPane.showMessageDialog(null, "Error: " + e1.getMessage());
                }
            }
        });

     
        JLabel lblCustomerId = new JLabel("Customer ID");
        lblCustomerId.setFont(new Font("Arial", Font.PLAIN, 16));
        GridBagConstraints gbc_lblCustomerId = new GridBagConstraints();
        gbc_lblCustomerId.insets = new Insets(10, 10, 10, 10);
        gbc_lblCustomerId.gridx = 0;
        gbc_lblCustomerId.gridy = 4;
        contentPane.add(lblCustomerId, gbc_lblCustomerId);

        textCustomerId = new JTextArea();
        textCustomerId.setFont(new Font("Arial", Font.PLAIN, 16));
        textCustomerId.setPreferredSize(new Dimension(200, 30));
        GridBagConstraints gbc_textCustomerId = new GridBagConstraints();
        gbc_textCustomerId.insets = new Insets(10, 10, 10, 10);
        gbc_textCustomerId.gridx = 1;
        gbc_textCustomerId.gridy = 4;
        contentPane.add(textCustomerId, gbc_textCustomerId);

        JButton btnGetCustomerOrders = new JButton("Get Customer Orders");
        btnGetCustomerOrders.setFont(new Font("Arial", Font.PLAIN, 16));
        GridBagConstraints gbc_btnGetCustomerOrders = new GridBagConstraints();
        gbc_btnGetCustomerOrders.insets = new Insets(20, 10, 10, 10);
        gbc_btnGetCustomerOrders.gridx = 3;
        gbc_btnGetCustomerOrders.gridy = 4;
        gbc_btnGetCustomerOrders.gridwidth = 2;
        contentPane.add(btnGetCustomerOrders, gbc_btnGetCustomerOrders);

        btnGetCustomerOrders.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String tcustomer_id = textCustomerId.getText();
                if (tcustomer_id.equals("")) {
                    JOptionPane.showMessageDialog(null, "Error: missing customer ID");
                    return;
                }

                int customer_id;
                try {
                    customer_id = Integer.parseInt(tcustomer_id);
                } catch (NumberFormatException e1) {
                    JOptionPane.showMessageDialog(null, "Error: Customer ID must be a number");
                    return;
                }

                String sql = "SELECT order_id, item_id, order_status FROM orders WHERE customer_id = " + customer_id;

                try (Connection conn = sql_connecter.getConnection();
                     Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery(sql)) {

                    StringBuilder sb = new StringBuilder();
                    while (rs.next()) {
                        int orderId = rs.getInt("order_id");
                        String Itemid = rs.getString("item_id");
                        String orderStatus = rs.getString("order_status");

                        sb.append(String.format("Order ID: %d\nOrder Name: %s\nOrder Status: %s\n\n",
                                orderId, Itemid, orderStatus));
                    }

                    if (sb.length() == 0) {
                        sb.append("No orders found for customer ID: ").append(customer_id);
                    }

                    JTextArea textArea = new JTextArea(sb.toString());
                    textArea.setFont(new Font("Arial", Font.PLAIN, 16));
                    textArea.setEditable(false);
                    JScrollPane scrollPane = new JScrollPane(textArea);
                    scrollPane.setPreferredSize(new Dimension(500, 400));
                    JOptionPane.showMessageDialog(null, scrollPane, "Customer Orders", JOptionPane.INFORMATION_MESSAGE);

                } catch (SQLException e1) {
                    JOptionPane.showMessageDialog(null, "Error: " + e1.getMessage());
                }
            }
        });

       
        JLabel lblItemid = new JLabel("Item Id");
        lblItemid.setFont(new Font("Arial", Font.PLAIN, 16));
        GridBagConstraints gbc_lblItemid = new GridBagConstraints();
        gbc_lblItemid.insets = new Insets(10, 10, 10, 10);
        gbc_lblItemid.gridx = 0;
        gbc_lblItemid.gridy = 5;
        contentPane.add(lblItemid, gbc_lblItemid);

        textItemid = new JTextArea();
        textItemid.setFont(new Font("Arial", Font.PLAIN, 16));
        textItemid.setPreferredSize(new Dimension(200, 30));
        GridBagConstraints gbc_textItemid = new GridBagConstraints();
        gbc_textItemid.insets = new Insets(10, 10, 10, 10);
        gbc_textItemid.gridx = 1;
        gbc_textItemid.gridy = 5;
        contentPane.add(textItemid, gbc_textItemid);

       
        JLabel lblOrderStatus = new JLabel("Order Status");
        lblOrderStatus.setFont(new Font("Arial", Font.PLAIN, 16));
        GridBagConstraints gbc_lblOrderStatus = new GridBagConstraints();
        gbc_lblOrderStatus.insets = new Insets(10, 10, 10, 10);
        gbc_lblOrderStatus.gridx = 0;
        gbc_lblOrderStatus.gridy = 6;
        contentPane.add(lblOrderStatus, gbc_lblOrderStatus);

        JPanel statusPanel = new JPanel();
        statusPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        GridBagConstraints gbc_statusPanel = new GridBagConstraints();
        gbc_statusPanel.insets = new Insets(10, 10, 10, 10);
        gbc_statusPanel.gridx = 1;
        gbc_statusPanel.gridy = 6;
        contentPane.add(statusPanel, gbc_statusPanel);

        rbtnProcessing = new JRadioButton("Processing");
        rbtnProcessing.setFont(new Font("Arial", Font.PLAIN, 16));
        statusPanel.add(rbtnProcessing);

        rbtnShipping = new JRadioButton("Shipping");
        rbtnShipping.setFont(new Font("Arial", Font.PLAIN, 16));
        statusPanel.add(rbtnShipping);

        rbtnReady = new JRadioButton("Ready");
        rbtnReady.setFont(new Font("Arial", Font.PLAIN, 16));
        statusPanel.add(rbtnReady);

        orderStatusGroup = new ButtonGroup();
        orderStatusGroup.add(rbtnProcessing);
        orderStatusGroup.add(rbtnShipping);
        orderStatusGroup.add(rbtnReady);
        
        JButton btnGetAllOrders = new JButton("Get All Orders");
        btnGetAllOrders.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		String sql = "SELECT order_id, item_id, order_status, customer_id FROM orders";
                
                try (Connection conn = sql_connecter.getConnection();
                     Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery(sql)) {

                    StringBuilder sb = new StringBuilder();
                    while (rs.next()) {
                        int orderId = rs.getInt("order_id");
                        String Itemid = rs.getString("item_id");
                        String orderStatus = rs.getString("order_status");
                        int customerId = rs.getInt("customer_id");

                        sb.append(String.format("Order ID: %d\nOrder Name: %s\nOrder Status: %s\nCustomer ID: %d\n\n",
                                orderId, Itemid, orderStatus, customerId));
                    }

                    JTextArea textArea = new JTextArea(sb.toString());
                    textArea.setFont(new Font("Arial", Font.PLAIN, 16));
                    textArea.setEditable(false);
                    JScrollPane scrollPane = new JScrollPane(textArea);
                    scrollPane.setPreferredSize(new Dimension(500, 400));
                    JOptionPane.showMessageDialog(null, scrollPane, "All Orders", JOptionPane.INFORMATION_MESSAGE);

                } catch (SQLException e1) {
                    JOptionPane.showMessageDialog(null, "Error: " + e1.getMessage());
                }
        	}
        });
        
        JButton btnPlaceOrder = new JButton("Place Order");
        btnPlaceOrder.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
      

        		String sqlCheckInventory = "SELECT quantity FROM inventory WHERE item_id = ?";
        		String sqlUpdateInventory = "UPDATE inventory SET quantity = quantity - 1 WHERE item_id = ?";
        		String sqlGetItemInfo = "SELECT item_name, SupplierId FROM inventory WHERE item_id = ?";
        		String sqlGetSupplierInfo = "SELECT supplier_name, contact_info FROM suppliers WHERE supplier_id = ?";

        		String toder_id = textOrderId.getText();
        		String titem_id = textItemid.getText();
        		String tcustomer_id = textCustomerId.getText();

        		if (toder_id.equals("") || titem_id.equals("") || tcustomer_id.equals("")) {
        		    JOptionPane.showMessageDialog(null, "Error: missing required fields");
        		    return;
        		}

        		int order_id, customer_id, item_id;
        		try {
        		    order_id = Integer.parseInt(toder_id);
        		    item_id = Integer.parseInt(titem_id);
        		    customer_id = Integer.parseInt(tcustomer_id);
        		} catch (NumberFormatException e1) {
        		    JOptionPane.showMessageDialog(null, "Error: Order ID, Item ID and Customer ID must be numbers");
        		    return;
        		}

        		String order_status;
        		if (rbtnProcessing.isSelected()) {
        		    order_status = "Processing";
        		} else if (rbtnShipping.isSelected()) {
        		    order_status = "Shipping";
        		} else if (rbtnReady.isSelected()) {
        		    order_status = "Ready";
        		} else {
        		    JOptionPane.showMessageDialog(null, "Error: Please select an order status");
        		    return;
        		}

        		try (Connection conn = sql_connecter.getConnection()) {
        		    // Check if the item exists and has enough quantity
        		    try (PreparedStatement pstmtCheck = conn.prepareStatement(sqlCheckInventory)) {
        		        pstmtCheck.setInt(1, item_id);
        		        try (ResultSet rs = pstmtCheck.executeQuery()) {
        		            if (rs.next()) {
        		                int currentQuantity = rs.getInt("quantity");
        		                if (currentQuantity <= 0) {
        		                    int response = JOptionPane.showConfirmDialog(null, 
        		                            "Error: Not enough quantity in stock. Do you want to send an automated email to the supplier?", 
        		                            "Stock Error", 
        		                            JOptionPane.YES_NO_OPTION, 
        		                            JOptionPane.QUESTION_MESSAGE);

        		                   
        		                    if (response == JOptionPane.YES_OPTION) {
        		              
        		                        try (PreparedStatement pstmtItem = conn.prepareStatement(sqlGetItemInfo)) {
        		                            pstmtItem.setInt(1, item_id);
        		                            try (ResultSet rsItem = pstmtItem.executeQuery()) {
        		                                if (rsItem.next()) {
        		                                    String item_name = rsItem.getString("item_name");
        		                                    int supplier_id = rsItem.getInt("SupplierId");

        		                                    try (PreparedStatement pstmtSupplier = conn.prepareStatement(sqlGetSupplierInfo)) {
        		                                        pstmtSupplier.setInt(1, supplier_id);
        		                                        try (ResultSet rsSupplier = pstmtSupplier.executeQuery()) {
        		                                            if (rsSupplier.next()) {
        		                                                String supplier_name = rsSupplier.getString("supplier_name");
        		                                                String supplier_email = rsSupplier.getString("contact_info");

        		                                                new Thread(() -> {
        		                                                    try {
        		                                                        Mail_sender mailSender = new Mail_sender();
        		                                                        String subject = "Restock Request for Item " + item_name;
        		                                                        String message = "Dear " + supplier_name + ",\n\n" +
        		                                                                         "We are running low on item: " + item_name + ". Please restock as soon as possible.\n\n" +
        		                                                                         "Best regards,\n" +
        		                                                                         "The ShipeShape Team";

        		                                                        mailSender.send(supplier_email, subject, message);
        		                                                    } catch (Exception e2) {
        		                                                        SwingUtilities.invokeLater(() -> 
        		                                                            JOptionPane.showMessageDialog(null, "Error sending email: " + e2.getMessage()));
        		                                                    }
        		                                                }).start();
        		                                                JOptionPane.showMessageDialog(null, "Email request send!");
        		                                            } else {
        		                                                JOptionPane.showMessageDialog(null, "Error: No supplier found for the given supplier ID");
        		                                            }
        		                                        }
        		                                    } catch (SQLException e1) {
        		                                        SwingUtilities.invokeLater(() -> 
        		                                            JOptionPane.showMessageDialog(null, "Error retrieving supplier information: " + e1.getMessage()));
        		                                        	return;
        		                                    }
        		                                } else {
        		                                    JOptionPane.showMessageDialog(null, "Error: No item found with the given item ID");
        		                                    return;
        		                                }
        		                            }
        		                        }
        		                   
        		                    }
        		                    return;
        		                }
        		            } else {
        		                JOptionPane.showMessageDialog(null, "Error: Invalid item ID");
        		                return;
        		            }
        		        }
        		    }

       
        		    String sqlOrder = "INSERT INTO orders (order_id, item_id, order_status, customer_id) VALUES (?, ?, ?, ?)";
        		    try (PreparedStatement pstmtOrder = conn.prepareStatement(sqlOrder)) {
        		        pstmtOrder.setInt(1, order_id);
        		        pstmtOrder.setInt(2, item_id);
        		        pstmtOrder.setString(3, order_status);
        		        pstmtOrder.setInt(4, customer_id);
        		        pstmtOrder.executeUpdate();
        		        JOptionPane.showMessageDialog(null, "Order Placed/Updated Successfully");

        		        try (PreparedStatement pstmtUpdate = conn.prepareStatement(sqlUpdateInventory)) {
        		            pstmtUpdate.setInt(1, item_id);
        		            pstmtUpdate.executeUpdate();
        		        }
        		    }

        		    if (order_status.equals("Ready")) {
        		        try {
        		            String sqlCustomer = "SELECT name, email FROM customers WHERE customer_id = ?";
        		            try (PreparedStatement pstmtCustomer = conn.prepareStatement(sqlCustomer)) {
        		                pstmtCustomer.setInt(1, customer_id);
        		                try (ResultSet rs = pstmtCustomer.executeQuery()) {
        		                    if (rs.next()) {
        		                        String customer_name = rs.getString("name");
        		                        String customer_email = rs.getString("email");

        		                        new Thread(() -> {
        		                            try {
        		                                Mail_sender mailSender = new Mail_sender();
        		                                String subject = "Order is ready to be collected";
        		                                String message = "Dear " + customer_name + ",\n\n" +
        		                                                 "We are happy to inform you that your order " + rs.getString("item_name") + " is ready to be collected.\n\n" +
        		                                                 "Best regards,\n" +
        		                                                 "The ShipeShape Team";

        		                                mailSender.send(customer_email, subject, message);
        		                            } catch (Exception e2) {
        		                                SwingUtilities.invokeLater(() -> 
        		                                    JOptionPane.showMessageDialog(null, "Error sending email: " + e2.getMessage()));
        		                            }
        		                        }).start();
        		                    } else {
        		                        System.out.println("No customer found with ID: " + customer_id);
        		                    }
        		                }
        		            }
        		        } catch (Exception e2) {
        		            SwingUtilities.invokeLater(() -> 
        		                JOptionPane.showMessageDialog(null, "Error retrieving customer information: " + e2.getMessage()));
        		        }
        		    }

        		} catch (SQLException e1) {
        		    JOptionPane.showMessageDialog(null, "Error: " + e1.getMessage());
        		}



        	}
        });
        
        JButton btnUpdate = new JButton("Update");
        btnUpdate.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		String toder_id = textOrderId.getText();
        		String titem_id = textItemid.getText();
        		String tcustomer_id = textCustomerId.getText();
        		
        		if (toder_id.equals("") || titem_id.equals("") || tcustomer_id.equals("")) {
        		    JOptionPane.showMessageDialog(null, "Error: missing required fields");
        		    return;
        		}
        		int customer_id = Integer.parseInt(tcustomer_id);
                if (toder_id.equals("")) {
                    JOptionPane.showMessageDialog(null, "Error: missing order ID");
                    return;
                }

                int order_id;
                try {
                    order_id = Integer.parseInt(toder_id);
                } catch (NumberFormatException e1) {
                    JOptionPane.showMessageDialog(null, "Error: Order ID must be a number");
                    return;
                }

                // Determine selected order status
                String order_status;
                if (rbtnProcessing.isSelected()) {
                    order_status = "Processing";
                } else if (rbtnShipping.isSelected()) {
                    order_status = "Shipping";
                } else if (rbtnReady.isSelected()) {
                    order_status = "Ready";
                } else {
                    JOptionPane.showMessageDialog(null, "Error: Please select an order status");
                    return;
                }

                // SQL update statement
                String sql = "UPDATE orders SET order_status = ? WHERE order_id = ?";

                try (Connection conn = sql_connecter.getConnection();
                     PreparedStatement pstmt = conn.prepareStatement(sql)) {

                    // Set parameters
                    pstmt.setString(1, order_status);
                    pstmt.setInt(2, order_id);

                    // Execute update
                    int rowsAffected = pstmt.executeUpdate();
                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(null, "Order status updated successfully");
                    } else {
                        JOptionPane.showMessageDialog(null, "Order not found");
                    }
                    
                    if (order_status.equals("Ready")) {
        		        try {
        		            String sqlCustomer = "SELECT name, email FROM customers WHERE customer_id = ?";
        		            try (PreparedStatement pstmtCustomer = conn.prepareStatement(sqlCustomer)) {
        		                pstmtCustomer.setInt(1, customer_id);
        		                try (ResultSet rs = pstmtCustomer.executeQuery()) {
        		                    if (rs.next()) {
        		                        String customer_name = rs.getString("name");
        		                        String customer_email = rs.getString("email");

        		                        new Thread(() -> {
        		                            try {
        		                                Mail_sender mailSender = new Mail_sender();
        		                                String subject = "Order is ready to be collected";
        		                                String message = "Dear " + customer_name + ",\n\n" +
        		                                                 "We are happy to inform you that your order " + titem_id + " is ready to be collected.\n\n" +
        		                                                 "Best regards,\n" +
        		                                                 "The ShipeShape Team";

        		                                mailSender.send(customer_email, subject, message);
        		                            } catch (Exception e2) {
        		                                SwingUtilities.invokeLater(() -> 
        		                                    JOptionPane.showMessageDialog(null, "Error sending email: " + e2.getMessage()));
        		                            }
        		                        }).start();
        		                    } else {
        		                        System.out.println("No customer found with ID: " + customer_id);
        		                    }
        		                }
        		            }
        		        } catch (Exception e2) {
        		            SwingUtilities.invokeLater(() -> 
        		                JOptionPane.showMessageDialog(null, "Error retrieving customer information: " + e2.getMessage()));
        		        }
        		    }

        		


                } catch (SQLException e1) {
                    JOptionPane.showMessageDialog(null, "Error: " + e1.getMessage());
                }
            
        	}
        });
        btnUpdate.setFont(new Font("Arial", Font.PLAIN, 16));
        GridBagConstraints gbc_btnUpdate = new GridBagConstraints();
        gbc_btnUpdate.insets = new Insets(0, 0, 5, 5);
        gbc_btnUpdate.gridx = 3;
        gbc_btnUpdate.gridy = 6;
        contentPane.add(btnUpdate, gbc_btnUpdate);
        btnPlaceOrder.setFont(new Font("Arial", Font.PLAIN, 16));
        GridBagConstraints gbc_btnPlaceOrder = new GridBagConstraints();
        gbc_btnPlaceOrder.insets = new Insets(0, 0, 0, 5);
        gbc_btnPlaceOrder.gridx = 3;
        gbc_btnPlaceOrder.gridy = 7;
        contentPane.add(btnPlaceOrder, gbc_btnPlaceOrder);
        btnGetAllOrders.setFont(new Font("Arial", Font.PLAIN, 16));
        GridBagConstraints gbc_btnGetAllOrders = new GridBagConstraints();
        gbc_btnGetAllOrders.anchor = GridBagConstraints.SOUTH;
        gbc_btnGetAllOrders.gridx = 4;
        gbc_btnGetAllOrders.gridy = 7;
        contentPane.add(btnGetAllOrders, gbc_btnGetAllOrders);
        createTableIfNotExists();
    }
}
