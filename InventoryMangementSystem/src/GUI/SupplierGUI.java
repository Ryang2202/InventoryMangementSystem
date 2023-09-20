package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import dao.SupplierDAO;
import model.Supplier;

public class SupplierGUI extends JFrame {
    // Table
    private JTable supplierTable;
    // Table model
    private DefaultTableModel supplierTableModel;
    // Buttons
    private JButton backButton, addButton, deleteButton, searchByIdButton, searchByNameButton, editButton, saveButton, clearButton;
    // Text fields
    private JTextField supNameTextField, supIdTextField;
    private JLabel supNameLabel, supIdLabel;
    // DAO
    private SupplierDAO supplierDAO;


    public SupplierGUI() {

        // Set the frame properties
        setTitle("Inventory Management System - Supplier Page");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        // Create the supplier DAO
        supplierDAO = new SupplierDAO();

        // Create a panel for the header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setPreferredSize(new Dimension(getWidth(), 50));
        headerPanel.setBackground(Color.darkGray);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));

        // Create a label for the title and add it to the header panel
        JLabel titleLabel = new JLabel("SUPPLIER PAGE", SwingConstants.CENTER);
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
        String[] columnNames = {"Supplier Name", "Supplier ID"};
        supplierTableModel = new DefaultTableModel(columnNames, 0);
        supplierTable = new JTable(supplierTableModel);

        // Create the scroll pane for the table
        JScrollPane tableScrollPane = new JScrollPane(supplierTable);
        tableScrollPane.setPreferredSize(new Dimension(getWidth(), 300));
        tableScrollPane.setBorder(BorderFactory.createTitledBorder("  Table  "));



        // Create the panel for the text fields and buttons
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(4, 1, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("  Input  "));



        // Create the labels for the text fields
        supNameLabel = new JLabel("Supplier Name:");
        supNameLabel.setFont(new Font("Arial", Font.BOLD, 14));

        supIdLabel = new JLabel("Supplier ID:");
        supIdLabel.setFont(new Font("Arial", Font.BOLD, 14));


        // Create the text fields
        supNameTextField = new JTextField();
        supIdTextField = new JTextField();

        // Add the labels and text fields to the input panel
        inputPanel.add(supNameLabel);
        inputPanel.add(supNameTextField);
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
                String name = supNameTextField.getText();
                String idString = supIdTextField.getText();
                int id = Integer.parseInt(idString);
                if (name.isEmpty() || idString.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter name and id.");
                    return;
                }
                Supplier supplier = new Supplier();
                supplier.setSupName(name);
                supplier.setSupId(id);

                boolean addResult = supplierDAO.addSupplierDAO(supplier);
                if (addResult) {
                    // Update the table with the new supplier data
                    Object[] row = {supplier.getSupName(), supplier.getSupId()};
                    supplierTableModel.addRow(row);
                }

                // Clear text fields
                supNameTextField.setText("");
                supIdTextField.setText("");
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = supplierTable.getSelectedRow();
                if (row == -1) {
                    JOptionPane.showMessageDialog(null, "Please select a row to delete.");
                    return;
                }
                int supId = (int) supplierTableModel.getValueAt(row, 1);
                supplierDAO.deleteSupplierDAO(supId);
                supplierTableModel.removeRow(row);
            }
        });

        searchByIdButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idString = supIdTextField.getText();
                int id = Integer.parseInt(idString);
                if (idString.equals("")) {
                    JOptionPane.showMessageDialog(null, "Please enter id for search.");
                    return;
                }
                ResultSet resultSet = supplierDAO.getSupplierByIdSearch(id);
                try {
                    if (resultSet.next()) {
                        // Display the search result in the table
                        supplierTableModel.setRowCount(0);
                        supplierTableModel.addRow(new Object[]{resultSet.getString("sup_name"), resultSet.getInt("sup_id")});
                    } else {
                        // Display an error message
                        JOptionPane.showMessageDialog(null, "No supplier found with the entered ID.");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        searchByNameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = supNameTextField.getText();
                if (name.equals("")) {
                    JOptionPane.showMessageDialog(null, "Please enter name for search.");
                    return;
                }
                ResultSet resultSet = supplierDAO.getSupplierByNameSearch(name);
                try {
                    if (resultSet.next()) {
                        // Display the search result in the table
                        supplierTableModel.setRowCount(0);
                        supplierTableModel.addRow(new Object[]{resultSet.getString("sup_name"), resultSet.getInt("sup_id")});
                    } else {
                        // Display an error message
                        JOptionPane.showMessageDialog(null, "No supplier found with the entered Name.");
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
                int selectedRow = supplierTable.getSelectedRow();

                // If no row is selected, show an error message and return
                if (selectedRow < 0)
                    JOptionPane.showMessageDialog(null, "Select a supplier from the table.");

                // Get the supplier object from the selected row
                String name = (String) supplierTableModel.getValueAt(selectedRow, 0);
                int id = (int) supplierTableModel.getValueAt(selectedRow, 1);

                // Set the text fields to the supplier info
                supNameTextField.setText(name);
                supIdTextField.setText(Integer.toString(id));
                supIdTextField.setEnabled(false);


                // Disable the Edit button and enable the Save button
                editButton.setEnabled(false);
                saveButton.setEnabled(true);
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the supplier info from the text fields
                int id = Integer.parseInt(supIdTextField.getText());
                String name = supNameTextField.getText();

                // Create a new supplier object with the updated info
                Supplier supplier = new Supplier();
                supplier.setSupId(id);
                supplier.setSupName(name);

                // Call the editSupplierDAO method in the SupplierDAO class
                supplierDAO.editSupplierDAO(supplier);

                // Update the table model with the new supplier info
                supplierTableModel.setValueAt(name, supplierTable.getSelectedRow(), 0);
                supplierTableModel.setValueAt(id, supplierTable.getSelectedRow(), 1);

                // Clear the text fields and enable the Edit button and disable the Save button
                supNameTextField.setText("");
                supIdTextField.setText("");
                editButton.setEnabled(true);
                saveButton.setEnabled(false);
                supIdTextField.setEnabled(true);

            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Clear text fields
                supNameTextField.setText("");
                supIdTextField.setText("");
                editButton.setEnabled(true);
                saveButton.setEnabled(false);
                supIdTextField.setEnabled(true);

                // Show all for table
                supplierTableModel.setRowCount(0);
                retrieveDataToTable();
            }
        });

        // retrieve data from database and add to table
        retrieveDataToTable();
    }

    // retrieve data from database and add to table
    public void retrieveDataToTable() {
        ResultSet resultSet = supplierDAO.getSupplierQueryResultForDisplay();
        try {
            while (resultSet.next()) {
                supplierTableModel.addRow(new Object[]{resultSet.getString("sup_name"), resultSet.getInt("sup_id")});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    public static void main (String[]args){
//        SupplierGUI gui = new SupplierGUI();
//        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        gui.setVisible(true);
//    }

}



