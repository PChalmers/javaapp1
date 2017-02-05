/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Main
 */
public class JavaApplication1 {

    static Connection conn = null;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        connect();

        getData();

    }

    private static void connect() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/world?" + "user=root&password=");
            // Do something with the Connection
            System.out.println("DB catelog name: " + conn.getCatalog());

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        System.out.println("Connection done ");
    }

    private static void getData() {
        // assume that conn is an already created JDBC connection (see previous examples)
        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM city");

            while (rs.next()) {
                System.out.println("->" + rs.getString("Name") + " ->" + rs.getString("District"));

                // Do whatever you want to do with these 2 values
            }

            // or alternatively, if you don't know ahead of time that
            // the query will be a SELECT...
//            if (stmt.execute("SELECT * FROM configdata")) {
//                rs = stmt.getResultSet();
//            }
            // Now do something with the ResultSet ....
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        } finally {
            // it is a good idea to release
            // resources in a finally{} block
            // in reverse-order of their creation
            // if they are no-longer needed

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
