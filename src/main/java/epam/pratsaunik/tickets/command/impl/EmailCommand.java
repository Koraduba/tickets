package epam.pratsaunik.tickets.command.impl;

import epam.pratsaunik.tickets.command.AbstractCommand;
import epam.pratsaunik.tickets.command.CommandResult;
import epam.pratsaunik.tickets.command.RequestContent;
import epam.pratsaunik.tickets.service.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

public class EmailCommand extends AbstractCommand {
    private final static Logger log = LogManager.getLogger();

    public EmailCommand(Service service) {
        super(service);
    }

    @Override
    public CommandResult execute(RequestContent content) {

        CommandResult commandResult=new CommandResult();
        final String from = "pradstaunik@gmail.com";
        final String password = "Mis51Dm1";

        Properties properties = new Properties();
        String file = "/WEB-INF/classes/mail.properties";
        try {
            properties.load(new FileInputStream(file));
        } catch (IOException e) {
            e.printStackTrace();//TODO
        }
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
            log.info("send failed, exception: ", mex);
        }
        return commandResult;
    }
}
