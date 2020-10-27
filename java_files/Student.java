import java.util.ArrayList;
import java.util.Hashtable;

// Student is an entity class
public class Student {
    // instance variables
    private String username;
    private String hashedPassword;
    private String name;
    private String matricNum;
    private String gender;
    private String nationality;
    private String addDropPeriod;
    private int totalAUs;
    // courses is an array of Course objects that the student is taking
    private ArrayList<Course> courses = new ArrayList<Course>();
    // this dictionary keeps track of which timeslot a student is taking with each course
    private Hashtable<String, Integer> dict;
    // student's timetable
    private int[][] studentTimetable;

    // constructor
    public Student(String username, String pw, String name, String num, String gender, String nat, String period){
        setUsername(username);
        setPassword(pw);
        setName(name);
        setMatricNum(num);
        setGender(gender);
        setNationality(nat);
        setAddDropPeriod(period);
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
    public int[][] getStudentTimetable(){return studentTimetable;}
    public void setStudentTimeTable(int[][] timetable){studentTimetable=timetable;}

    public String getUsername(){return username;}
    public void setUsername(String username){this.username = username;}

    public String getPassword(){return hashedPassword;}
    public void setPassword(String password){hashedPassword = password;}

    public String getName(){return name;}
    public void setName(String name){this.name = name;}

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
    public void addCourse(Course c){
        courses.add(c);
        totalAUs += c.getAUs();
    }

    // method to drop course
    public void removeCourse(Course c){
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
}
