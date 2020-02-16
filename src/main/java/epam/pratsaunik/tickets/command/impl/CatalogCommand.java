package epam.pratsaunik.tickets.command.impl;

import epam.pratsaunik.tickets.command.AbstractCommand;
import epam.pratsaunik.tickets.command.RequestContent;
import epam.pratsaunik.tickets.entity.Event;
import epam.pratsaunik.tickets.entity.Role;
import epam.pratsaunik.tickets.entity.User;
import epam.pratsaunik.tickets.exception.ServiceLevelException;
import epam.pratsaunik.tickets.service.Service;
import epam.pratsaunik.tickets.service.impl.EventServiceImpl;
import epam.pratsaunik.tickets.service.impl.UserServiceImpl;
import epam.pratsaunik.tickets.servlet.AttributeName;
import epam.pratsaunik.tickets.util.ConfigurationManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class CatalogCommand extends AbstractCommand {
    private final static int EVENTS_PER_PAGE=3;
    private final static Logger log = LogManager.getLogger();


    public CatalogCommand(Service service) {
        super(service);
    }

    @Override
    public String execute(RequestContent content) {

        List<Event> list;
        try {
            int nOfRecords = ((EventServiceImpl)service).getNumberOfRecords();
            int nOfPages = nOfRecords / EVENTS_PER_PAGE;
            if (nOfPages % EVENTS_PER_PAGE > 0) {
                nOfPages++;
            }
            int currentPage=Integer.parseInt(content.getRequestParameter("currentPage"));
            list = ((EventServiceImpl)service).findEventsByRange(currentPage, EVENTS_PER_PAGE);
            log.debug(list);
            log.debug("Current page:"+currentPage+" nOfPages:"+nOfPages+" Size of list"+list.size());
            content.setSessionAttribute("nOfPages",nOfPages);
            content.setSessionAttribute("currentPage",currentPage);
            content.setSessionAttribute(AttributeName.EVENTS, list);
        } catch (ServiceLevelException e) {
            e.printStackTrace();
        }


        return ConfigurationManager.getInstance().getProperty(ConfigurationManager.CATALOG_PAGE_PATH);
    }
}
