import java.sql.*;
import java.io.*;
import java.util.*;

public class Authentication {
    // Required of login/signup
    private final String tableName = "auth";
    private Statement statement = null;

    // Saving which user state
    boolean isLoggedIn = false;
    String userName = null;
    int userID;

    public Authentication(Connection connection) {
        try {
            this.statement = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int signUp(String name, String password) {
        int newUserID = generateUserID();
        try {
            statement.executeUpdate(
                    "INSERT INTO " + tableName + " values(" + newUserID + ",'" + name + "','" + password + "');");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newUserID;
    }

    private int generateUserID() {
        int newID = 0;
        try {
            ResultSet myResultSet = statement.executeQuery("Select * from " + tableName);
            while (myResultSet.next()) {
                newID = myResultSet.getInt("userID");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return newID + 1;
    }

    public boolean login(int userID, String password) {
        try {
            ResultSet myResultSet = statement.executeQuery(
                    "Select * from  " + tableName + " where userID=" + userID + " and pass='" + password + "';");
            boolean result = myResultSet.next();
            if (result) {
                this.userID = myResultSet.getInt("userID");
                this.userName = myResultSet.getString("userName");
                isLoggedIn = true;
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void close() {
        try {
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}