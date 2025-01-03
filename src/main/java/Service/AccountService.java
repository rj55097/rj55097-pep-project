package Service;

import Model.Account;
import DAO.AccountDAO;

import java.sql.SQLException;
import java.util.List;


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

        if (accountDAO.getAccount(account.getAccount_id()) == null) {
            return accountDAO.addAccount(account);
        }
        return null;
    }
}
