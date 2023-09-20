package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import dao.ProductDAO;
import model.Product;

public class ProductGUI extends JFrame {
    // Table
    private JTable proTable;
    // Table model
    private DefaultTableModel proTableModel;
    // Buttons
    private JButton backButton, addButton, deleteButton, searchByIdButton, searchByNameButton, editButton, saveButton, clearButton;
    // Text fields
    private JTextField proNameTextField, proIdTextField, proCostTextField, proSellTextField, supIdTextField;

    // Label
    private JLabel proNameLabel, proIdLabel, proCostLabel, proSellLabel, supIdLabel;
    // DAO
    private ProductDAO productDAO;


    public ProductGUI() {


        // Set the frame properties
        setTitle("Inventory Management System - Product Page");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        // Create the product DAO
        productDAO = new ProductDAO();

        // Create a panel for the header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setPreferredSize(new Dimension(getWidth(), 50));
        headerPanel.setBackground(Color.darkGray);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));

        // Create a label for the title and add it to the header panel
        JLabel titleLabel = new JLabel("PRODUCT PAGE", SwingConstants.CENTER);
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
        String[] columnNames = {"Product Name", "Product ID", "Product Cost Price", "Product Sell Price", "Supplier ID"};
        proTableModel = new DefaultTableModel(columnNames, 0);
        proTable = new JTable(proTableModel);

        // Create the scroll pane for the table
        JScrollPane tableScrollPane = new JScrollPane(proTable);
        tableScrollPane.setPreferredSize(new Dimension(getWidth(), 300));
        tableScrollPane.setBorder(BorderFactory.createTitledBorder("  Table  "));



        // Create the panel for the text fields and buttons
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(5, 1, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("  Input  "));
        inputPanel.setPreferredSize(new Dimension(750, 130));


        // Create the labels for the text fields
        proNameLabel = new JLabel("Product Name:");
        proNameLabel.setFont(new Font("Arial", Font.BOLD, 14));

        proIdLabel = new JLabel("Product ID:");
        proIdLabel.setFont(new Font("Arial", Font.BOLD, 14));

        proCostLabel = new JLabel("Product Cost Price:");
        proCostLabel.setFont(new Font("Arial", Font.BOLD, 14));

        proSellLabel = new JLabel("Product Sell Price:");
        proSellLabel.setFont(new Font("Arial", Font.BOLD, 14));

        supIdLabel = new JLabel("Supplier ID:");
        supIdLabel.setFont(new Font("Arial", Font.BOLD, 14));



        // Create the text fields
        proNameTextField = new JTextField();
        proIdTextField = new JTextField();
        proCostTextField = new JTextField();
        proSellTextField = new JTextField();
        supIdTextField = new JTextField();


        // Add the labels and text fields to the input panel
        inputPanel.add(proNameLabel);
        inputPanel.add(proNameTextField);
        inputPanel.add(proIdLabel);
        inputPanel.add(proIdTextField);
        inputPanel.add(proCostLabel);
        inputPanel.add(proCostTextField);
        inputPanel.add(proSellLabel);
        inputPanel.add(proSellTextField);
        inputPanel.add(supIdLabel);
        inputPanel.add(supIdTextField);


        // Create the buttons
        addButton = new JButton("Add");
        addButton.setFont(new Font("Arial", Font.BOLD, 14));

        deleteButton = new JButton("Delete");
        deleteButton.setFont(new Font("Arial", Font.BOLD, 14));

        searchByIdButton = new JButton("Search by ID");
        searchByIdButton.setFont(new Font("Arial", Font.BOLD, 14));

        searchByNameButton = new JButton("Search by Name");
        searchByNameButton.setFont(new Font("Arial", Font.BOLD, 14));

        editButton = new JButton("Edit");
        editButton.setFont(new Font("Arial", Font.BOLD, 14));

        saveButton = new JButton("Save");
        saveButton.setFont(new Font("Arial", Font.BOLD, 14));

        clearButton = new JButton("Clear");
        clearButton.setFont(new Font("Arial", Font.BOLD, 14));


        saveButton.setEnabled(false);


        // Create the panel for the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 2, 5, 5));
        buttonPanel.setBorder(BorderFactory.createTitledBorder("  Button  "));


        // Add the buttons to the button panel
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(searchByIdButton);
        buttonPanel.add(searchByNameButton);
        buttonPanel.add(editButton);
        buttonPanel.add(saveButton);
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
                String name = proNameTextField.getText();
                Double cost = Double.parseDouble(proCostTextField.getText());
                Double sell = Double.parseDouble(proSellTextField.getText());
                int sup = Integer.parseInt(supIdTextField.getText());
                String idString = proIdTextField.getText();
                int id = Integer.parseInt(idString);
                if (name.isEmpty() || idString.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter all product info.");
                    return;
                }
                Product product = new Product();
                product.setProName(name);
                product.setProId(id);
                product.setCostPrice(cost);
                product.setSellPrice(sell);
                product.setSupId(sup);

                boolean addResult = productDAO.addProductDAO(product);
                if (addResult) {
                    // Update the table with the new product data
                    Object[] row = {product.getProName(), product.getProId(), product.getCostPrice(), product.getSellPrice(), product.getSupId()};
                    proTableModel.addRow(row);
                }
                // Clear text fields
                proNameTextField.setText("");
                proIdTextField.setText("");
                proCostTextField.setText("");
                proSellTextField.setText("");
                supIdTextField.setText("");

            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = proTable.getSelectedRow();
                if (row == -1) {
                    JOptionPane.showMessageDialog(null, "Please select a row to delete.");
                    return;
                }
                int proId = (int) proTableModel.getValueAt(row, 1);
                productDAO.deleteProductDAO(proId);
                proTableModel.removeRow(row);
            }
        });

        searchByIdButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idString = proIdTextField.getText();
                int id = Integer.parseInt(idString);
                if (idString.equals("")) {
                    JOptionPane.showMessageDialog(null, "Please enter id for search.");
                    return;
                }
                ResultSet resultSet = productDAO.getProductByIdSearch(id);
                try {
                    if (resultSet.next()) {
                        // Display the search result in the table
                        proTableModel.setRowCount(0);
                        proTableModel.addRow(new Object[]{resultSet.getString("pro_name"), resultSet.getInt("pro_id"), resultSet.getDouble("cost_price"), resultSet.getDouble("sell_price"), resultSet.getInt("sup_id")});
                    } else {
                        // Display an error message
                        JOptionPane.showMessageDialog(null, "No product found with the entered ID.");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        searchByNameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = proNameTextField.getText();
                if (name.equals("")) {
                    JOptionPane.showMessageDialog(null, "Please enter name for search.");
                    return;
                }
                ResultSet resultSet = productDAO.getProductByNameSearch(name);
                try {
                    if (resultSet.next()) {
                        // Display the search result in the table
                        proTableModel.setRowCount(0);
                        proTableModel.addRow(new Object[]{resultSet.getString("pro_name"), resultSet.getInt("pro_id"), resultSet.getDouble("cost_price"), resultSet.getDouble("sell_price"), resultSet.getInt("sup_id")});
                    } else {
                        // Display an error message
                        JOptionPane.showMessageDialog(null, "No product found with the entered Name.");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the selected row index
                int selectedRow = proTable.getSelectedRow();

                // If no row is selected, show an error message and return
                if (selectedRow < 0)
                    JOptionPane.showMessageDialog(null, "Select a product from the table.");

                // Get the product object from the selected row
                String name = (String) proTableModel.getValueAt(selectedRow, 0);
                int id = (int) proTableModel.getValueAt(selectedRow, 1);
                double cost = (double) proTableModel.getValueAt(selectedRow, 2);
                double sell = (double) proTableModel.getValueAt(selectedRow, 3);
                int sup = (int) proTableModel.getValueAt(selectedRow, 4);


                // Set the text fields to the product info
                proNameTextField.setText(name);
                proIdTextField.setText(Integer.toString(id));
                proCostTextField.setText(Double.toString(cost));
                proSellTextField.setText(Double.toString(sell));
                supIdTextField.setText(Integer.toString(sup));

                proIdTextField.setEnabled(false);


                // Disable the Edit button and enable the Save button
                editButton.setEnabled(false);
                saveButton.setEnabled(true);
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the product info from the text fields
                int id = Integer.parseInt(proIdTextField.getText());
                String name = proNameTextField.getText();
                Double cost = Double.parseDouble(proCostTextField.getText());
                Double sell = Double.parseDouble(proSellTextField.getText());
                int sup = Integer.parseInt(supIdTextField.getText());

                // Create a new product object with the updated info
                Product product = new Product();
                product.setProName(name);
                product.setProId(id);
                product.setCostPrice(cost);
                product.setSellPrice(sell);
                product.setSupId(sup);

                // Call the editProductDAO method in the ProductDAO class
                productDAO.editProductDAO(product);

                // Update the table model with the new product info
                proTableModel.setValueAt(name, proTable.getSelectedRow(), 0);
                proTableModel.setValueAt(id, proTable.getSelectedRow(), 1);
                proTableModel.setValueAt(cost, proTable.getSelectedRow(), 2);
                proTableModel.setValueAt(sell, proTable.getSelectedRow(), 3);
                proTableModel.setValueAt(sup, proTable.getSelectedRow(), 4);

                // Clear the text fields and enable the Edit button and disable the Save button
                proNameTextField.setText("");
                proIdTextField.setText("");
                proCostTextField.setText("");
                proSellTextField.setText("");
                supIdTextField.setText("");
                editButton.setEnabled(true);
                saveButton.setEnabled(false);
                proIdTextField.setEnabled(true);

            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Clear text fields
                proNameTextField.setText("");
                proIdTextField.setText("");
                proCostTextField.setText("");
                proSellTextField.setText("");
                supIdTextField.setText("");
                editButton.setEnabled(true);
                saveButton.setEnabled(false);
                proIdTextField.setEnabled(true);

                // Show all for table
                proTableModel.setRowCount(0);
                retrieveDataToTable();
            }
        });

        // retrieve data from database and add to table
        retrieveDataToTable();
    }

    // retrieve data from database and add to table
    public void retrieveDataToTable() {
        ResultSet resultSet = productDAO.getProductQueryResultForDisplay();
        try {
            while (resultSet.next()) {
                proTableModel.addRow(new Object[]{resultSet.getString("pro_name"), resultSet.getInt("pro_id"), resultSet.getDouble("cost_price"), resultSet.getDouble("sell_price"), resultSet.getInt("sup_id")});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main (String[]args){
        ProductGUI gui = new ProductGUI();
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setVisible(true);
    }

}



