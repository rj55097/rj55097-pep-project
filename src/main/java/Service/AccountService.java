package Service;

import Model.Account;
import DAO.AccountDAO;

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
}
