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
        System.out.println("\nin service");
        System.out.println("this is account received by service from controller: " + account);
        Account checkAccount = this.accountDAO.findAccountByUsername(account.getUsername());
        System.out.println("this is account received by service from DAO looking for duplicates: " + checkAccount);
        
        if (checkAccount != null) {
            System.out.println("check account is not null, another account with same username exists");
            return null;
        }

        System.out.println("check account is null, account with same username does not exist");
        Account addedAccount = this.accountDAO.insertAccount(account);
        System.out.println("this is account received by service from DAO... inserting new account: " + addedAccount);
        return addedAccount;
    }
}
