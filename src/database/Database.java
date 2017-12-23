/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

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
 * @author tatewtaylor
 */
public class Database {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ClassNotFoundException {

        Connection connObj = null;
        Statement stateObj = null;
        ResultSet resultObj = null;
        ResultSetMetaData meta = null;
        String query = "Select * from user";
        try {
            //use your own username and login for the second and third parameters..I'll change this in the future to be dynamic
            connObj = DriverManager.getConnection("jdbc:mysql://localhost:3306/kbell?useSSL=false", "admin", "1qaz2wsx");
            stateObj = connObj.createStatement();
            resultObj = stateObj.executeQuery(query);
            meta = resultObj.getMetaData();
            int columns= meta.getColumnCount();
            System.out.println(columns);
            for (int i=1; i<=columns;i++){
                 System.out.print(meta.getColumnName(i)+"\t");
            }
            System.out.println();
            while(resultObj.next()){
                for (int i=1; i<=columns;i++){
                     System.out.print(resultObj.getObject(i));
                }
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        
      
    }
    
}
