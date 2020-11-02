import java.util.ArrayList;

public class AdminRecords {
    // instance variables
    private ArrayList<Admin> admins = new ArrayList<Admin>();

    // method to find and return admin instance
    public Admin findAdmin(String usernm){
        // search through the array to find the correct admin
        for(int i=0; i<admins.size(); i++){
            Admin a = admins.get(i);
            if(a.getUsername().equals(usernm))
                return a;
        }
        // at this point, admin still not found
        System.out.println("Admin not found in school's records");
        return null;
    }

    public void setAdmins(ArrayList<Admin> admins){
        this.admins = admins;
    }
}
