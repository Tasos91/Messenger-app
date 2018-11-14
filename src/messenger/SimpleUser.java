package messenger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import static messenger.File.createFile;
import static messenger.db_Accessibility.closeConnection;
import static messenger.db_Accessibility.conn;
import static messenger.db_Accessibility.openConnection;


public class SimpleUser {
    private static int readen=0;
    private static ResultSet rs;
    private static PreparedStatement mst;
    
    
    public static String chooseUser(){
        System.out.println("Send to:");//pairnei to onoma tou xrhsth pou theloume na steiloume message
        String rc;
        Scanner sc = new Scanner(System.in);
        rc=sc.nextLine();
     return rc;
    }
    
    public static boolean checkUser(String o){ //psaxnei na dei an uparxei
        boolean i=false;
        try{
         conn = openConnection();
         String sql = "select priority,username from users";
         mst=conn.prepareStatement(sql);
         rs=mst.executeQuery();
        while (rs.next()){
            if(rs.getString(2).equals(o)){
                i=true;
            }
        }
         mst.close();
            }
        catch(SQLException e){
                System.out.println(e);
            }
        
        return i;
    }
    
    public static String createMessage() throws FileNotFoundException, UnsupportedEncodingException, IOException{ //stelnei minima
       String msg=null;
      String rec=chooseUser();
      String k = null;
      try{
         conn = openConnection();
         while(checkUser(rec)==false){
             System.out.println("Give a name that exist in database:");
             rec=chooseUser();
             if(checkUser(rec)==true){
                 break;
             }
         }
         Scanner sc = new Scanner(System.in);
         System.out.println("Write a message: ");
         msg=sc.nextLine();
         while(msg.length()>250){
             System.out.println("Give a text that the number of letters are less or equal to 250!");
             msg=sc.nextLine();
             if(msg.length()<=250){
                 break;
             }
         }
         Date date = new Date();
         String dt=date.toString();
         String sql = "Insert INTO messages (id_reciever, id_sender, date, readen, txt) values (?, ?, ?, ?, ?)";
         LoginForm lg = new LoginForm();
         String userLogin=lg.getForm_username();
         mst=conn.prepareStatement(sql);
         mst.setString(1, rec);
         mst.setString(2,userLogin);  
         mst.setString(3,dt);
         mst.setInt(4,readen);
         mst.setString(5,msg);
         mst.executeUpdate();
         mst.close();
         
         if(readen==0){
             k="unread";
         }
         else{
             k="readen";
         }
         createFile(rec,userLogin,dt,k,msg);
         
         }
      catch(SQLException e){
         System.out.println(e);
         }
      conn=closeConnection(); 
      return msg; 
    }
    
    public static void readRecievedMessage(){
        try{
        change_Unread_To_Read_Messages();
        conn = openConnection();
        String sql="select txt,id_sender,date from messages where id_reciever=? ";
        mst=conn.prepareStatement(sql);
        mst.setString(1, LoginForm.getForm_username());
        rs=mst.executeQuery();
        System.out.println("Eiserxomena:");
        while (rs.next()){
            System.out.println("On "+rs.getString(3)+" "+rs.getString(2)+" said you:  "+rs.getString(1));
            }
        }catch(SQLException e){
                        System.out.println(e);
                }
        conn=closeConnection(); 
    }
    public void readSentMessage(){
        try{
        conn = openConnection();
        String sql="select id, id_reciever, id_sender, date, txt from messages where id_sender=? ";
        mst=conn.prepareStatement(sql);
        mst.setString(1, LoginForm.getForm_username());
        rs=mst.executeQuery();
        System.out.println("E3erxomena:");
        while (rs.next()){
            System.out.println(rs.getInt(1)+": On "+rs.getString(4)+" "+rs.getString(3)+" said to "+rs.getString(2)+": "+rs.getString(5));
            }
        }catch(SQLException e){
                        System.out.println(e);
                }
        conn=closeConnection(); 
    }
    
    
    
    
    public  void updateMessage(){
        try{
         conn = openConnection();
         SimpleUser sp = new SimpleUser();
         sp.readSentMessage();
         Scanner sc = new Scanner(System.in);
         System.out.println("Choose id of a sent message that you want to edit: ");
         String id2=sc.nextLine();
         int id = Integer.parseInt(id2);
         
         System.out.println("Edit your message: ");
         String msg=sc.nextLine();
         conn = openConnection();
         String sql="update messages SET txt =? WHERE id_sender = ? and id=? ";
         mst=conn.prepareStatement(sql);
         mst.setString(1,msg );
         mst.setString(2,LoginForm.getForm_username());
         mst.setInt(3,id);
         mst.executeUpdate();
         System.out.println("Your sent message is updated");
            
    }catch(SQLException e){
                        System.out.println(e);
                }
        conn=closeConnection(); 
    }
    
