package dao;

import database.MyJDBC;
import model.Customer;
import model.Supplier;

import javax.swing.*;
import java.sql.*;

public class SupplierDAO {
    Connection connection;
    Statement statement;
    PreparedStatement prepStatement;
    ResultSet resultSet;

    public SupplierDAO() {
        try {
            connection = new MyJDBC().getConn();
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Methods to add new supplier
    public boolean addSupplierDAO(Supplier supplier) {
        try {
            String query = "SELECT * FROM supplier WHERE sup_id='"
                    + supplier.getSupId()
                    + "'";
            resultSet = statement.executeQuery(query);
            if (resultSet.next())
                JOptionPane.showMessageDialog(null, "Supplier already exists.");
            else {
                addFunction(supplier);
                return(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return(false);
    }

    public void addFunction(Supplier supplier) {
        try {
            String query = "INSERT INTO supplier (`sup_name`,`sup_id`) VALUES(?,?)";
            prepStatement = connection.prepareStatement(query);
            prepStatement.setString(1, supplier.getSupName());
            prepStatement.setInt(2, supplier.getSupId());
            prepStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "New supplier has been added.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to edit existing supplier details
    public void editSupplierDAO(Supplier supplier) {
        try {
            String query = "UPDATE supplier SET sup_name=? WHERE sup_id=?";
            prepStatement = connection.prepareStatement(query);
            prepStatement.setString(1, supplier.getSupName());
            prepStatement.setInt(2, supplier.getSupId());
            prepStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Supplier details have been updated.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to delete existing supplier
    public void deleteSupplierDAO(int supId) {
        try {
            String query = "DELETE FROM supplier WHERE sup_id='" + supId + "'";
            statement.executeUpdate(query);
            JOptionPane.showMessageDialog(null, "Supplier removed.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to retrieve data set to be displayed
    public ResultSet getSupplierQueryResultForDisplay() {
        try {
            String query = "SELECT sup_name,sup_id FROM supplier ORDER BY sup_id";
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    // Method to retrieve search data
    public ResultSet getSupplierByIdSearch(int supId) {
        try {
            String query = "SELECT * FROM supplier WHERE sup_id='" +supId+ "'";
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public ResultSet getSupplierByNameSearch(String supName) {
        try {
            String query = "SELECT * FROM supplier WHERE sup_name='" +supName+ "'";
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

}
