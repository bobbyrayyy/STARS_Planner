import java.util.ArrayList;
import java.util.Arrays;

// control class that handles all the logic related to courses
public class CourseRecords {
    // instance variables
    private ArrayList<Course> courses = new ArrayList<Course>();

    // constructor
    public CourseRecords(){}

    // get method for the array of courses
    public ArrayList<Course> getCourses(){
        return courses;
    }

    // set method for the array of courses
    public void setCourses(ArrayList<Course> allCourses){
        courses = allCourses;
    }

    // method to find a course and return it
    public Course findCourse(String courseCode){
        for(int i=0; i<courses.size(); i++){
            Course c = courses.get(i);
            if(courseCode.equals(c.getCourseCode()))
                return c;
        }
        System.out.println("Course code not found in school's records.");
        return null;
    }

    // method to check vacancies: func requirement 4 in student menu
    public void checkVacancies(){
        // iterate through courses array list
        for(int i=0; i<courses.size(); i++){
            Course c = courses.get(i);
            System.out.println(c.getCourseCode()+", vacancies: "+c.getVacancies());
        }
    }
    // // method to add a course
    // public void addCourse(String csCode, String name, String sch, String indeces){
    //     // error handle existing course
    //     for(int i=0; i<courses.size(); i++){
    //         if(csCode.equals(courses.get(i).getCourseCode())){
    //             System.out.println("Course already exists.");
    //         }
    //     }
    //     // error handle indeces
    //     ArrayList<String> indexList = new ArrayList<>(Arrays.asList(indeces.split(" ")));
    //     for(int j=0; )

    // }
}
