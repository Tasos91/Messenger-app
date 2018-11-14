package messenger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;


public class File {
    
    
    public static void createFile(String rec, String usernameLogin,String dt, String readen,String msg) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new BufferedWriter(new FileWriter(usernameLogin, true)));
            writer.println("\n"+"On "+dt+" "+usernameLogin+" send to "+rec+":  "+msg+"\n");
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(File.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
        } finally {
            writer.close();
        }
          
    }
}
