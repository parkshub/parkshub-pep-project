package Service;
import Model.Account;
import DAO.AccountDAO;

public class AccountService {

    private AccountDAO accountDAO;

    public AccountService() {
        this.accountDAO = new AccountDAO();
    }
    
    public Account insertAccount(Account account) {
        Account checkAccount = this.accountDAO.findAccountByUsername(account.getUsername());
        
        if (checkAccount != null) {
            return null;
        }

        Account addedAccount = this.accountDAO.insertAccount(account);
        return addedAccount;
    }

    public Account verifyAccountByUserName(Account account) {
        Account verifiedAccount = this.accountDAO.findAccountByUsername(account.getUsername());

        if (verifiedAccount == null) {
            return null;
        }

        if (!verifiedAccount.getPassword().equals( account.getPassword())) {
            return null;
        }

        return verifiedAccount;
    }

    public Account verifyAccountById(int id) {
        Account verifiedAccount = this.accountDAO.findAccountById(id);

        return verifiedAccount;
    }
}
