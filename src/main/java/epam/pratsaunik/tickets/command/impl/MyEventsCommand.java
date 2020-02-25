package epam.pratsaunik.tickets.command.impl;

import epam.pratsaunik.tickets.command.AbstractCommand;
import epam.pratsaunik.tickets.command.CommandResult;
import epam.pratsaunik.tickets.command.RequestContent;
import epam.pratsaunik.tickets.entity.Event;
import epam.pratsaunik.tickets.entity.User;
import epam.pratsaunik.tickets.exception.CommandException;
import epam.pratsaunik.tickets.exception.ServiceLevelException;
import epam.pratsaunik.tickets.service.Service;
import epam.pratsaunik.tickets.service.impl.EventServiceImpl;
import epam.pratsaunik.tickets.servlet.AttributeName;
import epam.pratsaunik.tickets.util.ConfigurationManager2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class MyEventsCommand extends AbstractCommand {
    private final static int EVENTS_PER_PAGE = 3;
    private final static Logger log = LogManager.getLogger();

    public MyEventsCommand(Service service) {
        super(service);
    }

    @Override
    public CommandResult execute(RequestContent content) {
        CommandResult commandResult=new CommandResult();
        List<Event> list;
        try {
            int nOfRecords =  service.getNumberOfRecords();
            int nOfPages = nOfRecords / EVENTS_PER_PAGE;
            if (nOfRecords % EVENTS_PER_PAGE > 0) {
                nOfPages++;
            }
            int currentPage = Integer.parseInt(content.getRequestParameter("currentPage"));
            User owner = (User)content.getSessionAttribute("user");
            list = ((EventServiceImpl) service).findEventsByHost(owner, currentPage, EVENTS_PER_PAGE);
            log.debug(list);
            log.debug("nOfRecords" + nOfRecords + " nOfPages:" + nOfPages + " Size of list" + list.size());
            content.setSessionAttribute("nOfPages", nOfPages);
            content.setSessionAttribute("currentPage", currentPage);
            content.setSessionAttribute(AttributeName.EVENTS, list);
        } catch (ServiceLevelException e) {
            e.printStackTrace();
        }
        commandResult.setResponsePage(ConfigurationManager2.CATALOG_PAGE_PATH.getProperty());
        commandResult.setResponseType(CommandResult.ResponseType.FORWARD);
        return commandResult;
    }
}
