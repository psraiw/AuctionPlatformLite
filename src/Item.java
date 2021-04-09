import java.sql.*;
import java.util.*;
import java.io.*;

public class Item{
    final int itemId ,sellerUid, durationInHrs/* will consider in secs*/, minBid;
    int maxBidderUid, bidStatus;//status : active bid = 1 , sold = 2, cancelled = -1
    String itemName, itemDesc;
    double currentMaxBid;

    public Item(int itemId, int sellerUid, int durationInHrs, int minBid, int maxBidderUid, int bidStatus, String itemName, String itemDesc, double currentMaxBid) {
        this.itemId = itemId;
        this.sellerUid = sellerUid;
        this.durationInHrs = durationInHrs;
        this.minBid = minBid;
        this.maxBidderUid = maxBidderUid;
        this.bidStatus = 1;//new bid active ho chuki hai hello
        this.itemName = itemName;
        this.itemDesc = itemDesc;
        this.currentMaxBid = currentMaxBid;
    }

    //update method
    public void addItem(Connection conn){
        //auction started as soon as item added i.e. bid status = 1(active);
        try {
            Statement stat = conn.createStatement();
            String query = "INSERT INTO items values("+"'"+itemName+"','"+String.valueOf(itemId)+"',"+itemDesc+"','"+String.valueOf(sellerUid)+"','"+String.valueOf(minBid)+"','"+ String.valueOf(durationInHrs)+"','"+String.valueOf(currentMaxBid)+"','"+String.valueOf(maxBidderUid)+"','"+String.valueOf(bidStatus)+"')";
            ResultSet rs1 = stat.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    private int generateItemID() {
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

    //update method
    public void deleteItem(Connection conn){
        Statement stat=null;
        ResultSet rs1=null;
        try {
            Statement stat = conn.createStatement();
            String query = "DELETE FROM items where itemId='" + String.valueOf(itemId) + "'";
            ResultSet rs1 = stat.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }  
        finally{
            try {
                if(stat!=null){
                    stat.close();
                }if(rs1!=null){
                    rs1.close();
                }
            } catch (Exception e) {
                //TODO: handle exception
            }
        }
    }

    //
    public Item getItemforUser(Connection conn){
        

    }
}


class Driver{
    public static void main(String[] args) throws Exception {

        ArrayList<Item> auctionlist = new ArrayList<>();
        ArrayList<Users> Userlist = new ArrayList<>();

        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/auctiondb";
            String username = "root";
            String password = "Mysql@123";
            conn = DriverManager.getConnection(url, username, password);

            Statement stat = conn.createStatement();
            ResultSet rs1 = stat.executeQuery("Select * from Person");

        }catch(SQLException e){
            e.printStackTrace();
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            // try{
            //     if(conn!=null){
            //         conn.close();
            //     }if(stat!=null){
            //         stat.close();
            //     }if(rs1!=null){
            //         rs1.close();
            //     }
            // }catch(SQLException e){
            //     e.printStackTrace();
            // }
            conn.close();
            stat.close();
            rs1.close();
        }
    }   
}
