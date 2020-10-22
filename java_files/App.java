import java.io.Console;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws FileNotFoundException, DateTimeParseException, ParseException {
        Scanner scanner = new Scanner(System.in);

        // instantiate student manager
        StudentMgr studentManager = new StudentMgr();
        // instantiate student records
        StudentRecords studentRecs = new StudentRecords();
        // instantiate StudentDetails
        StudentDetails studentDet = new StudentDetails();
        // instantiate CourseDetails
        CourseDetails courseDet = new CourseDetails();
        // instantiate CourseRecords
        CourseRecords courseRecs = new CourseRecords();
        // instantiate Login class
        Login loginMgr = new Login();
        // instantiate data reader
        DataReader reader = new DataReader();
        // instantiate all students and store in studentRecords and Login object
        reader.instantiateStudents(studentRecs, loginMgr);
        // instantiate all courses and store in courseManager
        reader.instantiateCourses(courseRecs);
        // instantiate all admins and store in loginMgr
        reader.instantiateAdmins(loginMgr);
        
        //boolean loop = true;
        while(true){
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

            if(choice==1){
                // authenticate student login using LoginMgr
                loginSuccess = loginMgr.authenticateStudentLogin(username, password);
                if(loginSuccess==false)
                    System.exit(-1);
                Student student = studentRecs.findStudent(username);
                int menuChoice;
                do{
                    // print the menu
                    System.out.println("=========================================");
                    System.out.println("1. Add course                            ");
                    System.out.println("2. Drop course                           ");
                    System.out.println("3. Check/Print courses registered        ");
                    System.out.println("4. Check vacancies available             ");
                    System.out.println("5. Change index number of course         ");
                    System.out.println("6. Swap index number with another student");
                    System.out.println("7. Logout                                ");
                    System.out.println("8. Quit                                  ");
                    System.out.println("=========================================");
                    menuChoice = scanner.nextInt();
                    switch(menuChoice){
                        case 1:
                            break;
                        case 2:
                            System.out.println("Enter the course code of the course you wish to drop.");
                            String code = scanner.next();
                            boolean x = student.dropCourse(code);
                            if(x)
                                System.out.println("Course successfully removed. Number of AUs you are currently taking: " + student.getStudentAUs());
                            else
                                System.out.println("Removal unsuccessful. You are currently not taking this course. Number of AUs you are currently taking: " + student.getStudentAUs());
                            break;
                        case 3:
                            studentDet.checkCoursesRegistered(student);
                            break;
                        case 4:
                            courseRecs.checkVacancies();
                            break;
                        case 5:
                            break;
                        case 6:
                            break;
                        case 7:
                            System.out.println("Logging out.");
                            break;
                        case 8:
                            System.out.println("Exiting program.");
                            System.exit(0);
                            break;
                        default:
                            System.out.println("Please enter a valid integer.");
                    }
                }while(menuChoice!=7 && menuChoice!=8);
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
                    System.out.println("7. Logout                                  ");
                    System.out.println("8. Quit                                    ");
                    System.out.println("===========================================");
                    menuChoice = scanner.nextInt();
                    switch (menuChoice){
                        case 1:
                            System.out.println("Enter the username of the student whose access period you wish to edit:");
                            String usern = scanner.next();
                            Student s = studentRecs.findStudent(usern);
                            boolean success = false;
                            if(s!=null){
                                System.out.println("Enter the new access period for this student in the following format YYYY-MM-DD:YYYY-MM-DD");
                                String accessPeriod = scanner.next();
                                success = studentManager.editAccessPeriod(s, accessPeriod);
                            }
                            if(success==true)
                                studentRecs.printAllStudents();
                            break;
                        
                        case 2:
                            System.out.println("Enter the details of the student you wish to add.");
                            System.out.println("Username: ");
                            String usernm = scanner.next();
                            System.out.println("Name: ");
                            scanner.nextLine();
                            String nm = scanner.nextLine();
                            System.out.println("Matriculation Number: ");
                            String matric = scanner.next();
                            System.out.println("Gender: ");
                            String g = scanner.next();
                            System.out.println("Nationality: ");
                            String nat = scanner.next();
                            System.out.println("Access period in the following format YYYY-MM-DD:YYYY-MM-DD");
                            String accessPer = scanner.next();
                            studentRecs.addStudent(usernm, nm, matric, g, nat, accessPer); 
                            break;
                        case 3:
                            int choice2;
                            do{
                            System.out.println("==================");
                            System.out.println("1. Add a course   ");
                            System.out.println("2. Update a course");
                            System.out.println("==================");
                            choice2 = scanner.nextInt();
                                if(choice2==1){
                                    System.out.println("Course code: ");
                                    String csCode = scanner.next();
                                    System.out.println("Course name: ");
                                    scanner.nextLine();
                                    String name = scanner.nextLine();
                                    System.out.println("School: ");
                                    String sch = scanner.next();
                                    System.out.println("Index numbers, separated only by whitespace: ");
                                    scanner.nextLine();
                                    String indeces = scanner.nextLine();
                                    //courseRecs.addCourse(csCode, name, sch, indeces);
                                }
                                else{

                                }
                            } while(choice2!=1 && choice2!=2);
                            break;
                        case 4:
                            System.out.println("Enter the course code of the course: ");
                            String courseCode = scanner.next();
                            Course c = courseRecs.findCourse(courseCode);
                            if(c!=null){
                                System.out.println("Enter the index number: ");
                                int index = scanner.nextInt();
                                courseDet.checkAvailibility(c, index);
                            }
                            break;
                        case 5:
                            System.out.println("Enter the course code of the course: ");
                            String cCode = scanner.next();
                            Course c1 = courseRecs.findCourse(cCode);
                            if(c1!=null){
                                System.out.println("Enter the index number: ");
                                int indexNum = scanner.nextInt();
                                courseDet.printStudentsByIndexNum(c1, indexNum);
                            }
                            break;
                        case 6:
                            System.out.println("Enter the course code of the course: ");
                            String code = scanner.next();
                            Course c2 = courseRecs.findCourse(code);
                            if(c2!=null)
                                courseDet.printStudentsByCourse(c2);
                            break;
                        case 7:
                            System.out.println("Logging out.");
                            break;
                        case 8:
                            System.out.println("Exiting program.");
                            System.exit(0);
                            break;
                        default:
                            System.out.println("Please enter a valid integer.");
                    }
                }while(menuChoice!=7 && menuChoice!=8);
            }
            //scanner.close();
        }
        //scanner.close();
    }
}
