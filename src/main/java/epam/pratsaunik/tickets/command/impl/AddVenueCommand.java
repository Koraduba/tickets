package epam.pratsaunik.tickets.command.impl;

import epam.pratsaunik.tickets.command.AbstractCommand;
import epam.pratsaunik.tickets.command.CommandResult;
import epam.pratsaunik.tickets.command.RequestContent;
import epam.pratsaunik.tickets.entity.Venue;
import epam.pratsaunik.tickets.exception.CommandException;
import epam.pratsaunik.tickets.exception.ServiceLevelException;
import epam.pratsaunik.tickets.service.Service;
import epam.pratsaunik.tickets.service.impl.EventServiceImpl;
import epam.pratsaunik.tickets.servlet.ParameterName;
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
    public CommandResult execute(RequestContent content) throws CommandException {
        CommandResult commandResult=new CommandResult();
        Venue venue = new Venue();
        List<Venue> venueList = null;
        venue.setName(content.getRequestParameter(ParameterName.VENUE_NAME));
        venue.setCapacity(Integer.parseInt(content.getRequestParameter(ParameterName.VENUE_CAPACITY)));
        try {
            long eventId=((EventServiceImpl)service).createVenue(venue);
            venueList=((EventServiceImpl)service).findAllVenues();
            content.setSessionAttribute("id", eventId);
            content.setSessionAttribute("venues",venueList);
            commandResult.setResponsePage(ConfigurationManager2.UPLOAD_LAYOUT_PATH.getProperty());
            commandResult.setResponseType(CommandResult.ResponseType.FORWARD);
        } catch (ServiceLevelException e) {
            throw new CommandException(e);
        }
        return commandResult;
    }
}
