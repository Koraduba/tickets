package epam.pratsaunik.tickets.command.impl;

import epam.pratsaunik.tickets.command.AbstractCommand;
import epam.pratsaunik.tickets.command.CommandResult;
import epam.pratsaunik.tickets.command.CommandType;
import epam.pratsaunik.tickets.command.RequestContent;
import epam.pratsaunik.tickets.exception.CommandException;
import epam.pratsaunik.tickets.service.Service;
import epam.pratsaunik.tickets.util.ConfigurationManager2;
import epam.pratsaunik.tickets.util.LocaleName;
import epam.pratsaunik.tickets.util.MessageManager;

import java.util.Locale;

public class ChangeLocaleCommand extends AbstractCommand {
    public static final Locale rus = new Locale("ru", "RU");
    public ChangeLocaleCommand(Service service) {
        super(service);
    }

    @Override
    public CommandResult execute(RequestContent content) throws CommandException {
        CommandResult commandResult = new CommandResult();
        if (content.getSessionAttribute("locale")==LocaleName.LOCALE_RU) {
            content.setSessionAttribute("locale", LocaleName.LOCALE_EN);
            MessageManager.INSTANCE.changeResource(LocaleName.LOCALE_EN);
        }
        else{
            content.setSessionAttribute("locale",LocaleName.LOCALE_RU);
            MessageManager.INSTANCE.changeResource(LocaleName.LOCALE_RU);
        }
        commandResult.setResponsePage(ConfigurationManager2.HOME_PAGE_PATH.getProperty());
        commandResult.setResponseType(CommandResult.ResponseType.FORWARD);
        return commandResult;
    }
}
