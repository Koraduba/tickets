package epam.pratsaunik.tickets.command.impl;

import epam.pratsaunik.tickets.command.AbstractCommand;
import epam.pratsaunik.tickets.command.CommandResult;
import epam.pratsaunik.tickets.command.RequestContent;
import epam.pratsaunik.tickets.entity.Role;
import epam.pratsaunik.tickets.entity.User;
import epam.pratsaunik.tickets.service.Service;
import epam.pratsaunik.tickets.servlet.AttributeName;
import epam.pratsaunik.tickets.util.ConfigurationManager2;
import epam.pratsaunik.tickets.util.LocaleName;
import epam.pratsaunik.tickets.util.MessageManager;

/**
 * Class{@code GuestCommand} to enter as a guest
 *
 * @version 1.0
 * @see AbstractCommand
 */
public class GuestCommand extends AbstractCommand {
    public GuestCommand(Service service) {
        super(service);
    }

    /**
     * @param content{@code RequestContent} instance to provide request parameters ans session attributes access
     * @return {@code CommandResult} instance with information about response type and further destination page
     * @see RequestContent
     * @see CommandResult
     */
    @Override
    public CommandResult execute(RequestContent content) {
        CommandResult commandResult = new CommandResult();
        User user = new User();
        user.setRole(Role.GUEST);
        user.setName(Role.GUEST.toString());
        content.setSessionAttribute(AttributeName.USER_ROLE, Role.GUEST.toString());
        MessageManager.INSTANCE.changeResource(LocaleName.LOCALE_EN);
        content.setSessionAttribute(AttributeName.LOCALE, LocaleName.LOCALE_EN);
        content.setSessionAttribute(AttributeName.USER, user);
        commandResult.setResponsePage(ConfigurationManager2.HOME_PAGE_PATH.getProperty());
        commandResult.setResponseType(CommandResult.ResponseType.FORWARD);
        return commandResult;
    }
}
