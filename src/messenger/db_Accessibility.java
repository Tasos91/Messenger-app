package messenger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;



public class db_Accessibility {
    private static PreparedStatement mst;
    private static ResultSet rs;
    public static Connection conn;
    private static boolean k;
    
    public static Connection openConnection(){
         try{
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?autoReconnect=true&useSSL=false","tasos","tasos");
            
         }
         catch(ClassNotFoundException | SQLException e){
                        System.out.println(e);
                }
         return conn;
    }
    
    
    public static Connection closeConnection(){
   
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(db_Accessibility.class.getName()).log(Level.SEVERE, null, ex);
        }
    return conn;
    }
    
    
    public static String checkCredentials(){
        String pri=null;
        try{
            conn = openConnection();
            
            
            String sql="select * from users where username=? and password=?";
            mst=conn.prepareStatement(sql);
            mst.setString(1, LoginForm.getForm_username());
            mst.setString(2, LoginForm.getForm_password());
            rs=mst.executeQuery();
            k=true;
            if (rs.next()){
                pri=rs.getString(1);
            }
            else{
                k=false;
            }
            rs.close();
            mst.close();
        }
        catch(SQLException e){
                        System.out.println(e);
                }
        conn=closeConnection(); 
        
       return pri;
    }
}

          
     
     
    
     
