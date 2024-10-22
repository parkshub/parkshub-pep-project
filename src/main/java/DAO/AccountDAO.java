package DAO;
import Util.ConnectionUtil;
import Model.Account;
import java.sql.*;

public class AccountDAO {
    public Account insertAccount(Account account) {
        // Connection connection = ConnectionUtil.getConnection();
        System.out.println("\nin DAO insert account 1");

        try(Connection connection = ConnectionUtil.getConnection()) {
            System.out.println("\nin DAO insert account 2");
            String sql = "insert into account (username, password) values (?, ?);";
            
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                int accountId = (int) rs.getLong(1);
                Account addedAccount = new Account(accountId, account.getUsername(), account.getPassword());
                return addedAccount;
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public Account findAccountByUsername(String username) {
        System.out.println("\nin DAO find account by username 1");
        try (Connection connection = ConnectionUtil.getConnection()) {
            System.out.println("\nin DAO find account by username 2");
            String sql = "select * from account where username = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();            

            if(rs.next()) {
                Account account = new Account(
                    rs.getInt("account_id"), 
                    rs.getString("username"), 
                    rs.getString("password")
                );
                return account;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }
}
