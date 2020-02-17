package epam.pratsaunik.tickets.command.impl;

import epam.pratsaunik.tickets.command.AbstractCommand;
import epam.pratsaunik.tickets.command.RequestContent;
import epam.pratsaunik.tickets.entity.Event;
import epam.pratsaunik.tickets.exception.ServiceLevelException;
import epam.pratsaunik.tickets.service.Service;
import epam.pratsaunik.tickets.service.impl.EventServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UploadCommand extends AbstractCommand {
    private final static Logger log = LogManager.getLogger();
    public UploadCommand(Service service) {
        super(service);
    }
    @Override
    public String execute(RequestContent content) {
        log.debug("UploadCommand launched");
        log.debug("Current page",content.getSessionAttribute("current_page").toString());

        Event event;
        try {
            event=((EventServiceImpl) service).findEventById((Long)content.getSessionAttribute("id"));
            log.debug("Event:",event);
            event.setImage((String)content.getSessionAttribute("path"));
            ((EventServiceImpl) service).update(event);
        } catch (ServiceLevelException e) {
            e.printStackTrace();
        }
        return null;
    }
}
