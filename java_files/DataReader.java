import java.io.FileNotFoundException;

public interface DataReader {
    public abstract void instantiateStudents(StudentRecords students) throws FileNotFoundException;
    public abstract void instantiateCourses(CourseRecords courseRec) throws FileNotFoundException;
    public abstract void instantiateAdmins(AdminRecords adminRecs) throws FileNotFoundException;
}