    public static void showMessageForDelete(){
        try{
        conn = openConnection();
        String sql="select id,id_sender,id_reciever,date,txt from messages where id_sender=? or id_reciever=? ";
        mst=conn.prepareStatement(sql);
        mst.setString(1, LoginForm.getForm_username());
        mst.setString(2, LoginForm.getForm_username());
        rs=mst.executeQuery();
        while (rs.next()){
            System.out.println(rs.getString(1)+". "+rs.getString(4)+" "+rs.getString(2)+"  said on "+rs.getString(3)+": "+rs.getString(4));
            }
        }catch(SQLException e){
                        System.out.println(e);
                }
        conn=closeConnection(); 
        
    }
        
        
        public static void deleteMessage(){
        try{
        showMessageForDelete();
        conn = openConnection();
        System.out.println("Choose the number of the following messages that you want to delete: ");
        Scanner sc = new Scanner(System.in);
        String id1=sc.nextLine();
        int id = Integer.parseInt(id1);
         
        String sql1="delete from messages where id=?";
        mst=conn.prepareStatement(sql1);
        mst.setInt(1, id);
        mst.executeUpdate();
        }catch(SQLException e){
                        System.out.println(e);
                }
        conn=closeConnection(); 
    }
    
    public String chooseAction() {
        showReadUnreadMessagesAndChange();//methodos--> h opoia tha sou leei posa adiavasta kai posa diavasmena mhnumata exeis!!!
        System.out.println("Type one of the following actions: recieved,sent,create,update,delete");
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
                Logger.getLogger(SimpleUser.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println(ex);
            } catch (IOException ex) {
                Logger.getLogger(SimpleUser.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println(ex);
            }
        }
                            break;
                case "update":
                    updateMessage();
                    
                            break;
                case "delete": 
                    deleteMessage();
                   
                            break;
                            
              }
           return i;
    }
    public  void outOrInOfTheMenu(){ //na fugoume h na meinoume sto menu
        while(true){
            String i="a";
            SimpleUser sp=new SimpleUser();
            sp.chooseAction();
            System.out.println("For exit press b and then enter. If you want to go to the basic menu press any other button and then enter...");
            Scanner sc = new Scanner(System.in);
            i=sc.nextLine();
            if(i.equals("b")){
                break;
            }
        }
    
    }
    
    public static void showReadUnreadMessagesAndChange(){
        try{
            conn = openConnection();
            String sql = "select  readen from messages where id_reciever=?";
            mst=conn.prepareStatement(sql);
            mst.setString(1,LoginForm.getForm_username());
            rs=mst.executeQuery();
            int count1=0;
            int count2=0;
            while(rs.next()){
                if(rs.getString(1).equals("0")){
                    count1++;
                }
                if(rs.getString(1).equals("1")){
                    count2++;
                }
            }
            System.out.println("You have "+count1+" unread messages");
            System.out.println("You have "+count2+" readen messages");
        }catch(SQLException e){
                        System.out.println(e);
                }
         conn=closeConnection();
    }
    
    public static void change_Unread_To_Read_Messages(){ 
        try{
            conn = openConnection();
            String sql = "update messages SET readen =? WHERE id_reciever = ?";
            mst=conn.prepareStatement(sql);
            int i=1;
            mst.setInt(1,i);
            mst.setString(2,LoginForm.getForm_username());
            mst.executeUpdate();
        }catch(SQLException e){
                        System.out.println(e);
                }
         conn=closeConnection();
    }
    
}
