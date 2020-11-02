// entity class that stores Admin's details
public class Admin extends User{
    // constructor
    public Admin(String username, String pw, String name){
        setUsername(username);
        setPassword(pw);
        setName(name);
    }

    // method to authenticate admin's login
    public boolean authenticateLogin(String username, String password){
        boolean correctPW = false;
        PasswordAuthentication1 p = new PasswordAuthentication1();

        // compare the corresponding passwords
        correctPW = p.authenticate(password.toCharArray(), getPassword());
        // if the end of the for loop is reached, and currentUser is still null, it means that username was not found in database

        // if wrong password was entered
        if(correctPW==false){
            System.out.println("Wrong password.");
            return false;
        }
        else{
            System.out.println("Login successful.");
            return true;
        }
    }
}
