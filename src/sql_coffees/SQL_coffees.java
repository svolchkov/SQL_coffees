/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sql_coffees;

import java.io.IOException;
import java.sql.*;
import java.util.concurrent.TimeUnit;
/**
 *
 * @author sergeyv
 */
public class SQL_coffees {

    /**
     * @param args the command line arguments
     */
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   static final String DB_ADDRESS = "jdbc:mysql://localhost/";
   static final String DB_NAME = "COFFEES";
   static final String FULL_NAME = DB_ADDRESS + DB_NAME;

   //  Database credentials
   static final String USER = "root";
   static final String PASS = "";
   
   public static void main(String[] args) {
   Connection conn = null;
   Statement stmt = null;
   
   try{
        Process process = Runtime.getRuntime().exec("C:\\xampp\\mysql\\bin\\mysqld.exe");
        System.out.println("Waiting for the SQL server to start...");
        TimeUnit.SECONDS.sleep(5);
   }catch (IOException e){
       e.printStackTrace();
   }catch (InterruptedException ie){
       ie.printStackTrace();       
   }
   
   try{
      //STEP 2: Register JDBC driver
      Class.forName("com.mysql.jdbc.Driver");

      //STEP 3: Open a connection
      System.out.println("Connecting to a selected database...");
      
      conn = DriverManager.getConnection(DB_ADDRESS, USER, PASS);
      Statement s = conn.createStatement();
      int Result = s.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DB_NAME);
      try{
         if(conn!=null)
            conn.close();
      }catch(SQLException se){
         se.printStackTrace();
      };
      
      
      conn = DriverManager.getConnection(FULL_NAME, USER, PASS);
      System.out.println("Connected database successfully...");
      
      //STEP 4: Execute a query
      System.out.println("Creating tables in given database...");
      stmt = conn.createStatement();

      String sql = "";
      try{
          sql = ReadFromFile.getSQL("C:\\Users\\sergeyv\\Downloads\\Deploy Software\\suppliers.sql");
      }catch (IOException e){
          e.printStackTrace();
      }
    
     stmt.executeUpdate(sql);
      System.out.println("Created table 'suppliers' in given database...");
      
      sql = "";
      try{
          sql = ReadFromFile.getSQL("C:\\Users\\sergeyv\\Downloads\\Deploy Software\\coffees.sql");
      }catch (IOException e){
          e.printStackTrace();
      }
      
      
      
//      
//      
//      String sql = "CREATE TABLE COFFEES( " +
//              
//                   "Supplier VARCHAR(10)," +
//                    "PayTo VARCHAR(10)," +
//                    "Name VARCHAR(30)," +
//                    "OpeningBal FLOAT," +
//                    "Balance FLOAT," +
//                    "Future FLOAT," +
//                    "PurchaseYtd FLOAT," +
//                    "DefPaymentTerms VARCHAR(2)," +
//                    "PaymentSelection VARCHAR(1)," +
//                    "AutoRqFlag VARCHAR(1)," +
//                    "Type VARCHAR(4)," +
//                    "ChqOrBankdraft VARCHAR(1)," +
//                    "AccountStatus VARCHAR(1)," +
//                    "ClearingFlag VARCHAR(1)," +
//                    "Disc VARCHAR(2)," +
//                    "VolumeDiscCode VARCHAR(2)," +
//                    "DiscRate FLOAT," +
//                    "SettlementDiscAmtYtd FLOAT," +
//                    "Agebal1  FLOAT," +
//                    "Agebal2  FLOAT," +
//                    "Agebal3  FLOAT," +
//                    "Agebal4  FLOAT," +
//                    "Agebal5  FLOAT," +
//                    "Agebal6  FLOAT," +
//                    "Agebal7  FLOAT," +
//                    "Agebal8  FLOAT," +
//                    "Agebal9  FLOAT," +
//                    "Agebal10  FLOAT," +
//                    "Agebal11  FLOAT," +
//                    "Agebal12  FLOAT," +
//                    "Agebal13  FLOAT," +
//                    "DateLastPayment DATE," +
//                    "DateLastPurchase DATE," +
//                    "PurHistory1  FLOAT," +
//                    "PurHistory2  FLOAT," +
//                    "PurHistory3  FLOAT," +
//                    "PurHistory4  FLOAT," +
//                    "PurHistory5  FLOAT," +
//                    "PurHistory6  FLOAT," +
//                    "PurHistory7  FLOAT," +
//                    "PurHistory8  FLOAT," +
//                    "PurHistory9  FLOAT," +
//                    "PurHistory10  FLOAT," +
//                    "PurHistory11  FLOAT," +
//                    "PurHistory12  FLOAT," +
//                    "PurHistory13  FLOAT," +
//                    "Contact VARCHAR(20)," +
//                    "Account VARCHAR(25)," +
//                    "DateLastChanged DATE," +
//                    "Cur VARCHAR(4)," +
//                    "DateCreated DATE," +
//                    "DefPurchaseTermsDays INTEGER," +
//                    "OrderTerms VARCHAR(4)," +
//                    "Grp VARCHAR(4)," +
//                    "TaxCode VARCHAR(2)," +
//                    "UserOnlyAlpha43 VARCHAR(4)," +
//                    "UserOnlyAlpha44 VARCHAR(4)," +
//                    "Spare1 VARCHAR(1)," +
//                    "Spare2 VARCHAR(4)," +
//                   " PRIMARY KEY ( Supplier ))"; 

      stmt.executeUpdate(sql);
      System.out.println("Created table 'coffees' in given database...");
      
      SQLInsert.insertData("COFFEES.SUPPLIERS", "C:\\Users\\sergeyv\\Downloads\\Deploy Software\\suppliers.csv");
      SQLInsert.insertData("COFFEES.COFFEES", "C:\\Users\\sergeyv\\Downloads\\Deploy Software\\coffees.csv");
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
   }//end try
   System.out.println("Goodbye!");
    }
}

