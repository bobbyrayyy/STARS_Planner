import java.util.ArrayList;

// entity class that stores data of each timeslot
public class TimeSlot {
    // instance variables
    private int indexNum;
    private int vacancy;
    private ArrayList<Student> studentsInTimeslot = new ArrayList<Student>();
    private ArrayList<Lesson> lessons = new ArrayList<Lesson>();

    // constructor
    public TimeSlot(int num){
        setIndexNum(num);
        vacancy = 10;
    }

    // getters and setters
    public int getIndexNum(){return indexNum;}
    public void setIndexNum(int num){indexNum = num;}

    public int getVacancy(){return vacancy;}
    public void setVacancy(int vacancy){this.vacancy = vacancy;}

    // method that returns all the students in timeslot
    public ArrayList<Student> getStudentsInTimeslot(){return studentsInTimeslot;}
    // method to add student to time slot
    public void addStudentToTimeslot(Student s){
        studentsInTimeslot.add(s);
        vacancy--;
    }
    // method to remove student from timeslot

    // method that returns all the lessons in timeslot
    public ArrayList<Lesson> getAllLessons(){return lessons;}
    // method to add lesson to timeslot
    public void addLesson(Lesson l){
        lessons.add(l);
    }
    // method to remove a lesson from timeslot


}
