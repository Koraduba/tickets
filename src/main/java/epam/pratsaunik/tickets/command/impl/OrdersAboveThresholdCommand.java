package epam.pratsaunik.tickets.command.impl;

import epam.pratsaunik.tickets.command.AbstractCommand;
import epam.pratsaunik.tickets.command.CommandResult;
import epam.pratsaunik.tickets.command.RequestContent;
import epam.pratsaunik.tickets.entity.Order;
import epam.pratsaunik.tickets.entity.OrderLine;
import epam.pratsaunik.tickets.exception.CommandException;
import epam.pratsaunik.tickets.exception.ServiceLevelException;
import epam.pratsaunik.tickets.service.Service;
import epam.pratsaunik.tickets.service.impl.OrderServiceImpl;
import epam.pratsaunik.tickets.util.ConfigurationManager2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
/**
 * Class{@code OrdersAboveThreshold} is used to provide data of orders
 *  above given threshold
 * @version 1.0
 * @see AbstractCommand
 */
public class OrdersAboveThresholdCommand extends AbstractCommand {
    private final static Logger log= LogManager.getLogger();
    public OrdersAboveThresholdCommand(Service service) {

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
        CommandResult commandResult=new CommandResult();
        BigDecimal amount = BigDecimal.valueOf(Long.parseLong(content.getRequestParameter("amount")));
        List<Order> orderList;
        List<BigDecimal> orderSumList ;
        try {
            orderList = ((OrderServiceImpl)service).findOrdersAboveAmount(amount);
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
