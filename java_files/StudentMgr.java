import java.text.ParseException;
import java.util.ArrayList;

public class StudentMgr {
    // instance variables
    private StudentDetails deets;
    private Email email;
    // constructor
    public StudentMgr(){
        deets = new StudentDetails();
    }

    public StudentDetails getStudentDetails(){return deets;}

    // method to edit a student's access period
    public boolean editAccessPeriod(Student student, String period) throws ParseException {
        if(student==null)
            return false;

        ErrorHandler e = new ErrorHandler();
        // returns true if input period has no error
        boolean x = e.errorHandlePeriod(period);
        if(x==false)
            return false;

        student.setAddDropPeriod(period);
        return true;
    }

    // method for student to add course
    public boolean addCourse(Student student, TimeSlot addedIndex, Course addedCourse){
        
        // checking for lesson clash
        int[][] newStudentTimetable = deets.checkLessonClash(student.getStudentTimetable(), addedIndex.getIndexTimetable());
        if(newStudentTimetable==null)
            return false;
        // if no clash, check whether there are vacancies in that timeslot
        if(addedIndex.getVacancy()==0){
            // add student to waitlist
            addedIndex.addToWaitList(student);
            return true;
        }
        else{
            // update student's timetable
            student.setStudentTimeTable(newStudentTimetable);
            // add student to timeslot's student array
            addedIndex.addStudentToTimeslot(student);
            // add course to student's course array
            student.addCourseToArray(addedCourse);
            // add key value pair to student's dictionary
            student.addToDict(addedCourse.getCourseCode(), addedIndex.getIndexNum());
            this.email = new Email(student);
            this.email.addNotification(student, addedCourse);
            return true;

        }
    }
    
    
    // method for student to drop course
    public void dropCourse(String courseCode, Course course, Student student){
        // find the index that the student is registered to
        int indexDropped = student.fromDict(courseCode);
        
        // update student's timetable
        int[][] newTimetable = student.getStudentTimetable();
        for(int i=0; i<5; i++){
            for(int j=0; j<12; j++){
                if(newTimetable[i][j]==indexDropped){
                    newTimetable[i][j] = 0;
                }
            }
        }
        student.setStudentTimeTable(newTimetable);

        // remove student from timeslot's student array
        TimeSlot t = course.findTimeslot(indexDropped);
        t.removeStudentFromTimeslot(student);

        // remove course from student's course array
        student.removeCourseFromArray(course);

        // remove key value pair from dictionary
        student.removeFromDict(courseCode);
        //Email notification
        this.email = new Email(student);
        this.email.dropNotification(course,indexDropped, student);

        // settle waitlist
        if(t.getVacancy()==1){
            // remove the first student from waitlist
            ArrayList<Student> waitlist = t.getWaitList();
            Student s = waitlist.get(0);
            waitlist.remove(0);
            // add course to student s
            addCourse(s, t, course);
            // simulate notification sent to student s
            System.out.println("Notification sent to student: " + s.getName() + " to inform that course " + course.getCourseCode() + " has been successfully added.");
        }
    }

    // method for two students to swap index numbers
    public void swapCourse(Student student, Student friend, Course course){
        // I cannot use drop course here, in case that spot in the index gets assigned to someone else on the waitlist
        // find the index that the student is registered to
        int studentIndex = student.fromDict(course.getCourseCode());

        // update student's timetable
        int[][] studentTimetable = student.getStudentTimetable();
        for(int i=0; i<5; i++){
            for(int j=0; j<12; j++){
                if(studentTimetable[i][j]==studentIndex){
                    studentTimetable[i][j] = 0;
                }
            }
        }
        student.setStudentTimeTable(studentTimetable);

        // remove student from timeslot's student array
        TimeSlot t = course.findTimeslot(studentIndex);
        t.removeStudentFromTimeslot(student);

        // remove course from student's course array
        student.removeCourseFromArray(course);

        // remove key value pair from dictionary
        student.removeFromDict(course.getCourseCode());

        // repeating the whole process for the friend
        // find the index that the friend is registered to
        int friendIndex = friend.fromDict(course.getCourseCode());

        // update friend's timetable
        int[][] friendTimetable = friend.getStudentTimetable();
        for(int i=0; i<5; i++){
            for(int j=0; j<12; j++){
                if(friendTimetable[i][j]==friendIndex){
                    friendTimetable[i][j] = 0;
                }
            }
        }
        friend.setStudentTimeTable(friendTimetable);

        // remove friend from timeslot's student array
        TimeSlot t1 = course.findTimeslot(friendIndex);
        t1.removeStudentFromTimeslot(student);

        // remove course from friend's course array
        friend.removeCourseFromArray(course);

        // remove key value pair from dictionary
        friend.removeFromDict(course.getCourseCode());

        // adding the courses back for both friend and student
        addCourse(student, t1, course);
        addCourse(friend, t, course);
    }
}

