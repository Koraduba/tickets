package epam.pratsaunik.tickets.command.impl;

import epam.pratsaunik.tickets.command.AbstractCommand;
import epam.pratsaunik.tickets.command.CommandResult;
import epam.pratsaunik.tickets.command.CommandType;
import epam.pratsaunik.tickets.command.RequestContent;
import epam.pratsaunik.tickets.entity.Order;
import epam.pratsaunik.tickets.entity.OrderLine;
import epam.pratsaunik.tickets.exception.ServiceLevelException;
import epam.pratsaunik.tickets.service.Service;
import epam.pratsaunik.tickets.service.impl.OrderServiceImpl;
import epam.pratsaunik.tickets.servlet.AttributeName;
import epam.pratsaunik.tickets.servlet.ParameterName;
import epam.pratsaunik.tickets.util.ConfigurationManager;
import epam.pratsaunik.tickets.validator.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Class{@code OrdersAboveThreshold} is used to provide data of orders
 * above given threshold
 *
 * @version 1.0
 * @see AbstractCommand
 */
public class OrdersAboveThresholdCommand extends AbstractCommand {
    private final static Logger log = LogManager.getLogger();

    public OrdersAboveThresholdCommand(Service service) {

        super(service);
    }

    /**
     * @param content{@code RequestContent} instance to provide request parameters ans session attributes access
     * @return {@code CommandResult} instance with information about response type and further destination page
     * @see RequestContent
     * @see CommandResult
     */
    @Override
    public CommandResult execute(RequestContent content) {
        CommandResult commandResult = new CommandResult();
        if (!Validator.validateOrderAmount(content)) {
            commandResult.setResponsePage(ConfigurationManager.STATISTIC_PAGE_PATH.getProperty());
            commandResult.setResponseType(CommandResult.ResponseType.FORWARD);
            return commandResult;
        }

        BigDecimal amount = BigDecimal.valueOf(Long.parseLong(content.getRequestParameter(ParameterName.ORDER_AMOUNT)));
        List<Order> orderList;
        List<BigDecimal> orderSumList;
        try {
            orderList = ((OrderServiceImpl) service).findOrdersAboveAmount(amount);
            orderSumList = new ArrayList<>();
            for (Order order : orderList) {
                List<OrderLine> orderLinesByOrder = ((OrderServiceImpl) service).findOrderLinesByOrder(order);
                BigDecimal sum = orderLinesByOrder.stream()
                        .map(line -> line.getTicket().getPrice().multiply(BigDecimal.valueOf(line.getTicketQuantity())))
                        .reduce(BigDecimal::add).orElse(new BigDecimal(0));
                log.debug("sum: " + sum);
                orderSumList.add(sum);
            }
        } catch (ServiceLevelException e) {
            log.error(e);
            content.setRequestAttribute(AttributeName.COMMAND, CommandType.STATISTIC.toString());
            commandResult.setResponsePage(ConfigurationManager.ERROR_PAGE_PATH.getProperty());
            commandResult.setResponseType(CommandResult.ResponseType.FORWARD);
            return commandResult;
        }
        content.setRequestAttribute(AttributeName.ORDER_LIST, orderList);
        content.setRequestAttribute(AttributeName.ORDER_SUMS, orderSumList);
        commandResult.setResponsePage(ConfigurationManager.ORDERS_PAGE_PATH.getProperty());
        commandResult.setResponseType(CommandResult.ResponseType.FORWARD);
        return commandResult;
    }
}
