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


public class UltraSlimAdmin extends SimpleUser{
    
    private static ResultSet rs;
    private static PreparedStatement mst;
    
    @Override
    public  void outOrInOfTheMenu(){
        while(true){
            String i=null;
            UltraSlimAdmin usa=new UltraSlimAdmin();
            usa.chooseAction();
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
        System.out.println("Type action: recieved,sent,create,update,delete,readall,updateother");
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
                Logger.getLogger(UltraSlimAdmin.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(UltraSlimAdmin.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
                            break;
                case "update":
                    SimpleUser sp = new SimpleUser();
                    sp.updateMessage();
                            break;
                case "delete": 
                    deleteMessage();
                            break;
                            
                case "readall": //extra periptwseis
                    readOtherMessage();
                            break;
                case "updateother":
                    updateMessage();
                          
                }
            
       return i;    
    }
    
    public String chooseMessage(){
        String i=null;
        try{
        conn = openConnection();
        String sql="select * from messages";
        mst=conn.prepareStatement(sql);
        
        rs=mst.executeQuery();
        System.out.println("All messages:");
        while (rs.next()){
            System.out.println("id:"+rs.getString(1)+"  "+"On "+rs.getString(4)+" "+rs.getString(3)+" send to "+rs.getString(2)+":  "+rs.getString(6));
            }
        System.out.println("Choose of the above messages,the 'id' of the message you want to update:");
        Scanner sc = new Scanner(System.in);
        i=sc.nextLine();
        }catch(SQLException e){
                        System.out.println(e);
                }
        conn=closeConnection();
        return i;
    }
    
    
    
    @Override
    public  void updateMessage(){
        try{
         Scanner sc = new Scanner(System.in);
         String id = chooseMessage();
         int idn = Integer.parseInt(id);        
         System.out.println("Edit your message: ");         
         String msg=sc.nextLine();
         conn = openConnection();
         String sql="update messages SET txt =? WHERE id = ?";
         mst=conn.prepareStatement(sql);
         mst.setString(1,msg );
         mst.setInt(2,idn);
         mst.executeUpdate();
         System.out.println("The message is updated");
        
        }catch(SQLException e){
                        System.out.println(e);
                }
        conn=closeConnection(); 
    }
}
