package epam.pratsaunik.tickets.command.impl;

import epam.pratsaunik.tickets.command.AbstractCommand;
import epam.pratsaunik.tickets.command.CommandResult;
import epam.pratsaunik.tickets.command.CommandType;
import epam.pratsaunik.tickets.command.RequestContent;
import epam.pratsaunik.tickets.email.EmailSender;
import epam.pratsaunik.tickets.entity.User;
import epam.pratsaunik.tickets.exception.ServiceLevelException;
import epam.pratsaunik.tickets.service.Service;
import epam.pratsaunik.tickets.service.impl.UserServiceImpl;
import epam.pratsaunik.tickets.servlet.AttributeName;
import epam.pratsaunik.tickets.servlet.ParameterName;
import epam.pratsaunik.tickets.util.ConfigurationManager2;
import epam.pratsaunik.tickets.util.MessageManager;
import epam.pratsaunik.tickets.validator.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Class{@code NewPasswordCommand} is used to change user password
 *
 * @version 1.0
 * @see AbstractCommand
 */
public class NewPasswordCommand extends AbstractCommand {
    private static final Logger log = LogManager.getLogger();

    public NewPasswordCommand(Service service) {
        super(service);
    }

    /**
     * @param content{@code RequestContent} instance to provide request parameters ans session attributes access
     * @return {@code CommandResult} instance with information about response type and further destination page
     * @see RequestContent
     * @see CommandResult
     */
    @Override
    public CommandResult execute(RequestContent content)  {
        CommandResult commandResult = new CommandResult();
        UserServiceImpl userService = (UserServiceImpl) service;
        content.setSessionAttribute(AttributeName.ERROR_CHANGE_PASSWORD_MESSAGE,null);
        content.setSessionAttribute(AttributeName.ERROR_USER_PASSWORD_MESSAGE,null);

        User user = ((User) content.getSessionAttribute(AttributeName.USER));
        String oldPassword = content.getRequestParameter(ParameterName.OLD_PASSWORD);
        String newPassword = content.getRequestParameter(ParameterName.NEW_PASSWORD);
        String login = user.getLogin();
        boolean hasAccess = userService.checkUser(login, oldPassword, user);
        if (!hasAccess) {
            content.setSessionAttribute(AttributeName.ERROR_CHANGE_PASSWORD_MESSAGE, MessageManager.INSTANCE.getProperty("message.wrongpassword"));
            commandResult.setResponsePage(ConfigurationManager2.CHANGE_PASSWORD_PAGE_PATH.getProperty());
            commandResult.setResponseType(CommandResult.ResponseType.FORWARD);
            return commandResult;
        }

        if (!Validator.validatePassword(content)) {
            commandResult.setResponsePage(ConfigurationManager2.CHANGE_PASSWORD_PAGE_PATH.getProperty());
            commandResult.setResponseType(CommandResult.ResponseType.FORWARD);
            return commandResult;
        }


        try {
            user.setPassword(newPassword);
            boolean result = userService.update(user);
            if (result) {
                EmailSender emailSender = new EmailSender();
                emailSender.sendConfirmation(user.getEmail(), "password changed", "Your password was changed\nNew password is " + newPassword);
            }
        } catch (ServiceLevelException e) {
            log.error(e);
            content.setRequestAttribute(AttributeName.COMMAND, CommandType.HOME.toString());
            commandResult.setResponsePage(ConfigurationManager2.ERROR_PAGE_PATH.getProperty());
            commandResult.setResponseType(CommandResult.ResponseType.FORWARD);
            return commandResult;
        }

        content.setSessionAttribute("HomeMessage", MessageManager.INSTANCE.getProperty("message.passwordchanged"));
        commandResult.setResponsePage(ConfigurationManager2.HOME_PAGE_PATH.getProperty());
        commandResult.setResponseType(CommandResult.ResponseType.FORWARD);
        return commandResult;
    }
}
