package GUI;

import dao.UserDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginGUI extends JFrame {

    private JLabel userLabel, passLabel;
    private JTextField userTextField;
    private JPasswordField passField;
    private JButton loginButton, cancelButton;
    private JPanel inputPanel, buttonPanel;

    public LoginGUI() {
        // Set the frame properties
        setTitle("Login Page");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Create the user DAO
        UserDAO userDAO = new UserDAO();

        // Create the panel for the text fields and buttons
        inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2, 0, 0));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(50, 200, 0, 200));

        // Create the labels for the text fields
        userLabel = new JLabel("User ID:");
        userLabel.setFont(new Font("Arial", Font.BOLD, 18));

        passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Arial", Font.BOLD, 18));


        // Create a panel for the header
        JPanel headerPanel = new JPanel(new GridLayout(2, 1, 0, 0));
        headerPanel.setPreferredSize(new Dimension(getWidth(), 150));
        headerPanel.setBackground(Color.darkGray);

        // Create a label for the title and add it to the header panel
        JLabel titleLabel = new JLabel("Inventory Management System", SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        headerPanel.add(titleLabel);

        // Create a label for the subtitle and add it to the header panel
        JLabel subtitleLabel = new JLabel("LOGIN PAGE", SwingConstants.CENTER);
        subtitleLabel.setForeground(Color.WHITE);
        subtitleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(subtitleLabel);

        // Create the text fields
        userTextField = new JTextField();
        passField = new JPasswordField();

        // Add the labels and text fields to the input panel
        inputPanel.add(userLabel);
        inputPanel.add(userTextField);
        inputPanel.add(passLabel);
        inputPanel.add(passField);

        // Create the button
        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 18));
        cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Arial", Font.BOLD, 18));


        // Add the button to the input panel
        buttonPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        buttonPanel.add(loginButton);
        buttonPanel.add(cancelButton);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 250, 50, 250));

        // Create a panel for the content
        JPanel contentPanel = new JPanel(new GridLayout(2, 1));
        contentPanel.add(inputPanel);
        contentPanel.add(buttonPanel);


        // Create a panel for the footer
        JPanel footerPanel = new JPanel(new GridLayout(1, 1));
        footerPanel.setPreferredSize(new Dimension(getWidth(), 60));
        footerPanel.setBackground(Color.darkGray);

        JLabel footerLabel = new JLabel("Ren Yang  - -  Info 5100 Final Project", SwingConstants.CENTER);
        footerLabel.setForeground(Color.WHITE);
        footerLabel.setFont(new Font("Arial", Font.BOLD, 12));
        footerPanel.add(footerLabel);

        // Add the input panel and button panel to the frame
        add(headerPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
        add(footerPanel, BorderLayout.SOUTH);

        // Add ActionListener to login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the user ID and password entered by the user
                String idString = userTextField.getText();
                int userId = Integer.parseInt(idString);
                String password = String.valueOf(passField.getPassword());

                // Verify the user ID and password
                boolean isValidUser = userDAO.isValidUser(userId, password);

                if (isValidUser) {
                    // Go to main page
                    dispose();
                    MainGUI mainGUI = new MainGUI();
                } else {
                    // Show error message
                    JOptionPane.showMessageDialog(null, "Invalid user ID or password. Please try again.");
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Clear text fields
                userTextField.setText("");
                passField.setText("");
            }
        });
    }

    public static void main(String[] args) {
        LoginGUI gui = new LoginGUI();
        gui.setVisible(true);
    }
}
