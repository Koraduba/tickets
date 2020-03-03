package epam.pratsaunik.tickets.command.impl;

import epam.pratsaunik.tickets.command.AbstractCommand;
import epam.pratsaunik.tickets.command.CommandResult;
import epam.pratsaunik.tickets.command.RequestContent;
import epam.pratsaunik.tickets.exception.CommandException;
import epam.pratsaunik.tickets.util.ConfigurationManager;
import epam.pratsaunik.tickets.entity.User;
import epam.pratsaunik.tickets.exception.ServiceLevelException;
import epam.pratsaunik.tickets.service.Service;
import epam.pratsaunik.tickets.service.impl.UserServiceImpl;
import epam.pratsaunik.tickets.servlet.AttributeName;
import epam.pratsaunik.tickets.util.ConfigurationManager2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
/**
 * Class{@code AddVenueCommand} is used to create and save new venue in data base
 * @version 1.0
 * @see AbstractCommand
 */
public class UsersCommand extends AbstractCommand {
    private final static int RECORDS_PER_PAGE=3;
    private final static Logger log = LogManager.getLogger();

    public UsersCommand (Service service){
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
        List<User> list = null;
        try {
            int nOfRecords = ((UserServiceImpl)service).getNumberOfRecords();
            int nOfPages = nOfRecords / RECORDS_PER_PAGE;
            if (nOfRecords % RECORDS_PER_PAGE > 0) {
                nOfPages++;
            }
            int currentPage=Integer.parseInt(content.getRequestParameter("currentPage"));
            list = ((UserServiceImpl)service).findRange(currentPage, RECORDS_PER_PAGE);
            log.debug("Current page:"+currentPage+" nOfPages:"+nOfPages+" Size of list"+list.size());
            content.setSessionAttribute("nOfPages",nOfPages);
            content.setSessionAttribute("currentPage",currentPage);
            content.setSessionAttribute(AttributeName.USERS, list);
        } catch (ServiceLevelException e) {
            throw new CommandException(e);
        }
        commandResult.setResponsePage(ConfigurationManager2.USERS_PAGE_PATH.getProperty());
        commandResult.setResponseType(CommandResult.ResponseType.FORWARD);
        return commandResult;
    }
}
