package DAO;
import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {
    public Message addMessage(Message message) throws SQLException{
        Connection connection = ConnectionUtil.getConnection();
        
        //Write SQL logic here
        String sql = "insert into message (message_id, posted_by, message_text, time_posted) values (?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        //write preparedStatement's setString and setInt methods here.
        preparedStatement.setInt(1, message.getMessage_id());
        preparedStatement.setInt(2, message.getPosted_by());
        preparedStatement.setString(3, message.getMessage_text());
        preparedStatement.setLong(4, message.getTime_posted_epoch());

        preparedStatement.executeUpdate();
        return message;
    }
    
    public List<Message> getAllMessages() throws SQLException{
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
       
        //Write SQL logic here
        String sql = "select * from message";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet rs = preparedStatement.executeQuery();
        while(rs.next()){
            Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted"));
            messages.add(message);
        }
        return messages;
    }

}
