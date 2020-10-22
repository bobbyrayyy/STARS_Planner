public class Main {
    public static void main(String[] args){
        PasswordAuthentication p = new PasswordAuthentication();
        String pw = "adminPW3";
        String hashed = p.hash(pw.toCharArray());
        System.out.println(hashed);
    }
}
