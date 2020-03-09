package epam.pratsaunik.tickets.command.impl;

import epam.pratsaunik.tickets.command.AbstractCommand;
import epam.pratsaunik.tickets.command.CommandResult;
import epam.pratsaunik.tickets.command.CommandType;
import epam.pratsaunik.tickets.command.RequestContent;
import epam.pratsaunik.tickets.entity.Event;
import epam.pratsaunik.tickets.entity.Ticket;
import epam.pratsaunik.tickets.entity.TicketCat;
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
import java.util.List;

/**
 * Class{@code EditEventCommand} is used to implement changes in Event
 *
 * @see AbstractCommand
 */
public class EditEventCommand extends AbstractCommand {
    private final Logger log = LogManager.getLogger();

    public EditEventCommand(Service service) {
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
    public CommandResult execute(RequestContent content) {
        CommandResult commandResult = new CommandResult();
        InputKeeper.getInstance().keepEvent(content);
        if (!Validator.validateEvent(content)) {
            commandResult.setResponsePage(ConfigurationManager2.EDIT_EVENT_PAGE_PATH.getProperty());
            commandResult.setResponseType(CommandResult.ResponseType.FORWARD);
            return commandResult;
        }
        Event event = (Event) content.getSessionAttribute(AttributeName.EVENT);
        List<Ticket> ticketList = null;
        try {
            ticketList = ((EventServiceImpl) service).findTicketsByEvent(event);
        } catch (ServiceLevelException e) {
            log.error(e);
            content.setRequestAttribute(AttributeName.COMMAND, CommandType.HOME.toString());
            commandResult.setResponsePage(ConfigurationManager2.ERROR_PAGE_PATH.getProperty());
            commandResult.setResponseType(CommandResult.ResponseType.FORWARD);
            return commandResult;
        }
        event.setName(content.getRequestParameter(ParameterName.EVENT_NAME));
        event.setDate(content.getRequestParameter(ParameterName.EVENT_DATE));
        event.setTime(content.getRequestParameter(ParameterName.EVENT_TIME));
        event.setDescription(content.getRequestParameter(ParameterName.EVENT_DESCRIPTION));
        String path = (String) content.getSessionAttribute(AttributeName.EVENT_IMAGE);
        event.setImage(path);
        String venue = content.getRequestParameter(ParameterName.EVENT_VENUE);
        try {
            if (venue != null && !venue.isEmpty()) {
                event.setVenue(((EventServiceImpl) service).findVenueByName(venue));
            }
            ((EventServiceImpl) service).update(event);
            if (ticketList != null) {
                for (Ticket ticket : ticketList) {
                    if (ticket.getCategory() == TicketCat.STANDARD) {
                        ticket.setPrice(new BigDecimal(content.getRequestParameter(ParameterName.TICKET_PRICE_STANDARD)));
                    }
                    if (ticket.getCategory() == TicketCat.VIP) {
                        ticket.setPrice(new BigDecimal(content.getRequestParameter(ParameterName.TICKET_PRICE_VIP)));
                    }
                    ((EventServiceImpl) service).updateTicket(ticket);
                }
            }

            InputKeeper.getInstance().clearEvent(content);
            content.setSessionAttribute(AttributeName.HOME_MESSAGE, MessageManager.INSTANCE.getProperty(MessageType.EVENT_EDITED));
            commandResult.setResponsePage(ConfigurationManager2.HOME_PAGE_PATH.getProperty());
            commandResult.setResponseType(CommandResult.ResponseType.REDIRECT);
            InputKeeper.getInstance().clearEvent(content);
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
