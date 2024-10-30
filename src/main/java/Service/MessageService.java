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

    public ArrayList<Message> getAllMessages() {
        return this.messageDAO.getAllMessages();
        // return new ArrayList<>();
    }
    
}
