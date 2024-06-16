package shipshape;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*;

public class Inventory_view extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Inventory_view frame = new Inventory_view();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    public void createTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS inventory (" +
                "item_id INT PRIMARY KEY, " +
                "item_name VARCHAR(255) NOT NULL, " +
                "quantity INT NOT NULL, " +
                "SupplierId INT NOT NULL" +
                ")";

        try (Connection conn = sql_connecter.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Inventory_view() {
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
        
   
        JLabel titleLabel = new JLabel("Inventory Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        GridBagConstraints gbc_titleLabel = new GridBagConstraints();
        gbc_titleLabel.gridwidth = 3;
        gbc_titleLabel.insets = new Insets(10, 10, 20, 10);
        gbc_titleLabel.gridx = 1;
        gbc_titleLabel.gridy = 1;
        contentPane.add(titleLabel, gbc_titleLabel);

     
        JLabel lblitemId = new JLabel("Item ID");
        lblitemId.setFont(new Font("Arial", Font.PLAIN, 16));
        GridBagConstraints gbc_lblitemId = new GridBagConstraints();
        gbc_lblitemId.insets = new Insets(10, 10, 10, 10);
        gbc_lblitemId.gridx = 0;
        gbc_lblitemId.gridy = 2;
        contentPane.add(lblitemId, gbc_lblitemId);

        JTextArea textitemId = new JTextArea();
        textitemId.setFont(new Font("Arial", Font.PLAIN, 16));
        textitemId.setPreferredSize(new Dimension(200, 30));
        GridBagConstraints gbc_textitemId = new GridBagConstraints();
        gbc_textitemId.insets = new Insets(10, 10, 10, 10);
        gbc_textitemId.gridx = 1;
        gbc_textitemId.gridy = 2;
        contentPane.add(textitemId, gbc_textitemId);
        
        JButton btnGetitemDetails = new JButton("Get Details");
        btnGetitemDetails.setFont(new Font("Arial", Font.PLAIN, 16));
        GridBagConstraints gbc_btnGetitemDetails = new GridBagConstraints();
        gbc_btnGetitemDetails.insets = new Insets(20, 10, 10, 10);
        gbc_btnGetitemDetails.gridx = 3;
        gbc_btnGetitemDetails.gridy = 2;
        contentPane.add(btnGetitemDetails, gbc_btnGetitemDetails);

        
        
        JButton btnDeleteitem = new JButton("Delete item");
        btnDeleteitem.setFont(new Font("Arial", Font.PLAIN, 16));
        GridBagConstraints gbc_btnDeleteitem = new GridBagConstraints();
        gbc_btnDeleteitem.insets = new Insets(20, 10, 10, 10);
        gbc_btnDeleteitem.gridx = 4;
        gbc_btnDeleteitem.gridy = 2;
        contentPane.add(btnDeleteitem, gbc_btnDeleteitem);

        btnDeleteitem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String titem_id = textitemId.getText();
                if (titem_id.equals("")) {
                    JOptionPane.showMessageDialog(null, "Error: missing item ID");
                    return;
                }

                int item_id;
                try {
                    item_id = Integer.parseInt(titem_id);
                } catch (NumberFormatException e1) {
                    JOptionPane.showMessageDialog(null, "Error: item ID must be a number");
                    return;
                }

                String sql = "DELETE FROM inventory WHERE item_id = ?";

                try (Connection conn = sql_connecter.getConnection();
                     PreparedStatement pstmt = conn.prepareStatement(sql)) {

                    pstmt.setInt(1, item_id);

                    int rowsAffected = pstmt.executeUpdate();
                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(null, "item Deleted Successfully");
                    } else {
                        JOptionPane.showMessageDialog(null, "item Not Found");
                    }

                } catch (SQLException e1) {
                    JOptionPane.showMessageDialog(null, "Error: " + e1.getMessage());
                }
            }
        });

        // item Name
        JLabel lblitemName = new JLabel("Item Name");
        lblitemName.setFont(new Font("Arial", Font.PLAIN, 16));
        GridBagConstraints gbc_lblitemName = new GridBagConstraints();
        gbc_lblitemName.insets = new Insets(10, 10, 10, 10);
        gbc_lblitemName.gridx = 0;
        gbc_lblitemName.gridy = 3;
        contentPane.add(lblitemName, gbc_lblitemName);

        JTextArea textitemName = new JTextArea();
        textitemName.setFont(new Font("Arial", Font.PLAIN, 16));
        textitemName.setPreferredSize(new Dimension(200, 30));
        GridBagConstraints gbc_textitemName = new GridBagConstraints();
        gbc_textitemName.insets = new Insets(10, 10, 10, 10);
        gbc_textitemName.gridx = 1;
        gbc_textitemName.gridy = 3;
        contentPane.add(textitemName, gbc_textitemName);
        
      
        JLabel lblQuantity = new JLabel("Quantity");
        lblQuantity.setFont(new Font("Arial", Font.PLAIN, 16));
        GridBagConstraints gbc_lblQuantity = new GridBagConstraints();
        gbc_lblQuantity.insets = new Insets(10, 10, 10, 10);
        gbc_lblQuantity.gridx = 0;
        gbc_lblQuantity.gridy = 4;
        contentPane.add(lblQuantity, gbc_lblQuantity);

        JTextArea textQuantity = new JTextArea();
        textQuantity.setFont(new Font("Arial", Font.PLAIN, 16));
        textQuantity.setPreferredSize(new Dimension(200, 30));
        GridBagConstraints gbc_textQuantity = new GridBagConstraints();
        gbc_textQuantity.insets = new Insets(10, 10, 10, 10);
        gbc_textQuantity.gridx = 1;
        gbc_textQuantity.gridy = 4;
        contentPane.add(textQuantity, gbc_textQuantity);
        
      
        JLabel lblSupplierId = new JLabel("SupplierId");
        lblSupplierId.setFont(new Font("Arial", Font.PLAIN, 16));
        GridBagConstraints gbc_lblSupplierId = new GridBagConstraints();
        gbc_lblSupplierId.insets = new Insets(10, 10, 10, 10);
        gbc_lblSupplierId.gridx = 0;
        gbc_lblSupplierId.gridy = 5;
        contentPane.add(lblSupplierId, gbc_lblSupplierId);

        JTextArea textSupplierId = new JTextArea();
        textSupplierId.setFont(new Font("Arial", Font.PLAIN, 16));
        textSupplierId.setPreferredSize(new Dimension(200, 30));
        GridBagConstraints gbc_textSupplierId = new GridBagConstraints();
        gbc_textSupplierId.insets = new Insets(10, 10, 10, 10);
        gbc_textSupplierId.gridx = 1;
        gbc_textSupplierId.gridy = 5;
        contentPane.add(textSupplierId, gbc_textSupplierId);
        
              
                JButton btnAdditem = new JButton("Add / Update Item");
                btnAdditem.setFont(new Font("Arial", Font.PLAIN, 16));
                GridBagConstraints gbc_btnAdditem = new GridBagConstraints();
                gbc_btnAdditem.insets = new Insets(20, 10, 10, 10);
                gbc_btnAdditem.gridx = 1;
                gbc_btnAdditem.gridy = 6;
                contentPane.add(btnAdditem, gbc_btnAdditem);
                
                        btnAdditem.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                            	String titemId = textitemId.getText();

                            	int item_id;
                            	try {
                            	    item_id = Integer.parseInt(titemId);
                            	} catch (NumberFormatException e1) {
                            	    JOptionPane.showMessageDialog(null, "Error: item ID must be a number");
                            	    return;
                            	}

                            	String itemName = textitemName.getText();
                            	String quantityStr = textQuantity.getText();
                            	String SupplierIdStr = textSupplierId.getText();

                            	if (itemName.isEmpty() || quantityStr.isEmpty() || SupplierIdStr.isEmpty()) {
                            	    JOptionPane.showMessageDialog(null, "Error: All fields must be filled");
                            	    return;
                            	}

                            	int quantity, SupplierId;
                            	try {
                            	    quantity = Integer.parseInt(quantityStr);
                            	    SupplierId = Integer.parseInt(SupplierIdStr);
                            	} catch (NumberFormatException e1) {
                            	    JOptionPane.showMessageDialog(null, "Error: Quantity and SupplierId must be numbers");
                            	    return;
                            	}

                            
                            	String validateSupplierSql = "SELECT COUNT(*) FROM suppliers WHERE supplier_id = ?";
                            	boolean isValidSupplier = false;

                            	try (Connection conn = sql_connecter.getConnection();
                            	     PreparedStatement validatePstmt = conn.prepareStatement(validateSupplierSql)) {
                            	    validatePstmt.setInt(1, SupplierId);
                            	    ResultSet rs = validatePstmt.executeQuery();
                            	    if (rs.next()) {
                            	        isValidSupplier = rs.getInt(1) > 0;
                            	    }
                            	} catch (SQLException e1) {
                            	    JOptionPane.showMessageDialog(null, "Error: " + e1.getMessage());
                            	    return;
                            	}

                            	if (!isValidSupplier) {
                            	    JOptionPane.showMessageDialog(null, "Error: Supplier ID is not found in database");
                            	    return;
                            	}

                           
                            	String sql = "REPLACE INTO inventory (item_id, item_name, quantity, SupplierId) VALUES (?, ?, ?, ?)";

                            	try (Connection conn = sql_connecter.getConnection();
                            	     PreparedStatement pstmt = conn.prepareStatement(sql)) {
                            	    pstmt.setInt(1, item_id);
                            	    pstmt.setString(2, itemName);
                            	    pstmt.setInt(3, quantity);
                            	    pstmt.setInt(4, SupplierId);

                            	    int rowsAffected = pstmt.executeUpdate();
                            	    if (rowsAffected > 0) {
                            	        JOptionPane.showMessageDialog(null, "Item Added/Updated Successfully");
                            	    } else {
                            	        JOptionPane.showMessageDialog(null, "Failed to Add Item");
                            	    }

                            	} catch (SQLException e1) {
                            	    JOptionPane.showMessageDialog(null, "Error: " + e1.getMessage());
                            	}
                            }
                        });

        JButton btnViewAll = new JButton("View All");
        btnViewAll.setFont(new Font("Arial", Font.PLAIN, 16));
        GridBagConstraints gbc_btnViewAll = new GridBagConstraints();
        gbc_btnViewAll.insets = new Insets(20, 10, 10, 10);
        gbc_btnViewAll.gridx = 3;
        gbc_btnViewAll.gridy = 6;
        contentPane.add(btnViewAll, gbc_btnViewAll);
        
        btnGetitemDetails.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String titem_id = textitemId.getText();
                if (titem_id.equals("")) {
                    JOptionPane.showMessageDialog(null, "Error: missing item ID");
                    return;
                }

                int item_id;
                try {
                    item_id = Integer.parseInt(titem_id);
                } catch (NumberFormatException e1) {
                    JOptionPane.showMessageDialog(null, "Error: item ID must be a number");
                    return;
                }

                String sql = "SELECT item_id, item_name, quantity, SupplierId FROM inventory WHERE item_id = " + item_id;

                try (Connection conn = sql_connecter.getConnection();
                     Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery(sql)) {

                    if (rs.next()) {
                        int retrieveditemId = rs.getInt("item_id");
                        String itemName = rs.getString("item_name");
                        int quantity = rs.getInt("quantity");
                        int SupplierId = rs.getInt("SupplierId");

                    
                        textitemId.setText(String.valueOf(retrieveditemId));
                        textitemName.setText(itemName);
                        textQuantity.setText(String.valueOf(quantity));
                        textSupplierId.setText(String.valueOf(SupplierId));
                    } else {
                        JOptionPane.showMessageDialog(null, "item not found");
                    }

                } catch (SQLException e1) {
                    JOptionPane.showMessageDialog(null, "Error: " + e1.getMessage());
                }
            }
        });

        btnViewAll.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String sql = "SELECT item_id, item_name, quantity, SupplierId FROM inventory";

                try (Connection conn = sql_connecter.getConnection();
                     Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery(sql)) {

                    StringBuilder message = new StringBuilder("");
                    while (rs.next()) {
                        int item_id = rs.getInt("item_id");
                        String itemName = rs.getString("item_name");
                        int quantity = rs.getInt("quantity");
                        int SupplierId = rs.getInt("SupplierId");

                        message.append(String.format("Item ID: %d\nitem Name: %s\nQuantity: %d\nSupplierId: %d\n\n",
                                item_id, itemName, quantity, SupplierId));
                    }

                    JTextArea textArea = new JTextArea(message.toString());
                    textArea.setFont(new Font("Arial", Font.PLAIN, 16));
                    textArea.setEditable(false);
                    JScrollPane scrollPane = new JScrollPane(textArea);
                    scrollPane.setPreferredSize(new Dimension(500, 400));
                    JOptionPane.showMessageDialog(null, scrollPane, "items Details", JOptionPane.INFORMATION_MESSAGE);

                } catch (SQLException e1) {
                    JOptionPane.showMessageDialog(null, "Error: " + e1.getMessage());
                }
            }
        });
        createTableIfNotExists();
    }
}
