package epam.pratsaunik.tickets.command.impl;

import epam.pratsaunik.tickets.command.AbstractCommand;
import epam.pratsaunik.tickets.command.CommandResult;
import epam.pratsaunik.tickets.command.CommandType;
import epam.pratsaunik.tickets.command.RequestContent;
import epam.pratsaunik.tickets.entity.Event;
import epam.pratsaunik.tickets.exception.ServiceLevelException;
import epam.pratsaunik.tickets.service.Service;
import epam.pratsaunik.tickets.service.impl.EventServiceImpl;
import epam.pratsaunik.tickets.servlet.AttributeName;
import epam.pratsaunik.tickets.util.ConfigurationManager2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Class{@code CatalogCommand} is used to forward to events page with providing needed data
 *
 * @version 1.0
 * @see AbstractCommand
 */
public class CatalogCommand extends AbstractCommand {
    private final static int EVENTS_PER_PAGE = 3;
    private final static Logger log = LogManager.getLogger();


    public CatalogCommand(Service service) {
        super(service);
    }

    /**
     * @param content{@code RequestContent} instance to provide request parameters ans session attributes access
     * @return {@code CommandResult} instance with information about response type and further destination page
     * @see RequestContent
     * @see CommandResult
     */
    @Override
    public CommandResult execute(RequestContent content)  {
        CommandResult commandResult = new CommandResult();
        List<Event> list;
        try {
            int nOfRecords = service.getNumberOfRecords();
            int nOfPages = nOfRecords / EVENTS_PER_PAGE;
            if (nOfRecords % EVENTS_PER_PAGE > 0) {
                nOfPages++;
            }
            int currentPage = Integer.parseInt(content.getRequestParameter("currentPage"));
            list = ((EventServiceImpl) service).findEventsByRange(currentPage, EVENTS_PER_PAGE);
            log.debug(list);
            log.debug("nOfRecords" + nOfRecords + " nOfPages:" + nOfPages + " Size of list" + list.size());
            content.setSessionAttribute("nOfPages", nOfPages);
            content.setSessionAttribute("currentPage", currentPage);
            content.setSessionAttribute(AttributeName.EVENTS, list);
            content.setSessionAttribute(AttributeName.HOME_MESSAGE, null);
        } catch (ServiceLevelException e) {
            log.error(e);
            content.setRequestAttribute(AttributeName.COMMAND, CommandType.HOME.toString());
            commandResult.setResponsePage(ConfigurationManager2.ERROR_PAGE_PATH.getProperty());
            commandResult.setResponseType(CommandResult.ResponseType.FORWARD);
            return commandResult;
        }
        commandResult.setResponsePage(ConfigurationManager2.CATALOG_PAGE_PATH.getProperty());
        commandResult.setResponseType(CommandResult.ResponseType.FORWARD);
        return commandResult;
    }
}
