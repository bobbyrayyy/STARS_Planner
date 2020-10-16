import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

// control class that handles all the logic related to courses
public class CourseMgr {
    // instance variables
    private ArrayList<Course> courses = new ArrayList<Course>();

    // constructor
    public CourseMgr() throws FileNotFoundException {
        // whenever a new course manager is created, instantiate all the courses, timeslots, and lessons currently in our database
        this.instantiateCourses();
    }

    // this method reads in our databse txt file, and stores all the course records
    // in the courses array
    public void instantiateCourses() throws FileNotFoundException {
        Scanner scStream = new Scanner(new File("course_records.txt"));
        String inputLine;
        String inputLine2;

        while(scStream.hasNext()){
            inputLine = scStream.nextLine();
            // splitting each input line by commas, and storing all the items in a list
            ArrayList<String> inputList = new ArrayList<>(Arrays.asList(inputLine.split(",")));
            // getting all the info by indexing into array
            String courseCode = inputList.get(0);
            String name = inputList.get(1);
            String school = inputList.get(2);
            String timeslots = inputList.get(3);
            
            // instantianting a Course object
            Course c = new Course(courseCode, name, school);

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
                // add this timeslot to the course
                c.addTimeSlot(t);
            }
            // at this point, the current course has been fully populated with all of its corresponding timeslots
            // at this course to the courses arraylist
            courses.add(c);
        }
        scStream.close();
    }

    // get method for the array of courses
    public ArrayList<Course> getCourses(){
        return courses;
    }
}
