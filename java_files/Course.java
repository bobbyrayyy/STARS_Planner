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

    public String getCourseCode(){return courseCode;}
    public void setCourseCode(String code){courseCode = code;}

    public String getName(){return name;}
    public void setCourseName(String name){this.name = name;}

    public String getSchool(){return school;}
    public void setSchool(String sch){school = sch;}

    public int getVacancies(){
        calculateAndSetVacancies();
        return vacancies;
    }

    public void setVacancies(int vac){vacancies = vac;}

    public ArrayList<TimeSlot> getTimeslots(){return timeslots;}
    public void setTimeslots(ArrayList<TimeSlot> t){timeslots=t;}

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

    // method to print info about the course
    public void printCourseInfo(){
        System.out.println("Course information: ");
        System.out.println(courseCode + ", " + name + ", " + school);
        System.out.println("Number of AUs: " + AUs);
        System.out.println("Index numbers and corresponding vacancies: ");
        for(int i=0; i<timeslots.size(); i++){
            TimeSlot t = timeslots.get(i);
            System.out.println(t.getIndexNum() + ": " + t.getVacancy());
        }
        System.out.println("Total vacancies in course: " + getVacancies());
    }

    // method that returns a chosen timeslot
    public TimeSlot findTimeslot(int index){
        for(int i=0; i<timeslots.size(); i++){
            TimeSlot t = timeslots.get(i);
            if(index==t.getIndexNum()){
                return t;
            }
        }
        return null;
    }

    // method that returns a chosen student
    public Student findStudent(String username){
        for(int i=0; i<timeslots.size(); i++){
            TimeSlot t = timeslots.get(i);
            ArrayList<Student> students = t.getStudentsInTimeslot();
            for(int j=0; j<students.size(); j++){
                Student s = students.get(j);
                if(username.equals(s.getUsername())){
                    return s;
                }
            }
        }
        return null;
    }
}
