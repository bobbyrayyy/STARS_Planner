import java.util.ArrayList;

// entity class that holds an array of all students
public class StudentRecords {
    // instance variables
    private ArrayList<Student> students = new ArrayList<Student>();

    // constructor
    public StudentRecords(){}

    // get method for the arraylist of students
    public ArrayList<Student> getStudents() {
        return students;
    }

    // set method for the arraylist of students
    public void setStudents(ArrayList<Student> myList){
        students = myList;
    }

    // method to print all students
    public void printAllStudents(){
        // print the full list of students with their corresponding access period
        System.out.println("Full list of students after access period has been edited:");
        for(int i=0; i<students.size(); i++){
            Student s = students.get(i);
            System.out.println("Name: "+s.getName()+", Matric Number: " + s.getMatricNum() + ", Access Period: "+ s.getAddDropPeriod());
        }
    }

    // method to find and return student instance
    public Student findStudent(String usernm){
        // search through the array to find the correct student
        for(int i=0; i<students.size(); i++){
            Student s = students.get(i);
            if(s.getUsername().equals(usernm))
                return s;
        }
        // at this point, student still not found
        System.out.println("Student not found in school's records");
        return null;
    }

    // method to add student
    public void addStudent(String usernm, String nm, String matric, String g, String nat, String accessPer) {
        // error handle matric number
        // assuming that all matric numbers must be of length 9
        if(matric.length()!=9){
            System.out.println("Please enter a valid matriculation number.");
            return;
        }
        //assuming that all matric numbers must start and end with a capital letter
        if(!(matric.charAt(0)>='A' && matric.charAt(0)<='Z') || !(matric.charAt(8)>='A' && matric.charAt(8)<='Z')){
            System.out.println("Please enter a valid matriculation number.");
            return;
        }
        // check if student already exists
        for(int i=0; i<students.size(); i++){
            if(matric.equals(students.get(i).getMatricNum())){
                System.out.println("This student already exists.");
                return;
            }
        }
        // error handle gender
        if(!g.equals("Female") && !g.equals("Male")){
            System.out.println("Please enter a valid gender.");
            return;
        }
        // error handle access period
        ErrorHandler e = new ErrorHandler();
        boolean x = e.errorHandlePeriod(accessPer);
        if(x==false)
            return; 
        
        PasswordAuthentication p = new PasswordAuthentication();
        // when a student is added, the default password will be the same as his/her username
        String hashedPW = p.hash(usernm.toCharArray());
        // creating a new student
        Student newStudent = new Student(usernm, hashedPW, nm, matric, g, nat, accessPer);
        // adding new student to array list
        students.add(newStudent);

        // print the new list of students
        System.out.println("New full list of students after addition:");
        for(int i=0; i<students.size(); i++){
            System.out.println("Name: "+students.get(i).getName()+", Matric Number: " + students.get(i).getMatricNum() + ", Access Period: "+ students.get(i).getAddDropPeriod());
        }
        return;
    }
}

