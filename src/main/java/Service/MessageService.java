package Service;

import Model.Message;
import DAO.MessageDAO;

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
    
}
