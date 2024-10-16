package Service;
import Model.Account;
import DAO.AccountDAO;

public class AccountService {

    private AccountDAO AccountDAO;

    public AccountService() {
        this.AccountDAO = new AccountDAO();
    }
    
    public Account insertAccount(Account account) {
        // maybe also need to add search by username to see that account doesn't exist

        return AccoutDAO.insertAccount(account);
    }
}
