package epam.pratsaunik.tickets.command.impl;

import epam.pratsaunik.tickets.command.AbstractCommand;
import epam.pratsaunik.tickets.command.CommandResult;
import epam.pratsaunik.tickets.command.RequestContent;
import epam.pratsaunik.tickets.entity.Event;
import epam.pratsaunik.tickets.entity.Ticket;
import epam.pratsaunik.tickets.entity.TicketCat;
import epam.pratsaunik.tickets.entity.User;
import epam.pratsaunik.tickets.exception.CommandException;
import epam.pratsaunik.tickets.exception.ServiceLevelException;
import epam.pratsaunik.tickets.service.Service;
import epam.pratsaunik.tickets.service.impl.EventServiceImpl;
import epam.pratsaunik.tickets.servlet.ParameterName;
import epam.pratsaunik.tickets.util.ConfigurationManager2;
import epam.pratsaunik.tickets.util.MessageManager;
import epam.pratsaunik.tickets.validator.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.Date;

public class NewEventCommand extends AbstractCommand {
    private final static Logger log = LogManager.getLogger();


    public NewEventCommand(Service service) {
        super(service);
    }

    @Override
    public CommandResult execute(RequestContent content) throws CommandException {
        CommandResult commandResult = new CommandResult();
        if (!Validator.validateEvent(content)) {
            commandResult.setResponsePage(ConfigurationManager2.NEW_EVENT_PAGE_PATH.getProperty());
            commandResult.setResponseType(CommandResult.ResponseType.FORWARD);
            return commandResult;
        }
        Event event = new Event();
        Ticket ticketStd = new Ticket();
        Ticket ticketVip = new Ticket();
        Date date = null;
        event.setName(content.getRequestParameter(ParameterName.EVENT_NAME));
        event.setDate(content.getRequestParameter(ParameterName.EVENT_DATE));
        event.setTime(content.getRequestParameter(ParameterName.EVENT_TIME));
        event.setDescription(content.getRequestParameter(ParameterName.EVENT_DESCRIPTION));
        String path=(String)content.getSessionAttribute("path");
        event.setImage(path);
        ticketStd.setCategory(TicketCat.STANDARD);
        ticketVip.setCategory(TicketCat.VIP);
        ticketStd.setPrice(new BigDecimal(content.getRequestParameter(ParameterName.TICKET_PRICE_STANDARD)));
        ticketVip.setPrice(new BigDecimal(content.getRequestParameter(ParameterName.TICKET_PRICE_VIP)));
        String venue = content.getRequestParameter(ParameterName.EVENT_VENUE);
        event.setOwner((User) content.getSessionAttribute("user"));
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
            content.setSessionAttribute("HomeMessage",MessageManager.INSTANCE.getProperty("message.eventcreated"));
            commandResult.setResponsePage(ConfigurationManager2.HOME_PAGE_PATH.getProperty());
            commandResult.setResponseType(CommandResult.ResponseType.FORWARD);
        } catch (ServiceLevelException e) {
            throw new CommandException(e);
        }
        return commandResult;
    }
}
