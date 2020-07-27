package epam.pratsaunik.tickets.command.impl;

import epam.pratsaunik.tickets.command.AbstractCommand;
import epam.pratsaunik.tickets.command.CommandResult;
import epam.pratsaunik.tickets.command.CommandType;
import epam.pratsaunik.tickets.command.RequestContent;
import epam.pratsaunik.tickets.entity.Order;
import epam.pratsaunik.tickets.entity.OrderLine;
import epam.pratsaunik.tickets.entity.User;
import epam.pratsaunik.tickets.exception.ServiceLevelException;
import epam.pratsaunik.tickets.service.Service;
import epam.pratsaunik.tickets.service.impl.OrderServiceImpl;
import epam.pratsaunik.tickets.servlet.AttributeName;
import epam.pratsaunik.tickets.util.ConfigurationManager;
import epam.pratsaunik.tickets.util.MessageManager;
import epam.pratsaunik.tickets.util.MessageType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Class{@code OrderCommand} is used to create and save new order in data base
 *
 * @version 1.0
 * @see AbstractCommand
 */
public class OrderCommand extends AbstractCommand {

    private final static Logger log = LogManager.getLogger();
    private final String DATA_FORMAT = "yyyy-MM-dd HH:mm";

    public OrderCommand(Service service) {
        super(service);
    }

    /**
     * @param content{@code RequestContent} instance to provide request parameters ans session attributes access
     * @return {@code CommandResult} instance with information about response type and further destination page
     * @see RequestContent
     * @see CommandResult
     */
    @Override
    public CommandResult execute(RequestContent content)  {
        CommandResult commandResult = new CommandResult();

        List<OrderLine> orderLines = (List<OrderLine>) content.getSessionAttribute(AttributeName.SHOPPING_CART);
        if (orderLines==null){
            content.setSessionAttribute(AttributeName.HOME_MESSAGE, MessageManager.INSTANCE.getProperty(MessageType.CART_IS_EMPTY));
            commandResult.setResponsePage(ConfigurationManager.HOME_PAGE_PATH.getProperty());
            commandResult.setResponseType(CommandResult.ResponseType.FORWARD);
            return commandResult;
        }
        Order order = new Order();
        User user = (User) (content.getSessionAttribute(AttributeName.USER));
        order.setUser(user);
        DateFormat dateFormat = new SimpleDateFormat(DATA_FORMAT);
        Date now = new Date();
        String date = dateFormat.format(now);
        order.setDate(date);
        Long orderId;
        try {
            orderId = ((OrderServiceImpl) service).create(order);
            order.setOrderId(orderId);
            orderLines.forEach(orderLine -> orderLine.setOrder(order));
            for (OrderLine orderLine : orderLines) {
                ((OrderServiceImpl) service).createOrderLine(orderLine);
            }
        } catch (ServiceLevelException e) {
            log.error(e);
            content.setRequestAttribute(AttributeName.COMMAND, CommandType.HOME.toString());
            commandResult.setResponsePage(ConfigurationManager.ERROR_PAGE_PATH.getProperty());
            commandResult.setResponseType(CommandResult.ResponseType.FORWARD);
            return commandResult;
        }
        content.setSessionAttribute(AttributeName.HOME_MESSAGE, MessageManager.INSTANCE.getProperty(MessageType.ORDER_PLACED));
        content.setSessionAttribute(AttributeName.SHOPPING_CART,null);
        commandResult.setResponsePage(ConfigurationManager.HOME_PAGE_PATH.getProperty());
        commandResult.setResponseType(CommandResult.ResponseType.FORWARD);
        return commandResult;
    }
}
