import java.util.ArrayList;

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
    }

    // getters and setters
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
    }
    // method to remove course
    public boolean dropCourse(String courseCode){
        // search through the arrayList to find the correct course
        for(int i=0; i<courses.size(); i++){
            Course c = courses.get(i);
            // if the courseCode matches
            if(courseCode.equals(c.getCourseCode())){
                // reduce the number of AUs for the student
                totalAUs -= c.getAUs();
                // remove the course from the student's courses array
                courses.remove(i);
                return true;
            }
        }
        return false;
    }
}
