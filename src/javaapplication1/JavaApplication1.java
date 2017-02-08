/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Main
 */
public class JavaApplication1 {

    private Connection conn = null;
    private final String CONNECTIONSTRING = "jdbc:mysql://localhost/world?" + "user=root&password=";
    private final String queryDB = "SELECT * FROM city WHERE Population > 1000000";

    public JavaApplication1() {
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        JavaApplication1 obj = new JavaApplication1();
        obj.startstuff();
    }

    private void startstuff() {
        connectToDB();

        getData();
    }

    private void connectToDB() {
        try {
            conn = DriverManager.getConnection(CONNECTIONSTRING);
            // Do something with the Connection
            System.out.println("DB catelog name: " + conn.getCatalog());
            
            testDBExists();
             

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        System.out.println("Connection done ");
    }
    
    private void testDBExists() {

        try {
            // Connection connection = <your java.sql.Connection>
            ResultSet resultSet = conn.getMetaData().getCatalogs();
            
            //iterate each catalog in the ResultSet
            while (resultSet.next()) {
                // Get the database name, which is at position 1
                String databaseName = resultSet.getString(1);
                System.out.println("DB name -> " + databaseName);
            }
            resultSet.close();
        } catch (SQLException ex) {
            Logger.getLogger(JavaApplication1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void getData() {
        // assume that conn is an already created JDBC connection (see previous examples)
        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(queryDB);

            while (rs.next()) {

                //from result set give metadata
                ResultSetMetaData rsmd = rs.getMetaData();

                //columns count from metadata object
                int numOfCols = rsmd.getColumnCount();

                System.out.println(rs.getRow() + ": ->" + rs.getString("Name") + " ->" + rs.getString("District"));

                // Do whatever you want to do with these 2 values
            }

            // Now do something with the ResultSet ....
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        } finally {
            // it is a good idea to release resources in a finally{} block
            // in reverse-order of their creation if they are no-longer needed
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException sqlEx) {
                } // ignore
                rs = null;
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) {
                } // ignore
                stmt = null;
            }
        }
    }



}
