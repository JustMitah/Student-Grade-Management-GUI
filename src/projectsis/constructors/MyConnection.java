/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectsis.constructors;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class MyConnection {
public Connection connection;
 
    public Connection getConnection(){
    try{
    String connectionURL = "jdbc:mysql://127.0.0.1:3306/project";
    Class.forName("com.mysql.jdbc.Driver");
    connection = DriverManager.getConnection(connectionURL,"root","Onepiece2020");
    } catch (ClassNotFoundException | SQLException e){
    }
    return connection;
}}
