package epam.pratsaunik.tickets.command.impl;

import epam.pratsaunik.tickets.command.AbstractCommand;
import epam.pratsaunik.tickets.command.CommandResult;
import epam.pratsaunik.tickets.command.CommandType;
import epam.pratsaunik.tickets.command.RequestContent;
import epam.pratsaunik.tickets.entity.User;
import epam.pratsaunik.tickets.exception.CommandException;
import epam.pratsaunik.tickets.exception.ServiceLevelException;
import epam.pratsaunik.tickets.service.Service;
import epam.pratsaunik.tickets.service.impl.UserServiceImpl;
import epam.pratsaunik.tickets.servlet.AttributeName;
import epam.pratsaunik.tickets.servlet.ParameterName;
import epam.pratsaunik.tickets.util.ConfigurationManager2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class DeleteUserCommand extends AbstractCommand {
    private final static int RECORDS_PER_PAGE = 3;
    private final static Logger log = LogManager.getLogger();
    public DeleteUserCommand(Service service) {
        super(service);
    }

    @Override
    public CommandResult execute(RequestContent content)  {
        CommandResult commandResult = new CommandResult();
        List<User> list;
        try {
            String login = content.getRequestParameter(ParameterName.USER_LOGIN);
            User user = ((UserServiceImpl)service).findUserByLogin(login).get(0);
            log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<"+user.getUserId());
            ((UserServiceImpl)service).delete(user);
            int nOfRecords = service.getNumberOfRecords();
            int nOfPages = nOfRecords / RECORDS_PER_PAGE;
            if (nOfRecords % RECORDS_PER_PAGE > 0) {
                nOfPages++;
            }
            int currentPage = Integer.parseInt(content.getRequestParameter(ParameterName.CURRENT_PAGE));
            list = ((UserServiceImpl) service).findRange(currentPage, RECORDS_PER_PAGE);
            log.debug("Current page:" + currentPage + " nOfPages:" + nOfPages + " Size of list" + list.size());
            content.setSessionAttribute(AttributeName.NUMBER_OF_PAGES, nOfPages);
            content.setSessionAttribute(AttributeName.CURRENT_PAGE, currentPage);
            content.setSessionAttribute(AttributeName.USERS, list);
        } catch (ServiceLevelException e) {
            log.error(e);
            content.setRequestAttribute(AttributeName.COMMAND, CommandType.HOME.toString());
            commandResult.setResponsePage(ConfigurationManager2.ERROR_PAGE_PATH.getProperty());
            commandResult.setResponseType(CommandResult.ResponseType.FORWARD);
            return commandResult;
        }
        commandResult.setResponsePage(ConfigurationManager2.USERS_PAGE_PATH.getProperty());
        commandResult.setResponseType(CommandResult.ResponseType.FORWARD);
        content.setSessionAttribute(AttributeName.HOME_MESSAGE, null);
        return commandResult;
    }
}
