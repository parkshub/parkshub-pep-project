package Service;
import DAO.MessageDAO;
import Model.Message;
import java.util.*;

public class MessageService {
    MessageDAO messageDAO;

    public MessageService() {
        this.messageDAO = new MessageDAO();
    }

    public Message insertMessage(Message message) {
        Message insertedMessage = this.messageDAO.insertMessage(message);
        return insertedMessage;
    }

    public ArrayList<Message> getMessages() {
        return this.messageDAO.getMessages();
    }

    public Message getMessageById(String messageId) {
        Message message = this.messageDAO.getMessageById(messageId);
        return message;
    }

    public Message deleteMessageById(String messageId) {
        Message message = this.messageDAO.getMessageById(messageId);
        int row = this.messageDAO.deleteMessageById(messageId);

        return message;
    }

    public ArrayList<Message> getMessagesByUser(String accountId) {
        ArrayList<Message> messages = this.messageDAO.getMessagesByUser(accountId);
        return messages;
    }
}
