import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Hashtable;

// Student is an entity class
public class Student extends User{
    // instance variables
    private String matricNum;
    private String gender;
    private String nationality;
    private String addDropPeriod;
    private int totalAUs;
    private String email;
    // courses is an array of Course objects that the student is taking
    private ArrayList<Course> courses = new ArrayList<Course>();
    // this dictionary keeps track of which timeslot a student is taking with each course
    private Hashtable<String, Integer> dict;
    // student's timetable
    private int[][] studentTimetable;

    // constructor
    public Student(String username, String pw, String name, String num, String gender, String nat, String period, String email){
        setUsername(username);
        setPassword(pw);
        setName(name);
        setMatricNum(num);
        setGender(gender);
        setNationality(nat);
        setAddDropPeriod(period);
        setEmail(email);
        totalAUs = 0;
        // creating the student's timetable and filling it with zeros
        int [][] timetable = new int[5][12];
        for(int i=0; i<5; i++){
            for(int j=0; j<12; j++){
                timetable[i][j] = 0;
            }
        }
        studentTimetable = timetable;
        // create an empty dictionary
        dict = new Hashtable<String,Integer>();
    }
    // method to add something into dictionary
    public void addToDict(String courseCode, int index){
        dict.put(courseCode, index);
    }

    // method to remove key value pair from dictionary
    public void removeFromDict(String courseCode){
        dict.remove(courseCode);
    }
    
    // method to get a value from the dictionary
    public Integer fromDict(String courseCode){
        return dict.get(courseCode);
    }
    
    // getters and setters
    public String getEmail(){return email;}
    public void setEmail(String email){this.email=email;}

    public int[][] getStudentTimetable(){return studentTimetable;}
    public void setStudentTimeTable(int[][] timetable){studentTimetable=timetable;}

    public String getMatricNum(){return matricNum;}
    public void setMatricNum(String num){matricNum = num;}

    public String getGender(){return gender;}
    public void setGender(String gender){this.gender = gender;}

    public String getNationality(){return nationality;}
    public void setNationality(String nat){nationality = nat;}

    public String getAddDropPeriod(){return addDropPeriod;}
    public void setAddDropPeriod(String period){addDropPeriod = period;}

    public int getStudentAUs(){return totalAUs;}
    public void setStudentAUs(int AUs){totalAUs = AUs;}

    public ArrayList<Course> getCourses(){return courses;}

    // method to add course
    public void addCourseToArray(Course c){
        courses.add(c);
        totalAUs += c.getAUs();
    }

    // method to drop course
    public void removeCourseFromArray(Course c){
        courses.remove(c);
        totalAUs -= c.getAUs();
    }

    // method to find a course that a student is taking. returns a Course object
    public Course findCourse(String courseCode){
        for(int i=0; i<courses.size(); i++){
            Course c = courses.get(i);
            if(courseCode.equals(c.getCourseCode()))
                return c;
        }
        return null;
    }

    // method to authenticate a student's login
    public boolean authenticateLogin(String username, String password) throws ParseException {
        boolean correctPW = false;
        boolean correctPeriod = false;
        PasswordAuthentication1 p = new PasswordAuthentication1();
        
        correctPW = p.authenticate(password.toCharArray(), getPassword());

        // if wrong password was entered
        if(correctPW==false){
            System.out.println("Wrong password.");
            return false;
        }
        
        // at this point, username and password are both correct. I need to check the add/drop period
        String addDropPeriod = getAddDropPeriod();
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
}
