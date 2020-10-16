import java.util.ArrayList;

// control class that handles all the logic related to courses
public class CourseMgr {
    // instance variables
    private ArrayList<Course> courses = new ArrayList<Course>();

    // constructor
    public CourseMgr(){}

    // get method for the array of courses
    public ArrayList<Course> getCourses(){
        return courses;
    }

    // set method for the array of courses
    public void setCourses(ArrayList<Course> allCourses){
        courses = allCourses;
    }
}

