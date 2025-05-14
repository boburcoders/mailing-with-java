import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

public class MailingWithMailtrap {
    public static void main(String[] args) {
        Properties properties = getProperties();

        String password = "528879c29f28a1";
        String userName = "6ed281ed2a2c82";
        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        });
        try {
            MimeMessage message = new MimeMessage(session);

            Multipart multipart = new MimeMultipart();
            BodyPart attachment = new MimeBodyPart();
            attachment.setFileName("MyCv.txt");
            DataSource source = new FileDataSource("cv.txt");
            DataHandler dh = new DataHandler(source);
            attachment.setDataHandler(dh);

            BodyPart textPart = new MimeBodyPart();
            String bodyText = "Hello World";  // agar html da image yubormoqchi bolsak base64 orqali baytes ga ogirib keyin yuboramiz
            textPart.setContent(bodyText, "text/html");


            multipart.addBodyPart(attachment);
            multipart.addBodyPart(textPart);
            message.setContent(multipart);
            message.setSubject("Hello From Bobur");
            message.setText("This is text");
            message.setFrom(new InternetAddress(userName));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress("mail@gmail.com"));
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

    private static Properties getProperties() {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "sandbox.smtp.mailtrap.io");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.auth", "true");
        return properties;
    }
}