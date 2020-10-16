import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Login {
    // instance variables
    private ArrayList<Student> students = new ArrayList<Student>();
    private ArrayList<Admin> admins = new ArrayList<Admin>();

    // constructor
    public Login(){}

    // getters and setters
    public ArrayList<Student> getStudents(){return students;}
    public void setStudents(ArrayList<Student> students){this.students = students;}

    public ArrayList<Admin> getAdmins(){return admins;}
    public void setAdmins(ArrayList<Admin> admins){this.admins = admins;}

    // method to authenticate student's login
    public boolean authenticateStudentLogin(String username, String password) throws ParseException {
        boolean correctPW = false;
        boolean correctPeriod = false;
        Student currentUser = null;
        PasswordAuthentication p = new PasswordAuthentication();
        
        // search through arraylist for the student with this username
        for(int i=0; i<students.size(); i++){
            Student currentStudent = students.get(i);
            // if the usernames match
            if(username.equals(currentStudent.getUsername())){
                currentUser = currentStudent;
                // compare the corresponding passwords
                correctPW = p.authenticate(password.toCharArray(), currentStudent.getPassword());
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
        // at this point, username and password are both correct. I need to check the add/drop period
        String addDropPeriod = currentUser.getAddDropPeriod();
        ArrayList<String> dateList = new ArrayList<>(Arrays.asList(addDropPeriod.split(":")));
        String start = dateList.get(0);
        String end = dateList.get(1);
        // converting to date objects
        Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(start);
        Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(end);
        // getting today's date
        LocalDate localDate = LocalDate.now();
        Date dateToday = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        // checking if today's date is within the start and end date
        correctPeriod = !(dateToday.before(startDate) || dateToday.after(endDate));

        if(correctPeriod==false){
            if(dateToday.before(startDate))
                System.out.println("Your add drop period has not started.");
            else
                System.out.println("Your add drop period has already ended.");
            return false;
        }
        else{
            System.out.println("Login successful.");
            return true;
        }
    }

    // method to authenticate admin's login
    public boolean authenticateAdminLogin(String username, String password){
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
