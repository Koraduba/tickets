package epam.pratsaunik.tickets.command.impl;

import epam.pratsaunik.tickets.command.AbstractCommand;
import epam.pratsaunik.tickets.command.CommandResult;
import epam.pratsaunik.tickets.command.RequestContent;
import epam.pratsaunik.tickets.entity.Event;
import epam.pratsaunik.tickets.entity.Ticket;
import epam.pratsaunik.tickets.entity.Venue;
import epam.pratsaunik.tickets.exception.CommandException;
import epam.pratsaunik.tickets.exception.ServiceLevelException;
import epam.pratsaunik.tickets.service.Service;
import epam.pratsaunik.tickets.service.impl.EventServiceImpl;
import epam.pratsaunik.tickets.util.ConfigurationManager2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;


public class EditEventPageCommand extends AbstractCommand {
    private static final Logger log = LogManager.getLogger();

    public EditEventPageCommand(Service service) {
        super(service);
    }

    @Override
    public CommandResult execute(RequestContent content) throws CommandException {
        CommandResult commandResult=new CommandResult();
        List<Venue> venueList;
        List<Ticket> ticketList=null;
        Event event=null;
        try {
            venueList=((EventServiceImpl)service).findAllVenues();
        } catch (ServiceLevelException e) {
            log.error("Error in EditEventPageCommand:"+e);
            throw new CommandException(e);
        }
        try {
            event = ((EventServiceImpl)service).findEventById(Long.parseLong(content.getRequestParameter("eventId")));
            ticketList=((EventServiceImpl)service).findTicketsByEvent(event);
        } catch (ServiceLevelException e) {
            e.printStackTrace();
        }
        content.setRequestAttribute("tickets",ticketList );
        content.setRequestAttribute("event",event);
        content.setSessionAttribute("venues",venueList);
        commandResult.setResponsePage(ConfigurationManager2.EDIT_EVENT_PAGE_PATH.getProperty());
        commandResult.setResponseType(CommandResult.ResponseType.FORWARD);
        return commandResult;
    }
}