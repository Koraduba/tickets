package epam.pratsaunik.tickets.command.impl;

import epam.pratsaunik.tickets.command.AbstractCommand;
import epam.pratsaunik.tickets.command.CommandResult;
import epam.pratsaunik.tickets.command.CommandType;
import epam.pratsaunik.tickets.command.RequestContent;
import epam.pratsaunik.tickets.entity.Event;
import epam.pratsaunik.tickets.entity.User;
import epam.pratsaunik.tickets.exception.ServiceLevelException;
import epam.pratsaunik.tickets.service.Service;
import epam.pratsaunik.tickets.service.impl.EventServiceImpl;
import epam.pratsaunik.tickets.servlet.AttributeName;
import epam.pratsaunik.tickets.servlet.ParameterName;
import epam.pratsaunik.tickets.util.ConfigurationManager2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Class{@code MyEventsCommand} is used to create and save new venue in data base
 *
 * @version 1.0
 * @see AbstractCommand
 */
public class MyEventsCommand extends AbstractCommand {
    private final static int EVENTS_PER_PAGE = 3;
    private final static Logger log = LogManager.getLogger();

    public MyEventsCommand(Service service) {
        super(service);
    }

    /**
     * @param content{@code RequestContent} instance to provide request parameters ans session attributes access
     * @return {@code CommandResult} instance with information about response type and further destination page
     * @throws CommandException custom exception to be thrown in case of exception on service level
     * @see RequestContent
     * @see CommandResult
     */
    @Override
    public CommandResult execute(RequestContent content)  {
        CommandResult commandResult = new CommandResult();
        List<Event> list;
        User owner = (User) content.getSessionAttribute(AttributeName.USER);
        try {
            int nOfRecords = ((EventServiceImpl) service).getNumberOfEventsByHost(owner);
            int nOfPages = nOfRecords / EVENTS_PER_PAGE;
            if (nOfRecords % EVENTS_PER_PAGE > 0) {
                nOfPages++;
            }
            int currentPage = Integer.parseInt(content.getRequestParameter(ParameterName.CURRENT_PAGE));
            list = ((EventServiceImpl) service).findEventsByHost(owner, currentPage, EVENTS_PER_PAGE);
            log.debug(list);
            content.setSessionAttribute(AttributeName.NUMBER_OF_PAGES, nOfPages);
            content.setSessionAttribute(AttributeName.CURRENT_PAGE, currentPage);
            content.setSessionAttribute(AttributeName.EVENTS, list);
            content.setSessionAttribute(AttributeName.HOME_MESSAGE, null);
            content.setSessionAttribute(AttributeName.EVENT,null);
        } catch (ServiceLevelException e) {
            log.error(e);
            content.setRequestAttribute(AttributeName.COMMAND, CommandType.HOME.toString());
            commandResult.setResponsePage(ConfigurationManager2.ERROR_PAGE_PATH.getProperty());
            commandResult.setResponseType(CommandResult.ResponseType.FORWARD);
            return commandResult;
        }
        commandResult.setResponsePage(ConfigurationManager2.MY_EVENTS_PAGE_PATH.getProperty());
        commandResult.setResponseType(CommandResult.ResponseType.FORWARD);
        return commandResult;
    }
}
