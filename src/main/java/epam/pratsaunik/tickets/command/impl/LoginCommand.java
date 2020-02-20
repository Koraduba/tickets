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
import epam.pratsaunik.tickets.util.ConfigurationManager;
import epam.pratsaunik.tickets.util.ConfigurationManager2;
import epam.pratsaunik.tickets.util.MessageType;
import epam.pratsaunik.tickets.util.MessageManager;
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
        log.info("LoginCommand");
        String login = content.getRequestParameter("login");
        String password = content.getRequestParameter("password");
        boolean hasAccount;
        try {
            List<User> user = ((UserServiceImpl) service).findUserByLogin(login);
            if (user.isEmpty()) {
                log.info("No user with such login");
                hasAccount = false;
            } else {
                hasAccount = ((UserServiceImpl) service).checkUser(login, password, user.get(0));
            }
            if (hasAccount) {
                content.setSessionAttribute(AttributeName.USER_ROLE,user.get(0).getRole().toString());
                Locale rus = new Locale("ru", "RU");
                content.setSessionAttribute(AttributeName.LOCALE, rus);
                content.setSessionAttribute(AttributeName.USER, user.get(0));
                commandResult.setResponsePage(ConfigurationManager2.HOME_PAGE_PATH.getProperty());
                commandResult.setResponseType(CommandResult.ResponseType.FORWARD);
            } else {
                content.setRequestAttribute("errorLoginPassMessage", MessageManager.INSTANCE.getProperty(MessageType.NO_SUCH_USER));
                commandResult.setResponsePage(ConfigurationManager2.LOGIN_PAGE_PATH.getProperty());
                commandResult.setResponseType(CommandResult.ResponseType.FORWARD);
            }
        } catch (ServiceLevelException e) {
            throw new CommandException(e);
        }
        return commandResult;

    }
}