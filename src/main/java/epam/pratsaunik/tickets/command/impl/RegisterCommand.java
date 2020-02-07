package epam.pratsaunik.tickets.command.impl;


import epam.pratsaunik.tickets.command.AbstractCommand;
import epam.pratsaunik.tickets.command.RequestContent;
import epam.pratsaunik.tickets.dao.ColumnName;
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

import java.util.List;
import java.util.Locale;

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
            ((UserServiceImpl) service).create(user);
            log.debug("user created");
            List<User> list = ((UserServiceImpl) service).findAllUsers();
            log.debug("users found");
            content.setSessionAttribute(AttributeName.USERS, list);//FIXME
            content.setSessionAttribute(AttributeName.USER, user);
            content.setSessionAttribute(AttributeName.LOCALE,Locale.getDefault());
            page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.CATALOG_PAGE_PATH);
        } catch (ServiceLevelException e) {
            log.error("Exception in RegisterCommand "+e);
            page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.ERROR_PAGE_PATH);
        }
        return page;

    }
}
