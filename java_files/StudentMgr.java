import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

// control class that handles all the logic related to students
public class StudentMgr {
    // instance variables
    private ArrayList<Student> students = new ArrayList<Student>();

    // constructor
    public StudentMgr() throws FileNotFoundException {
        // whenever a new student manager is created, instantiate all the students currently in our database
        this.instantiateStudents();
    }

    // this method reads in our database text file, and stores all the students'
    // records in the students array
    public void instantiateStudents() throws FileNotFoundException {
        // scanner to read in each line in txt file
        Scanner scStream = new Scanner(new File("student_records.txt"));
        String inputLine;

        while (scStream.hasNext()) {
            inputLine = scStream.nextLine();
            // splitting each input line by commas, and storing all the items in a list
            ArrayList<String> inputList = new ArrayList<>(Arrays.asList(inputLine.split(",")));
            // getting all the info by indexing into array
            String username = inputList.get(0);
            String hashedPassword = inputList.get(1);
            String name = inputList.get(2);
            String matricNum = inputList.get(3);
            String gender = inputList.get(4);
            String nationality = inputList.get(5);
            String addDropPeriod = inputList.get(6);
            // creating a new student object and adding it into the list
            students.add(new Student(username, hashedPassword, name, matricNum, gender, nationality, addDropPeriod));
        }
    }

    // get method for the array of students
    public ArrayList<Student> getStudents() {
        return students;
    }

    // method to add student
    public void addStudent(String usernm, String nm, String matric, String g, String nat, String accessPer) {
        // error handle matric number
        // assuming that all matric numbers must be of length 9
        if(matric.length()!=9){
            System.out.println("Please enter a valid matriculation number.");
            return;
        }
        //assuming that all matric numbers must start and end with a capital letter
        if(!(matric.charAt(0)>='A' && matric.charAt(0)<='Z') || !(matric.charAt(8)>='A' && matric.charAt(8)<='Z')){
            System.out.println("Please enter a valid matriculation number.");
            return;
        }
        // check if student already exists
        for(int i=0; i<students.size(); i++){
            if(matric.equals(students.get(i).getMatricNum())){
                System.out.println("This student already exists.");
                return;
            }
        }
        // error handle gender
        if(!g.equals("Female") && !g.equals("Male")){
            System.out.println("Please enter a valid gender.");
            return;
        }
        // error handle access period
        boolean x = errorHandle(accessPer);
        if(x==false)
            return; 
        
        PasswordAuthentication p = new PasswordAuthentication();
        // when a student is added, the default password will be the same as his/her username
        String hashedPW = p.hash(usernm.toCharArray());
        // creating a new student
        Student newStudent = new Student(usernm, hashedPW, nm, matric, g, nat, accessPer);
        // adding new student to array list
        students.add(newStudent);

        // print the new list of students
        System.out.println("New full list of students after addition:");
        for(int i=0; i<students.size(); i++){
            System.out.println("Name: "+students.get(i).getName()+", Matric Number: " + students.get(i).getMatricNum() + ", Access Period: "+ students.get(i).getAddDropPeriod());
        }
        return;
    }
    // method to remove student

    // method to authenticate student's login
    public boolean authenticate(String username, String password) throws ParseException {
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

    // method to edit a student's access period
    public void editAccessPeriod(String matric, String period) throws ParseException {
        // returns true if input period has no error
        boolean x = errorHandle(period);
        if(x==false)
            return;

        int found = 0;
        
        // search through student arraylist to find the correct student
        for(int i=0; i<students.size(); i++){
            Student curr = students.get(i);
            // if matric numbers match
            if(matric.equals(curr.getMatricNum())){
                found = 1;
                curr.setAddDropPeriod(period);
            }
        }
        if(found==0){
            System.out.println("Student not found");
        }
        else{
            // print the full list of students with their corresponding access period
            System.out.println("Full list of students after access period has been edited:");
            for(int i=0; i<students.size(); i++){
                Student s = students.get(i);
                System.out.println("Name: "+s.getName()+", Matric Number: " + s.getMatricNum() + ", Access Period: "+ s.getAddDropPeriod());
            }
        }
    }

    // method to error handle access period input. Input must be in the format YYYY-MM-DD:YYYY-MM-DD
    public boolean errorHandle(String period){
        try{
            // error handling the input access period
            ArrayList<String> dateList = new ArrayList<>(Arrays.asList(period.split(":")));
            String start = dateList.get(0);
            String end = dateList.get(1);

            ArrayList<String> startList = new ArrayList<>(Arrays.asList(start.split("-")));
            ArrayList<String> endList = new ArrayList<>(Arrays.asList(end.split("-")));
            // YYYY
            String year = startList.get(0);
            String year2 = endList.get(0);
            if(year.length()!=4 || year2.length()!=4){
                System.out.println("Please enter a valid access period in the appropriate format.");
                return false;
            }
            // MM
            int month = Integer.parseInt(startList.get(1));
            int month2 = Integer.parseInt(endList.get(1));
            if(month<1 || month>12 || month2<1 || month2>12){
                System.out.println("Please enter a valid access period in the appropriate format.");
                return false;
            }
            // DD
            int day = Integer.parseInt(startList.get(2));
            int day2 = Integer.parseInt(endList.get(2));
            if(day<1 || day>31 || day2<1 || day2>31){
                System.out.println("Please enter a valid access period in the appropriate format.");
                return false;
            }

            // converting to date objects
            Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(start);
            Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(end);
            if(endDate.before(startDate)){
                System.out.println("Please enter a valid access period.");
                return false;
            }
            return true;
        }
        catch(Exception e){
            System.out.println("Please enter a valid access period in the appropriate format.");
            return false;
        }
    }
}
