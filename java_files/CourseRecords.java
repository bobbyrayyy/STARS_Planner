import java.util.ArrayList;
//import java.util.Arrays;

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
    // method to add a course
    public void addCourse(String csCode, String name, String sch, int AU, ArrayList<Integer> indexList, ArrayList<Integer> vacList){
        // arraylist of timeslot objects
        ArrayList<TimeSlot> timeslotlist = new ArrayList<TimeSlot>();
        // creating timeslot objects and add to the arraylist
        for(int i=0; i<indexList.size(); i++){
            TimeSlot t = new TimeSlot(indexList.get(i));
            t.setVacancy(vacList.get(i));
            timeslotlist.add(t);
        }

        // create a new course object
        Course newCourse = new Course(csCode, name, sch, AU);
        newCourse.setTimeslots(timeslotlist);
        newCourse.calculateAndSetVacancies();
        // add it to the courses arraylist
        courses.add(newCourse);
        // print the full list of courses
        System.out.println("Full list of courses after successful addition: ");
        printAllCourses();
    }

    //method to remove a student from timeslot's student array whenever a student drops a course
    public void removeStudent(Student s, String code, int indexDropped){
        // look through the courses arraylist to find the correct course
        for(int i=0; i<courses.size(); i++){
            Course c = courses.get(i);
            // if correct course is found
            if(code.equals(c.getCourseCode())){
                // get the timeslots array for that course
                ArrayList<TimeSlot> timeslots = c.getTimeslots();
                // look through the timeslots arraylist
                for(int j=0; j<timeslots.size(); j++){
                    TimeSlot t = timeslots.get(j);
                    // if the correct timeslot is found
                    if(indexDropped==t.getIndexNum()){
                        // get the students array for that timeslot
                        ArrayList<Student> students = t.getStudentsInTimeslot();
                        // remove the student from the arraylist
                        students.remove(s);
                    }
                }
            }
        }
    }

    // method to print all courses
    public void printAllCourses(){
        for(int i=0; i<courses.size(); i++){
            Course c = courses.get(i);
            System.out.println(c.getCourseCode() + ", " + c.getName() + ", total vacancies: " + c.getVacancies());
        }
    }
}
