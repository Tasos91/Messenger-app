package messenger;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import static messenger.SimpleUser.createMessage;
import static messenger.SimpleUser.deleteMessage;
import static messenger.SimpleUser.readRecievedMessage;
import static messenger.db_Accessibility.closeConnection;
import static messenger.db_Accessibility.conn;
import static messenger.db_Accessibility.openConnection;

public class Admin extends SimpleUser{
    
    private static ResultSet rs;
    private static PreparedStatement mst;
    
    public static void readOtherMessage(){
    try{
        conn = openConnection();
        String sql="select * from messages";
        mst=conn.prepareStatement(sql);
        
        rs=mst.executeQuery();
        System.out.println("All messages:");
        while (rs.next()){
            System.out.println("On "+rs.getString(4)+" "+rs.getString(3)+" send to "+rs.getString(2)+":  "+rs.getString(6));
            }
        }catch(SQLException e){
                        System.out.println(e);
                }
        conn=closeConnection(); 
    }
    
    public  void outOrInOfTheMenu(){
        while(true){
            String i="a";
            Admin admin=new Admin();
            admin.chooseAction();
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
        System.out.println("Type action: recieved,sent,create,update,delete,readall");
        Scanner sc = new Scanner(System.in);
        String i=sc.next();
            switch(i){
                case "recieved":
                    readRecievedMessage();
                            break;
                case "sent":
                    readSentMessage();
                            break;            
                case "create":
        {
            try {
                createMessage();
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
            }
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
                }
            
       return i;    
    } 
    
    }
    
    

