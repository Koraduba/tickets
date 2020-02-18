package epam.pratsaunik.tickets.command.impl;

import epam.pratsaunik.tickets.command.AbstractCommand;
import epam.pratsaunik.tickets.command.RequestContent;
import epam.pratsaunik.tickets.entity.OrderLine;
import epam.pratsaunik.tickets.entity.Ticket;
import epam.pratsaunik.tickets.service.Service;
import epam.pratsaunik.tickets.service.impl.EventServiceImpl;
import epam.pratsaunik.tickets.service.impl.OrderServiceImpl;
import epam.pratsaunik.tickets.servlet.ParameterName;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

public class OrderLineCommand extends AbstractCommand {
    private final static Logger log = LogManager.getLogger();

    public OrderLineCommand(Service service) {
        super(service);
    }

    @Override
    public String execute(RequestContent content) {

        List<OrderLine> orderLineList=(List<OrderLine>)content.getSessionAttribute("cart");


        OrderLine orderLine = new OrderLine();

        orderLine.setTicketQuantity(Integer.parseInt(content.getRequestParameter(ParameterName.ORDER_LINE_QUANTITY)));
        Ticket ticket = new Ticket();
        ticket=((EventServiceImpl)service).findTicketById(Long.parseLong(content.getRequestParameter(ParameterName.ORDER_LINE_TICKET)));
        orderLine.setTicket(ticket);

        String page = (String) content.getSessionAttribute("current_page");
        log.info(page);

        return page;
    }
}
