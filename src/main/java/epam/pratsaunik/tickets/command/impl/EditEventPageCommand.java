package epam.pratsaunik.tickets.command.impl;

import epam.pratsaunik.tickets.command.AbstractCommand;
import epam.pratsaunik.tickets.command.CommandResult;
import epam.pratsaunik.tickets.command.CommandType;
import epam.pratsaunik.tickets.command.RequestContent;
import epam.pratsaunik.tickets.entity.Event;
import epam.pratsaunik.tickets.entity.Ticket;
import epam.pratsaunik.tickets.entity.Venue;
import epam.pratsaunik.tickets.exception.ServiceLevelException;
import epam.pratsaunik.tickets.service.Service;
import epam.pratsaunik.tickets.service.impl.EventServiceImpl;
import epam.pratsaunik.tickets.servlet.AttributeName;
import epam.pratsaunik.tickets.util.ConfigurationManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Class{@code EditEventPageCommand} to forward to EditEventPage with providing of deeded data
 *
 * @version 1.0
 * @see AbstractCommand
 */
public class EditEventPageCommand extends AbstractCommand {
    private static final Logger log = LogManager.getLogger();

    public EditEventPageCommand(Service service) {
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
        List<Venue> venueList;
        List<Ticket> ticketList = null;
        Event event = null;
        try {
            venueList = ((EventServiceImpl) service).findAllVenues();
            content.setSessionAttribute(AttributeName.VENUE_LIST, venueList);
            if (content.getSessionAttribute(AttributeName.EVENT) == null) {
                event = ((EventServiceImpl) service).findEventById(Long.parseLong(content.getRequestParameter("eventId")));
                ticketList = ((EventServiceImpl) service).findTicketsByEvent(event);
                content.setSessionAttribute(AttributeName.EVENT, event);
                content.setSessionAttribute(AttributeName.TICKET_LIST, ticketList);
                content.setSessionAttribute(AttributeName.EVENT_NAME, event.getName());
                content.setSessionAttribute(AttributeName.EVENT_DATE, event.getDate());
                content.setSessionAttribute(AttributeName.EVENT_TIME, event.getTime());
                content.setSessionAttribute(AttributeName.EVENT_DESCRIPTION, event.getDescription());
                content.setSessionAttribute(AttributeName.EVENT_NAME, event.getName());
                content.setSessionAttribute(AttributeName.EVENT_VENUE, event.getVenue());
            }
            if (content.getSessionAttribute(AttributeName.EVENT_IMAGE) == null) {
                content.setSessionAttribute(AttributeName.EVENT_IMAGE, event.getImage());
            }
        } catch (ServiceLevelException e) {
            log.error(e);
            content.setRequestAttribute(AttributeName.COMMAND, CommandType.HOME.toString());
            commandResult.setResponsePage(ConfigurationManager.ERROR_PAGE_PATH.getProperty());
            commandResult.setResponseType(CommandResult.ResponseType.FORWARD);
            return commandResult;
        }
        commandResult.setResponsePage(ConfigurationManager.EDIT_EVENT_PAGE_PATH.getProperty());
        commandResult.setResponseType(CommandResult.ResponseType.FORWARD);
        content.setSessionAttribute(AttributeName.HOME_MESSAGE, null);
        return commandResult;
    }
}
