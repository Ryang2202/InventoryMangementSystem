package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import dao.ProductDAO;
import dao.OrderDAO;
import model.Order;

public class OrderGUI extends JFrame {
    // Table
    private JTable orderTable;
    // Table model
    private DefaultTableModel orderTableModel;
    // Buttons
    private JButton backButton, addButton, deleteButton, searchByOrderIdButton, searchByCustomerButton, clearButton;
    // Text fields
    private JTextField orderIdTextField, orderDateTextField, orderQuantityTextField, orderTotalTextField, proIdTextField, proSellPriceTextField, cusIdTextField;
    // Label
    private JLabel orderIdLabel, orderDateLabel, orderQuantityLabel, orderTotalLabel, proIdLabel, cusIdLabel, proSellPriceLabel;
    // DAO
    private ProductDAO productDAO;
    private OrderDAO orderDAO;



    public OrderGUI() {

        // Set the frame properties
        setTitle("Inventory Management System - Order Page");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        // Create the order DAO
        orderDAO = new OrderDAO();
        productDAO = new ProductDAO();

        // Create a panel for the header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setPreferredSize(new Dimension(getWidth(), 50));
        headerPanel.setBackground(Color.darkGray);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));

        // Create a label for the title and add it to the header panel
        JLabel titleLabel = new JLabel("ORDER PAGE", SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        backButton = new JButton("Back to Main");
        backButton.setFont(new Font("Arial", Font.BOLD, 12));
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MainGUI mainPage = new MainGUI();
                mainPage.setVisible(true);
                dispose();
            }
        });
        headerPanel.add(backButton, BorderLayout.WEST);

        // Create the table model and table
        String[] columnNames = {"Order ID", "Order Date (YYYY-MM-DD)", "Customer ID", "Product ID", "Product Quantity", "Product Sell Price", "Order Total"};
        orderTableModel = new DefaultTableModel(columnNames, 0);
        orderTable = new JTable(orderTableModel);

        // Create the scroll pane for the table
        JScrollPane tableScrollPane = new JScrollPane(orderTable);
        tableScrollPane.setPreferredSize(new Dimension(getWidth(), 300));


        // Create the panel for the text fields and buttons
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(5, 2, 2, 2));
        inputPanel.setBorder(BorderFactory.createTitledBorder("  Input  "));
        inputPanel.setPreferredSize(new Dimension(750, 130));

        // Create the labels for the text fields
        orderIdLabel = new JLabel("Order ID:");
        orderIdLabel.setFont(new Font("Arial", Font.BOLD, 14));

        orderDateLabel = new JLabel("Order Date (YYYY-MM-DD):");
        orderDateLabel.setFont(new Font("Arial", Font.BOLD, 14));

        cusIdLabel = new JLabel("Customer ID:");
        cusIdLabel.setFont(new Font("Arial", Font.BOLD, 14));

        proIdLabel = new JLabel("Product ID:");
        proIdLabel.setFont(new Font("Arial", Font.BOLD, 14));

        orderQuantityLabel = new JLabel("Product quantity:");
        orderQuantityLabel.setFont(new Font("Arial", Font.BOLD, 14));



        // Create the text fields
        orderIdTextField = new JTextField();
        orderDateTextField = new JTextField();
        cusIdTextField = new JTextField();
        proIdTextField = new JTextField();
        proSellPriceTextField = new JTextField();
        orderQuantityTextField = new JTextField();

        // Add the labels and text fields to the input panel
        inputPanel.add(orderIdLabel);
        inputPanel.add(orderIdTextField);
        inputPanel.add(orderDateLabel);
        inputPanel.add(orderDateTextField);
        inputPanel.add(cusIdLabel);
        inputPanel.add(cusIdTextField);
        inputPanel.add(proIdLabel);
        inputPanel.add(proIdTextField);
        inputPanel.add(orderQuantityLabel);
        inputPanel.add(orderQuantityTextField);

        // Create the buttons
        addButton = new JButton("Add");
        addButton.setFont(new Font("Arial", Font.BOLD, 14));

        deleteButton = new JButton("Delete");
        deleteButton.setFont(new Font("Arial", Font.BOLD, 14));

        searchByOrderIdButton = new JButton("Search by Order ID");
        searchByOrderIdButton.setFont(new Font("Arial", Font.BOLD, 14));

        searchByCustomerButton = new JButton("Search by Customer ID");
        searchByCustomerButton.setFont(new Font("Arial", Font.BOLD, 14));

        clearButton = new JButton("Clear");
        clearButton.setFont(new Font("Arial", Font.BOLD, 14));


        // Create the panel for the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 2, 5, 5));
        buttonPanel.setBorder(BorderFactory.createTitledBorder("  Button  "));

        // Add the buttons to the button panel
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(searchByOrderIdButton);
        buttonPanel.add(searchByCustomerButton);
        buttonPanel.add(clearButton);

        // Create a panel for the header
        JPanel operationPanel = new JPanel(new GridLayout(1, 2, 0, 0));
        operationPanel.add(inputPanel, BorderLayout.WEST);
        operationPanel.add(buttonPanel, BorderLayout.EAST);


        // Add the input panel, button panel, and table scroll pane to the frame
        add(headerPanel, BorderLayout.NORTH);
        add(operationPanel, BorderLayout.CENTER);
        add(tableScrollPane, BorderLayout.SOUTH);

        //Add ActionListener to each button
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the order details from the GUI components
                int orderId = Integer.parseInt(orderIdTextField.getText());
                Date orderDate = Date.valueOf(orderDateTextField.getText());
                int cusId = Integer.parseInt(cusIdTextField.getText());
                int proId = Integer.parseInt(proIdTextField.getText());
                int orderQuantity = Integer.parseInt(orderQuantityTextField.getText());

                // Calculate the sell price and order total
                double sellPrice = productDAO.getProSellPrice(proId);
                double orderTotal = orderQuantity * sellPrice;

                // Create a new Order object with the retrieved details
                Order order = new Order();
                order.setOrderId(orderId);
                order.setOrderDate(orderDate);
                order.setCusId(cusId);
                order.setProId(proId);
                order.setOrderQuantity(orderQuantity);
                order.setSellPrice(sellPrice);
                order.setOrderTotal(orderTotal);

                    // Add order to database
                    boolean addResult = orderDAO.addOrderDAO(order);
                    if (addResult) {
                        // Update the table with the new order data
                        Object[] row = {order.getOrderId(), order.getOrderDate(), order.getCusId(), order.getProId(), order.getOrderQuantity(), order.getSellPrice(), order.getOrderTotal()};
                        orderTableModel.addRow(row);
                    }
                    // Clear text fields
                    orderIdTextField.setText("");
                    orderDateTextField.setText("");
                    cusIdTextField.setText("");
                    proIdTextField.setText("");
                    orderQuantityTextField.setText("");
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = orderTable.getSelectedRow();
                if (row == -1) {
                    JOptionPane.showMessageDialog(null, "Please select a row to delete.");
                    return;
                }
                int orderId = (int) orderTableModel.getValueAt(row, 0);
                orderDAO.deleteOrderDAO(orderId);
                orderTableModel.removeRow(row);
            }
        });

        searchByOrderIdButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idString = orderIdTextField.getText();
                int id = Integer.parseInt(idString);
                if (idString.equals("")) {
                    JOptionPane.showMessageDialog(null, "Please enter order id for search.");
                    return;
                }
                ResultSet resultSet = orderDAO.getOrderByOrderIdSearch(id);
                try {
                    if (resultSet.next()) {
                        // Display the search result in the table
                        orderTableModel.setRowCount(0);
                        orderTableModel.addRow(new Object[]{resultSet.getInt("order_id"), resultSet.getString("order_date"), resultSet.getInt("cus_id"), resultSet.getInt("pro_id"), resultSet.getInt("order_quantity"),resultSet.getDouble("sell_price"), resultSet.getDouble("order_total")});
                    } else {
                        // Display an error message
                        JOptionPane.showMessageDialog(null, "No order found with the entered ID.");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });


        searchByCustomerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idString = cusIdTextField.getText();
                int id = Integer.parseInt(idString);
                if (idString.equals("")) {
                    JOptionPane.showMessageDialog(null, "Please enter customer id for search.");
                    return;
                }
                ResultSet resultSet = orderDAO.getOrderByCustomerIdSearch(id);
                try {
                    // Clear existing rows from the table model
                    orderTableModel.setRowCount(0);
                    while (resultSet.next()) {
                        // Display the search result in the table
                        orderTableModel.addRow(new Object[]{resultSet.getInt("order_id"), resultSet.getString("order_date"), resultSet.getInt("cus_id"), resultSet.getInt("pro_id"), resultSet.getInt("order_quantity"),resultSet.getDouble("sell_price"), resultSet.getDouble("order_total")});
                    }
                    if (orderTableModel.getRowCount() == 0) {
                        // Display an error message
                        JOptionPane.showMessageDialog(null, "No order found with the entered ID.");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });


        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Clear text fields
                orderIdTextField.setText("");
                orderDateTextField.setText("");
                cusIdTextField.setText("");
                proIdTextField.setText("");
                orderQuantityTextField.setText("");
                // Show all for table
                orderTableModel.setRowCount(0);
                retrieveDataToTable();
            }
        });

        // retrieve data from database and add to table
        retrieveDataToTable();
    }

    // retrieve data from database and add to table
    public void retrieveDataToTable() {
        ResultSet resultSet = orderDAO.getOrderQueryResultForDisplay();
//        ResultSet proResultSet = productDAO.getProductQueryResultForDisplay();
        try {
            while (resultSet.next()) {
                orderTableModel.addRow(new Object[]{resultSet.getInt("order_id"), resultSet.getString("order_date"), resultSet.getInt("cus_id"), resultSet.getInt("pro_id"), resultSet.getInt("order_quantity"),resultSet.getDouble("sell_price"), resultSet.getDouble("order_total")});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main (String[]args){
        OrderGUI orderGUI = new OrderGUI();
        orderGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        orderGUI.setVisible(true);
    }

}



