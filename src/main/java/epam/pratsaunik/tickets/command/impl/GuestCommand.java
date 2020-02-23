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

public class GuestCommand extends AbstractCommand {
    public GuestCommand(Service service) {
        super(service);
    }

    @Override
    public CommandResult execute(RequestContent content) throws CommandException {
        CommandResult commandResult = new CommandResult();
        content.setSessionAttribute(AttributeName.USER_ROLE,Role.GUEST);
        Locale rus = new Locale("ru", "RU");
        MessageManager.INSTANCE.changeResource(rus);
        content.setSessionAttribute(AttributeName.LOCALE, rus);
        commandResult.setResponsePage(ConfigurationManager2.CATALOG_PAGE_PATH.getProperty());
        commandResult.setResponseType(CommandResult.ResponseType.FORWARD);
        return commandResult;
    }
}
