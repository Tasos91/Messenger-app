 package messenger;

import java.util.Scanner;
import static messenger.db_Accessibility.checkCredentials;


public class LoginForm {
        private static String form_username;
        private static String form_password;

      
    public static String form(){
        while(checkCredentials()==null){
         Scanner sc = new Scanner(System.in);
         System.out.print("Username:");
         form_username=sc.nextLine();
         System.out.println("Password:");
         form_password=sc.nextLine();
            if(checkCredentials()==null){
                System.out.println("Write correct username and password");
            }
        }
    return form_username;
    }
    
   
    public static String getForm_username() {
        return form_username;
    }

    public static void setForm_username(String form_username) {
        LoginForm.form_username = form_username;
    }

    public static String getForm_password() {
        return form_password;
    }

    public static void setForm_password(String form_password) {
        LoginForm.form_password = form_password;
    }
}
