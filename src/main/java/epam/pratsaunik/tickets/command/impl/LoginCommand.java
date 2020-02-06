package epam.pratsaunik.tickets.command.impl;

import epam.pratsaunik.tickets.command.AbstractCommand;
import epam.pratsaunik.tickets.command.RequestContent;
import epam.pratsaunik.tickets.entity.User;
import epam.pratsaunik.tickets.exception.ServiceLevelException;
import epam.pratsaunik.tickets.service.Service;
import epam.pratsaunik.tickets.service.impl.UserServiceImpl;
import epam.pratsaunik.tickets.servlet.AttributeName;
import epam.pratsaunik.tickets.util.ConfigurationManager;
import epam.pratsaunik.tickets.util.MessageType;
import epam.pratsaunik.tickets.util.MessageManager;
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
    public String execute(RequestContent content) {
        log.info("LoginCommand");
        String page;
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
                content.setRequestAttribute(AttributeName.USER_ROLE,user.get(0).getRole().toString());
                Locale rus = new Locale("ru", "RU");
                content.setSessionAttribute(AttributeName.LOCALE, rus);
                content.setSessionAttribute(AttributeName.USER, user.get(0));
                page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.CATALOG_PAGE_PATH);
            } else {
                content.setRequestAttribute("errorLoginPassMessage", MessageManager.INSTANCE.getProperty(MessageType.NO_SUCH_USER));
                page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.LOGIN_PAGE_PATH);
            }
        } catch (ServiceLevelException e) {
            log.error("Exception in LoginCommand"+e);
            page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.ERROR_PAGE_PATH);
        }
        return page;

    }
}