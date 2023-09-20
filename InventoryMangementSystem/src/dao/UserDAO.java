package dao;

import database.MyJDBC;
import model.User;

import javax.swing.*;
import java.sql.*;

public class UserDAO {
    Connection connection;
    Statement statement;
    PreparedStatement prepStatement;
    ResultSet resultSet;

    public UserDAO() {
        try {
            connection = new MyJDBC().getConn();
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Methods to add new user
    public boolean addUserDAO(User user) {
        try {
            String query = "SELECT * FROM user WHERE user_id='"
                    + user.getUserId()
                    + "'";
            resultSet = statement.executeQuery(query);
            if (resultSet.next())
                JOptionPane.showMessageDialog(null, "User already exists.");
            else{
                addFunction(user);
                return(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return(false);
    }

    public void addFunction(User user) {
        try {
            String query = "INSERT INTO user (`user_name`,`user_id`,`user_password`) VALUES(?,?,?)";
            prepStatement = connection.prepareStatement(query);
            prepStatement.setString(1, user.getUserName());
            prepStatement.setInt(2, user.getUserId());
            prepStatement.setString(3, user.getUserPassword());
            prepStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "New User has been added.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to edit existing user details
    public void editUserDAO(User user) {
        try {
            String query = "UPDATE user SET user_name=?,user_password=? WHERE user_id=?";
            prepStatement = connection.prepareStatement(query);
            prepStatement.setString(1, user.getUserName());
            prepStatement.setString(2, user.getUserPassword());
            prepStatement.setInt(3, user.getUserId());
            prepStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "User details have been updated.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to delete existing user
    public void deleteUserDAO(int userId) {
        try {
            String query = "DELETE FROM user WHERE user_id='" + userId + "'";
            statement.executeUpdate(query);
            JOptionPane.showMessageDialog(null, "User removed.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to retrieve data set to be displayed
    public ResultSet getUserQueryResultForDisplay() {
        try {
            String query = "SELECT user_name,user_id,user_password FROM user ORDER BY user_id";
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    // Method to retrieve search data
    public ResultSet getUserByIdSearch(int userId) {
        try {
            String query = "SELECT * FROM user WHERE user_id='" +userId+ "'";
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public ResultSet getUserByNameSearch(String userName) {
        try {
            String query = "SELECT * FROM user WHERE user_name='" +userName+ "'";
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public boolean isValidUser(int userId, String password) {
        boolean isValid = false;
        try {
            String query = "SELECT * FROM user WHERE user_id = ? AND user_password = ?";
            prepStatement = connection.prepareStatement(query);
            prepStatement.setInt(1, userId);
            prepStatement.setString(2, password);
            resultSet = prepStatement.executeQuery();
            if (resultSet.next()) {
                isValid = true;
            }
            resultSet.close();
            prepStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isValid;
    }

}
