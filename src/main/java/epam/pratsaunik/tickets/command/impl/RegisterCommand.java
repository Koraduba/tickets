package epam.pratsaunik.tickets.command.impl;


import epam.pratsaunik.tickets.command.AbstractCommand;
import epam.pratsaunik.tickets.command.CommandResult;
import epam.pratsaunik.tickets.command.RequestContent;
import epam.pratsaunik.tickets.email.EmailSender;
import epam.pratsaunik.tickets.entity.Role;
import epam.pratsaunik.tickets.entity.User;
import epam.pratsaunik.tickets.exception.CommandException;
import epam.pratsaunik.tickets.exception.ServiceLevelException;
import epam.pratsaunik.tickets.service.Service;
import epam.pratsaunik.tickets.service.impl.UserServiceImpl;
import epam.pratsaunik.tickets.servlet.AttributeName;
import epam.pratsaunik.tickets.servlet.ParameterName;
import epam.pratsaunik.tickets.util.ConfigurationManager2;
import epam.pratsaunik.tickets.util.InputKeeper;
import epam.pratsaunik.tickets.util.MessageManager;
import epam.pratsaunik.tickets.util.MessageType;
import epam.pratsaunik.tickets.validator.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Locale;

/**
 * Class{@code RegisterCommand} is used to create and save new user in data base
 *
 * @version 1.0
 * @see AbstractCommand
 */
public class RegisterCommand extends AbstractCommand {
    private final static Logger log = LogManager.getLogger();

    public RegisterCommand(Service service) {
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
    public CommandResult execute(RequestContent content) throws CommandException {
        CommandResult commandResult = new CommandResult();
        InputKeeper.getInstance().keepUser(content);
        if (!Validator.validateUser(content)) {
            commandResult.setResponsePage(ConfigurationManager2.REGISTRATION_PAGE_PATH.getProperty());
            commandResult.setResponseType(CommandResult.ResponseType.FORWARD);
            return commandResult;
        }
        String login = content.getRequestParameter(ParameterName.USER_LOGIN);
        List<User> users;
        try {
            users = ((UserServiceImpl) service).findUserByLogin(login);
        } catch (ServiceLevelException e) {
            throw new CommandException(e);
        }
        if (!users.isEmpty()) {
            commandResult.setResponsePage(ConfigurationManager2.REGISTRATION_PAGE_PATH.getProperty());
            commandResult.setResponseType(CommandResult.ResponseType.FORWARD);
            content.setRequestAttribute(AttributeName.ERROR_USER_LOGIN_MESSAGE, MessageManager.INSTANCE.getProperty(MessageType.USER_EXISTS));
            return commandResult;
        }
        User user = new User();
        user.setEmail(content.getRequestParameter(ParameterName.USER_EMAIL));
        user.setName(content.getRequestParameter(ParameterName.USER_NAME));
        user.setSurname(content.getRequestParameter(ParameterName.USER_SURNAME));
        String password = content.getRequestParameter(ParameterName.USER_PASSWORD);
        user.setPassword(password);
        user.setLogin(login);
        user.setRole(Role.valueOf(content.getRequestParameter(ParameterName.USER_ROLE)));
        try {
            long result = ((UserServiceImpl) service).create(user);
            if (result > 0) {
                EmailSender emailSender = new EmailSender();
                emailSender.sendConfirmation(user.getEmail(), "confirmation", "Your account is created\n");
            }
            String role = (String) content.getSessionAttribute(AttributeName.USER_ROLE);
            if (role == null) {
                content.setSessionAttribute(AttributeName.USER, user);
                content.setSessionAttribute(AttributeName.USER_ROLE, user.getRole().toString());
                content.setSessionAttribute(AttributeName.LOCALE, Locale.getDefault());
            }
            content.setSessionAttribute(AttributeName.HOME_MESSAGE, MessageManager.INSTANCE.getProperty(MessageManager.INSTANCE.ACCOUNT_CREATED_MESSAGE));
            commandResult.setResponsePage(ConfigurationManager2.HOME_PAGE_PATH.getProperty());
            commandResult.setResponseType(CommandResult.ResponseType.REDIRECT);
        } catch (ServiceLevelException e) {
            throw new CommandException(e);
        }
        return commandResult;
    }
}
