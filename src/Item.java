import java.sql.*;
import java.util.*;
import java.io.*;

public class Item{
    final int sellerUid, durationInHrs/* will consider in secs*/, minBid;
    int maxBidderUid, bidStatus, itemId;//status : active bid = 1 , sold = 2, cancelled = -1
    String itemName, itemDesc;
    double currentMaxBid;

    public Item(int itemId, int sellerUid, int durationInHrs, int minBid, int maxBidderUid, int bidStatus, String itemName, String itemDesc, double currentMaxBid, Connection conn) {
        this.itemId = itemId;
        this.sellerUid = sellerUid;
        this.durationInHrs = durationInHrs;
        this.minBid = minBid;
        this.maxBidderUid = maxBidderUid;
        this.bidStatus = 1;//new bid active
        this.itemName = itemName;
        this.itemDesc = itemDesc;
        this.currentMaxBid = minBid;

        addItem(conn);
    }

    //update method
    public void addItem(Connection conn){
        this.itemId = generateItemID(conn);
        PreparedStatement preparedStatement = null;

        Statement stat= null;
        Calendar calendar = Calendar.getInstance();
        java.sql.Timestamp ourJavaTimestampObject = new java.sql.Timestamp(calendar.getTime().getTime());
        //auction started as soon as item added i.e. bid status = 1(active);
        try {
            stat = conn.createStatement();
            String query = "INSERT INTO items(itemName, itemId, itemDesc, sellerUid, minBid, durationInHrs, currentMaxBid, maxBidderUid, bidStatus) values("+"'"+itemName+"','"+String.valueOf(itemId)+"',"+itemDesc+"','"+String.valueOf(sellerUid)+"','"+String.valueOf(minBid)+"','"+ String.valueOf(durationInHrs)+"','"+String.valueOf(currentMaxBid)+"','"+String.valueOf(maxBidderUid)+"','"+String.valueOf(bidStatus)+"')";
            stat.executeUpdate(query);
        
            String sqlTimestampInsertStatement = "INSERT INTO items (timestamp1) VALUES (?)";
            preparedStatement = conn.prepareStatement(sqlTimestampInsertStatement);
            preparedStatement.setTimestamp(1, ourJavaTimestampObject);

            // (4) execute the sql timestamp insert statement, then shut everything down
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try{
                if(stat!=null){
                    stat.close();}/* 
                if(preparedStatement != null){
                    preparedStatement.close();} */
            }
            catch(SQLException e){
                e.printStackTrace();
            }
        }
        
    }

    private int generateItemID(Connection conn) {

        Statement stat = null;
        String tablename = "items";
        int newID = 0;
        try {
            stat = conn.createStatement();
            ResultSet myResultSet = stat.executeQuery("Select * from " + tablename);
            while (myResultSet.next()) {
                newID = myResultSet.getInt("itemId");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            try{
            if(stat!=null){
            stat.close();}}
            catch(Exception e){
                e.printStackTrace();
            }
        }
        return newID + 1;
    }

    //update method
    public void deleteItem(Connection conn){
        Statement stat=null;
        ResultSet rs1=null;
        try {
            stat = conn.createStatement();
            String query = "DELETE FROM items where itemId='" + String.valueOf(itemId) + "'";
             stat.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }  
        finally{
            try {
                if(stat!=null){
                    stat.close();
                }
                
            } catch (Exception e) {
                //TODO: handle exception
            }
        }
    }

    //bid
    public void bid(Connection conn, double presentbid, int bidderid){
        Calendar calendar = Calendar.getInstance();
        Statement stat = null;
        java.sql.Timestamp ourJavaTimestampObject = new java.sql.Timestamp(calendar.getTime().getTime());
        //auction started as soon as item added i.e. bid status = 1(active);
        PreparedStatement preparedStatement = null;
        try {

             stat = conn.createStatement();
            String query0 = "SELECT * from items where itemId='" + this.itemId+"'";
            ResultSet rs0 = stat.executeQuery(query0);
            if(ourJavaTimestampObject.compareTo(rs0.getTimestamp("timestamp1")) > 0 && presentbid > rs0.getDouble("currentMaxBid") && (rs0.getInt("bidStatus") == 1) ){

                String query = "UPDATE items SET currentMaxBid ='"+presentbid+"',maxBidderUid='"+bidderid+"'";
                stat.executeUpdate(query);
            
                String sqlTimestampInsertStatement = "INSERT INTO items (timestamp1) VALUES (?)";
                preparedStatement = conn.prepareStatement(sqlTimestampInsertStatement);
                preparedStatement.setTimestamp(1, ourJavaTimestampObject);

                // (4) execute the sql timestamp insert statement, then shut everything down
                preparedStatement.executeUpdate();

                this.currentMaxBid = presentbid;
                this.maxBidderUid = bidderid;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            try{
                if(preparedStatement !=null)
                    preparedStatement.close();
                if(stat !=null)
                    stat.close();
            }
            catch(SQLException e){
                e.printStackTrace();
            }
        }
    }
}


class Driver{
    public static void main(String[] args) throws Exception {
/* 
        ArrayList<Item> auctionlist = new ArrayList<>();
        ArrayList<Users> Userlist = new ArrayList<>(); */

        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/auctiondb";
            String username = "root";
            String password = "Mysql@123";
            conn = DriverManager.getConnection(url, username, password);

            Authentication auth = new Authentication(conn);
            auth.signUp("golu1", "golu1");
            System.out.println(auth.login(101, "golu1"));
            Users u1 = new Users(101, "golu1", "golu1");
            
            Item i1 = new Item(1, 100, 1, 100, 0, 1, "bicycle", "good condition", 100,conn);
            i1.bid(conn, 200, 101);

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
        }
   }   
}
