import java.text.ParseException;

public abstract class User {
    // instance variables
    private String username;
    private String hashedPassword;
    private String name;

    // getters and setters
    public String getUsername(){return username;}
    public void setUsername(String username){this.username = username;}

    public String getPassword(){return hashedPassword;}
    public void setPassword(String pw){hashedPassword = pw;}

    public String getName(){return name;}
    public void setName(String name){this.name = name;}

    // abstract method to authenticate password
    public abstract boolean authenticateLogin(String username, String password) throws ParseException;
}
