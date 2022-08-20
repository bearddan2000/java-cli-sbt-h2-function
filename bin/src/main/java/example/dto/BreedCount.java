package example.dto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BreedCount {

  public static int getCount(int id)
  {
    int result = 0;

    String databaseName = "test";
    String opptions = ";DB_CLOSE_DELAY=-1";
    opptions += ";INIT=CREATE SCHEMA IF NOT EXISTS " + databaseName + "\\";
    opptions += ";SET SCHEMA " + databaseName;

    Connection conn = null;
    Statement stmt = null;

    try {
      Class.forName("org.h2.Driver");
      //Getting the Connection object
      String URL = "jdbc:h2:mem:"+databaseName + opptions;
      conn = DriverManager.getConnection(URL);

      //Creating the Statement object
      stmt = conn.createStatement();

      //Executing the query
      String query = "SELECT SUM(1) FROM dog WHERE breedId = " + id;

      ResultSet rs = stmt.executeQuery(query);

      while(rs.next()) {
         result = rs.getInt(1);
      }

      stmt.close();
      conn.close();
   } catch(SQLException se) {
      //Handle errors for JDBC
      se.printStackTrace();
   } catch(Exception e) {
      //Handle errors for Class.forName
      e.printStackTrace();
   } finally {
      //finally block used to close resources
      try{
         if(stmt!=null) stmt.close();
      } catch(SQLException se2) {
      } // nothing we can do
      try {
         if(conn!=null) conn.close();
      } catch(SQLException se){
         se.printStackTrace();
      } //end finally try
   } //end try
    return result;
  }
}
