package epam.pratsaunik.tickets.command.impl;

import epam.pratsaunik.tickets.command.AbstractCommand;
import epam.pratsaunik.tickets.command.CommandResult;
import epam.pratsaunik.tickets.command.RequestContent;
import epam.pratsaunik.tickets.service.Service;
import epam.pratsaunik.tickets.servlet.AttributeName;
import epam.pratsaunik.tickets.util.ConfigurationManager;

/**
 * Class{@code ProfileCommand} is used to forward to profile jsp
 *
 * @version 1.0
 * @see AbstractCommand
 */
public class ProfileCommand extends AbstractCommand {
    public ProfileCommand(Service service) {
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
        commandResult.setResponsePage(ConfigurationManager.PROFILE_PAGE_PATH.getProperty());
        commandResult.setResponseType(CommandResult.ResponseType.REDIRECT);
        content.setSessionAttribute(AttributeName.HOME_MESSAGE, null);
        return commandResult;
    }
}
