package epam.pratsaunik.tickets.command.impl;

import epam.pratsaunik.tickets.command.AbstractCommand;
import epam.pratsaunik.tickets.command.CommandResult;
import epam.pratsaunik.tickets.command.RequestContent;
import epam.pratsaunik.tickets.exception.CommandException;
import epam.pratsaunik.tickets.service.Service;

public class EditUserCommand extends AbstractCommand {
    public EditUserCommand(Service service) {
        super(service);
    }

    @Override
    public CommandResult execute(RequestContent content) throws CommandException {
        return null;
    }
}
