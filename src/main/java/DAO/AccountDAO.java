package DAO;
import Util.ConnectionUtil;
import Model.Account;
import java.sql.*;

public class AccountDAO {
    public Account insertAccount(Account account) {
        // Connection connection = ConnectionUtil.getConnection();

        try(Connection connection = ConnectionUtil.getConnection()) {
            String sql = "insert into account (username, password) values (?, ?);";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());
            ps.executeQuery();
            ResultSet rs = ps.getGeneratedKeys();

            while(rs.next()){
                int accountId = (int) rs.getLong(1);
                return new Account(accountId, account.getUsername(), account.getPassword());
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public Account getAccountById(int id) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String sql = "select * from account where account_id = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();            

            while(rs.next()) {
                return new Account(
                    rs.getInt("account_id"), 
                    rs.getString("username"), 
                    rs.getString("password")
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


        return null;
    }
}
