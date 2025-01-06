package Service;

import Model.Message;
import DAO.MessageDAO;

import java.sql.SQLException;
import java.util.List;

public class MessageService {
    public MessageDAO messageDAO;

    // no-args constructor
    public MessageService(){
        messageDAO = new MessageDAO();
    }
    
    // constructor for when messageDAO is provided
    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }

    public Message addMessage(Message message) throws SQLException {

        // if (messageDAO.getMessage(message.getMessage_id()) == null) {
        //     return messageDAO.addMessage(message);
        // }
        // return null;
        return (message.getMessage_text() != "" && message.getMessage_text().length() <= 255)
            ? messageDAO.addMessage(message) : null;
    }

    public List<Message> getAllMessages() throws SQLException{
        return messageDAO.getAllMessages();
    }

    public Message getMessage(int message_id) throws SQLException {
        return messageDAO.getMessage(message_id);
    }

    public Message deleteMessage(int message_id) throws SQLException {
        return messageDAO.deleteMessage(message_id);
    }

    public Message patchMessage(int message_id) throws SQLException {
        return messageDAO.patchMessage(message_id);
    }
    
    
}
