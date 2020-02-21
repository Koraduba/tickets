package epam.pratsaunik.tickets.command.impl;

import epam.pratsaunik.tickets.command.AbstractCommand;
import epam.pratsaunik.tickets.command.CommandResult;
import epam.pratsaunik.tickets.command.RequestContent;
import epam.pratsaunik.tickets.exception.CommandException;
import epam.pratsaunik.tickets.service.Service;
import epam.pratsaunik.tickets.util.ConfigurationManager2;

public class ProfileCommand extends AbstractCommand {
    public ProfileCommand(Service service) {
        super(service);
    }

    @Override
    public CommandResult execute(RequestContent content) throws CommandException {
        CommandResult commandResult =new CommandResult();
        commandResult.setResponsePage(ConfigurationManager2.PROFILE_PAGE_PATH.getProperty());
        commandResult.setResponseType(CommandResult.ResponseType.REDIRECT);
        return commandResult;
    }
}
