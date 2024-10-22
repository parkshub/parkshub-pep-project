package Service;
import Model.Account;
import DAO.AccountDAO;

public class AccountService {

    private AccountDAO accountDAO;

    public AccountService() {
        this.accountDAO = new AccountDAO();
    }
    
    public Account insertAccount(Account account) {
        // maybe also need to add search by username to see that account doesn't exist
        System.out.println("in service");
        System.out.println("service received account: " + account);
        Account checkAccount = this.accountDAO.findAccountByUsername(account.getUsername());
        System.out.println("this is service account" + checkAccount);

        return checkAccount != null ? null : this.accountDAO.insertAccount(account);

        // return this.accountDAO.insertAccount(account);
    }
}
