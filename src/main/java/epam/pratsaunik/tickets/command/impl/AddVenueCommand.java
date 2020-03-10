package epam.pratsaunik.tickets.command.impl;

import epam.pratsaunik.tickets.command.AbstractCommand;
import epam.pratsaunik.tickets.command.CommandResult;
import epam.pratsaunik.tickets.command.CommandType;
import epam.pratsaunik.tickets.command.RequestContent;
import epam.pratsaunik.tickets.entity.Venue;
import epam.pratsaunik.tickets.exception.ServiceLevelException;
import epam.pratsaunik.tickets.service.Service;
import epam.pratsaunik.tickets.service.impl.EventServiceImpl;
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

/**
 * Class{@code AddVenueCommand} is used to create and save new venue in data base
 *
 * @version 1.0
 * @see AbstractCommand
 */
public class AddVenueCommand extends AbstractCommand {
    private static final String NEW_EVENT = "new";
    private static final String EDIT_EVENT = "edit";
    private final static Logger log = LogManager.getLogger();

    public AddVenueCommand(Service service) {
        super(service);
    }

    /**
     * @param content{@code RequestContent} instance to provide request parameters ans session attributes access
     * @return {@code CommandResult} instance with information about response type  and further destination page
     * @see RequestContent
     * @see CommandResult
     */
    @Override
    public CommandResult execute(RequestContent content) {
        CommandResult commandResult = new CommandResult();
        InputKeeper.getInstance().keepVenue(content);
        if (!Validator.validateVenue(content)) {
            commandResult.setResponsePage(ConfigurationManager2.NEW_VENUE_PAGE_PATH.getProperty());
            commandResult.setResponseType(CommandResult.ResponseType.FORWARD);
            return commandResult;
        }
        Venue venue;
        String name = content.getRequestParameter(ParameterName.VENUE_NAME);
        try {
            venue = ((EventServiceImpl) service).findVenueByName(name);
            if (venue != null) {
                commandResult.setResponsePage(ConfigurationManager2.NEW_VENUE_PAGE_PATH.getProperty());
                commandResult.setResponseType(CommandResult.ResponseType.FORWARD);
                content.setRequestAttribute(AttributeName.ERROR_VENUE_NAME_MESSAGE, MessageManager.INSTANCE.getProperty(MessageType.VENUE_EXISTS));
                return commandResult;
            }
        } catch (ServiceLevelException e) {
            log.error(e);
            content.setRequestAttribute(AttributeName.COMMAND, CommandType.HOME.toString());
            commandResult.setResponsePage(ConfigurationManager2.ERROR_PAGE_PATH.getProperty());
            commandResult.setResponseType(CommandResult.ResponseType.FORWARD);
            return commandResult;
        }
        List<Venue> venueList = null;
        venue = new Venue();
        venue.setName(name);
        venue.setCapacity(Integer.parseInt(content.getRequestParameter(ParameterName.VENUE_CAPACITY)));
        try {
            ((EventServiceImpl) service).createVenue(venue);
            venueList = ((EventServiceImpl) service).findAllVenues();
            content.setSessionAttribute("venues", venueList);
            switch ((String) content.getSessionAttribute(AttributeName.MODE)) {
                case NEW_EVENT:
                    commandResult.setResponsePage(ConfigurationManager2.NEW_EVENT_PAGE_PATH.getProperty());
                    break;
                case EDIT_EVENT:
                    commandResult.setResponsePage(ConfigurationManager2.EDIT_EVENT_PAGE_PATH.getProperty());
                    break;
            }
            commandResult.setResponseType(CommandResult.ResponseType.REDIRECT);
        } catch (ServiceLevelException e) {
            log.error(e);
            content.setRequestAttribute(AttributeName.COMMAND, CommandType.HOME.toString());
            commandResult.setResponsePage(ConfigurationManager2.ERROR_PAGE_PATH.getProperty());
            commandResult.setResponseType(CommandResult.ResponseType.FORWARD);
            return commandResult;
        }
        return commandResult;
    }
}
