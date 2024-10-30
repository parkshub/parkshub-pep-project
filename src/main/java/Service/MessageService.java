package Service;
import DAO.MessageDAO;
import Model.Message;


public class MessageService {
    MessageDAO messageDAO;

    public MessageService() {
        this.messageDAO = new MessageDAO();
    }

    public Message insertMessage(Message message) {
        Message insertedMessage = this.messageDAO.insertMessage(message);
        return insertedMessage;
    }
    
}
