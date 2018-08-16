/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication2;

/**
 *
 * @author astghik
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {

    private Connection conn = null;
    private static DatabaseManager instance = null;
    private static final boolean[] LOCK_INSTANCE = new boolean[]{};
    private static final String DRIVER_NAME     = "org.postgresql.Driver";
    private static final String JDBC_PREFIX     = "jdbc:postgresql://";
    private static final String PARAM_USER      = "user";
    private static final String PARAM_PASSWORD  = "password";
    private static final String HOST_NAME = "localhost";
    private static final String DB_NAME = "postgres";
    private static final String USER_NAME = "postgres";
    private static final String PASSWORD = "ast123";
    private static final String PORT_NUMBER = "5432";
   

    /**
     * This method is required to implement Singleton pattern for the DatabaseManager.
     */
    public static DatabaseManager getInstance()
    {
        if (instance != null) {
            return instance;
        }

        synchronized (LOCK_INSTANCE) {
            if (instance != null) {
                return instance;
            }

            instance = new DatabaseManager();
            return instance;
        }
    }

    /**
     * As DatabaseManager is Singleton, its constructor must be private.
     */
    private DatabaseManager() {
        super();
    }

    
    
    private void createConnection() {
        try {
            StringBuilder builder = new StringBuilder(JDBC_PREFIX);
            builder.append(HOST_NAME)
                   .append(":").append(PORT_NUMBER).append("/")
                   .append(DB_NAME)
                   .append("?").append(PARAM_USER + "=" + USER_NAME)
                   .append("&" + PARAM_PASSWORD + "=" + PASSWORD);

            Class.forName(DRIVER_NAME).newInstance();
            conn = DriverManager.getConnection(builder.toString());
            System.out.println("[DatabaseManager] Connection is created.");
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState:     " + ex.getSQLState());
            System.out.println("VendorError:  " + ex.getErrorCode());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Closes the connection.
     */
    public void closeConnection(){
	try { 
            conn.close(); 
            System.out.println("[DatabaseManager] Connection is closed.");
        } catch (Exception e) { /* ignored */ }
    }
    
   public int register(String name, String username, String password) {
       createConnection();
       Statement stmt = null;
        ResultSet rs = null;
        int result = 0;
        try{
            stmt=conn.createStatement();
            String query = "Select * from  users where username='" + 
                                                                username + "'";
            rs = stmt.executeQuery(query);
            if(!rs.next()){ 
                String q = "Insert into users (name, username, " +
                        "password) values ('" + name + "', '" +
                        username + "','" + password+"')";
                result = stmt.executeUpdate(q);
            }             
        } catch (Exception e) {
            System.out.println("Execution failed!");            
            e.printStackTrace();
            result = 2;
        } finally {
            try { stmt.close(); } catch (Exception e) { /* ignored */ }
            try { rs.close(); } catch (Exception e) { /* ignored */ }
        }
        closeConnection();
        return result;
   }
   
   
   
}



