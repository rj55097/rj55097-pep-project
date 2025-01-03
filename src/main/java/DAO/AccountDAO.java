package DAO;
import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {
    public Account addAccount(Account account) throws SQLException{
        Connection connection = ConnectionUtil.getConnection();
        
        String check = "select count(*) from account where username = ?";
        PreparedStatement ps = connection.prepareStatement(check);
        ps.setString(1, account.getUsername());
        ResultSet resultSet = ps.executeQuery();

        // if username already exists, return null
        if (resultSet.next() && resultSet.getInt(1) > 0) {
            return null;
        }
        // if username is blank or password is too short, return null
        if (account.getUsername() == "" || account.getPassword().length() < 4) {
            return null;
        }

        //Write SQL logic here
        String sql = "insert into account (username, password) values (?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        //write preparedStatement's setString and setInt methods here.
        preparedStatement.setString(1, account.getUsername());
        preparedStatement.setString(2, account.getPassword());

        preparedStatement.executeUpdate();
        return new Account(account.getUsername(), account.getPassword());
    }
}
