package epam.pratsaunik.tickets.command.impl;

import epam.pratsaunik.tickets.command.AbstractCommand;
import epam.pratsaunik.tickets.command.CommandResult;
import epam.pratsaunik.tickets.command.RequestContent;
import epam.pratsaunik.tickets.entity.User;
import epam.pratsaunik.tickets.exception.CommandException;
import epam.pratsaunik.tickets.exception.ServiceLevelException;
import epam.pratsaunik.tickets.service.Service;
import epam.pratsaunik.tickets.service.impl.UserServiceImpl;
import epam.pratsaunik.tickets.servlet.AttributeName;
import epam.pratsaunik.tickets.servlet.ParameterName;
import epam.pratsaunik.tickets.util.*;
import jdk.jfr.Event;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Locale;

public class LoginCommand extends AbstractCommand {

    private final static Logger log = LogManager.getLogger();

    public LoginCommand(Service service) {
        super(service);
    }

    @Override
    public CommandResult execute(RequestContent content) throws CommandException {
        CommandResult commandResult=new CommandResult();
        log.info("LoginCommand works");
        String login = content.getRequestParameter(ParameterName.USER_LOGIN);
        String password = content.getRequestParameter(ParameterName.USER_PASSWORD);
        boolean hasAccount;
        try {
            List<User> user = ((UserServiceImpl) service).findUserByLogin(login);
            if (user.isEmpty()) {
                hasAccount = false;
            } else {
                hasAccount = ((UserServiceImpl) service).checkUser(login, password, user.get(0));
            }
            if (hasAccount) {
                content.setSessionAttribute(AttributeName.USER_ROLE,user.get(0).getRole().toString());
                MessageManager.INSTANCE.changeResource(LocaleName.LOCALE_EN);
                content.setSessionAttribute(AttributeName.LOCALE, LocaleName.LOCALE_EN);
                content.setSessionAttribute(AttributeName.USER, user.get(0));
                commandResult.setResponsePage(ConfigurationManager2.HOME_PAGE_PATH.getProperty());
                commandResult.setResponseType(CommandResult.ResponseType.FORWARD);
                log.debug("LoginCommand. user:"+user.get(0));
            } else {
                content.setRequestAttribute(AttributeName.ERROR_LOGIN_PASS_MESSAGE, MessageManager.INSTANCE.getProperty(MessageType.NO_SUCH_USER));
                commandResult.setResponsePage(ConfigurationManager2.LOGIN_PAGE_PATH.getProperty());
                commandResult.setResponseType(CommandResult.ResponseType.FORWARD);
            }
        } catch (ServiceLevelException e) {
            throw new CommandException(e);
        }


        return commandResult;
    }
}