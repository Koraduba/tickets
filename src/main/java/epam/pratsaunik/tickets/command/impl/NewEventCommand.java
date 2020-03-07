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
import epam.pratsaunik.tickets.servlet.AttributeName;
import epam.pratsaunik.tickets.servlet.ParameterName;
import epam.pratsaunik.tickets.util.ConfigurationManager2;
import epam.pratsaunik.tickets.util.InputKeeper;
import epam.pratsaunik.tickets.util.MessageManager;
import epam.pratsaunik.tickets.util.MessageType;
import epam.pratsaunik.tickets.validator.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;

/**
 * Class{@code NewEventCommand} is used to create and save new event
 *
 * @version 1.0
 * @see AbstractCommand
 */
public class NewEventCommand extends AbstractCommand {
    private final static Logger log = LogManager.getLogger();


    public NewEventCommand(Service service) {
        super(service);
    }

    /**
     * @param content{@code RequestContent} instance to provide request parameters ans session attributes access
     * @return {@code CommandResult} instance with information about response type and further destination page
     * @throws CommandException custom exception to be thrown in case of exception on service level
     * @see RequestContent
     * @see CommandResult
     */
    @Override
    public CommandResult execute(RequestContent content) throws CommandException {
        CommandResult commandResult = new CommandResult();
        InputKeeper.getInstance().keepEvent(content);
        if (!Validator.validateEvent(content)) {
            commandResult.setResponsePage(ConfigurationManager2.NEW_EVENT_PAGE_PATH.getProperty());
            commandResult.setResponseType(CommandResult.ResponseType.FORWARD);
            return commandResult;
        }
        Event event;
        String name = content.getRequestParameter(ParameterName.EVENT_NAME);
        try {
            event = ((EventServiceImpl) service).findEventByName(name);
            if (event != null) {
                commandResult.setResponsePage(ConfigurationManager2.NEW_EVENT_PAGE_PATH.getProperty());
                commandResult.setResponseType(CommandResult.ResponseType.FORWARD);
                content.setRequestAttribute(AttributeName.ERROR_EVENT_NAME_MESSSAGE, MessageManager.INSTANCE.getProperty(MessageType.EVENT_EXISTS));
                return commandResult;
            }
        } catch (ServiceLevelException e) {
            throw new CommandException(e);
        }


        event = new Event();
        Ticket ticketStd = new Ticket();
        Ticket ticketVip = new Ticket();
        event.setName(name);
        event.setDate(content.getRequestParameter(ParameterName.EVENT_DATE));
        event.setTime(content.getRequestParameter(ParameterName.EVENT_TIME));
        event.setDescription(content.getRequestParameter(ParameterName.EVENT_DESCRIPTION));
        String path = (String) content.getSessionAttribute(AttributeName.EVENT_IMAGE);
        event.setImage(path);
        ticketStd.setCategory(TicketCat.STANDARD);
        ticketVip.setCategory(TicketCat.VIP);
        ticketStd.setPrice(new BigDecimal(content.getRequestParameter(ParameterName.TICKET_PRICE_STANDARD)));
        ticketVip.setPrice(new BigDecimal(content.getRequestParameter(ParameterName.TICKET_PRICE_VIP)));
        String venue = content.getRequestParameter(ParameterName.EVENT_VENUE);
        event.setOwner((User) content.getSessionAttribute(AttributeName.USER));
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
            content.setSessionAttribute(AttributeName.HOME_MESSAGE, MessageManager.INSTANCE.getProperty(MessageManager.INSTANCE.EVENT_CREATED_MESSAGE));
            commandResult.setResponsePage(ConfigurationManager2.HOME_PAGE_PATH.getProperty());
            commandResult.setResponseType(CommandResult.ResponseType.REDIRECT);
            InputKeeper.getInstance().clearEvent(content);
        } catch (ServiceLevelException e) {
            throw new CommandException("NewEventCommand", e);
        }
        return commandResult;
    }
}
