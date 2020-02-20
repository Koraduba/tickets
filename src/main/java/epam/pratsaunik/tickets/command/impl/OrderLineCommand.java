package epam.pratsaunik.tickets.command.impl;

import epam.pratsaunik.tickets.command.AbstractCommand;
import epam.pratsaunik.tickets.command.CommandResult;
import epam.pratsaunik.tickets.command.RequestContent;
import epam.pratsaunik.tickets.entity.OrderLine;
import epam.pratsaunik.tickets.entity.Ticket;
import epam.pratsaunik.tickets.service.Service;
import epam.pratsaunik.tickets.service.impl.EventServiceImpl;
import epam.pratsaunik.tickets.service.impl.OrderServiceImpl;
import epam.pratsaunik.tickets.servlet.ParameterName;
import epam.pratsaunik.tickets.util.ConfigurationManager;
import epam.pratsaunik.tickets.util.ConfigurationManager2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

public class OrderLineCommand extends AbstractCommand {
    private final static Logger log = LogManager.getLogger();

    public OrderLineCommand(Service service) {
        super(service);
    }

    @Override
    public CommandResult execute(RequestContent content) {
        CommandResult commandResult=new CommandResult();
        List<OrderLine> orderLineList = (List<OrderLine>) content.getSessionAttribute("cart");
        if (orderLineList==null){
            orderLineList=new ArrayList<>();
            content.setSessionAttribute("cart",orderLineList);
        }
        OrderLine orderLine = new OrderLine();
        orderLine.setTicketQuantity(Integer.parseInt(content.getRequestParameter(ParameterName.ORDER_LINE_QUANTITY)));
        Ticket ticket = new Ticket();
        Long ticketId = Long.parseLong(content.getRequestParameter(ParameterName.ORDER_LINE_TICKET));
        ticket = ((EventServiceImpl) service).findTicketById(ticketId);
        orderLine.setTicket(ticket);
        orderLineList.add(orderLine);
        commandResult.setResponsePage(ConfigurationManager2.ERROR_PAGE_PATH.getProperty());
        commandResult.setResponseType(CommandResult.ResponseType.FORWARD);

        return commandResult;
    }
}
