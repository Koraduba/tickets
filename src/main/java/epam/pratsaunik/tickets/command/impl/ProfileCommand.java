package epam.pratsaunik.tickets.command.impl;

import epam.pratsaunik.tickets.command.AbstractCommand;
import epam.pratsaunik.tickets.command.CommandResult;
import epam.pratsaunik.tickets.command.RequestContent;
import epam.pratsaunik.tickets.exception.CommandException;
import epam.pratsaunik.tickets.service.Service;
import epam.pratsaunik.tickets.util.ConfigurationManager2;
/**
 * Class{@code AddVenueCommand} is used to create and save new venue in data base
 * @version 1.0
 * @see AbstractCommand
 */
public class ProfileCommand extends AbstractCommand {
    public ProfileCommand(Service service) {
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
        CommandResult commandResult =new CommandResult();
        commandResult.setResponsePage(ConfigurationManager2.PROFILE_PAGE_PATH.getProperty());
        commandResult.setResponseType(CommandResult.ResponseType.REDIRECT);
        return commandResult;
    }
}
