// entity class that stores Admin's details
public class Admin {
    // instance variables
    private String username;
    private String hashedPassword;
    private String name;

    // constructor
    public Admin(String username, String pw, String name){
        setUsername(username);
        setPassword(pw);
        setName(name);
    }

    // getters and setters
    public String getUsername(){return username;}
    public void setUsername(String username){this.username = username;}

    public String getPassword(){return hashedPassword;}
    public void setPassword(String pw){hashedPassword = pw;}

    public String getName(){return name;}
    public void setName(String name){this.name = name;}
}
