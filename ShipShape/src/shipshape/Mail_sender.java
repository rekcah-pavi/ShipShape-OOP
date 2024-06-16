package shipshape;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class Mail_sender {

    private Properties properties;
    private Session session;


    public Mail_sender() {

    	String smtp_host= "smtp-pavi.alwaysdata.net";
    	String smtp_port="587";
    	String mail_username="pavi@alwaysdata.net";
    	String mail_password= "Ptest123@";
    	
    	
    	
        properties = new Properties();
        properties.put("mail.smtp.host", smtp_host);
        properties.put("mail.smtp.port", smtp_port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        final String username = mail_username;
        final String password = mail_password;

        session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }

    public void send(String recipient, String subject, String messageBody) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("ShipShape@rk"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject(subject);
            message.setText(messageBody);

            Transport.send(message);

            System.out.println("Email sent successfully to " + recipient);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Mail_sender mailSender = new Mail_sender();
        mailSender.send("rkpavi06@gmail.com", "Test Email from Java", "Hello, this is a test email sent from a Java!");
    }
}
