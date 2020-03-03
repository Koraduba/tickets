package epam.pratsaunik.tickets.command.impl;

import epam.pratsaunik.tickets.command.AbstractCommand;
import epam.pratsaunik.tickets.command.CommandResult;
import epam.pratsaunik.tickets.command.RequestContent;
import epam.pratsaunik.tickets.entity.Venue;
import epam.pratsaunik.tickets.exception.CommandException;
import epam.pratsaunik.tickets.exception.ServiceLevelException;
import epam.pratsaunik.tickets.service.Service;
import epam.pratsaunik.tickets.service.impl.EventServiceImpl;
import epam.pratsaunik.tickets.servlet.AttributeName;
import epam.pratsaunik.tickets.util.ConfigurationManager;
import epam.pratsaunik.tickets.util.ConfigurationManager2;
import epam.pratsaunik.tickets.util.InputKeeper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
/**
 * Class{@code AddVenueCommand} is used to create and save new venue in data base
 * @version 1.0
 * @see AbstractCommand
 */
public class NewEventPageCommand extends AbstractCommand {
    private final static Logger log = LogManager.getLogger();
    public NewEventPageCommand(Service service) {
        super(service);
    }
    /**
     *
     * @param content{@code RequestContent} instance to provide request parameters ans session attributes access
     * @return {@code CommandResult} instance with information about response type and further destination page
     * @throws CommandException custom exception to be thrown in case of exception on service level
     * @see RequestContent
     * @see CommandResult
     */
    @Override
    public CommandResult execute(RequestContent content) throws CommandException {
        CommandResult commandResult=new CommandResult();
        if(content.getSessionAttribute("event")!=null){
            InputKeeper.getInstance().clearEvent(content);
        }
        List<Venue> venueList;
        try {
            venueList=((EventServiceImpl)service).findAllVenues();
        } catch (ServiceLevelException e) {
            log.error("Error in NewEventPageCommand:"+e);
            throw new CommandException("NewEventPageCommand",e);
        }
        content.setSessionAttribute(AttributeName.VENUE_LIST,venueList);
        commandResult.setResponsePage(ConfigurationManager2.NEW_EVENT_PAGE_PATH.getProperty());
        commandResult.setResponseType(CommandResult.ResponseType.FORWARD);
        return commandResult;
    }
}
