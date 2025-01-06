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
            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)";
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
    }
    
    public List<Message> getAllMessages() throws SQLException{
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
       
        try {
            String sql = "SELECT * FROM message";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"), 
                        rs.getInt("posted_by"), 
                        rs.getString("message_text"), 
                        rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return messages;
    }

    public Message getMessage(int message_id) throws SQLException{
        Connection connection = ConnectionUtil.getConnection();
        
        //Write SQL logic here
        String sql = "SELECT * FROM message WHERE message_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        //write preparedStatement's setInt method here.
        preparedStatement.setInt(1, message_id);

        ResultSet rs = preparedStatement.executeQuery();
        while(rs.next()){
            Message message = new Message(rs.getInt("message_id"),
                    rs.getInt("posted_by"), 
                    rs.getString("message_text"), 
                    rs.getLong("time_posted_epoch"));
            return message;
        }
        return null;
    }

    public Message deleteMessage(int message_id) throws SQLException {
        Connection connection = ConnectionUtil.getConnection();
    
        // get the message before deleting
        String sql = "SELECT * FROM message WHERE message_id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, message_id);

        ResultSet rs = ps.executeQuery();

        Message message = null;
        if (rs.next()) {
            message = new Message(
                rs.getInt("message_id"),
                rs.getInt("posted_by"),
                rs.getString("message_text"),
                rs.getLong("time_posted_epoch")
            );
        }

        // return null if message not found
        if (message == null) {
            return null;
        }

        // delete message
        String deleteSql = "DELETE FROM message WHERE message_id = ?";
        PreparedStatement deletePreparedStatement = connection.prepareStatement(deleteSql);
        deletePreparedStatement.setInt(1, message_id);
        
        deletePreparedStatement.executeUpdate();
        return message;
    }

    public Message patchMessage(int message_id, String message_text) throws SQLException {
        Connection connection = ConnectionUtil.getConnection();

        String sql = "UPDATE message SET message_text = ? WHERE message_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, message_text);
        preparedStatement.setInt(2, message_id);
        
        int rowsUpdated = preparedStatement.executeUpdate();
        if (rowsUpdated > 0) {
            return getMessage(message_id);
        }

        return null;
    }

    public List<Message> getAllMessagesByAccount(int account_id){
        Connection connection = ConnectionUtil.getConnection();
        try {
            List<Message> messages = new ArrayList<Message>();
            String sql = "SELECT * FROM message WHERE posted_by = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, account_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                messages.add(new Message(
                    resultSet.getInt("message_id"),
                    resultSet.getInt("posted_by"),
                    resultSet.getString("message_text"),
                    resultSet.getLong("time_posted_epoch")
                ));
            };
            return messages;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

}
