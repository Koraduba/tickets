package epam.pratsaunik.tickets.command.impl;

import epam.pratsaunik.tickets.command.AbstractCommand;
import epam.pratsaunik.tickets.command.CommandResult;
import epam.pratsaunik.tickets.command.RequestContent;
import epam.pratsaunik.tickets.exception.CommandException;
import epam.pratsaunik.tickets.exception.ServiceLevelException;
import epam.pratsaunik.tickets.service.Service;
import epam.pratsaunik.tickets.service.impl.UserServiceImpl;
import epam.pratsaunik.tickets.util.ConfigurationManager2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Class{@code InitCommand} is used to create default admin account
 *
 * @version 1.0
 * @see AbstractCommand
 */
public class InitCommand extends AbstractCommand {
    private final static Logger log = LogManager.getLogger();

    public InitCommand(Service service) {
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
    public CommandResult execute(RequestContent content) throws CommandException {
        CommandResult commandResult = new CommandResult();
        try {
            ((UserServiceImpl) service).createAdmin();
            commandResult.setResponsePage(ConfigurationManager2.LOGIN_PAGE_PATH.getProperty());
            commandResult.setResponseType(CommandResult.ResponseType.FORWARD);
        } catch (ServiceLevelException e) {
            throw new CommandException(e);
        }
        return commandResult;
    }
}

