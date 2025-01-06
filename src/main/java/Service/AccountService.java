package Service;

import Model.Account;
import DAO.AccountDAO;

import java.sql.SQLException;

public class AccountService {
    public AccountDAO accountDAO;

    // no-args constructor
    public AccountService(){
        accountDAO = new AccountDAO();
    }
    
    // constructor for when accountDAO is provided
    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    public Account addAccount(Account account) throws SQLException {
        // check if user and pass are long enough and if account doesn't already exist
        return (account.getUsername().length() > 0 && account.getPassword().length() > 4 && accountDAO.verifyAccount(account) == null)
            ? accountDAO.registerAccount(account) : null;
    }

    public Account verifyLogin (Account account) throws SQLException {
        return accountDAO.verifyLogin(account);
    }

    

    
}
