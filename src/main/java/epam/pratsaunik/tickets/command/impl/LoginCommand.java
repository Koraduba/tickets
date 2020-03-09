package epam.pratsaunik.tickets.command.impl;

import epam.pratsaunik.tickets.command.AbstractCommand;
import epam.pratsaunik.tickets.command.CommandResult;
import epam.pratsaunik.tickets.command.CommandType;
import epam.pratsaunik.tickets.command.RequestContent;
import epam.pratsaunik.tickets.entity.User;
import epam.pratsaunik.tickets.exception.ServiceLevelException;
import epam.pratsaunik.tickets.service.Service;
import epam.pratsaunik.tickets.service.impl.UserServiceImpl;
import epam.pratsaunik.tickets.servlet.AttributeName;
import epam.pratsaunik.tickets.servlet.ParameterName;
import epam.pratsaunik.tickets.util.ConfigurationManager2;
import epam.pratsaunik.tickets.util.LocaleName;
import epam.pratsaunik.tickets.util.MessageManager;
import epam.pratsaunik.tickets.util.MessageType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Class{@code LoginCommand} is used for user authentication
 *
 * @version 1.0
 * @see AbstractCommand
 */
public class LoginCommand extends AbstractCommand {

    private final static Logger log = LogManager.getLogger();

    public LoginCommand(Service service) {
        super(service);
    }

    /**
     * @param content{@code RequestContent} instance to provide request parameters ans session attributes access
     * @return {@code CommandResult} instance with information about response type and further destination page
     * @throws CommandException custom exception to be thrown in case of exception on service level
     * @see RequestContent
     * @see CommandResult
     */
    @Override
    public CommandResult execute(RequestContent content) {
        CommandResult commandResult = new CommandResult();
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
                content.setSessionAttribute(AttributeName.USER_ROLE, user.get(0).getRole().toString());
                MessageManager.INSTANCE.changeResource(LocaleName.LOCALE_EN);
                content.setSessionAttribute(AttributeName.LOCALE, LocaleName.LOCALE_EN);
                content.setSessionAttribute(AttributeName.USER, user.get(0));
                commandResult.setResponsePage(ConfigurationManager2.HOME_PAGE_PATH.getProperty());
                commandResult.setResponseType(CommandResult.ResponseType.FORWARD);
                log.debug("LoginCommand. user:" + user.get(0));
            } else {
                content.setRequestAttribute(AttributeName.ERROR_LOGIN_PASS_MESSAGE, MessageManager.INSTANCE.getProperty(MessageType.NO_SUCH_USER));
                commandResult.setResponsePage(ConfigurationManager2.LOGIN_PAGE_PATH.getProperty());
                commandResult.setResponseType(CommandResult.ResponseType.FORWARD);
            }
        } catch (ServiceLevelException e) {
            log.error(e);
            content.setRequestAttribute(AttributeName.COMMAND, CommandType.LOGIN_PAGE.toString());
            commandResult.setResponsePage(ConfigurationManager2.ERROR_PAGE_PATH.getProperty());
            commandResult.setResponseType(CommandResult.ResponseType.FORWARD);
            return commandResult;
        }


        return commandResult;
    }
}