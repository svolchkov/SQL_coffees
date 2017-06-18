/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sql_coffees;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import static sql_coffees.SQL_coffees.FULL_NAME;
import static sql_coffees.SQL_coffees.PASS;
import static sql_coffees.SQL_coffees.USER;

/**
 *
 * @author sergeyv
 */
public class SQLgetColumns {
       static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   String dbName;
   List<String> columns = new ArrayList<>();
   List<String> colTypes = new ArrayList<>();
   //static final String DB_URL = "jdbc:mysql://localhost/stock";

   //  Database credentials
   //static final String USER = "root";
   //static final String PASS = "";
   
   
   
   public void getColumns(String dbn) {
       dbName = dbn;
       Connection conn = null;
       Statement stmt = null;
       
       //LinkedHashMap columnTypes = new LinkedHashMap();
       try{
          //STEP 2: Register JDBC driver
          Class.forName("com.mysql.jdbc.Driver");

          //STEP 3: Open a connection
          System.out.println("Connecting to a selected database...");
          conn = DriverManager.getConnection(FULL_NAME, USER, PASS);
          ResultSet rsColumns = null;
          DatabaseMetaData meta = conn.getMetaData();
          rsColumns = meta.getColumns(null, null, dbName, null);
          while (rsColumns.next()) {
              columns.add(rsColumns.getString("COLUMN_NAME"));
              colTypes.add(rsColumns.getString("TYPE_NAME"));
          }
       }catch(SQLException se){
          //Handle errors for JDBC
          se.printStackTrace();
       }catch(Exception e){
          //Handle errors for Class.forName
          e.printStackTrace();
       }finally{
          //finally block used to close resources
          try{
             if(stmt!=null)
                conn.close();
          }catch(SQLException se){
          }// do nothing
          try{
             if(conn!=null)
                conn.close();
          }catch(SQLException se){
             se.printStackTrace();
          }//end finally try
       }
       //return (columnTypes);
   }//end try
}
