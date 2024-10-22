package DAO;
import Util.ConnectionUtil;
import Model.Account;
import java.sql.*;

public class AccountDAO {
    public Account insertAccount(Account account) {
        // Connection connection = ConnectionUtil.getConnection();
        System.out.println("in DAO insert account 1");

        try(Connection connection = ConnectionUtil.getConnection()) {
            System.out.println("in DAO insert account 2");
            String sql = "insert into account (username, password) values (?, ?);";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());
            ps.executeQuery();
            ResultSet rs = ps.getGeneratedKeys();

            // while(rs.next()){
            int accountId = (int) rs.getLong(1);
            return new Account(accountId, account.getUsername(), account.getPassword());
            // }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Account findAccountByUsername(String username) {
        System.out.println("in DAO find account by username 1");
        try (Connection connection = ConnectionUtil.getConnection()) {
            System.out.println("in DAO find account by username 2");
            String sql = "select * from account where username = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();            

            while(rs.next()) {
                Account account = new Account(
                    rs.getInt("account_id"), 
                    rs.getString("username"), 
                    rs.getString("password")
                );
                return account;
            }
        } catch (SQLException e) {
            // System.out.println("SQL Exception happened\n\n");
            System.out.println(e.getMessage());
        }

        return null;
    }
}
