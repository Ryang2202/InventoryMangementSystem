package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import dao.RestockDAO;
import model.Restock;


public class RestockGUI extends JFrame {
    // Table
    private JTable restockTable;
    // Table model
    private DefaultTableModel restockTableModel;
    // Buttons
    private JButton backButton, addButton, deleteButton, searchByProIdButton, searchByRestockIdButton, editButton, saveButton, clearButton;
    // Text fields
    private JTextField restockIdTextField, restockDateTextField, restockQuantityTextField, proIdTextField;
    // Label
    private JLabel restockIdLabel, restockDateLabel, restockQuantityLabel, proIdLabel;
    // DAO
    private RestockDAO restockDAO;



    public RestockGUI() {

        // Set the frame properties
        setTitle("Inventory Management System - Restock Page");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        // Create the order DAO
        restockDAO = new RestockDAO();


        // Create a panel for the header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setPreferredSize(new Dimension(getWidth(), 50));
        headerPanel.setBackground(Color.darkGray);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));

        // Create a label for the title and add it to the header panel
        JLabel titleLabel = new JLabel("RESTOCK PAGE", SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
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
        String[] columnNames = {"Restock Record ID", "Restock Date (YYYY-MM-DD)", "Product ID", "Restock Quantity"};
        restockTableModel = new DefaultTableModel(columnNames, 0);
        restockTable = new JTable(restockTableModel);

        // Create the scroll pane for the table
        JScrollPane tableScrollPane = new JScrollPane(restockTable);
        tableScrollPane.setPreferredSize(new Dimension(getWidth(), 300));
        tableScrollPane.setBorder(BorderFactory.createTitledBorder("  Table  "));



        // Create the panel for the text fields and buttons
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(4, 2, 2, 2));
        inputPanel.setBorder(BorderFactory.createTitledBorder("  Input  "));
        inputPanel.setPreferredSize(new Dimension(750, 130));

        // Create the labels for the text fields
        restockIdLabel = new JLabel("ReStock Record ID:");
        restockIdLabel.setFont(new Font("Arial", Font.BOLD, 13));

        restockDateLabel = new JLabel("Restock Date(YYYY-MM-DD):");
        restockDateLabel.setFont(new Font("Arial", Font.BOLD, 13));

        proIdLabel = new JLabel("Product ID:");
        proIdLabel.setFont(new Font("Arial", Font.BOLD, 13));

        restockQuantityLabel = new JLabel("Restock Quantity:");
        restockQuantityLabel.setFont(new Font("Arial", Font.BOLD, 13));


        // Create the text fields
        restockIdTextField = new JTextField();
        restockDateTextField = new JTextField();
        proIdTextField = new JTextField();
        restockQuantityTextField = new JTextField();


        // Add the labels and text fields to the input panel
        inputPanel.add(restockIdLabel);
        inputPanel.add(restockIdTextField);
        inputPanel.add(restockDateLabel);
        inputPanel.add(restockDateTextField);
        inputPanel.add(proIdLabel);
        inputPanel.add(proIdTextField);
        inputPanel.add(restockQuantityLabel);
        inputPanel.add(restockQuantityTextField);



        // Create the buttons
        addButton = new JButton("Add");
        addButton.setFont(new Font("Arial", Font.BOLD, 14));

        deleteButton = new JButton("Delete");
        deleteButton.setFont(new Font("Arial", Font.BOLD, 14));

        searchByRestockIdButton = new JButton("Search by Restock ID");
        searchByRestockIdButton.setFont(new Font("Arial", Font.BOLD, 14));

        searchByProIdButton = new JButton("Search by Product ID");
        searchByProIdButton.setFont(new Font("Arial", Font.BOLD, 14));

        clearButton = new JButton("Clear");
        clearButton.setFont(new Font("Arial", Font.BOLD, 14));


        // Create the panel for the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 2, 5, 5));
        buttonPanel.setBorder(BorderFactory.createTitledBorder("  Button  "));

        // Add the buttons to the button panel
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(searchByRestockIdButton);
        buttonPanel.add(searchByProIdButton);
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
                String idString = restockIdTextField.getText();
                int id = Integer.parseInt(idString);
                Date date = Date.valueOf(restockDateTextField.getText());
                int proId = Integer.parseInt(proIdTextField.getText());
                int quantity = Integer.parseInt(restockQuantityTextField.getText());
                if (idString.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter all product info.");
                    return;
                }
                Restock restock = new Restock();
                restock.setRestockId(id);
                restock.setRestockDate(date);
                restock.setProId(proId);
                restock.setRestockQuantity(quantity);

                boolean addResult = restockDAO.addRestockDAO(restock);
                if (addResult) {
                    // Update the table with the new product data
                    Object[] row = {restock.getRestockId(), restock.getRestockDate(), restock.getProId(), restock.getRestockQuantity()};
                    restockTableModel.addRow(row);
                }
                // Clear text fields
                restockIdTextField.setText("");
                restockDateTextField.setText("");
                proIdTextField.setText("");
                restockQuantityTextField.setText("");
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = restockTable.getSelectedRow();
                if (row == -1) {
                    JOptionPane.showMessageDialog(null, "Please select a row to delete.");
                    return;
                }
                int restockId = (int) restockTableModel.getValueAt(row, 0);
                restockDAO.deleteRestockDAO(restockId);
                restockTableModel.removeRow(row);
            }
        });


        searchByRestockIdButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idString = restockIdTextField.getText();
                int id = Integer.parseInt(idString);
                if (idString.equals("")) {
                    JOptionPane.showMessageDialog(null, "Please enter Restock Id for search.");
                    return;
                }
                ResultSet resultSet = restockDAO.getRestockByRestockIdSearch(id);
                try {
                    if (resultSet.next()) {
                        // Display the search result in the table
                        restockTableModel.setRowCount(0);
                        restockTableModel.addRow(new Object[]{resultSet.getInt("restock_id"), resultSet.getString("restock_date"), resultSet.getInt("pro_id"), resultSet.getInt("restock_quantity")});
                    } else {
                        // Display an error message
                        JOptionPane.showMessageDialog(null, "No restock record found with the entered Restock ID.");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        searchByProIdButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idString = proIdTextField.getText();
                int proId = Integer.parseInt(idString);
                if (idString.equals("")) {
                    JOptionPane.showMessageDialog(null, "Please enter Product Id for search.");
                    return;
                }
                ResultSet resultSet = restockDAO.getRestockByProIdSearch(proId);
                try {
                    // Clear existing rows from the table model
                    restockTableModel.setRowCount(0);
                    while (resultSet.next()) {
                        // Add each row of data to the table model
                        restockTableModel.addRow(new Object[]{
                                resultSet.getInt("restock_id"),
                                resultSet.getString("restock_date"),
                                resultSet.getInt("pro_id"),
                                resultSet.getInt("restock_quantity")
                        });
                    }
                    // Display an error message if no rows were found
                    if (restockTableModel.getRowCount() == 0) {
                        JOptionPane.showMessageDialog(null, "No restock record found with the entered Product ID.");
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
                restockIdTextField.setText("");
                restockDateTextField.setText("");
                proIdTextField.setText("");
                restockQuantityTextField.setText("");

                // Show all for table
                restockTableModel.setRowCount(0);
                retrieveDataToTable();
            }
        });

        // retrieve data from database and add to table
        retrieveDataToTable();
    }

    // retrieve data from database and add to table
    public void retrieveDataToTable() {
        ResultSet resultSet = restockDAO.getRestockQueryResultForDisplay();
        try {
            while (resultSet.next()) {
                restockTableModel.addRow(new Object[]{resultSet.getInt("restock_id"), resultSet.getString("restock_date"), resultSet.getInt("pro_id"), resultSet.getInt("restock_quantity")});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main (String[]args){
        RestockGUI gui = new RestockGUI();
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setVisible(true);
    }

}



