package Service;

import Model.Message;
import Util.ConnectionUtil;
import DAO.MessageDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
        // checking if posted_by refers to a real, existing user
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM account WHERE account_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.executeQuery();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                return null; // if posted_by does not refer to an existing user
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }   

        // checking if message is valid
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

    public Message patchMessage(int message_id, String message_text) throws SQLException {
        // checking if message is valid
        return (message_text != "" && message_text.length() <= 255)
            ? messageDAO.patchMessage(message_id, message_text) : null;
    }

    public List<Message> getAllMessagesByAccount (int account_id) throws SQLException{
        return messageDAO.getAllMessagesByAccount(account_id);
    }
    
    
}
