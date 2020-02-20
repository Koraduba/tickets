package epam.pratsaunik.tickets.command.impl;


import epam.pratsaunik.tickets.command.AbstractCommand;
import epam.pratsaunik.tickets.command.CommandResult;
import epam.pratsaunik.tickets.command.RequestContent;
import epam.pratsaunik.tickets.dao.ColumnName;
import epam.pratsaunik.tickets.email.EmailSender;
import epam.pratsaunik.tickets.exception.CommandException;
import epam.pratsaunik.tickets.hash.PasswordHash;
import epam.pratsaunik.tickets.servlet.ParameterName;
import epam.pratsaunik.tickets.util.ConfigurationManager;
import epam.pratsaunik.tickets.entity.Role;
import epam.pratsaunik.tickets.entity.User;
import epam.pratsaunik.tickets.exception.ServiceLevelException;
import epam.pratsaunik.tickets.service.Service;
import epam.pratsaunik.tickets.service.impl.UserServiceImpl;
import epam.pratsaunik.tickets.servlet.AttributeName;
import epam.pratsaunik.tickets.util.ConfigurationManager2;
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
    public CommandResult execute(RequestContent content) throws CommandException {
        CommandResult commandResult=new CommandResult();
        if (!Validator.validateUser(content)){
            commandResult.setResponsePage(ConfigurationManager2.REGISTRATION_PAGE_PATH.getProperty());
            commandResult.setResponseType(CommandResult.ResponseType.FORWARD);
            return commandResult;
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
            commandResult.setResponsePage(ConfigurationManager2.HOME_PAGE_PATH.getProperty());
            commandResult.setResponseType(CommandResult.ResponseType.FORWARD);
        } catch (ServiceLevelException e) {
            throw new CommandException(e);
        }
        return commandResult;
    }
}
