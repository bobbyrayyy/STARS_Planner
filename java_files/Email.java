import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Email implements Notification {
 String username = "g22002stars@gmail.com"; // to be added
	 String password ="G2!2002stars!";
	private Properties props; 
    private Session session;
    private Student student; 

    //initialize email class with student
    public Email(Student student){
		this.student = student;
		this.props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		this.session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
	});
}

    @Override
    public void addNotification(Student student, Course course) {
        
		try {
			Message message = new MimeMessage(this.session);
			message.setFrom(new InternetAddress("from-email@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(this.student.getEmail())); // to be added an email addr
			message.setSubject("Course added");
			message.setText("You have successfully added a course Details of the course are as follows: " + "\nCourse name: "+ course.getName() +
			"\nCourse number: "+ course.getCourseCode() +
			"\nIndex number: "+ student.fromDict(course.getCourseCode()));

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

    }
	public void dropNotification(Course course, int indexDropped, Student student) {
        
		try {
			Message message = new MimeMessage(this.session);
			message.setFrom(new InternetAddress("from-email@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(this.student.getEmail())); // to be added an email addr
			message.setSubject("Course dropped");
			message.setText("You have successfully dropped a course. Details of the course are as follows: " + "\nCourse name: "+ course.getName() +
			"\nCourse number: "+ course.getCourseCode() +
			"\nIndex number: "+ String.valueOf(indexDropped));

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

    }

	@Override
	public void accessPeriodNotification(Student student) {
		 
		try {
			Message message = new MimeMessage(this.session);
			message.setFrom(new InternetAddress("from-email@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(this.student.getEmail())); // to be added an email addr
			message.setSubject("Access period changed");
			message.setText("This email is sent to notify you that your access period for the Stars Planner system has been changed" + "\nYour new access period is: " + student.getAddDropPeriod());

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}


//notify when add student (Student, Courseno., indexno.)
//notify when student drop course (Student, Courseno., indexno. )
//notify when student access period change (Student)