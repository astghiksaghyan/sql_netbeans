
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import java.sql.Connection;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author student
 */




public class DatabaseManagerMongo extends DatabaseManager {
    
    
    
    private MongoClient mongoClient;
    private DB db;
    DBCollection dBCollection;
    

    DatabaseManagerMongo() {
        super();
    }
    
    
    @Override
    public void createConnection() {
        
        try {
            
            mongoClient= new MongoClient("localhost",27017);
            db = mongoClient.getDB("users");
            System.out.println("OK");
       } catch (Exception e)
       {
           System.out.println("Exeption");
       }

   
    }

    
    
    
    
    @Override
    public void closeConnection() {
        mongoClient.close();
         
    }

    @Override
    public int register(String name, String username, String password) {
        
             
        createConnection();
        int result = 0;
        dBCollection = db.getCollection("users");

        DBObject person = new BasicDBObject()
                           .append("name", name)
                            .append("username", username)
                             .append("password", password);

        dBCollection.insert(person);
        
        closeConnection();

     
        

        return result;

    }
    
}
