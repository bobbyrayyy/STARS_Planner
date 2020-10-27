import java.util.ArrayList;

public class CourseDetails {
    // constructors
    public CourseDetails(){}

    // method to check number of vacancies for a timeslot
    public void checkAvailibility(Course course, int index){
        // get all the timeslots for that course
        ArrayList<TimeSlot> timeslots = course.getTimeslots();
        // search through timeslots array list to look for correct index
        for(int j=0; j<timeslots.size(); j++){
            TimeSlot t = timeslots.get(j);
            // if the index number matches
            if(index==t.getIndexNum()){
                // print the number of vacancies for that timeslot
                System.out.println("Number of vacancies for course " + course.getCourseCode() + ", index number " + t.getIndexNum() + ": " + t.getVacancy() + "/10.");
                return;
            }
        }
        System.out.println("Index number not found for this course.");
        return;
    }


    // method to print all students taking a course. It returns true if there are no students taking the course, and false otherwise
    public boolean printStudentsByCourse(Course course){
        boolean empty = true;
        System.out.println("List of students taking " + course.getCourseCode());
        // get all the timeslots for that course
        ArrayList<TimeSlot> timeslots = course.getTimeslots();
        // for each timeslot in the array list
        for(int j=0; j<timeslots.size(); j++){
            TimeSlot t = timeslots.get(j);
            // get all the students in that timeslot
            ArrayList<Student> students = t.getStudentsInTimeslot();
            if(students.size()!=0)
                empty = false;
            // for each student in the array list
            for(int k=0; k<students.size(); k++){
                Student s = students.get(k);
                System.out.println(s.getName() + ", " + s.getGender() + ", " + s.getNationality());
            }
        }
        return empty;
    }

    // method to print all students in a particular timeslot
    public void printStudentsByIndexNum(Course course, int indexNum){
        // get all the timeslots for that course
        ArrayList<TimeSlot> timeslots = course.getTimeslots();
        // search through timeslots array list to look for correct index
        for(int j=0; j<timeslots.size(); j++){
            TimeSlot t = timeslots.get(j);
            // if the index number matches
            if(indexNum==t.getIndexNum()){
                // get all the students in that timeslot
                ArrayList<Student> students = t.getStudentsInTimeslot();
                if(students.size()==0){
                    System.out.println("No students in index " + indexNum);
                    return;
                }
                System.out.println("List of students in index " + indexNum);
                // for each student in the array list
                for(int k=0; k<students.size(); k++){
                    Student s = students.get(k);
                    System.out.println(s.getName() + ", " + s.getGender() + ", " + s.getNationality());
                }
                return;
            }
        }
        System.out.println("Index number not found for this course.");
        return;
    }
}
