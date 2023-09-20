package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import dao.CustomerDAO;
import model.Customer;

public class CustomerGUI extends JFrame {
    // Table
    private JTable customerTable;
    // Table model
    private DefaultTableModel customerTableModel;
    // Buttons
    private JButton backButton, addButton, deleteButton, searchByIdButton, searchByNameButton, editButton, saveButton, clearButton;
    // Text fields
    private JTextField cusNameTextField, cusIdTextField;
    private JLabel cusNameLabel, cusIdLabel;
    // DAO
    private CustomerDAO customerDAO;


    public CustomerGUI() {

        // Set the frame properties
        setTitle("Inventory Management System - Customer Page");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        // Create the customer DAO
        customerDAO = new CustomerDAO();



        // Create a panel for the header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setPreferredSize(new Dimension(getWidth(), 50));
        headerPanel.setBackground(Color.darkGray);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));


        // Create a label for the title and add it to the header panel
        JLabel titleLabel = new JLabel("CUSTOMER PAGE", SwingConstants.CENTER);
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
        String[] columnNames = {"Customer Name", "Customer ID"};
        customerTableModel = new DefaultTableModel(columnNames, 0);
        customerTable = new JTable(customerTableModel);

        // Create the scroll pane for the table
        JScrollPane tableScrollPane = new JScrollPane(customerTable);
        tableScrollPane.setPreferredSize(new Dimension(getWidth(), 300));
        tableScrollPane.setBorder(BorderFactory.createTitledBorder("  Table  "));



        // Create the panel for the text fields and buttons
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(4, 1, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("  Input  "));
        inputPanel.setPreferredSize(new Dimension(750, 100));


        // Create the labels for the text fields
        cusNameLabel = new JLabel("Customer Name:");
        cusNameLabel.setFont(new Font("Arial", Font.BOLD, 14));

        cusIdLabel = new JLabel("Customer ID:");
        cusIdLabel.setFont(new Font("Arial", Font.BOLD, 14));


        // Create the text fields
        cusNameTextField = new JTextField();
        cusIdTextField = new JTextField();

        // Add the labels and text fields to the input panel
        inputPanel.add(cusNameLabel);
        inputPanel.add(cusNameTextField);
        inputPanel.add(cusIdLabel);
        inputPanel.add(cusIdTextField);

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
                String name = cusNameTextField.getText();
                String idString = cusIdTextField.getText();
                int id = Integer.parseInt(idString);
                if (name.equals("") || idString.equals("")){
                    JOptionPane.showMessageDialog(null, "Please enter name and id.");
                    return;
                }
                Customer customer = new Customer();
                customer.setCusName(name);
                customer.setCusId(id);
                boolean addResult = customerDAO.addCustomerDAO(customer);
                if (addResult) {
                    // Update the table with the new customer data
                    Object[] row = {customer.getCusName(), customer.getCusId()};
                    customerTableModel.addRow(row);
                }
                // Clear text fields
                cusNameTextField.setText("");
                cusIdTextField.setText("");
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = customerTable.getSelectedRow();
                if (row == -1) {
                    JOptionPane.showMessageDialog(null, "Please select a row to delete.");
                    return;
                }
                int cusId = (int) customerTableModel.getValueAt(row, 1);
                customerDAO.deleteCustomerDAO(cusId);
                customerTableModel.removeRow(row);
            }
        });

        searchByIdButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idString = cusIdTextField.getText();
                int id = Integer.parseInt(idString);
                if (idString.equals("")) {
                    JOptionPane.showMessageDialog(null, "Please enter id for search.");
                    return;
                }
                ResultSet resultSet = customerDAO.getCustomerByIdSearch(id);
                try {
                    if (resultSet.next()) {
                        // Display the search result in the table
                        customerTableModel.setRowCount(0);
                        customerTableModel.addRow(new Object[]{resultSet.getString("cus_name"), resultSet.getInt("cus_id")});
                    } else {
                        // Display an error message
                        JOptionPane.showMessageDialog(null, "No customer found with the entered ID.");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        searchByNameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = cusNameTextField.getText();
                if (name.equals("")) {
                    JOptionPane.showMessageDialog(null, "Please enter name for search.");
                    return;
                }
                ResultSet resultSet = customerDAO.getCustomerByNameSearch(name);
                try {
                    if (resultSet.next()) {
                        // Display the search result in the table
                        customerTableModel.setRowCount(0);
                        customerTableModel.addRow(new Object[]{resultSet.getString("cus_name"), resultSet.getInt("cus_id")});
                    } else {
                        // Display an error message
                        JOptionPane.showMessageDialog(null, "No customer found with the entered Name.");
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
                int selectedRow = customerTable.getSelectedRow();

                // If no row is selected, show an error message and return
                if (selectedRow < 0)
                    JOptionPane.showMessageDialog(null, "Select a customer from the table.");

                // Get the customer object from the selected row
                String name = (String) customerTableModel.getValueAt(selectedRow, 0);
                int id = (int) customerTableModel.getValueAt(selectedRow, 1);

                // Set the text fields to the customer info
                cusNameTextField.setText(name);
                cusIdTextField.setText(Integer.toString(id));
                cusIdTextField.setEnabled(false);

                // Disable the Edit button and enable the Save button
                editButton.setEnabled(false);
                saveButton.setEnabled(true);
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the customer info from the text fields
                int id = Integer.parseInt(cusIdTextField.getText());
                String name = cusNameTextField.getText();

                // Create a new Customer object with the updated info
                Customer customer = new Customer();
                customer.setCusId(id);
                customer.setCusName(name);

                // Call the editCustomerDAO method in the CustomerDAO class
                customerDAO.editCustomerDAO(customer);

                // Update the table model with the new customer info
                customerTableModel.setValueAt(name, customerTable.getSelectedRow(), 0);
                customerTableModel.setValueAt(id, customerTable.getSelectedRow(), 1);

                // Clear the text fields and enable the Edit button and disable the Save button
                cusNameTextField.setText("");
                cusIdTextField.setText("");
                editButton.setEnabled(true);
                saveButton.setEnabled(false);
                cusIdTextField.setEnabled(true);

            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Clear text fields
                cusNameTextField.setText("");
                cusIdTextField.setText("");
                editButton.setEnabled(true);
                saveButton.setEnabled(false);
                cusIdTextField.setEnabled(true);

                // Show all for table
                customerTableModel.setRowCount(0);
                retrieveDataToTable();
            }
        });

        // retrieve data from database and add to table
        retrieveDataToTable();
    }

    // retrieve data from database and add to table
    public void retrieveDataToTable() {
        ResultSet resultSet = customerDAO.getCustomerQueryResultForDisplay();
        try {
            while (resultSet.next()) {
                customerTableModel.addRow(new Object[]{resultSet.getString("cus_name"), resultSet.getInt("cus_id")});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main (String[]args){
        CustomerGUI gui = new CustomerGUI();
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setVisible(true);
    }

}



