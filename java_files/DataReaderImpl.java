import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

// this boundary class reads in all the txt files and instantiates all the student objects,
// storing them in StudentMgr class.
// instantiates all the Admin objects, storing them in AdminMgr class
// instantiates all the Course objects, storing them in CourseMgr class
public class DataReaderImpl implements DataReader{
    // constructor
    public DataReaderImpl(){}

    // this method reads in our database text file, instantiates all the student objects,
    // and stores an arraylist of these student objects in StudentMgr class
    public void instantiateStudents(StudentRecords students) throws FileNotFoundException {
        // scanner to read in each line in txt file
        Scanner scStream = new Scanner(new File("student_records.txt"));
        String inputLine;
        // creating an arraylist to store student objects
        ArrayList<Student> allStudents = new ArrayList<Student>();

        while (scStream.hasNext()) {
            inputLine = scStream.nextLine();
            // splitting each input line by commas, and storing all the items in a list
            ArrayList<String> inputList = new ArrayList<>(Arrays.asList(inputLine.split(",")));
            // getting all the info by indexing into array
            String username = inputList.get(0);
            String hashedPassword = inputList.get(1);
            String name = inputList.get(2);
            String matricNum = inputList.get(3);
            String gender = inputList.get(4);
            String nationality = inputList.get(5);
            String addDropPeriod = inputList.get(6);
            String email = inputList.get(7);
            // creating a new student object and adding it into the list
            allStudents.add(new Student(username, hashedPassword, name, matricNum, gender, nationality, addDropPeriod, email));
        }
        // storing the arraylist of students in StudentRecords object
        students.setStudents(allStudents);
    }
    
    // this method reads in our databse txt file, and stores all the course records
    // in the courses array
    public void instantiateCourses(CourseRecords courseRec) throws FileNotFoundException {
        Scanner scStream = new Scanner(new File("course_records.txt"));
        String inputLine;
        String inputLine2;
        ArrayList<Course> allCourses = new ArrayList<Course>();

        while(scStream.hasNext()){
            inputLine = scStream.nextLine();
            // splitting each input line by commas, and storing all the items in a list
            ArrayList<String> inputList = new ArrayList<>(Arrays.asList(inputLine.split(",")));
            // getting all the info by indexing into array
            String courseCode = inputList.get(0);
            String name = inputList.get(1);
            String school = inputList.get(2);
            int AUs = Integer.parseInt(inputList.get(3));
            String timeslots = inputList.get(4);
            
            // instantianting a Course object
            Course c = new Course(courseCode, name, school, AUs);

            // timeslotList is an arraylist of all the index numbers for a particular course
            ArrayList<String> timeslotList = new ArrayList<>(Arrays.asList(timeslots.split(";")));

            // iterate through the list of timeslots
            for(int i=0; i<timeslotList.size(); i++){
                int indexNum = Integer.parseInt(timeslotList.get(i));
                // create a timeslot for that index number
                TimeSlot t = new TimeSlot(indexNum);

                Scanner scStream2 = new Scanner(new File("lessons.txt"));
                // read the lessons.txt file
                while(scStream2.hasNext()){
                    inputLine2 = scStream2.nextLine();
                    // splitting each input line by commas, and storing all the items in a list
                    ArrayList<String> lessonInfoList = new ArrayList<>(Arrays.asList(inputLine2.split(",")));
                    // if the index number matches the index number in the text file
                    if(indexNum==Integer.parseInt(lessonInfoList.get(0))){
                        String type = lessonInfoList.get(1);
                        String group = lessonInfoList.get(2);
                        String venue = lessonInfoList.get(3);
                        String day = lessonInfoList.get(4);
                        int start = Integer.parseInt(lessonInfoList.get(5));
                        int end = Integer.parseInt(lessonInfoList.get(6));
                        // instantiate a new lesson and add it to Timeslot t
                        t.addLesson(new Lesson(type, group, venue, day, start, end));
                    }
                }
                scStream2.close();
                // at this point, the current timeslot has been fully populated with all of its corresponding lessons
                // generate the timetable for this timeslot
                t.createTimetable();
                // add this timeslot to the course
                c.addTimeSlot(t);
            }
            // at this point, the current course has been fully populated with all of its corresponding timeslots
            // calculate and set total number of vacancies in this course
            c.calculateAndSetVacancies();
            // at this course to the courses arraylist
            allCourses.add(c);
        }
        scStream.close();
        courseRec.setCourses(allCourses);
    }

    // this method reads in the admin.txt file, and stores all the admins' records
    // in the admins array
    public void instantiateAdmins(AdminRecords adminRecords) throws FileNotFoundException {
        // scanner to read each line in txt file
        Scanner scStream = new Scanner(new File("admin.txt"));
        String inputLine;
        ArrayList<Admin> admins = new ArrayList<Admin>();

        while(scStream.hasNext()){
            inputLine = scStream.nextLine();
            // splitting each input line by commas, and storing all the items in a list
            ArrayList<String> inputList = new ArrayList<>(Arrays.asList(inputLine.split(",")));
            // getting all the info by indexing into arraylist
            String username = inputList.get(0);
            String hashedPW = inputList.get(1);
            String name = inputList.get(2);
            // creating a new admin object and adding it into the arraylist
            admins.add(new Admin(username, hashedPW, name));
            adminRecords.setAdmins(admins);
        }
    }
}
