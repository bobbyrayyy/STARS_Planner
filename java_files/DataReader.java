import java.io.FileNotFoundException;

public interface DataReader {
    public abstract void instantiateStudents(StudentRecords students, Login l) throws FileNotFoundException;
    public abstract void instantiateCourses(CourseRecords courseRec) throws FileNotFoundException;
    public abstract void instantiateAdmins(Login l) throws FileNotFoundException;
}
