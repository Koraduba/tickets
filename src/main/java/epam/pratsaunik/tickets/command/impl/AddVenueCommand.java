package epam.pratsaunik.tickets.command.impl;

import epam.pratsaunik.tickets.command.AbstractCommand;
import epam.pratsaunik.tickets.command.RequestContent;
import epam.pratsaunik.tickets.entity.Venue;
import epam.pratsaunik.tickets.exception.CommandException;
import epam.pratsaunik.tickets.exception.ServiceLevelException;
import epam.pratsaunik.tickets.service.Service;
import epam.pratsaunik.tickets.service.impl.EventServiceImpl;
import epam.pratsaunik.tickets.util.ConfigurationManager;
import epam.pratsaunik.tickets.util.ConfigurationManager2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class AddVenueCommand extends AbstractCommand {
    private final static Logger log = LogManager.getLogger();
    public AddVenueCommand(Service service) {
        super(service);
    }

    @Override
    public String execute(RequestContent content) throws CommandException {
        String page=null;
        Venue venue = new Venue();
        List<Venue> venueList = null;
        venue.setName(content.getRequestParameter("name"));
        venue.setCapacity(Integer.parseInt(content.getRequestParameter("capacity")));
        try {
            long eventId=((EventServiceImpl)service).createVenue(venue);
            venueList=((EventServiceImpl)service).findAllVenues();
            content.setSessionAttribute("id", eventId);
            content.setSessionAttribute("venues",venueList);
            page=ConfigurationManager2.UPLOAD_LAYOUT_PATH.getProperty();
        } catch (ServiceLevelException e) {
            throw new CommandException(e);
        }
        return page;
    }
}
