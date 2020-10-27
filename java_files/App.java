import java.io.Console;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws FileNotFoundException, DateTimeParseException, ParseException {
        Scanner scanner = new Scanner(System.in);

        // instantiate student manager
        StudentMgr studentManager = new StudentMgr();
        // instantiate student records
        StudentRecords studentRecs = new StudentRecords();
        // instantiate CourseDetails
        CourseDetails courseDet = new CourseDetails();
        // instantiate CourseRecords
        CourseRecords courseRecs = new CourseRecords();
        // instantiate Login class
        Login loginMgr = new Login();
        // instantiate data reader
        DataReaderImpl reader = new DataReaderImpl();
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
                if(loginSuccess==true){
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
                                System.out.println("List of all courses and corresponding no. of vacancies:");
                                courseRecs.printAllCourses();
                                System.out.println("Enter the course code of the course you wish to add:");
                                String courseCode = scanner.next();
                                // check if the student is already taking this course
                                Course addedCourse = student.findCourse(courseCode);
                                if(addedCourse!=null){
                                    System.out.println("You are already taking this course. Please add another one.");
                                    break;
                                }
                                // try to find this course in the list of courses
                                addedCourse = courseRecs.findCourse(courseCode);
                                if(addedCourse==null)
                                    break;
                                addedCourse.printCourseInfo();
                                System.out.println("Enter the index number you wish to choose");
                                int indexNo = scanner.nextInt();
                                // trying to find the timeslot object 
                                TimeSlot addedIndex = addedCourse.findTimeslot(indexNo);
                                if(addedIndex==null){
                                    System.out.println("Index not found. Please enter a valid index.");
                                    break;
                                }
                                studentManager.addCourse(student, addedIndex, addedCourse);
                                studentManager.getStudentDetails().checkCoursesRegistered(student);
                                break;
                            case 2:
                                System.out.println("List of courses you are currently taking: ");
                                studentManager.getStudentDetails().checkCoursesRegistered(student);
                                System.out.println("Enter the course code of the course you wish to drop.");
                                String code = scanner.next();
                                Course courseDropped = student.findCourse(code);
                                if(courseDropped==null){
                                    System.out.println("You are currently not taking this course.");
                                    break;
                                }
                                studentManager.dropCourse(code, courseDropped, student);
                                studentManager.getStudentDetails().checkCoursesRegistered(student);
                                break;
                            case 3:
                                studentManager.getStudentDetails().checkCoursesRegistered(student);
                                break;
                            case 4:
                                courseRecs.checkVacancies();
                                break;
                            case 5:
                                studentManager.getStudentDetails().checkCoursesRegistered(student);
                                System.out.println("Enter the course code of the course to change the index number");
                                String code1 = scanner.next();
                                Course course = student.findCourse(code1);
                                if(course==null){
                                    System.out.println("You are currently not taking this course.");
                                    break;
                                }
                                course.printCourseInfo();
                                System.out.println("You are currently registered to index number " + student.fromDict(code1));
                                System.out.println("Enter the new index number that you wish to switch to: ");
                                int newIndexNum = scanner.nextInt();
                                if(newIndexNum==student.fromDict(code1)){
                                    System.out.println("You are already registered to this index");
                                    break;
                                }
                                TimeSlot newIndex = course.findTimeslot(newIndexNum);
                                if(newIndex==null){
                                    System.out.println("Please enter a valid index number.");
                                    break;
                                }
                                studentManager.dropCourse(code1, course, student);
                                studentManager.addCourse(student, newIndex, course);
                                studentManager.getStudentDetails().checkCoursesRegistered(student);
                                break;
                            case 6:
                                studentManager.getStudentDetails().checkCoursesRegistered(student);
                                System.out.println("Enter the course code of the course for which you wish to swap index number: ");
                                String code2 = scanner.next();
                                Course course1 = student.findCourse(code2);
                                if(course1==null){
                                    System.out.println("You are currently not taking this course.");
                                    break;
                                }
                                boolean empty = courseDet.printStudentsByCourse(course1);
                                if(empty==true){
                                    System.out.println("There are no students taking this course.");
                                    break;
                                }
                                System.out.println("Enter the username of the student you wish to swap index with.");
                                String username1 = scanner.next();
                                Student friend = course1.findStudent(username1);
                                if(friend==null){
                                    System.out.println("This student is currently not taking this course");
                                    break;
                                }
                                String friendPassword = new String(console.readPassword("Please get this student to enter his/her password: "));
                                boolean loginStatus = loginMgr.authenticateStudentLogin(username1, friendPassword);
                                if(loginStatus==false){
                                    System.out.println("Wrong password.");
                                    break;
                                }
                                studentManager.swapCourse(student, friend, course1);
                                System.out.println("User " + student.getUsername() + " is registered to " + code2 + ", index " + student.fromDict(code2));
                                System.out.println("User " + friend.getUsername() + " is registered to " + code2 + ", index " + friend.fromDict(code2));
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
            }
            else{
                // authenticate admin login using LoginMgr
                loginSuccess = loginMgr.authenticateAdminLogin(username, password);
                if(loginSuccess==true){
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
                                        Course c = courseRecs.findCourse(csCode);
                                        if(c!=null){
                                            System.out.println("Course already exists.");
                                            break;
                                        }
                                        System.out.println("Course name: ");
                                        scanner.nextLine();
                                        String name = scanner.nextLine();
                                        System.out.println("School: ");
                                        String sch = scanner.next();
                                        System.out.println("Enter the number of AUs for this course: ");
                                        int AU = scanner.nextInt();
                                        System.out.println("Enter the number of timeslots for this course: ");
                                        int numIndex = scanner.nextInt();
                                        ArrayList<Integer> indexList= new ArrayList<Integer>();
                                        ArrayList<Integer> vacList = new ArrayList<Integer>();
                                        for(int i=0; i<numIndex; i++){
                                            System.out.println("Enter the index number of timeslot " + (i+1) + ": ");
                                            int indexNo = scanner.nextInt();
                                            indexList.add(indexNo);
                                            System.out.println("Enter the number of vacancies in timeslot " + (i+1) + ": ");
                                            int vacancy = scanner.nextInt();
                                            vacList.add(vacancy);
                                        }
                                        courseRecs.addCourse(csCode, name, sch, AU, indexList, vacList);
                                    }
                                    else{
                                        // update a course
                                        System.out.println("Enter the course code of the course you wish to update: ");
                                        String code = scanner.next();
                                        Course c = courseRecs.findCourse(code);
                                        if(c==null)
                                            break;
                                        int selection;
                                        do{
                                            c.printCourseInfo();
                                            System.out.println("=====================");
                                            System.out.println("1. Edit Course Code  ");
                                            System.out.println("2. Edit Course Name  ");
                                            System.out.println("3. Edit Course School");
                                            System.out.println("4. Edit no. of AUs   ");
                                            System.out.println("5. Edit Index Number ");
                                            System.out.println("6. Finished updating ");
                                            System.out.println("=====================");
                                            selection = scanner.nextInt();
                                            switch(selection){
                                                case 1:
                                                    System.out.println("Enter the new course code: ");
                                                    String courseCode = scanner.next();
                                                    c.setCourseCode(courseCode);
                                                    break;
                                                case 2:
                                                    System.out.println("Enter the new course name: ");
                                                    scanner.nextLine();
                                                    String name = scanner.nextLine();
                                                    c.setCourseName(name);
                                                    break;
                                                case 3:
                                                    System.out.println("Enter the new course school: ");
                                                    String sch = scanner.next();
                                                    c.setSchool(sch);
                                                    break;
                                                case 4:
                                                    System.out.println("Enter the new no. of AUs: ");
                                                    int au = scanner.nextInt();
                                                    c.setAUs(au);
                                                    break;
                                                case 5:
                                                    System.out.println("Enter the index number that you wish to edit: ");
                                                    int indexNum = scanner.nextInt();
                                                    TimeSlot t = c.findTimeslot(indexNum);
                                                    if(t==null){
                                                        System.out.println("Index number not found.");
                                                        break;
                                                    }
                                                    System.out.println("Enter the new index number: ");
                                                    int newIndex = scanner.nextInt();
                                                    if(c.findTimeslot(newIndex)!=null){
                                                        System.out.println("The timeslot that you entered already exists. Please enter a different one.");
                                                        break;
                                                    }
                                                    System.out.println("Enter the new number of vacancies: ");
                                                    int newVac = scanner.nextInt();
                                                    t.setIndexNum(newIndex);
                                                    t.setVacancy(newVac);
                                                    break;
                                                case 6:
                                                    // print all course info
                                                    c.printCourseInfo();
                                                    System.out.println("Full course list: ");
                                                    courseRecs.printAllCourses();
                                                    break;
                                                default:
                                                    System.out.println("Please input an integer from 1-5");
                                            }
                                        }while(selection!=6);
                                    }
                                } while(choice2!=1 && choice2!=2);
                                break;
                            case 4:
                                System.out.println("Full course list: ");
                                courseRecs.printAllCourses();
                                System.out.println("Enter the course code of the course: ");
                                String courseCode = scanner.next();
                                Course c = courseRecs.findCourse(courseCode);
                                if(c!=null){
                                    c.printCourseInfo();
                                    System.out.println("Enter the index number: ");
                                    int index = scanner.nextInt();
                                    courseDet.checkAvailibility(c, index);
                                }
                                break;
                            case 5:
                                System.out.println("Full course list: ");
                                courseRecs.printAllCourses();
                                System.out.println("Enter the course code of the course: ");
                                String cCode = scanner.next();
                                Course c1 = courseRecs.findCourse(cCode);
                                if(c1!=null){
                                    c1.printCourseInfo();
                                    System.out.println("Enter the index number: ");
                                    int indexNum = scanner.nextInt();
                                    courseDet.printStudentsByIndexNum(c1, indexNum);
                                }
                                break;
                            case 6:
                                System.out.println("Full course list: ");
                                courseRecs.printAllCourses();
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
            }
            //scanner.close();
        }
        //scanner.close();
    }
}
