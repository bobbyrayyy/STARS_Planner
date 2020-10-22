import java.text.ParseException;
import java.util.ArrayList;

public class StudentMgr {
    // constructor
    public StudentMgr(){}

    // method to edit a student's access period
    public boolean editAccessPeriod(Student student, String period) throws ParseException {
        if(student==null)
            return false;

        ErrorHandler e = new ErrorHandler();
        // returns true if input period has no error
        boolean x = e.errorHandlePeriod(period);
        if(x==false)
            return false;

        student.setAddDropPeriod(period);
        return true;
    }
}

