package DAO;
import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {
    // public Account addAccount(Account account) throws SQLException{
    //     Connection connection = ConnectionUtil.getConnection();
        
    //     String check = "select count(*) from account where username = ?";
    //     PreparedStatement ps = connection.prepareStatement(check);
    //     ps.setString(1, account.getUsername());
    //     ResultSet resultSet = ps.executeQuery();

    //     // if username already exists, return null
    //     if (resultSet.next() && resultSet.getInt(1) > 0) {
    //         return null;
    //     }
    //     // if username is blank or password is too short, return null
    //     if (account.getUsername() == "" || account.getPassword().length() < 4) {
    //         return null;
    //     }

    //     //Write SQL logic here
    //     String sql = "insert into account (username, password) values (?, ?)";
    //     PreparedStatement preparedStatement = connection.prepareStatement(sql);

    //     //write preparedStatement's setString and setInt methods here.
    //     preparedStatement.setString(1, account.getUsername());
    //     preparedStatement.setString(2, account.getPassword());

    //     preparedStatement.executeUpdate();
    //     return new Account(account.getUsername(), account.getPassword());
    // }

    public Account verifyAccount(Account acc) throws SQLException{
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM account WHERE username =? AND password =?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, acc.getUsername());
            preparedStatement.setString(2, acc.getPassword());
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                return new Account(resultSet.getInt("account_id"), acc.getUsername(), acc.getPassword());
            };
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account registerAccount(Account acc) throws SQLException{
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO account (username, password) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, acc.getUsername());
            preparedStatement.setString(2, acc.getPassword());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                int newId = resultSet.getInt("account_id");
                return new Account(newId, acc.getUsername(), acc.getPassword());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account verifyLogin(Account account) throws SQLException {
        Connection connection = ConnectionUtil.getConnection();

        String sql = "SELECT account WHERE username = ? AND password = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, account.getUsername());
        preparedStatement.setString(2, account.getPassword());
        preparedStatement.executeUpdate();

        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        if (resultSet.next()) {
            return new Account(account.getAccount_id(), account.getUsername(), account.getPassword());
        }
        return null;
    }
}
