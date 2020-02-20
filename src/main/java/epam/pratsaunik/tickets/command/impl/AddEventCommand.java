package epam.pratsaunik.tickets.command.impl;

import epam.pratsaunik.tickets.command.AbstractCommand;
import epam.pratsaunik.tickets.command.CommandResult;
import epam.pratsaunik.tickets.command.RequestContent;
import epam.pratsaunik.tickets.entity.Event;
import epam.pratsaunik.tickets.entity.Ticket;
import epam.pratsaunik.tickets.entity.TicketCat;
import epam.pratsaunik.tickets.exception.CommandException;
import epam.pratsaunik.tickets.exception.ServiceLevelException;
import epam.pratsaunik.tickets.service.Service;
import epam.pratsaunik.tickets.service.impl.EventServiceImpl;
import epam.pratsaunik.tickets.servlet.ParameterName;
import epam.pratsaunik.tickets.util.ConfigurationManager2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Parameter;
import java.math.BigDecimal;
import java.util.Date;

public class AddEventCommand extends AbstractCommand {
    private final static Logger log = LogManager.getLogger();

    public AddEventCommand(Service service) {
        super(service);
    }

    @Override
    public CommandResult execute(RequestContent content) throws CommandException {
        CommandResult commandResult=new CommandResult();
        Event event = new Event();
        Ticket ticketStd = new Ticket();
        Ticket ticketVip = new Ticket();
        Date date = null;
        event.setName(content.getRequestParameter(ParameterName.EVENT_NAME));
        event.setDate(content.getRequestParameter(ParameterName.EVENT_DATE));
        event.setTime(content.getRequestParameter(ParameterName.EVENT_TIME));
        event.setDescription(content.getRequestParameter(ParameterName.EVENT_DESCRIPTION));
        event.setImage((String) content.getSessionAttribute(ParameterName.EVENT_IMAGE));
        ticketStd.setCategory(TicketCat.STANDARD);
        ticketVip.setCategory(TicketCat.VIP);
        ticketStd.setPrice(new BigDecimal(content.getRequestParameter(ParameterName.TICKET_PRICE_STANDARD)));
        ticketVip.setPrice(new BigDecimal(content.getRequestParameter(ParameterName.TICKET_PRICE_VIP)));
        String venue = content.getRequestParameter(ParameterName.EVENT_VENUE);
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
            commandResult.setResponsePage(ConfigurationManager2.UPLOAD_PAGE_PATH.getProperty());
            commandResult.setResponseType(CommandResult.ResponseType.FORWARD);
        } catch (ServiceLevelException e) {
            throw new CommandException(e);
        }
        return commandResult;
    }
}
