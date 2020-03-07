package epam.pratsaunik.tickets.command.impl;

import epam.pratsaunik.tickets.command.AbstractCommand;
import epam.pratsaunik.tickets.command.CommandResult;
import epam.pratsaunik.tickets.command.RequestContent;
import epam.pratsaunik.tickets.exception.CommandException;
import epam.pratsaunik.tickets.service.Service;
import epam.pratsaunik.tickets.util.ConfigurationManager2;
/**
 * Class{@code NewPasswordPageCommand} to forward to NewPasswordPage
 * @version 1.0
 * @see AbstractCommand
 */
public class NewPasswordPageCommand extends AbstractCommand {
    public NewPasswordPageCommand(Service service) {
        super(service);
    }

    /**
     *
     * @param content{@code RequestContent} instance to provide request parameters ans session attributes access
     * @return {@code CommandResult} instance with information about response type and further destination page
     * @see RequestContent
     * @see CommandResult
     */
    @Override
    public CommandResult execute(RequestContent content) throws CommandException {
        CommandResult commandResult = new CommandResult();
        commandResult.setResponsePage(ConfigurationManager2.CHANGE_PASSWORD_PAGE_PATH.getProperty());
        commandResult.setResponseType(CommandResult.ResponseType.FORWARD);
        return commandResult;
    }
}
