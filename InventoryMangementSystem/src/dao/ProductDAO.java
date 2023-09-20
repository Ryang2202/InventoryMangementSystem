package dao;

import java.sql.*;
import model.Product;
import database.MyJDBC;
import javax.swing.*;
public class ProductDAO {
    Connection connection;
    Statement statement;
    PreparedStatement prepStatement;
    PreparedStatement prepStatement1;
    PreparedStatement prepStatement2;
    ResultSet resultSet;

    public ProductDAO() {
        try {
            connection = new MyJDBC().getConn();
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Double getProSellPrice(int proId) {
        Double sellPrice = null;
        try {
            String query = "SELECT sell_price FROM product WHERE pro_id = ?";
            prepStatement1 = connection.prepareStatement(query);
            prepStatement1.setInt(1, proId);
            resultSet = prepStatement1.executeQuery();

            if (resultSet.next())
                sellPrice = resultSet.getDouble("sell_price");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return sellPrice;
    }

    public Product getProductById(int proId) {
        Product product = null;
        try {
            String query = "SELECT * FROM product WHERE pro_id='" +proId+ "'";
            resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                product = new Product();
                product.setProId(resultSet.getInt("pro_id"));
                product.setProName(resultSet.getString("pro_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }


    // Methods to add new product
    public boolean addProductDAO(Product product) {
        try {
            String query = "SELECT * FROM product WHERE pro_id='"
                    + product.getProId()
                    + "'";
            resultSet = statement.executeQuery(query);
            if (resultSet.next())
                JOptionPane.showMessageDialog(null, "Product already exists.");
            else {
                addFunction(product);
                return(true);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return(false);
    }

    public void addFunction(Product product) {
        try {
            String query = "INSERT INTO product (`pro_name`,`pro_id`,`cost_price`,`sell_price`,`sup_id`) VALUES(?,?,?,?,?)";
            prepStatement = connection.prepareStatement(query);
            prepStatement.setString(1, product.getProName());
            prepStatement.setInt(2, product.getProId());
            prepStatement.setDouble(3, product.getCostPrice());
            prepStatement.setDouble(4, product.getSellPrice());
            prepStatement.setInt(5, product.getSupId());


            String query2 = "INSERT INTO inventory (`pro_id`,`inv_quantity`) VALUES(?,?)";
            prepStatement2 = connection.prepareStatement(query2);
            prepStatement2.setInt(1, product.getProId());
            prepStatement2.setInt(2, 0);

            prepStatement.executeUpdate();
            prepStatement2.executeUpdate();
            JOptionPane.showMessageDialog(null, "New product has been added.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to edit existing product details
    public void editProductDAO(Product product) {
        try {
            String query = "UPDATE product SET pro_name=?,cost_price=?,sell_price=?,sup_id=? WHERE pro_id=?";
            prepStatement = connection.prepareStatement(query);
            prepStatement.setString(1, product.getProName());
            prepStatement.setDouble(2, product.getCostPrice());
            prepStatement.setDouble(3, product.getSellPrice());
            prepStatement.setInt(4, product.getSupId());
            prepStatement.setInt(5, product.getProId());



            prepStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Product details have been updated.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Method to delete existing product
    public void deleteProductDAO(int proId) {
        try {
            String query = "DELETE FROM product WHERE pro_id='" + proId + "'";
            statement.executeUpdate(query);
            JOptionPane.showMessageDialog(null, "Product has been removed.");

            String query2 = "DELETE FROM inventory WHERE pro_id='" + proId + "'";
            statement.executeUpdate(query2);
            JOptionPane.showMessageDialog(null, "Product's inventory has been removed.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to retrieve data set to be displayed
    public ResultSet getProductQueryResultForDisplay() {
            try {
                String query = "SELECT pro_name,pro_id,cost_price,sell_price,sup_id FROM product ORDER BY pro_id";
                resultSet = statement.executeQuery(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return resultSet;
        }

    // Method to retrieve search data
    public ResultSet getProductByIdSearch(int proId) {
        try {
            String query = "SELECT * FROM product WHERE pro_id='" +proId+ "'";
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public ResultSet getProductByNameSearch(String proName) {
        try {
            String query = "SELECT * FROM product WHERE pro_name='" +proName+ "'";
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

}

