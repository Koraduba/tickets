package epam.pratsaunik.tickets.command.impl;

import epam.pratsaunik.tickets.command.AbstractCommand;
import epam.pratsaunik.tickets.command.CommandResult;
import epam.pratsaunik.tickets.command.RequestContent;
import epam.pratsaunik.tickets.service.Service;
import epam.pratsaunik.tickets.servlet.AttributeName;
import epam.pratsaunik.tickets.util.ConfigurationManager;
import epam.pratsaunik.tickets.util.InputKeeper;
import epam.pratsaunik.tickets.util.MessageManager;

import java.util.Locale;

/**
 * Class{@code LogoutCommand} is used to log out
 *
 * @version 1.0
 * @see AbstractCommand
 */
public class LogoutCommand extends AbstractCommand {
    public LogoutCommand(Service service) {
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
        InputKeeper.getInstance().clearEvent(content);
        CommandResult commandResult = new CommandResult();
        content.setSessionAttribute(AttributeName.HOME_MESSAGE, null);
        content.setSessionAttribute(AttributeName.USER_ROLE, null);
        content.setSessionAttribute(AttributeName.USER, null);
        MessageManager.INSTANCE.changeResource(Locale.getDefault());
        content.setSessionAttribute(AttributeName.LOCALE, Locale.getDefault());
        commandResult.setResponsePage(ConfigurationManager.LOGIN_PAGE_PATH.getProperty());
        commandResult.setResponseType(CommandResult.ResponseType.REDIRECT);
        return commandResult;
    }
}
