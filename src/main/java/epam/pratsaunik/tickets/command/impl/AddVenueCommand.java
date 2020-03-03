package epam.pratsaunik.tickets.command.impl;

import epam.pratsaunik.tickets.command.AbstractCommand;
import epam.pratsaunik.tickets.command.CommandResult;
import epam.pratsaunik.tickets.command.RequestContent;
import epam.pratsaunik.tickets.entity.Venue;
import epam.pratsaunik.tickets.exception.CommandException;
import epam.pratsaunik.tickets.exception.ServiceLevelException;
import epam.pratsaunik.tickets.service.Service;
import epam.pratsaunik.tickets.service.impl.EventServiceImpl;
import epam.pratsaunik.tickets.servlet.AttributeName;
import epam.pratsaunik.tickets.servlet.ParameterName;
import epam.pratsaunik.tickets.util.*;
import epam.pratsaunik.tickets.validator.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
/**
 * Class{@code AddVenueCommand} is used to create and save new venue in data base
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
     *
     * @param content{@code RequestContent} instance to provide request parameters ans session attributes access
     * @return {@code CommandResult} instance with information about response type  and further destination page
     * @throws CommandException custom exception to be thrown in case of exception on service level
     * @see RequestContent
     * @see CommandResult
     */
    @Override
    public CommandResult execute(RequestContent content) throws CommandException {
        CommandResult commandResult = new CommandResult();
        if (!Validator.validateVenue(content)) {
            InputKeeper.getInstance().keepVenue(content);
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
            throw new CommandException(e);
        }
        List<Venue> venueList = null;
        venue=new Venue();
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
                default:
                    throw new CommandException();
            }
            commandResult.setResponseType(CommandResult.ResponseType.REDIRECT);
        } catch (ServiceLevelException e) {
            throw new CommandException(e);
        }
        return commandResult;
    }
}
