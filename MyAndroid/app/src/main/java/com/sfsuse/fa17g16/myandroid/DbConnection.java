package com.sfsuse.fa17g16.myandroid;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Gabin on 14.01.2018.
 */

public class DbConnection {
    public String realEstate(){
        String estateInfo = "voila";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://192.168.137.190:3306/fa17g16","root","");

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM real_estates");
            ResultSet result = statement.executeQuery();
            while(result.next()){

            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return estateInfo;
    }
}
