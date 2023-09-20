package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import dao.UserDAO;
import model.User;

public class UserGUI extends JFrame {
    // Table
    private JTable userTable;
    // Table model
    private DefaultTableModel userTableModel;
    // Buttons
    private JButton backButton, addButton, deleteButton, searchByIdButton, searchByNameButton, editButton, saveButton, clearButton;
    // Text fields
    private JTextField userNameTextField, userIdTextField, userPasswordTextField;

    // Label
    private JLabel userPasswordLabel, userIdLabel, userNameLabel;
    // DAO
    private UserDAO userDAO;


    public UserGUI() {


        // Set the frame properties
        setTitle("Inventory Management System - User Page");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        // Create the user DAO
        userDAO = new UserDAO();


        // Create a panel for the header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setPreferredSize(new Dimension(getWidth(), 50));
        headerPanel.setBackground(Color.darkGray);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));

        // Create a label for the title and add it to the header panel
        JLabel titleLabel = new JLabel("USER PAGE", SwingConstants.CENTER);
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
        String[] columnNames = {"User Name", "User ID", "User Password"};
        userTableModel = new DefaultTableModel(columnNames, 0);
        userTable = new JTable(userTableModel);

        // Create the scroll pane for the table
        JScrollPane tableScrollPane = new JScrollPane(userTable);
        tableScrollPane.setPreferredSize(new Dimension(getWidth(), 300));
        tableScrollPane.setBorder(BorderFactory.createTitledBorder("  Table  "));




        // Create the panel for the text fields and buttons
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("  Input  "));
        inputPanel.setPreferredSize(new Dimension(750, 120));


        // Create the labels for the text fields
        userNameLabel = new JLabel("User Name:");
        userNameLabel.setFont(new Font("Arial", Font.BOLD, 14));

        userIdLabel = new JLabel("User ID:");
        userIdLabel.setFont(new Font("Arial", Font.BOLD, 14));

        userPasswordLabel = new JLabel("User Password:");
        userPasswordLabel.setFont(new Font("Arial", Font.BOLD, 14));



        // Create the text fields
        userNameTextField = new JTextField();
        userIdTextField = new JTextField();
        userPasswordTextField = new JTextField();


        // Add the labels and text fields to the input panel
        inputPanel.add(userNameLabel);
        inputPanel.add(userNameTextField);
        inputPanel.add(userIdLabel);
        inputPanel.add(userIdTextField);
        inputPanel.add(userPasswordLabel);
        inputPanel.add(userPasswordTextField);

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
                String name = userNameTextField.getText();
                String password = userPasswordTextField.getText();
                String idString = userIdTextField.getText();
                int id = Integer.parseInt(idString);
                if (name.isEmpty() || idString.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter name, id, and password.");
                    return;
                }
                User user = new User();
                user.setUserName(name);
                user.setUserId(id);
                user.setUserPassword(password);

                boolean addResult = userDAO.addUserDAO(user);
                if (addResult) {
                    // Update the table with the new user data
                    Object[] row = {user.getUserName(), user.getUserId(), user.getUserPassword()};
                    userTableModel.addRow(row);
                }

                // Clear text fields
                userNameTextField.setText("");
                userIdTextField.setText("");
                userPasswordTextField.setText("");

            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = userTable.getSelectedRow();
                if (row == -1) {
                    JOptionPane.showMessageDialog(null, "Please select a row to delete.");
                    return;
                }
                int userId = (int) userTableModel.getValueAt(row, 1);
                userDAO.deleteUserDAO(userId);
                userTableModel.removeRow(row);
            }
        });

        searchByIdButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idString = userIdTextField.getText();
                int id = Integer.parseInt(idString);
                if (idString.equals("")) {
                    JOptionPane.showMessageDialog(null, "Please enter id for search.");
                    return;
                }
                ResultSet resultSet = userDAO.getUserByIdSearch(id);
                try {
                    if (resultSet.next()) {
                        // Display the search result in the table
                        userTableModel.setRowCount(0);
                        userTableModel.addRow(new Object[]{resultSet.getString("user_name"), resultSet.getInt("user_id"), resultSet.getString("user_password")});
                    } else {
                        // Display an error message
                        JOptionPane.showMessageDialog(null, "No user found with the entered ID.");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        searchByNameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = userNameTextField.getText();
                if (name.equals("")) {
                    JOptionPane.showMessageDialog(null, "Please enter name for search.");
                    return;
                }
                ResultSet resultSet = userDAO.getUserByNameSearch(name);
                try {
                    if (resultSet.next()) {
                        // Display the search result in the table
                        userTableModel.setRowCount(0);
                        userTableModel.addRow(new Object[]{resultSet.getString("user_name"), resultSet.getInt("user_id"), resultSet.getString("user_password")});
                    } else {
                        // Display an error message
                        JOptionPane.showMessageDialog(null, "No user found with the entered Name.");
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
                int selectedRow = userTable.getSelectedRow();

                // If no row is selected, show an error message and return
                if (selectedRow < 0)
                    JOptionPane.showMessageDialog(null, "Select a user from the table.");

                // Get the user object from the selected row
                String name = (String) userTableModel.getValueAt(selectedRow, 0);
                int id = (int) userTableModel.getValueAt(selectedRow, 1);
                String password = (String) userTableModel.getValueAt(selectedRow, 2);


                // Set the text fields to the user info
                userNameTextField.setText(name);
                userIdTextField.setText(Integer.toString(id));
                userPasswordTextField.setText(password);
                userIdTextField.setEnabled(false);


                // Disable the Edit button and enable the Save button
                editButton.setEnabled(false);
                saveButton.setEnabled(true);
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the user info from the text fields
                int id = Integer.parseInt(userIdTextField.getText());
                String name = userNameTextField.getText();
                String password = userPasswordTextField.getText();

                // Create a new user object with the updated info
                User user = new User();
                user.setUserId(id);
                user.setUserName(name);
                user.setUserPassword(password);

                // Call the editUserDAO method in the UserDAO class
                userDAO.editUserDAO(user);

                // Update the table model with the new user info
                userTableModel.setValueAt(name, userTable.getSelectedRow(), 0);
                userTableModel.setValueAt(id, userTable.getSelectedRow(), 1);
                userTableModel.setValueAt(password, userTable.getSelectedRow(), 2);


                // Clear the text fields and enable the Edit button and disable the Save button
                userNameTextField.setText("");
                userIdTextField.setText("");
                userPasswordTextField.setText("");
                editButton.setEnabled(true);
                saveButton.setEnabled(false);
                userIdTextField.setEnabled(true);
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Clear text fields
                userNameTextField.setText("");
                userIdTextField.setText("");
                userPasswordTextField.setText("");
                editButton.setEnabled(true);
                saveButton.setEnabled(false);
                userIdTextField.setEnabled(true);
                // Show all for table
                userTableModel.setRowCount(0);
                retrieveDataToTable();
            }
        });

        // retrieve data from database and add to table
        retrieveDataToTable();
    }

    // retrieve data from database and add to table
    public void retrieveDataToTable() {
        ResultSet resultSet = userDAO.getUserQueryResultForDisplay();
        try {
            while (resultSet.next()) {
                userTableModel.addRow(new Object[]{resultSet.getString("user_name"), resultSet.getInt("user_id"), resultSet.getString("user_password")});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    public static void main (String[]args){
//        UserGUI gui = new UserGUI();
//        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        gui.setVisible(true);
//    }

}



