import java.util.ArrayList;

// entity class that stores data of each timeslot
public class TimeSlot {
    // instance variables
    private int indexNum;
    private int vacancy;
    private ArrayList<Student> waitList = new ArrayList<Student>();
    private ArrayList<Student> studentsInTimeslot = new ArrayList<Student>();
    private ArrayList<Lesson> lessons = new ArrayList<Lesson>();
    // 2 dimensional array
    private int[][] indexTimetable;

    // constructor
    public TimeSlot(int num){
        setIndexNum(num);
        vacancy = 10;
    }

    // getters and setters
    public ArrayList<Student> getWaitList(){return waitList;}
    public void setWaitList(ArrayList<Student> waitList){this.waitList=waitList;}
    
    public int getIndexNum(){return indexNum;}
    public void setIndexNum(int num){indexNum = num;}

    public int getVacancy(){return vacancy;}
    public void setVacancy(int vacancy){this.vacancy=vacancy;}
    public void editVacancy(int newVacancy, Course course, StudentMgr mgr){
        // whenever admin uses this method to set new vacancy, check whether there is anyone in the waitlist
        if(waitList.size()==0)
            vacancy = newVacancy;
        else{
            // update vacancy
            vacancy = newVacancy;
            int x = waitList.size();
            // need to remove students from the waitlist
            if(newVacancy > waitList.size()){
                // remove everyone on the waitlist
                for(int i=0; i<x; i++){
                    // remove the first student from the waitlist
                    Student s = waitList.get(0);
                    waitList.remove(0);
                    // add course to student s
                    mgr.addCourse(s, this, course);
                    // simulate notification sent to student s
                    System.out.println("Notification sent to student: " + s.getName() + " to inform that course " + course.getCourseCode() + " has been successfully added.");
                }
            }
            else if(newVacancy==waitList.size()){
                // remove everyone on the waitlist
                for(int i=0; i<x; i++){
                    // remove the first student from the waitlist
                    Student s = waitList.get(0);
                    waitList.remove(0);
                    // add course to student s
                    mgr.addCourse(s, this, course);
                    // simulate notification sent to student s
                    System.out.println("Notification sent to student: " + s.getName() + " to inform that course " + course.getCourseCode() + " has been successfully added.");
                }
            }
            else{
                // more students in waitlist than new vacancies
                for(int i=0; i<x; i++){
                    // remove the first student from the waitlist
                    Student s = waitList.get(0);
                    waitList.remove(0);
                    // add course to student s
                    mgr.addCourse(s, this, course);
                    // simulate notification sent to student s
                    System.out.println("Notification sent to student: " + s.getName() + " to inform that course " + course.getCourseCode() + " has been successfully added.");
                }
            }
        }
    }

    public int[][] getIndexTimetable(){return indexTimetable;}

    // method that returns all the students in timeslot
    public ArrayList<Student> getStudentsInTimeslot(){return studentsInTimeslot;}

    // method that adds student to wait list
    public void addToWaitList(Student s){
        System.out.println("You will be added to the waitlist for this timeslot. Number of students ahead of you: " + waitList.size());
        waitList.add(s);
    }
    
    // method to add student to time slot
    public void addStudentToTimeslot(Student s){
        studentsInTimeslot.add(s);
        vacancy--;
    }

    // method to remove student from time slot
    public void removeStudentFromTimeslot(Student s){
        // remove student from arraylist
        studentsInTimeslot.remove(s);
        // increase vacancy
        vacancy++;
    }

    // method that returns all the lessons in timeslot
    public ArrayList<Lesson> getAllLessons(){return lessons;}
    // method to add lesson to timeslot
    public void addLesson(Lesson l){
        lessons.add(l);
    }
    // method to read all the lesson start and end times and come up with the 2D matrix timetable
    public void createTimetable(){
        // creating a 2D array, with 5 rows, 1 for each day, and 12 columns, 1 for each hour (0830 to 2030)
        int[][] timetable = new int[5][12];
        // fill up the array with zeros first
        for(int i=0; i<5; i++){
            for(int j=0; j<12; j++){
                timetable[i][j] = 0;
            }
        }
        // iterate through the lesson array
        for(int i=0; i<lessons.size(); i++){
            Lesson l = lessons.get(i);
            String day = l.getLessonDay();
            // converting startTime and endTime into ints. 0830 will become 0, 1930 will become 11
            int startTime = (l.getStartTime() - 830)/100;
            //System.out.println(startTime);
            int endTime = (l.getEndTime() - 830)/100;
            //System.out.println(endTime);
            // converting the day of the week into an int (MON is 0, FRI is 4)
            int dayNum=0;
            switch(day){
                case "MON":
                    dayNum = 0;
                    break;
                case "TUE":
                    dayNum = 1;
                    break;
                case "WED":
                    dayNum = 2;
                    break;
                case "THU":
                    dayNum = 3;
                    break;
                case "FRI":
                    dayNum = 4;
                    break;
                default:
                    System.out.println("Timetable error");
            }
            // filling up the timetable matrix
            for(int j=startTime; j<endTime; j++){
                timetable[dayNum][j] = indexNum;
            }
        }
        indexTimetable = timetable;
    }
}
