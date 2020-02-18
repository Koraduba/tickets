package epam.pratsaunik.tickets.command.impl;

import epam.pratsaunik.tickets.command.AbstractCommand;
import epam.pratsaunik.tickets.command.RequestContent;
import epam.pratsaunik.tickets.entity.Event;
import epam.pratsaunik.tickets.entity.Venue;
import epam.pratsaunik.tickets.exception.CommandException;
import epam.pratsaunik.tickets.exception.ServiceLevelException;
import epam.pratsaunik.tickets.service.Service;
import epam.pratsaunik.tickets.service.impl.EventServiceImpl;
import epam.pratsaunik.tickets.util.ConfigurationManager;
import epam.pratsaunik.tickets.util.ConfigurationManager2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UploadLayoutCommand extends AbstractCommand {
    private final static Logger log = LogManager.getLogger();

    public UploadLayoutCommand(Service service) {
        super(service);
    }
    @Override
    public String execute(RequestContent content) throws CommandException {
        log.debug("UploadLayoutCommand launched");
        try {
            Long id=(Long)content.getSessionAttribute("id");
            if (id!=null){
                Venue venue = ((EventServiceImpl) service).findVenueById(id);
                ((EventServiceImpl) service).updateVenue(venue);
                venue.setLayout((String)content.getSessionAttribute("path"));
                ((EventServiceImpl) service).updateVenue(venue);
            }else{
                throw new CommandException("No venueId in session");
            }
        } catch (ServiceLevelException e) {
            throw new CommandException(e);
        }
        return ConfigurationManager2.HOME_PAGE_PATH.getProperty();
    }
}
