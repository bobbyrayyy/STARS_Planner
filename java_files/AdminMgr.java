import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

// control class that handles all logic related to admins
public class AdminMgr {
    // instance variables
    private ArrayList<Admin> admins = new ArrayList<Admin>();

    // constructor
    public AdminMgr() throws FileNotFoundException {
        // whenever a new admin manager is created, instantiate all the admins in our database
        this.instantiateAdmins();
    }

    // this method reads in the admin.txt file, and stores all the admins' records
    // in the admins array
    public void instantiateAdmins() throws FileNotFoundException {
        // scanner to read each line in txt file
        Scanner scStream = new Scanner(new File("admin.txt"));
        String inputLine;

        while(scStream.hasNext()){
            inputLine = scStream.nextLine();
            // splitting each input line by commas, and storing all the items in a list
            ArrayList<String> inputList = new ArrayList<>(Arrays.asList(inputLine.split(",")));
            // getting all the info by indexing into arraylist
            String username = inputList.get(0);
            String hashedPW = inputList.get(1);
            String name = inputList.get(2);
            // creating a new admin object and adding it into the arraylist
            admins.add(new Admin(username, hashedPW, name));
        }
    }

    // method to authenticate admin's login
    public boolean authenticate(String username, String password){
        boolean correctPW = false;
        Admin currentUser = null;
        PasswordAuthentication p = new PasswordAuthentication();

        // search through arraylist for the admin with this username
        for(int i=0; i<admins.size(); i++){
            Admin currentAdmin = admins.get(i);
            // if the usernames match
            if(username.equals(currentAdmin.getUsername())){
                currentUser = currentAdmin;
                // compare the corresponding passwords
                correctPW = p.authenticate(password.toCharArray(), currentAdmin.getPassword());
            }
        }
        // if the end of the for loop is reached, and currentUser is still null, it means that username was not found in database
        if(currentUser==null){
            System.out.println("Username not found in school's records. Please try again.");
            return false;
        }

        // if wrong password was entered
        if(correctPW==false){
            System.out.println("Wrong password.");
            return false;
        }
        else{
            System.out.println("Login successful.");
            return true;
        }
    }
}
