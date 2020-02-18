package epam.pratsaunik.tickets.command.impl;

import epam.pratsaunik.tickets.command.AbstractCommand;
import epam.pratsaunik.tickets.command.RequestContent;
import epam.pratsaunik.tickets.entity.Event;
import epam.pratsaunik.tickets.entity.Venue;
import epam.pratsaunik.tickets.exception.CommandException;
import epam.pratsaunik.tickets.exception.ServiceLevelException;
import epam.pratsaunik.tickets.service.Service;
import epam.pratsaunik.tickets.service.impl.EventServiceImpl;
import epam.pratsaunik.tickets.util.ConfigurationManager2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UploadCommand extends AbstractCommand {
    private final static Logger log = LogManager.getLogger();
    public UploadCommand(Service service) {
        super(service);
    }
    @Override
    public String execute(RequestContent content) throws CommandException {
        log.debug("UploadCommand launched");
        log.debug("Current page",content.getSessionAttribute("current_page").toString());

        try {
            Long id=(Long)content.getSessionAttribute("id");
            if (id!=null){
                Event event = ((EventServiceImpl) service).findEventById(id);
                event.setImage((String)content.getSessionAttribute("path"));
                ((EventServiceImpl) service).update(event);
            }else{
                throw new CommandException("No eventId in session");
            }
        } catch (ServiceLevelException e) {
            e.printStackTrace();
        }
        return ConfigurationManager2.HOME_PAGE_PATH.getProperty();
    }
}
