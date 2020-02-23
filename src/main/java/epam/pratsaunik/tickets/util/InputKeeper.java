package epam.pratsaunik.tickets.util;

import epam.pratsaunik.tickets.command.RequestContent;
import epam.pratsaunik.tickets.servlet.AttributeName;
import epam.pratsaunik.tickets.servlet.ParameterName;

public class InputKeeper {

    public void keepEvent(RequestContent content){
        content.setSessionAttribute(AttributeName.EVENT_NAME, content.getRequestParameter(ParameterName.EVENT_NAME));
        content.setSessionAttribute(AttributeName.EVENT_DATE, content.getRequestParameter(ParameterName.EVENT_DATE));
        content.setSessionAttribute(AttributeName.EVENT_TIME, content.getRequestParameter(ParameterName.EVENT_TIME));
        content.setSessionAttribute(AttributeName.EVENT_DESCRIPTION, content.getRequestParameter(ParameterName.EVENT_DESCRIPTION));
        content.setSessionAttribute(AttributeName.EVENT_VENUE, content.getRequestParameter(ParameterName.EVENT_VENUE));
        content.setSessionAttribute(AttributeName.TICKET_PRICE_STANDARD, content.getRequestParameter(ParameterName.TICKET_PRICE_STANDARD));
        content.setSessionAttribute(AttributeName.TICKET_PRICE_VIP, content.getRequestParameter(ParameterName.TICKET_PRICE_VIP));
    }
}
