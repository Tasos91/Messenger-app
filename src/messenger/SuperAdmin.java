package messenger;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import static messenger.Admin.readOtherMessage;
import static messenger.SimpleUser.createMessage;
import static messenger.SimpleUser.deleteMessage;
import static messenger.SimpleUser.readRecievedMessage;
import static messenger.db_Accessibility.closeConnection;
import static messenger.db_Accessibility.conn;
import static messenger.db_Accessibility.openConnection;


public class SuperAdmin extends UltraSlimAdmin{
    
    private static ResultSet rs;
    private static PreparedStatement mst;
    
    public  void outOrInOfTheMenu(){
        
        while(true){
            String i="a";
            SuperAdmin sup =new SuperAdmin();
            sup.chooseAction();
            System.out.println("For exit press b and then enter. If you want to go to the basic menu press any other button and then enter...");
            Scanner sc = new Scanner(System.in);
            i=sc.nextLine();
            if(i.equals("b")){
                break;
            }
        }
    
    }
    
    @Override
    public String chooseAction(){
        showReadUnreadMessagesAndChange();
        System.out.println("Type action: recieved,sent,create,update,delete,readall,updateother,deleteother,viewusers,deleteuser,createuser,updateuser");
        Scanner sc = new Scanner(System.in);
        String i=sc.next();
            switch(i){
                case "recieved":
                    readRecievedMessage();
                            break;
                case "sent":
                    SimpleUser sp1 = new SimpleUser();
                    sp1.readSentMessage();
                            break;            
                case "create":
        
                    try {
                        createMessage();
                        } catch (UnsupportedEncodingException ex) {
                            Logger.getLogger(SuperAdmin.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(SuperAdmin.class.getName()).log(Level.SEVERE, null, ex);
                        }
                            break;
                case "update":
                    
                    updateMessage();
                            break;
                case "delete": 
                    deleteMessage();
                            break;
                            
                case "readall": //extra periptwseis
                    readOtherMessage();
                            break;
                case "updateother":
                    updateMessage();
                            break;
                case "deleteother":
                    UltraAdmin ua = new UltraAdmin();
                    ua.deleteOtherMessage();
                            break;            
                
                case "viewusers": 
                    viewUsers();
                            break;  
                case "deleteuser":
                    deleteUser();
                            break;              
                case "createuser":
                    createUser();
                            break;  
                case "updateuser":
                    updateUser();
                                          
           
            }
      return i;
    }
    
    
    public static void viewUsers(){
    try{
        conn = openConnection();
        String sql="select * from users";
        mst=conn.prepareStatement(sql);
        
        rs=mst.executeQuery();
        System.out.println("All Users:");
        while (rs.next()){
            System.out.println("Priority: "+rs.getString(1)+" Username:"+rs.getString(2)+" Password:"+rs.getString(3));
            }
        }catch(SQLException e){
                        System.out.println(e);
                }
        conn=closeConnection(); 
    }
    
    public static void deleteUser(){
    try{
        String priority;
        String username;
        conn = openConnection();
        String sql="select * from users";
        mst=conn.prepareStatement(sql);
        
        rs=mst.executeQuery();
        System.out.println("All Users:");
        while (rs.next()){
            System.out.println("Priority: "+rs.getString(1)+" Username:"+rs.getString(2)+" Password:"+rs.getString(3));
            }
        System.out.println("Here you choose the user you want to delete...");
        Scanner sc = new Scanner(System.in);
        System.out.println("Write the priority of the user:");
        priority=sc.nextLine();
        System.out.println("Write the username of the user:");
        username=sc.nextLine();
        String sql1="delete from users WHERE priority = ? and username=?";
         mst=conn.prepareStatement(sql1);
         mst.setString(1,priority);
         mst.setString(2,username);
         mst.executeUpdate();
         System.out.println("The user is deleted");
         System.out.println("The users now are:");
         String sql2="select * from users";
        mst=conn.prepareStatement(sql2);
        
        rs=mst.executeQuery();
        System.out.println("All Users:");
        while (rs.next()){
            System.out.println("Priority: "+rs.getString(1)+" Username:"+rs.getString(2)+" Password:"+rs.getString(3));
            }
        }catch(SQLException e){
                        System.out.println(e);
                }
        conn=closeConnection(); 
    }
    
    public static String choosePriority(){
        System.out.println("What priority you choose for the user?");
        System.out.println("Priority: 2-->UltraAdmin, 3-->UltraSlimAdmin, 4-->Admin, 5-->SimpleUser");
        Scanner sc = new Scanner(System.in);
        String priority = sc.nextLine();
        while(priority!=null||priority!="2"|| priority!="3"|| priority!="4" || priority!="5"){
            if(priority.equals("2") || priority.equals("3")|| priority.equals("4") || priority.equals("5")){
                break;
            }
            else{
            System.out.println("Choose priority that exist!");
            priority = sc.nextLine();
            }
        }
        return priority;
    }
    
    public static void createUser(){
        try{
        conn = openConnection();
        String pri=choosePriority();
        Scanner sc = new Scanner(System.in);
        System.out.println("Write a username for the user:");
        String username = sc.nextLine();
        System.out.println("Write a password for the user:");
        String password = sc.nextLine();
        String sql = "Insert INTO users (priority, username, password) values (?, ?, ?)";
         mst=conn.prepareStatement(sql);
         mst.setString(1, pri);
         mst.setString(2,username);  
         mst.setString(3,password);
         mst.executeUpdate();
         System.out.println("User '"+username.toUpperCase()+"' has been created succesfully!!");
        }catch(SQLException e){
         System.out.println(e);
         }
        conn=closeConnection(); 
    }
    
    public static void updateUser(){
        try{
        conn = openConnection();
        String sql="select * from users ";
        mst=conn.prepareStatement(sql);
        rs=mst.executeQuery();
         System.out.println("Write the user you want to update: ");
         Scanner sc = new Scanner(System.in);
         String chooseUser=sc.nextLine();
         String pri=choosePriority();
         System.out.println("Write the username of the user: ");
         String username=sc.nextLine();
         System.out.println("Write the password you give him: ");
         String password=sc.nextLine();
         String sql1="update users SET priority =?,username=?,password=? WHERE username = ?";
         mst=conn.prepareStatement(sql1);
         mst.setString(1,pri);
         mst.setString(2,username);
         mst.setString(3,password);
         mst.setString(4,chooseUser);
         mst.executeUpdate();
         System.out.println("The user is updated");
               
            
    }catch(SQLException e){
                        System.out.println(e);
                }
        conn=closeConnection(); 
    }
}
