package epam.pratsaunik.tickets.command.impl;

import epam.pratsaunik.tickets.command.AbstractCommand;
import epam.pratsaunik.tickets.command.CommandResult;
import epam.pratsaunik.tickets.command.RequestContent;
import epam.pratsaunik.tickets.entity.Role;
import epam.pratsaunik.tickets.exception.CommandException;
import epam.pratsaunik.tickets.service.Service;
import epam.pratsaunik.tickets.servlet.AttributeName;
import epam.pratsaunik.tickets.util.ConfigurationManager2;
import epam.pratsaunik.tickets.util.MessageManager;

import java.util.Locale;

public class LogoutCommand extends AbstractCommand {
    public LogoutCommand(Service service) {
        super(service);
    }

    @Override
    public CommandResult execute(RequestContent content) throws CommandException {
        CommandResult commandResult = new CommandResult();
        content.setSessionAttribute(AttributeName.USER_ROLE,null);
        MessageManager.INSTANCE.changeResource(Locale.getDefault());
        content.setSessionAttribute(AttributeName.LOCALE, Locale.getDefault());
        commandResult.setResponsePage(ConfigurationManager2.LOGIN_PAGE_PATH.getProperty());
        commandResult.setResponseType(CommandResult.ResponseType.FORWARD);
        return commandResult;
    }
}
