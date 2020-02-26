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
import epam.pratsaunik.tickets.util.ConfigurationManager;
import epam.pratsaunik.tickets.util.ConfigurationManager2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class OrdersCommand extends AbstractCommand {
    private final static Logger log = LogManager.getLogger();
    public OrdersCommand(Service service) {
        super(service);
    }

    @Override
    public CommandResult execute(RequestContent content) throws CommandException {
        CommandResult commandResult = new CommandResult();
        User user = (User) content.getSessionAttribute("user");
        log.debug("CommandResult. user: " +user);
        List<Order> orderList = null;
        List<BigDecimal> orderSumList = null;
        try {
            orderList = ((OrderServiceImpl) service).findOrdersByUser(user);
            orderSumList = new ArrayList<>();
            for (Order order : orderList) {
                List<OrderLine> orderLinesByOrder = ((OrderServiceImpl) service).findOrderLinesByOrder(order);
                BigDecimal sum = orderLinesByOrder.stream()
                        .map(line -> line.getTicket().getPrice().multiply(BigDecimal.valueOf(line.getTicketQuantity())))
                        .reduce(BigDecimal::add).orElse(new BigDecimal(0));
                log.debug("sum: "+sum);
                orderSumList.add(sum);
            }
        } catch (ServiceLevelException e) {
            throw new CommandException(e);
        }
        content.setRequestAttribute("orders", orderList);
        content.setRequestAttribute("sums", orderSumList);
        commandResult.setResponsePage(ConfigurationManager2.ORDERS_PAGE_PATH.getProperty());
        commandResult.setResponseType(CommandResult.ResponseType.FORWARD);
        return commandResult;
    }
}
