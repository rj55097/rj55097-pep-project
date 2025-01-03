package DAO;
import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {
    public Account addAccount(Account account) throws SQLException{
        Connection connection = ConnectionUtil.getConnection();
        
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
