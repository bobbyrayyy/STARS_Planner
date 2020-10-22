import java.util.ArrayList;

public class StudentDetails {
    // constructor
    public StudentDetails(){}

    // method to check and print courses registered for a particular student
    public void checkCoursesRegistered(Student s){
        // each student has an array list of course objects
        ArrayList<Course> regCourses = s.getCourses();
        System.out.println("List of courses registered: ");
        // iterate through array list
        for(int i=0; i<regCourses.size(); i++){
            Course c = regCourses.get(i);
            System.out.println(c.getCourseCode() + c.getName());
        }
    }
}
