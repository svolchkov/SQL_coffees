/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sql_coffees;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import sql_coffees.SQLgetColumns;
import static sql_coffees.SQL_coffees.FULL_NAME;
import static sql_coffees.SQL_coffees.PASS;
import static sql_coffees.SQL_coffees.USER;

/**
 *
 * @author sergeyv
 */
public class SQLInsert {
    
     public static void insertData(String dbName,String dataFile) {
         
         SQLgetColumns cols = new SQLgetColumns();
         
         Connection conn = null;
         Statement stmt = null;
         try{
             Class.forName("com.mysql.jdbc.Driver");

              //STEP 3: Open a connection
              System.out.println("Inserting data into " + dbName +"...");
              conn = DriverManager.getConnection(FULL_NAME, USER, PASS);

              List<String> data = null;
              try{
                  data = ReadFromFile.getData(dataFile);
                  System.out.println("Successfully read data from " + dataFile +"...");
              }catch(IOException ie){
                  ie.printStackTrace();
              }
             

             Calendar calendar = Calendar.getInstance();
               // java.sql.Date startDate = new java.sql.Date(calendar.getTime().getTime());

             LinkedHashMap columns = new LinkedHashMap();
             cols.getColumns(dbName);
             String colNames = String.join(",",cols.columns);
             //System.out.println(colNames);
             // String valueString = new String(new char[cols.columns.size()]).replace("\0", "?,").substring(0, (cols.columns.size()-1));
             String valueString = String.join(",", Collections.nCopies(cols.columns.size(), "?"));
             int count = valueString.length() - valueString.replace("?", "").length();;
             //System.out.println(valueString + " " + cols.columns.size() + " " + count);
             // System.out.println(colNames);
          // the mysql insert statement
            String query = String.format("insert ignore into %s (%s) values (%s)",dbName,colNames,valueString);

          // create the mysql insert preparedstatement
             
             PreparedStatement preparedStmt = conn.prepareStatement(query);
             if (data != null){
                 int insertionCount = 0;
                 for (String record: data){
                     // System.out.println(record);
                     // record.replace("/", " ");
                     String[] values = record.split(",",-1);
                     System.out.println(Arrays.toString(values) + " " + values.length);
                     for (int i = 0; i <= (values.length-1); i++){
                         // String[] colType = (String[])columns.get(i);
                         // System.out.println(i);
                         String value = values[i].trim();
                         // System.out.println(i + " " + value);
                         switch(cols.colTypes.get(i)){
                             case "FLOAT": 
                                 preparedStmt.setDouble (i+1, Double.parseDouble(value));
                                 break;
                             case "INT":
                                 preparedStmt.setInt (i+1, Integer.parseInt(value));
                                 break;
                             case "VARCHAR":
                                 preparedStmt.setString(i+1, value);
                                 break;
                             case "DATE":
                                 try{
                                     SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yy");
                                     java.util.Date date = formatter.parse(value);
                                     Date sqlDate = new Date(date.getTime());
                                     preparedStmt.setDate(i+1, sqlDate);
                                 }catch (ParseException parseEx){
                                      preparedStmt.setNull(i+1, java.sql.Types.DATE);
                                 }
                                 break;
                             default:
                                 System.out.println("Unknown data type");
                                 break;
                         }
                     }
                            
                         insertionCount += preparedStmt.executeUpdate();
                     
                 }
                 System.out.println(String.format("%d records inserted, %d records ignored", 
                         insertionCount,(data.size()-insertionCount)));
                 
                 System.out.println(String.format("\nThese are the records currently in the %s data table:\n\n"
                         ,dbName));
                 
                 String selectQuery = String.format("SELECT * FROM %s;",dbName);
                 // String selectQuery = "SELECT Supplier,Name,Balance FROM SUPPLIERS;";
                 ResultSet result = null;
                 stmt = conn.createStatement();
                 result = stmt.executeQuery(selectQuery); // execute the SQL query
                 
                 System.out.println(String.join(" ", colNames));
                 
                 ResultSetMetaData rsmd = result.getMetaData();
                 int columnsNumber = rsmd.getColumnCount();
                 while (result.next()) { // loop until the end of the results
                           for(int i = 1; i <= columnsNumber; i++)
                            System.out.print(result.getString(i) + " ");
                            System.out.println();
                    }
             }
             
             
    //      preparedStmt.setString (1, "Barney");
    //      preparedStmt.setString (2, "Rubble");
    //      preparedStmt.setDate   (3, startDate);
    //      preparedStmt.setBoolean(4, false);
    //      preparedStmt.setInt    (5, 5000);
    // 
    //      // execute the preparedstatement
             

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
     }    
}
