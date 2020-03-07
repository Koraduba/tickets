package epam.pratsaunik.tickets.util;

import epam.pratsaunik.tickets.command.RequestContent;
import epam.pratsaunik.tickets.entity.Ticket;
import epam.pratsaunik.tickets.entity.TicketCat;
import epam.pratsaunik.tickets.servlet.AttributeName;
import epam.pratsaunik.tickets.servlet.ParameterName;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

/**
 * to keep data in form from initial input to no enter again in case of form resubmit.
 */
public class InputKeeper {

    private static InputKeeper instance;

    private InputKeeper() {
    }

    /**
     * Singleton
     * @return instance of InputKeeper
     */
    public static InputKeeper getInstance() {
        if (instance == null) {
            instance = new InputKeeper();
        }
        return instance;
    }

    /**
     * to keep user data by putting them in request attributes
     * @param content instance to provide request parameters ans session attributes
     */
    public void keepUser(RequestContent content) {
        content.setRequestAttribute(AttributeName.USER_EMAIL, content.getRequestParameter(ParameterName.USER_EMAIL));
        content.setRequestAttribute(AttributeName.USER_SURNAME, content.getRequestParameter(ParameterName.USER_SURNAME));
        content.setRequestAttribute(AttributeName.USER_NAME, content.getRequestParameter(ParameterName.USER_NAME));
        content.setRequestAttribute(AttributeName.USER_LOGIN, content.getRequestParameter(ParameterName.USER_LOGIN));
    }

    /**
     * to keep event data by putting them in request attributes
     * @param content instance to provide request parameters ans session attributes
     */
    public void keepEvent(RequestContent content) {
        content.setSessionAttribute(AttributeName.EVENT_NAME, content.getRequestParameter(ParameterName.EVENT_NAME));
        content.setSessionAttribute(AttributeName.EVENT_DATE, content.getRequestParameter(ParameterName.EVENT_DATE));
        content.setSessionAttribute(AttributeName.EVENT_TIME, content.getRequestParameter(ParameterName.EVENT_TIME));
        content.setSessionAttribute(AttributeName.EVENT_DESCRIPTION, content.getRequestParameter(ParameterName.EVENT_DESCRIPTION));
        content.setSessionAttribute(AttributeName.EVENT_VENUE, content.getRequestParameter(ParameterName.EVENT_VENUE));
        content.setSessionAttribute(AttributeName.TICKET_PRICE_STANDARD, content.getRequestParameter(ParameterName.TICKET_PRICE_STANDARD));
        content.setSessionAttribute(AttributeName.TICKET_PRICE_VIP, content.getRequestParameter(ParameterName.TICKET_PRICE_VIP));

        List<Ticket> ticketList = (List<Ticket>) content.getSessionAttribute(AttributeName.TICKET_LIST);
        if (ticketList != null) {
            for (Ticket ticket : ticketList) {
                TicketCat cat = ticket.getCategory();
                String parameter = "price_" + cat.toString();
                ticket.setPrice(new BigDecimal(content.getRequestParameter(parameter)));
            }
            content.setSessionAttribute(AttributeName.TICKET_LIST, ticketList);
        }

    }

    /**
     * to clear event data by setting {@code null} to attributes
     * @param content instance to provide request parameters ans session attributes
     */
    public void clearEvent(RequestContent content) {
        content.setSessionAttribute(AttributeName.EVENT, null);
        content.setSessionAttribute(AttributeName.EVENT_NAME, null);
        content.setSessionAttribute(AttributeName.EVENT_DATE, null);
        content.setSessionAttribute(AttributeName.EVENT_TIME, null);
        content.setSessionAttribute(AttributeName.EVENT_DESCRIPTION, null);
        content.setSessionAttribute(AttributeName.EVENT_VENUE, null);
        content.setSessionAttribute(AttributeName.TICKET_PRICE_STANDARD, null);
        content.setSessionAttribute(AttributeName.TICKET_PRICE_VIP, null);
        content.setSessionAttribute(AttributeName.EVENT_IMAGE, null);
        content.setSessionAttribute(AttributeName.VENUE_IMAGE, null);
        content.setSessionAttribute(AttributeName.TICKET_LIST, null);
    }


    /**
     * to keep venue data by putting them in request attributes
     * @param content instance to provide request parameters ans session attributes
     */
    public void keepVenue(RequestContent content) {
        content.setRequestAttribute(AttributeName.VENUE_NAME, content.getRequestParameter(ParameterName.VENUE_NAME));
        content.setRequestAttribute(AttributeName.VENUE_CAPACITY, content.getRequestParameter(ParameterName.VENUE_CAPACITY));

    }

}
