
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class ErrorHandler {
    // constructor
    public ErrorHandler(){}
    
    // method to error handle access period input. Input must be in the format YYYY-MM-DD:YYYY-MM-DD
    public boolean errorHandlePeriod(String period){
        try{
            // error handling the input access period
            ArrayList<String> dateList = new ArrayList<>(Arrays.asList(period.split(":")));
            String start = dateList.get(0);
            String end = dateList.get(1);

            ArrayList<String> startList = new ArrayList<>(Arrays.asList(start.split("-")));
            ArrayList<String> endList = new ArrayList<>(Arrays.asList(end.split("-")));
            // YYYY
            String startYear = startList.get(0);
            String endYear = endList.get(0);

            // MM
            int startMonth = Integer.parseInt(startList.get(1));
            int endMonth = Integer.parseInt(endList.get(1));

            // DD
            int startDay = Integer.parseInt(startList.get(2));
            int endDay = Integer.parseInt(endList.get(2));

            // converting to strings
            String startString = startYear + startMonth + startDay;
            String endString = endYear + endMonth + endDay;

            DateTimeFormatter dateFormatter = DateTimeFormatter.BASIC_ISO_DATE;
            LocalDate.parse(startString, dateFormatter);
            LocalDate.parse(endString, dateFormatter);

            // checking whether start date is earlier than end date
            Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(start);
            Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(end);
            if(endDate.before(startDate)){
                System.out.println("Please enter a valid access period. Start date entered is later than end date.");
                return false;
            }
            return true;
        }
        catch(DateTimeParseException e){
            System.out.println("Please enter valid dates.");
            return false;
        }
        catch(Exception e){
            System.out.println("Please enter a valid access period in the appropriate format.");
            return false;
        }
    }
}
