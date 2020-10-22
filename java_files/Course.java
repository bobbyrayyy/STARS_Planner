import java.util.ArrayList;

public class Course {
    // instance variables
    private String courseCode;
    private String name;
    private String school;
    private int vacancies;
    private ArrayList<TimeSlot> timeslots = new ArrayList<TimeSlot>();
    private int AUs;

    // constructor
    public Course(String code, String name, String sch, int AUs){
        setCourseCode(code);
        setCourseName(name);
        setSchool(sch);
        setAUs(AUs);
    }

    // getters and setters
    public String getCourseCode(){return courseCode;}
    public void setCourseCode(String code){courseCode = code;}

    public String getName(){return name;}
    public void setCourseName(String name){this.name = name;}

    public String getSchool(){return school;}
    public void setSchool(String sch){school = sch;}

    public int getVacancies(){return vacancies;}
    public void setVacancies(int vac){vacancies = vac;}

    public ArrayList<TimeSlot> getTimeslots(){return timeslots;}
    
    // method to add a new timeslot
    public void addTimeSlot(TimeSlot t){timeslots.add(t);}

    // method to remove a timeslot

    public int getAUs(){return AUs;}
    public void setAUs(int AUs){this.AUs = AUs;}

    // method to calculate total number of vacancies in a course
    public void calculateAndSetVacancies(){
        int totalVac = 0;
        // iterate through the array list of timeslots
        for(int i=0; i<timeslots.size(); i++){
            totalVac += timeslots.get(i).getVacancy();
        }
        // set the instance variable
        vacancies = totalVac;
    }
}
