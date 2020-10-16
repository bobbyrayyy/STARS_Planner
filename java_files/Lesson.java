// entity class that stores data of each lesson
public class Lesson {
    // instance variables
    private String type;
    private String group;
    private String venue;
    private String day;
    // start and end times of the lesson stored in 24h format e.g. 0930 and 1030
    private int startTime;
    private int endTime;

    // constructor
    public Lesson(String type, String group, String venue, String day, int start, int end){
        setLessonType(type);
        setLessonGroup(group);
        setVenue(venue);
        setLessonDay(day);
        setStartTime(start);
        setEndTime(end);
    }

    // getters and setters
    public String getLessonType(){return type;}
    public void setLessonType(String type){this.type = type;}

    public String getLessonGroup(){return group;}
    public void setLessonGroup(String group){this.group = group;}

    public String getVenue(){return venue;}
    public void setVenue(String venue){this.venue = venue;}

    public String getLessonDay(){return day;}
    public void setLessonDay(String day){this.day = day;}

    public int getStartTime(){return startTime;}
    public void setStartTime(int start){startTime = start;}

    public int getEndTime(){return endTime;}
    public void setEndTime(int end){endTime = end;}
}
