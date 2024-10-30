package DAO;
import Util.ConnectionUtil;
import Model.Message;
import java.sql.*;
import java.util.*;

public class MessageDAO {
    
    public Message insertMessage(Message message) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String sql = "insert into message (posted_by, message_text, time_posted_epoch) values(?, ?, ?);";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, message.getPosted_by());
            ps.setString(2, message.getMessage_text());
            ps.setLong(3, message.getTime_posted_epoch());
            ps.executeUpdate();
            
            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                Message addedMessage = new Message(
                    rs.getInt(1), 
                    message.getPosted_by(), 
                    message.getMessage_text(), 
                    message.getTime_posted_epoch()
                );

                return addedMessage;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public ArrayList<Message> getAllMessages() {
        ArrayList<Message> messages = new ArrayList<>();

        try (Connection connection = ConnectionUtil.getConnection()) {
            String sql = "select * from message";
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while(rs.next()) {
                Message message = new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch")
                );

                messages.add(message);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return messages;
    }

    public Message getMessageById(String messageId) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String sql = "select * from message where message_id = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(messageId));
            
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Message message = new Message(
                    rs.getInt("message_id"), 
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch")     
                );

                return message;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }
}
