package epam.pratsaunik.tickets.command.impl;

import epam.pratsaunik.tickets.command.AbstractCommand;
import epam.pratsaunik.tickets.command.RequestContent;
import epam.pratsaunik.tickets.entity.Event;
import epam.pratsaunik.tickets.entity.Venue;
import epam.pratsaunik.tickets.exception.ServiceLevelException;
import epam.pratsaunik.tickets.service.Service;
import epam.pratsaunik.tickets.service.impl.EventServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LayoutUploadCommand extends AbstractCommand {
    private final static Logger log = LogManager.getLogger();
    public LayoutUploadCommand(Service service) {
        super(service);
    }
    @Override
    public String execute(RequestContent content) {
        log.debug("LayoutUploadCommand launched");

        try {
            Venue venue=((EventServiceImpl) service).findVenueById((Long)content.getSessionAttribute("id"));
            venue.setLayout((String)content.getSessionAttribute("path"));
            ((EventServiceImpl) service).updateVenue(venue);
        } catch (ServiceLevelException e) {
            e.printStackTrace();
        }
        return null;
    }
}
