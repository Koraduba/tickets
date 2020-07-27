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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class OrderInfoCommand extends AbstractCommand {
    private final static Logger log = LogManager.getLogger();
    public OrderInfoCommand(Service service) {
        super(service);
    }

    @Override
    public CommandResult execute(RequestContent content)  {
        CommandResult commandResult = new CommandResult();
        Order order;
        List<OrderLine> orderLines;
        try {
            long id = Long.parseLong(content.getRequestParameter(ParameterName.ORDER_ID));
            order = ((OrderServiceImpl) service).findOrderById(id).get(0);
            orderLines=((OrderServiceImpl)service).findOrderLinesByOrder(order);
        } catch (ServiceLevelException e) {
            log.error(e);
            content.setRequestAttribute(AttributeName.COMMAND, CommandType.HOME.toString());
            commandResult.setResponsePage(ConfigurationManager.ERROR_PAGE_PATH.getProperty());
            commandResult.setResponseType(CommandResult.ResponseType.FORWARD);
            return commandResult;
        }
        content.setRequestAttribute(AttributeName.ORDER,order);
        content.setRequestAttribute(AttributeName.ORDERLINE_LIST,orderLines);
        commandResult.setResponsePage(ConfigurationManager.ORDER_PAGE_PATH.getProperty());
        commandResult.setResponseType(CommandResult.ResponseType.FORWARD);
        return commandResult;
    }
}
