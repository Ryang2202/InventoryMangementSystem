package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainGUI extends JFrame implements ActionListener {
    // Buttons for each section of the application
    private JButton customerButton;
    private JButton inventoryButton;
    private JButton userButton;
    private JButton supplierButton;
    private JButton productButton;
    private JButton orderButton;
    private JButton restockButton;
    private JButton logoutButton;

    public MainGUI() {
        // Set up the main window
        setTitle("Inventory Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Create a panel for the header
        JPanel headerPanel = new JPanel(new GridLayout(2, 1, 0, 0));
        headerPanel.setPreferredSize(new Dimension(getWidth(), 130));
        headerPanel.setBackground(Color.darkGray);

        // Create a label for the title and add it to the header panel
        JLabel titleLabel = new JLabel("Inventory Management System", SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        headerPanel.add(titleLabel);

        // Create a label for the subtitle and add it to the header panel
        JLabel subtitleLabel = new JLabel("MAIN PAGE", SwingConstants.CENTER);
        subtitleLabel.setForeground(Color.WHITE);
        subtitleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerPanel.add(subtitleLabel);

        // Create a panel to center the buttons
        JPanel buttonPanel = new JPanel(new GridLayout(3, 3, 50, 50));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        // Create buttons for each section of the application
        customerButton = new JButton("Customer");
        customerButton.setFont(new Font("Arial", Font.BOLD, 18));
        inventoryButton = new JButton("Inventory");
        inventoryButton.setFont(new Font("Arial", Font.BOLD, 18));
        userButton = new JButton("User");
        userButton.setFont(new Font("Arial", Font.BOLD, 18));
        supplierButton = new JButton("Supplier");
        supplierButton.setFont(new Font("Arial", Font.BOLD, 18));
        productButton = new JButton("Product");
        productButton.setFont(new Font("Arial", Font.BOLD, 18));
        orderButton = new JButton("Order");
        orderButton.setFont(new Font("Arial", Font.BOLD, 18));
        restockButton = new JButton("Restock");
        restockButton.setFont(new Font("Arial", Font.BOLD, 18));
        logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Arial", Font.BOLD, 14));



        // Add action listeners to each button
        customerButton.addActionListener(this);
        inventoryButton.addActionListener(this);
        userButton.addActionListener(this);
        supplierButton.addActionListener(this);
        productButton.addActionListener(this);
        orderButton.addActionListener(this);
        restockButton.addActionListener(this);
        logoutButton.addActionListener(this);

        // Add the buttons to the center panel
        buttonPanel.add(customerButton);
        buttonPanel.add(inventoryButton);
        buttonPanel.add(userButton);
        buttonPanel.add(supplierButton);
        buttonPanel.add(productButton);
        buttonPanel.add(orderButton);
        buttonPanel.add(restockButton);

        // Create a panel for the footer
        JPanel footerPanel = new JPanel(new GridLayout(1, 1));
        footerPanel.setPreferredSize(new Dimension(getWidth(), 60));
        footerPanel.setBackground(Color.darkGray);
        footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 290, 10, 290));


        // Add the logout button to the footer panel
        footerPanel.add(logoutButton);

        // Add the header, center, and footer panels to the main window
        add(headerPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(footerPanel, BorderLayout.SOUTH);

        // Show the main window
        setVisible(true);
    }

    // Action listener for button clicks
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        // Open the appropriate section of the application based on the button clicked
        if (action.equals("Customer")) {
            CustomerGUI customerGUI = new CustomerGUI();
            customerGUI.setVisible(true);
        } else if (action.equals("Inventory")) {
            InventoryGUI inventoryGUI = new InventoryGUI();
            inventoryGUI.setVisible(true);
        } else if (action.equals("User")) {
            UserGUI userGUI = new UserGUI();
            userGUI.setVisible(true);
        } else if (action.equals("Supplier")) {
            SupplierGUI supplierGUI = new SupplierGUI();
            supplierGUI.setVisible(true);
        } else if (action.equals("Product")) {
            dispose();
            ProductGUI productGUI = new ProductGUI();
            productGUI.setVisible(true);
        } else if (action.equals("Order")) {
            dispose();
            OrderGUI orderUI = new OrderGUI();
            orderUI.setVisible(true);
        } else if (action.equals("Restock")) {
            dispose();
            RestockGUI restockGUI = new RestockGUI();
            restockGUI.setVisible(true);
        } else if (action.equals("Logout")) {
//            setVisible(false);
            dispose();
            LoginGUI loginGUI = new LoginGUI();
            loginGUI.setVisible(true);
        }
    }


//    public static void main(String[] args) {
//        // Create the main GUI window
//        MainGUI mainGUI = new MainGUI();
//        mainGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        mainGUI.setVisible(true);
//    }
}
