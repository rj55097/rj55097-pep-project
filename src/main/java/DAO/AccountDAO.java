package DAO;
import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;

public class AccountDAO {

    public Account verifyAccount(Account acc) throws SQLException{
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "SELECT * FROM account WHERE username =?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, acc.getUsername());
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

        String sql = "SELECT * FROM account WHERE username = ? AND password = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, account.getUsername());
        preparedStatement.setString(2, account.getPassword());

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return new Account(resultSet.getInt("account_id"), resultSet.getString("username"), resultSet.getString("password"));
        }

        return null;
    }
}
