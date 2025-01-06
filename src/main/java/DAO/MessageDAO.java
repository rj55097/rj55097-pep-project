package DAO;
import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {
    public Message addMessage(Message message) throws SQLException{
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "insert into message (posted_by, message_text, time_posted) values (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                int newId = resultSet.getInt("message_id");
                return new Message(newId, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }   
        return null;
        // return new Message(message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
    }
    
    public List<Message> getAllMessages() throws SQLException{
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
       
        //Write SQL logic here
        String sql = "select * from message";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet rs = preparedStatement.executeQuery();
        while(rs.next()){
            Message message = new Message(rs.getInt("message_id"), 
                    rs.getInt("posted_by"), 
                    rs.getString("message_text"), 
                    rs.getLong("time_posted"));
            messages.add(message);
        }
        return messages;
    }

    public Message getMessage(int message_id) throws SQLException{
        Connection connection = ConnectionUtil.getConnection();
        
        //Write SQL logic here
        String sql = "select * from message where message_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        //write preparedStatement's setInt method here.
        preparedStatement.setInt(1, message_id);

        ResultSet rs = preparedStatement.executeQuery();
        while(rs.next()){
            Message message = new Message(rs.getInt("message_id"),
                    rs.getInt("posted_by"), 
                    rs.getString("message_text"), 
                    rs.getLong("time_posted"));
            return message;
        }
        return null;
    }

    public Message deleteMessage(int message_id) throws SQLException {
        Connection connection = ConnectionUtil.getConnection();
    
        // get the message before deleting
        String sql = "select * from message where message_id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, message_id);

        ResultSet rs = ps.executeQuery();

        Message message = null;
        if (rs.next()) {
            message = new Message(
                rs.getInt("message_id"),
                rs.getInt("posted_by"),
                rs.getString("message_text"),
                rs.getLong("time_posted")
            );
        }

        // return null if message not found
        if (message == null) {
            return null;
        }

        // delete message
        String deleteSql = "delete from message where message_id = ?";
        PreparedStatement deletePreparedStatement = connection.prepareStatement(deleteSql);
        deletePreparedStatement.setInt(1, message_id);
        
        deletePreparedStatement.executeUpdate();
        return message;
    }

    public Message patchMessage(int message_id) throws SQLException {
        Connection connection = ConnectionUtil.getConnection();

        return null;
    }

}
