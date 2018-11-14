package messenger;

import static messenger.db_Accessibility.checkCredentials;


public class AppMenu {
    
    public static void AppMethod() {
        
        String priority=checkCredentials();
        System.out.println("Priority of privilidges: 1.superadmin > 2.ultraAdmin > 3.ultraSlimAdmin > 4.Admin > 5.SimpleUser");
        
        switch (priority) {
            case "1":            
                System.out.println("You are the super Admin");
                SuperAdmin sa = new SuperAdmin();
                sa.outOrInOfTheMenu();
                     break;
            case "2":  ;         
                System.out.println("You are an ultra Admin");
                UltraAdmin ua = new UltraAdmin();
                ua.outOrInOfTheMenu();
                     break;
            case "3":  ;        
                System.out.println("You are an ultraSlim Admin");
                UltraSlimAdmin usa = new UltraSlimAdmin();
                usa.outOrInOfTheMenu();
                     break;
            case "4":  
                System.out.println("You are an Admin");
                Admin admin = new Admin(); 
                admin.outOrInOfTheMenu();
                     break;
            case "5":           
                System.out.println("You are a Simple User");
                SimpleUser sp = new SimpleUser();
                sp.outOrInOfTheMenu();
               
        }
        
    }

}  

