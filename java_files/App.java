import java.io.Console;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws FileNotFoundException, ParseException {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do{
            System.out.println("===================");
            System.out.println("1. Login as student");
            System.out.println("2. Login as admin  ");
            System.out.println("===================");
            choice = scanner.nextInt();
        } while(choice!=1 && choice!=2);
        
        // getting user to input username and password
        System.out.println("Please enter your username: ");
        String username = scanner.next();
        Console console = System.console();
        String password = new String(console.readPassword("Please enter your password: "));
        boolean loginSuccess = false;

        // instantiate student manager
        StudentMgr studentManager = new StudentMgr();
        // instantiate CourseMgr. This will instantiate all courses, timeslots, and lessons
        CourseMgr courseManager = new CourseMgr();
        // instantiate Login class
        Login loginMgr = new Login();
        // instantiate data reader
        DataReader reader = new DataReader();
        // instantiate all students and store in studentManager and Login object
        reader.instantiateStudents(studentManager, loginMgr);
        // instantiate all courses and store in courseManager
        reader.instantiateCourses(courseManager);
        // instantiate all admins and store in loginMgr
        reader.instantiateAdmins(loginMgr);

        if(choice==1){
            // authenticate student login using LoginMgr
            loginSuccess = loginMgr.authenticateStudentLogin(username, password);
            if(loginSuccess==false)
                System.exit(-1);
        }
        else{
            // authenticate admin login using LoginMgr
            loginSuccess = loginMgr.authenticateAdminLogin(username, password);
            if(loginSuccess==false)
                System.exit(-1);
            
            int menuChoice;
            do{
                // print the menu
                System.out.println("===========================================");
                System.out.println("1. Edit student access period              ");
                System.out.println("2. Add a student                           ");
                System.out.println("3. Add/Update a course                     ");
                System.out.println("4. Check available slot for an index number");
                System.out.println("5. Print student list by index number      ");
                System.out.println("6. Print student list by course            ");
                System.out.println("7. Exit                                    ");
                System.out.println("===========================================");
                menuChoice = scanner.nextInt();
                switch (menuChoice){
                    case 1:
                        System.out.println("Enter the matriculation number of the student whose access period you wish to edit:");
                        String matricNum = scanner.next();
                        System.out.println("Enter the new access period for this student in the following format YYYY-MM-DD:YYYY-MM-DD");
                        String accessPeriod = scanner.next();
                        studentManager.editAccessPeriod(matricNum, accessPeriod);
                        break;
                    
                    case 2:
                        System.out.println("Enter the details of the student you wish to add.");
                        System.out.println("Username: ");
                        String usernm = scanner.next();
                        System.out.print("Matriculation Number: ");
                        String matric = scanner.next();
                        System.out.println("Gender: ");
                        String g = scanner.next();
                        System.out.println("Nationality: ");
                        String nat = scanner.next();
                        System.out.println("Access period in the following format YYYY-MM-DD:YYYY-MM-DD");
                        String accessPer = scanner.next();
                        System.out.println("Name: ");
                        String nm = scanner.nextLine();
                        studentManager.addStudent(usernm, nm, matric, g, nat, accessPer); 
                        break;
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                        System.out.println("Logging out.");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Please enter a valid integer.");
                }
            }while(menuChoice!=7);
        }
        scanner.close();
    }
}
