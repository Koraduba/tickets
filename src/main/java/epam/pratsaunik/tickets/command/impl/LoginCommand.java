package epam.pratsaunik.tickets.command.impl;

import com.sun.org.apache.xml.internal.utils.ThreadControllerWrapper;
import epam.pratsaunik.tickets.command.AbstractCommand;
import epam.pratsaunik.tickets.command.RequestContent;
import epam.pratsaunik.tickets.entity.Role;
import epam.pratsaunik.tickets.entity.User;
import epam.pratsaunik.tickets.exception.ServiceLevelException;
import epam.pratsaunik.tickets.service.Service;
import epam.pratsaunik.tickets.service.impl.UserServiceImpl;
import epam.pratsaunik.tickets.servlet.AttributeName;
import epam.pratsaunik.tickets.util.ConfigurationManager;
import epam.pratsaunik.tickets.util.MessageManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
            List<User> user = ((UserServiceImpl) service).findUserByLogin(login);//todo
            if (user.isEmpty()) {
                log.debug("not found user with such login");
                hasAccount = false;
            } else {
                log.debug(password);
                log.debug(user.get(0).getPassword());
                hasAccount = ((UserServiceImpl) service).checkUser(login, password, user.get(0));
            }

            if (hasAccount) {
                Role role=user.get(0).getRole();

                content.setRequestAttribute(AttributeName.USER_ROLE,role.toString());
                Locale rus = new Locale("ru", "RU");
                content.setSessionAttribute(AttributeName.LOCALE, rus);
                content.setSessionAttribute(AttributeName.USER, user.get(0));
                log.debug(role);
                page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.LOGIN_PAGE_PATH);
            } else {
                content.setRequestAttribute("errorLoginPassMessage", MessageManager.NO_SUCH_USER);
                page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.LOGIN_PAGE_PATH);
            }
        } catch (ServiceLevelException e) {
            log.error("Exception in LoginCommand"+e);
            page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.ERROR_PAGE_PATH);
        }
        return page;

    }
}