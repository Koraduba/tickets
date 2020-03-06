package epam.pratsaunik.tickets.command.impl;

import epam.pratsaunik.tickets.command.AbstractCommand;
import epam.pratsaunik.tickets.command.CommandResult;
import epam.pratsaunik.tickets.command.RequestContent;
import epam.pratsaunik.tickets.entity.OrderLine;
import epam.pratsaunik.tickets.entity.Ticket;
import epam.pratsaunik.tickets.exception.CommandException;
import epam.pratsaunik.tickets.exception.ServiceLevelException;
import epam.pratsaunik.tickets.service.Service;
import epam.pratsaunik.tickets.service.impl.EventServiceImpl;
import epam.pratsaunik.tickets.servlet.AttributeName;
import epam.pratsaunik.tickets.servlet.ParameterName;
import epam.pratsaunik.tickets.util.ConfigurationManager2;
import epam.pratsaunik.tickets.validator.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
/**
 * Class{@code OrderLineCommand} is used to create new order line for shopping cart
 * and save it in session
 * @version 1.0
 * @see AbstractCommand
 */
public class OrderLineCommand extends AbstractCommand {
    private final static Logger log = LogManager.getLogger();

    public OrderLineCommand(Service service) {
        super(service);
    }
    /**
     *
     * @param content{@code RequestContent} instance to provide request parameters ans session attributes access
     * @return {@code CommandResult} instance with information about response type and further destination page
     * @throws CommandException custom exception to be thrown in case of exception on service level
     * @see RequestContent
     * @see CommandResult
     */
    @Override
    public CommandResult execute(RequestContent content) throws CommandException {
        log.debug("OrderLineCommand");
        CommandResult commandResult = new CommandResult();
        if (!Validator.validateOrderLine(content)) {
            commandResult.setResponsePage(ConfigurationManager2.EVENT_PAGE_PATH.getProperty());
            commandResult.setResponseType(CommandResult.ResponseType.FORWARD);
            return commandResult;
        }
        List<OrderLine> orderLineList = (List<OrderLine>) content.getSessionAttribute(AttributeName.SHOPPING_CART);
        if (orderLineList == null) {
            orderLineList = new ArrayList<>();
            content.setSessionAttribute(AttributeName.SHOPPING_CART, orderLineList);
        }
        OrderLine orderLine = new OrderLine();
        orderLine.setTicketQuantity(Integer.parseInt(content.getRequestParameter(ParameterName.ORDER_LINE_QUANTITY)));
        Long ticketId = Long.parseLong(content.getRequestParameter(ParameterName.ORDER_LINE_TICKET));
        Ticket ticket = null;
        try {
            ticket = ((EventServiceImpl) service).findTicketById(ticketId);
        } catch (ServiceLevelException e) {
            throw new CommandException(e);
        }
        orderLine.setTicket(ticket);
        orderLineList.add(orderLine);
        commandResult.setResponsePage(ConfigurationManager2.EVENT_PAGE_PATH.getProperty());
        commandResult.setResponseType(CommandResult.ResponseType.REDIRECT);

        return commandResult;
    }
}
