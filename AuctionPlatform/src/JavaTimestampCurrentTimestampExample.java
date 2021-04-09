import java.sql.*;
import java.util.Calendar;
import java.sql.*;

public class JavaTimestampCurrentTimestampExample
{
  public static void main(String[] args) throws Exception
  {
      Connection conn;
    // (1) connect to the database (mysql)
    Class.forName("com.mysql.cj.jdbc.Driver");
    String url = "jdbc:mysql://localhost:3306/auctiondb";
    String username = "root";
    String password = "Mysql@123";
    conn = DriverManager.getConnection(url, username, password);

    // (2) create a java timestamp object that represents the current time (i.e., a "current timestamp")
    Calendar calendar = Calendar.getInstance();
    java.sql.Timestamp ourJavaTimestampObject = new java.sql.Timestamp(calendar.getTime().getTime());
    
    // (3) create a java timestamp insert statement
    String sqlTimestampInsertStatement = "INSERT INTO timecheck (timestamp2) VALUES (?)";
    PreparedStatement preparedStatement = conn.prepareStatement(sqlTimestampInsertStatement);
    preparedStatement.setTimestamp(1, ourJavaTimestampObject);

    // (4) execute the sql timestamp insert statement, then shut everything down
    preparedStatement.executeUpdate();
    preparedStatement.close();
    System.exit(0);
  }
}