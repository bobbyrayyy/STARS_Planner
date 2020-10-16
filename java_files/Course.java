import java.util.ArrayList;

public class Course {
    // instance variables
    private String courseCode;
    private String name;
    private String school;
    private boolean full;
    private ArrayList<TimeSlot> timeslots = new ArrayList<TimeSlot>();

    // constructor
    public Course(String code, String name, String sch){
        setCourseCode(code);
        setCourseName(name);
        setSchool(sch);
        full = false; 
    }

    // getters and setters
    public String getCourseCode(){return courseCode;}
    public void setCourseCode(String code){courseCode = code;}

    public String getName(){return name;}
    public void setCourseName(String name){this.name = name;}

    public String getSchool(){return school;}
    public void setSchool(String sch){school = sch;}

    public boolean getAvailability(){return full;}
    public void setAvailability(boolean avail){full = avail;}

    public ArrayList<TimeSlot> getTimeslots(){return timeslots;}
    // method to add a new timeslot
    public void addTimeSlot(TimeSlot t){timeslots.add(t);}

    // method to remove a timeslot
}
