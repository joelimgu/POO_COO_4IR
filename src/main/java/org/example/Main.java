package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.model.conversation.Conversation;
import org.example.model.conversation.Message;
import org.example.model.conversation.User;
import org.example.services.StorageService;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        StorageService s = StorageService.StorageService("");

        try {
            User j = new User("Joel");
            Message m = new Message(j, new User("louis"), "coucou");
            List<Message> lsm = new ArrayList<>();
            lsm.add(m);
            s.save(new Message(new User("Joel"), new User("Louis"), "message1"));
            s.save(new Conversation(j, lsm));
            Gson g = new GsonBuilder().setPrettyPrinting().create();
            System.out.println(g.toJson(s.retrieveAllMessages()));
//            System.out.println(s.retrieveAllMessages().size());
            String colorRed = "\u001B[31m";
            String colorReset = "\u001B[0m";
            System.out.println(colorRed + "[ERROR] " + "dadw" + colorReset);
        } catch (SQLException e) {
            throw new RuntimeException("SQL", e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
//        Connection connection = null;
//        try
//        {
//            // create a database connection
//            connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
//            Statement statement = connection.createStatement();
//            statement.setQueryTimeout(30);  // set timeout to 30 sec.
//
////            statement.executeUpdate("drop table if exists person");
//            statement.executeUpdate("create table if not exists person (id integer, name string)");
//            statement.executeUpdate("insert into person values(1, 'leo')");
//            statement.executeUpdate("insert into person values(2, 'yui3')");
//            ResultSet rs = statement.executeQuery("select * from person");
//            while(rs.next())
//            {
//                // read the result set
//                System.out.println("name = " + rs.getString("name"));
//                System.out.println("id = " + rs.getInt("id"));
//            }
//        }
//        catch(SQLException e)
//        {
//            // if the error message is "out of memory",
//            // it probably means no database file is found
//            System.err.println(e.getMessage());
//        }
//        finally
//        {
//            try
//            {
//                if(connection != null)
//                    connection.close();
//            }
//            catch(SQLException e)
//            {
//                // connection close failed.
//                System.err.println(e.getMessage());
//            }
//        }
//    }
}
