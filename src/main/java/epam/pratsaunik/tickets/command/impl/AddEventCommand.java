package epam.pratsaunik.tickets.command.impl;

import epam.pratsaunik.tickets.command.AbstractCommand;
import epam.pratsaunik.tickets.command.RequestContent;
import epam.pratsaunik.tickets.entity.Event;
import epam.pratsaunik.tickets.entity.Venue;
import epam.pratsaunik.tickets.exception.ServiceLevelException;
import epam.pratsaunik.tickets.service.Service;
import epam.pratsaunik.tickets.service.impl.EventServiceImpl;
import epam.pratsaunik.tickets.service.impl.UserServiceImpl;
import epam.pratsaunik.tickets.util.ConfigurationManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddEventCommand extends AbstractCommand {
    private final static Logger log = LogManager.getLogger();

    public AddEventCommand(Service service) {
        super(service);
    }

    @Override
    public String execute(RequestContent content) {
        String page = null;
        Event event = new Event();
        Date date = null;
        event.setName(content.getRequestParameter("name"));
        log.debug(content.getRequestParameter("date"));
        log.debug(content.getRequestParameter("time"));
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm");
        try {
            date = dateFormat.parse(content.getRequestParameter("date")+" "+content.getRequestParameter("time"));
            log.debug(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        event.setDate(date);
        event.setDescription(content.getRequestParameter("description"));
        String venue = content.getRequestParameter("venue");
        try {
            if (venue != null) {
                event.setVenue(((EventServiceImpl) service).findVenueByName(venue));
            }

            ((EventServiceImpl) service).create(event);
            log.debug("event created");
            page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.CATALOG_PAGE_PATH);
        } catch (ServiceLevelException e) {
            log.error("Exception in AddEventCommand " + e);
            page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.ERROR_PAGE_PATH);
        }
        return page;
    }
}
