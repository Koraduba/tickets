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
/**
 * Class{@code AddVenueCommand} is used to create and save new venue in data base
 * @version 1.0
 * @see AbstractCommand
 */
public class ChangeLocaleCommand extends AbstractCommand {
    public static final Locale rus = new Locale("ru", "RU");
    public ChangeLocaleCommand(Service service) {
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
