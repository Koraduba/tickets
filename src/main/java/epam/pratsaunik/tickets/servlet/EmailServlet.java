package epam.pratsaunik.tickets.servlet;

import epam.pratsaunik.tickets.connection.ProxyConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

public class EmailServlet extends HttpServlet {
    private final static Logger log = LogManager.getLogger();

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        final String from = "pradstaunik@gmail.com";
        final String password = "Mis51Dm1";

        Properties properties = new Properties();
        ServletContext context =getServletContext();
        String file = context.getInitParameter("mail");
        properties.load(context.getResourceAsStream(file));
        Session session = Session.getDefaultInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(from, password);
                    }
                });
        log.info("Session for email OK");

        try {
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(from));
            msg.addRecipient(Message.RecipientType.TO,
                    new InternetAddress("pratsaunik@gmail.com"));
            msg.setSubject("JavaMail hello world example");
            msg.setSentDate(new Date());
            msg.setText("Hello, world!\n");
            Transport.send(msg);
        } catch (MessagingException mex) {
            log.info("send failed, exception: " + mex);
        }
        request.getRequestDispatcher("/WEB-INF/jsp/send.jsp").forward(request, response);
    }
}
