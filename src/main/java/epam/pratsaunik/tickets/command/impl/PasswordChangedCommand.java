package epam.pratsaunik.tickets.command.impl;

import epam.pratsaunik.tickets.command.AbstractCommand;
import epam.pratsaunik.tickets.command.CommandResult;
import epam.pratsaunik.tickets.command.RequestContent;
import epam.pratsaunik.tickets.email.EmailSender;
import epam.pratsaunik.tickets.entity.User;
import epam.pratsaunik.tickets.exception.CommandException;
import epam.pratsaunik.tickets.exception.ServiceLevelException;
import epam.pratsaunik.tickets.hash.PasswordHash;
import epam.pratsaunik.tickets.service.Service;
import epam.pratsaunik.tickets.service.impl.UserServiceImpl;
import epam.pratsaunik.tickets.util.ConfigurationManager2;
import epam.pratsaunik.tickets.util.MessageManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 * Class{@code AddVenueCommand} is used to create and save new venue in data base
 * @version 1.0
 * @see AbstractCommand
 */
public class PasswordChangedCommand extends AbstractCommand {
    private static final Logger log = LogManager.getLogger();

    public PasswordChangedCommand(Service service) {
        super(service);
    }
    /**
     *
     * @param content{@code RequestContent} instance to provide request parameters ans session attributes access
     * @return {@code CommandResult} instance with information about response type and further destination page
     * @throws CommandException custom exception to be thrown in case of exception on service level
     * @see RequestContent
     * @see CommandResult
     */
    @Override
    public CommandResult execute(RequestContent content) throws CommandException {
        UserServiceImpl userService = (UserServiceImpl) service;
        CommandResult commandResult = new CommandResult();
        User user = ((User) content.getSessionAttribute("user"));
        String oldPassword = content.getRequestParameter("oldPassword");
        String newPassword = content.getRequestParameter("newPassword");
        String login = user.getLogin();
        boolean hasAccess = userService.checkUser(login,oldPassword, user);
        log.debug(hasAccess);
        if (hasAccess) {
            try {
                user.setPassword(newPassword);
                boolean result=userService.update(user);
                if(result){
                    EmailSender emailSender = new EmailSender();
                    emailSender.sendConfirmation(user.getEmail(),"password changed","Your password was changed\nNew password is "+newPassword);
                }
            } catch (ServiceLevelException e) {
                throw new CommandException(e);
            }
        } else {
            content.setSessionAttribute("errorChangePasswordMessage", MessageManager.INSTANCE.getProperty("message.wrongpassword"));
            commandResult.setResponsePage(ConfigurationManager2.CHANGE_PASSWORD_PAGE_PATH.getProperty());
            commandResult.setResponseType(CommandResult.ResponseType.FORWARD);
            return commandResult;
        }


        content.setSessionAttribute("HomeMessage", MessageManager.INSTANCE.getProperty("message.passwordchanged"));
        commandResult.setResponsePage(ConfigurationManager2.HOME_PAGE_PATH.getProperty());
        commandResult.setResponseType(CommandResult.ResponseType.FORWARD);
        return commandResult;
    }
}
