package epam.pratsaunik.tickets.command.impl;


import epam.pratsaunik.tickets.command.AbstractCommand;
import epam.pratsaunik.tickets.command.RequestContent;
import epam.pratsaunik.tickets.dao.ColumnName;
import epam.pratsaunik.tickets.email.EmailSender;
import epam.pratsaunik.tickets.hash.PasswordHash;
import epam.pratsaunik.tickets.servlet.ParameterName;
import epam.pratsaunik.tickets.util.ConfigurationManager;
import epam.pratsaunik.tickets.entity.Role;
import epam.pratsaunik.tickets.entity.User;
import epam.pratsaunik.tickets.exception.ServiceLevelException;
import epam.pratsaunik.tickets.service.Service;
import epam.pratsaunik.tickets.service.impl.UserServiceImpl;
import epam.pratsaunik.tickets.servlet.AttributeName;
import epam.pratsaunik.tickets.validator.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

public class RegisterCommand extends AbstractCommand {
    private final static Logger log = LogManager.getLogger();
    public RegisterCommand(Service service) {
        super(service);
    }

    @Override
    public String execute(RequestContent content) {
        String page;

        if (!Validator.validateUser(content)){
            return ConfigurationManager.getInstance().getProperty(ConfigurationManager.REGISTRATION_PAGE_PATH);
        }

        User user = new User();
        user.setEmail(content.getRequestParameter(ParameterName.USER_EMAIL));
        user.setName(content.getRequestParameter(ParameterName.USER_NAME));
        user.setSurname(content.getRequestParameter(ParameterName.USER_SURNAME));
        String password=content.getRequestParameter(ParameterName.USER_PASSWORD);
        user.setPassword(password);//FIXME
        user.setLogin(content.getRequestParameter(ParameterName.USER_LOGIN));
        user.setRole(Role.valueOf(content.getRequestParameter(ParameterName.USER_ROLE)));
        try {
            long result=((UserServiceImpl) service).create(user);
            if(result>0){
                EmailSender emailSender = new EmailSender();
                emailSender.sendConfirmation(user.getEmail());
            }
            log.debug("user created");
            page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.LOGIN_PAGE_PATH);
        } catch (ServiceLevelException e) {
            log.error("Exception in RegisterCommand "+e);
            page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.ERROR_PAGE_PATH);
        }
        return page;
    }
}
