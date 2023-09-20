package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import dao.InventoryDAO;

public class InventoryGUI extends JFrame {
    // Table
    private JTable inventoryTable;
    // Table model
    private DefaultTableModel inventoryTableModel;
    // Buttons
    private JButton searchByProIdButton, backButton, clearButton;
    // Text fields
    private JTextField proNameTextField, proIdTextField;
    private JLabel proNameLabel, proIdLabel;
    // DAO
    private InventoryDAO inventoryDAO;

    public InventoryGUI() {

        // Set the frame properties
        setTitle("Inventory Management System - Inventory Page");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        // Create the order DAO
        inventoryDAO = new InventoryDAO();

        // Create a panel for the header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setPreferredSize(new Dimension(getWidth(), 50));
        headerPanel.setBackground(Color.darkGray);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));

        // Create a label for the title and add it to the header panel
        JLabel titleLabel = new JLabel("INVENTORY PAGE", SwingConstants.CENTER);
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
        String[] columnNames = {"Product ID", "Product Current Stock"};
        inventoryTableModel = new DefaultTableModel(columnNames, 0);
        inventoryTable = new JTable(inventoryTableModel);

        // Create the scroll pane for the table
        JScrollPane tableScrollPane = new JScrollPane(inventoryTable);
        tableScrollPane.setPreferredSize(new Dimension(getWidth(), 300));
        tableScrollPane.setBorder(BorderFactory.createTitledBorder("  Table  "));



        // Create the panel for the text fields and buttons
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(2, 1, 2, 2));
        inputPanel.setBorder(BorderFactory.createTitledBorder("  Input  "));


        // Create the labels for the text fields
        proIdLabel = new JLabel("Product ID:");
        proIdLabel.setFont(new Font("Arial", Font.BOLD, 14));


        // Create the text fields
        proIdTextField = new JTextField();

        // Add the labels and text fields to the input panel

        inputPanel.add(proIdLabel);
        inputPanel.add(proIdTextField);


        // Create the buttons
        searchByProIdButton = new JButton("Search by Product ID");
        searchByProIdButton.setFont(new Font("Arial", Font.BOLD, 14));

        clearButton = new JButton("Clear");
        clearButton.setFont(new Font("Arial", Font.BOLD, 14));


        // Create the panel for the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 1, 5, 5));
        buttonPanel.setBorder(BorderFactory.createTitledBorder("  Button  "));


        // Add the buttons to the button panel
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
        searchByProIdButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idString = proIdTextField.getText();
                int proId = Integer.parseInt(idString);
                if (idString.equals("")) {
                    JOptionPane.showMessageDialog(null, "Please enter Product Id for search.");
                    return;
                }
                ResultSet resultSet = inventoryDAO.getInvByIdSearch(proId);
                try {
                    if (resultSet.next()) {
                        // Display the search result in the table
                        inventoryTableModel.setRowCount(0);
                        inventoryTableModel.addRow(new Object[]{resultSet.getInt("pro_id"), resultSet.getInt("inv_quantity")});
                    } else {
                        // Display an error message
                        JOptionPane.showMessageDialog(null, "No inventory record found with the entered Product ID.");
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
                proIdTextField.setText("");

                // Show all for table
                inventoryTableModel.setRowCount(0);
                retrieveDataToTable();
            }
        });

        // retrieve data from database and add to table
        retrieveDataToTable();
    }

    // retrieve data from database and add to table
    public void retrieveDataToTable() {
        ResultSet resultSet = inventoryDAO.getInventoryQueryResultForDisplay();
        try {
            while (resultSet.next()) {
                inventoryTableModel.addRow(new Object[]{resultSet.getInt("pro_id"), resultSet.getInt("inv_quantity")});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main (String[]args){
        InventoryGUI gui = new InventoryGUI();
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setVisible(true);
    }

}


