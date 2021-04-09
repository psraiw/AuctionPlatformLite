import java.util.*;
import java.io.*;
import java.sql.*;

public class Users{
    final int uId;
    final String password;
    final String userName;
    

    Users(int uId, String password, String userName){
    this.uId = uId;
    this.password = password;
    this.userName = userName;
    }
 
    public void addUser(Connection conn){
        Statement stat=null;
        ResultSet rs1 = null;
        
        try {
            Statement stat = conn.createStatement();
            String query = "INSERT INTO users values('"+ String.valueOf(uId)+"','"+ password +"','"+userName+ "')";
            ResultSet rs1 = stat.executeUpdate(query);
        }catch(SQLException e){
            e.printStackTrace();
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            try {
                if (rs1 != null) {
                    rs1.close();
                }
                if (stat != null) {
                    stat.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println("Exception occured while releasing resources");
            }
            
        }
     
    }     

    public void deleteUser(Connection conn,int uid){
        Statement stat = conn.createStatement();
        String query = "delete from users where Uid =" + "'"+String.valueof(uid)+ "'";
        try {
            ResultSet rs1 = stat.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updatePassword(Connection conn,int uid, String pass){
        Statement stat = conn.createStatement();
        String query = "update users set password =" + "'"+pass+ "'" + "where Uid = '" + String.valueOf(uId) + "'";
        try {
            ResultSet rs1 = stat.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}