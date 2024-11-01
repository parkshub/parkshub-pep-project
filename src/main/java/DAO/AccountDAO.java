package DAO;
import Util.ConnectionUtil;
import Model.Account;
import java.sql.*;

public class AccountDAO {
    public Account insertAccount(Account account) {

        try(Connection connection = ConnectionUtil.getConnection()) {
            String sql = "insert into account (username, password) values (?, ?);";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                Account addedAccount = new Account(
                    rs.getInt(1), 
                    account.getUsername(), 
                    account.getPassword()
                );

                return addedAccount;
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public Account findAccountByUsername(String username) {

        try (Connection connection = ConnectionUtil.getConnection()) {
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

    public Account findAccountById(int id) {
        

        try (Connection connection = ConnectionUtil.getConnection()) {
            String sql = "select * from account where account_id = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Account account = new Account(
                    rs.getInt("account_id"), 
                    rs.getString("username"), 
                    rs.getString("password")
                );

                return account;
            }
        } catch (Exception e) {
            e.getMessage();
        }

        return null;
    }
}
