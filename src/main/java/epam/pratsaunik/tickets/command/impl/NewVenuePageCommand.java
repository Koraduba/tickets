package epam.pratsaunik.tickets.command.impl;

import epam.pratsaunik.tickets.command.AbstractCommand;
import epam.pratsaunik.tickets.command.CommandResult;
import epam.pratsaunik.tickets.command.RequestContent;
import epam.pratsaunik.tickets.service.Service;
import epam.pratsaunik.tickets.servlet.AttributeName;
import epam.pratsaunik.tickets.servlet.ParameterName;
import epam.pratsaunik.tickets.util.ConfigurationManager;
import epam.pratsaunik.tickets.util.InputKeeper;

/**
 * Class{@code NewVenuePageCommand} to forward to NewVenuePage
 *
 * @version 1.0
 * @see AbstractCommand
 */
public class NewVenuePageCommand extends AbstractCommand {
    public NewVenuePageCommand(Service service) {
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
        InputKeeper.getInstance().keepEvent(content);
        content.setSessionAttribute(AttributeName.MODE, content.getRequestParameter(ParameterName.MODE));
        commandResult.setResponsePage(ConfigurationManager.NEW_VENUE_PAGE_PATH.getProperty());
        commandResult.setResponseType(CommandResult.ResponseType.FORWARD);
        return commandResult;
    }
}
