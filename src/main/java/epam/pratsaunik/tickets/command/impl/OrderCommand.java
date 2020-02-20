package epam.pratsaunik.tickets.command.impl;

import epam.pratsaunik.tickets.command.AbstractCommand;
import epam.pratsaunik.tickets.command.CommandResult;
import epam.pratsaunik.tickets.command.RequestContent;
import epam.pratsaunik.tickets.entity.Order;
import epam.pratsaunik.tickets.entity.OrderLine;
import epam.pratsaunik.tickets.entity.User;
import epam.pratsaunik.tickets.exception.CommandException;
import epam.pratsaunik.tickets.exception.ServiceLevelException;
import epam.pratsaunik.tickets.service.Service;
import epam.pratsaunik.tickets.service.impl.OrderServiceImpl;
import epam.pratsaunik.tickets.util.ConfigurationManager2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class OrderCommand extends AbstractCommand {

    private final static Logger log = LogManager.getLogger();
    public OrderCommand(Service service) {
        super(service);
    }

    @Override
    public CommandResult execute(RequestContent content) throws CommandException {
        CommandResult commandResult=new CommandResult();
        List<OrderLine> orderLines=(List<OrderLine>)content.getSessionAttribute("cart");
        Order order = new Order();
        User user =(User)(content.getSessionAttribute("user"));
        order.setUser(user);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date now = new Date();
        String date=dateFormat.format(now);
        order.setDate(date);
        Long orderId;
        try {
            orderId=((OrderServiceImpl)service).create(order);
            order.setOrderId(orderId);
            orderLines.forEach(orderLine -> orderLine.setOrder(order));
            for (OrderLine orderLine:orderLines) {
                ((OrderServiceImpl)service).createOrderLine(orderLine);
            }
        } catch (ServiceLevelException e) {
            throw new CommandException(e);
        }

        commandResult.setResponsePage(ConfigurationManager2.HOME_PAGE_PATH.getProperty());
        commandResult.setResponseType(CommandResult.ResponseType.FORWARD);
        return commandResult;
    }
}
