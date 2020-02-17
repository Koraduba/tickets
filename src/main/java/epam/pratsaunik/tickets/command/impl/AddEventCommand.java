package epam.pratsaunik.tickets.command.impl;

import epam.pratsaunik.tickets.command.AbstractCommand;
import epam.pratsaunik.tickets.command.RequestContent;
import epam.pratsaunik.tickets.entity.Event;
import epam.pratsaunik.tickets.entity.Ticket;
import epam.pratsaunik.tickets.entity.TicketCat;
import epam.pratsaunik.tickets.entity.Venue;
import epam.pratsaunik.tickets.exception.ServiceLevelException;
import epam.pratsaunik.tickets.service.Service;
import epam.pratsaunik.tickets.service.impl.EventServiceImpl;
import epam.pratsaunik.tickets.service.impl.UserServiceImpl;
import epam.pratsaunik.tickets.util.ConfigurationManager;
import epam.pratsaunik.tickets.util.ConfigurationManager2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddEventCommand extends AbstractCommand {
    private final static Logger log = LogManager.getLogger();

    public AddEventCommand(Service service) {
        super(service);
    }

    @Override
    public String execute(RequestContent content) {
        String page = null;
        Event event = new Event();
        Ticket ticketStd = new Ticket();
        Ticket ticketVip = new Ticket();
        Date date = null;
        event.setName(content.getRequestParameter("name"));
        event.setDate(content.getRequestParameter("date"));
        event.setTime(content.getRequestParameter("time"));
        event.setDescription(content.getRequestParameter("description"));
        event.setImage((String) content.getSessionAttribute("path"));
        ticketStd.setCategory(TicketCat.STANDARD);
        ticketVip.setCategory(TicketCat.VIP);
        ticketStd.setPrice(new BigDecimal(content.getRequestParameter("price-standard")));
        ticketVip.setPrice(new BigDecimal(content.getRequestParameter("price-vip")));

        String venue = content.getRequestParameter("venue");
        try {
            if (venue != null) {
                event.setVenue(((EventServiceImpl) service).findVenueByName(venue));
            }
            long eventId = ((EventServiceImpl) service).create(event);
            event.setEventId(eventId);
            ticketStd.setEvent(event);
            ticketVip.setEvent(event);
            ((EventServiceImpl) service).createTicket(ticketStd);
            ((EventServiceImpl) service).createTicket(ticketVip);
            content.setSessionAttribute("id", eventId);
            page = ConfigurationManager2.UPLOAD_PAGE_PATH.getProperty();
        } catch (ServiceLevelException e) {
            log.error("Exception in AddEventCommand " + e);
            page = ConfigurationManager2.ERROR_PAGE_PATH.getProperty();
        }
        return page;
    }
}
