package epam.pratsaunik.tickets.command.impl;

import epam.pratsaunik.tickets.command.AbstractCommand;
import epam.pratsaunik.tickets.command.RequestContent;
import epam.pratsaunik.tickets.entity.Event;
import epam.pratsaunik.tickets.entity.Ticket;
import epam.pratsaunik.tickets.exception.ServiceLevelException;
import epam.pratsaunik.tickets.service.Service;
import epam.pratsaunik.tickets.service.impl.EventServiceImpl;
import epam.pratsaunik.tickets.util.ConfigurationManager;
import epam.pratsaunik.tickets.util.ConfigurationManager2;

import java.util.List;

public class EventCommand extends AbstractCommand {
    public EventCommand(Service service) {
        super(service);
    }

    @Override
    public String execute(RequestContent content) {
        String page = null;
        Event event = null;
        List<Ticket> ticketList= null;
        try {
            event=((EventServiceImpl) service).findEventById(Long.parseLong(content.getRequestParameter("eventId")));
            ticketList=((EventServiceImpl)service).findTicketsByEvent(event);
            content.setSessionAttribute("event",event);
            content.setSessionAttribute("tickets",ticketList);
        } catch (ServiceLevelException e) {

        }

        return ConfigurationManager2.EVENT_PAGE_PATH.getProperty();
    }
}
