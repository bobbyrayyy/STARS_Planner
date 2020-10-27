import java.util.ArrayList;

public class StudentDetails {
    // constructor
    public StudentDetails(){}

    // method to check and print courses registered for a particular student
    public void checkCoursesRegistered(Student s){
        System.out.println(s.getUsername() + ", " + s.getName());
        // each student has an array list of course objects
        ArrayList<Course> regCourses = s.getCourses();
        System.out.println("List of courses registered: ");
        // iterate through array list
        for(int i=0; i<regCourses.size(); i++){
            Course c = regCourses.get(i);
            System.out.println(c.getCourseCode() + " " + c.getName() + ", index " + s.fromDict(c.getCourseCode()));
        }
        System.out.println("Total AUs you are currently taking: " + s.getStudentAUs());
    }

    // method to check for timetable clash
    public int[][] checkLessonClash(int[][] studentTimetable, int[][] indexTimetable){
        // both are 5x12 2D arrays
        // create another 5x12 array
        int[][] combined = new int[5][12];
        // add both input arrays, storing the result in combined
        for(int i=0; i<5; i++){
            for(int j=0; j<12; j++){
                // check for lesson clash
                if(studentTimetable[i][j]!=0 && indexTimetable[i][j]!=0){
                    System.out.println("The index that you wish to add has lessons that clash with your current timetable. Please choose another index.");
                    return null;
                }
                else{
                    combined[i][j] = studentTimetable[i][j] + indexTimetable[i][j];
                }
            }
        }
        return combined;
    }
}
