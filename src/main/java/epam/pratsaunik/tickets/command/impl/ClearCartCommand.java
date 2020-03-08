package epam.pratsaunik.tickets.command.impl;

import epam.pratsaunik.tickets.command.AbstractCommand;
import epam.pratsaunik.tickets.command.CommandResult;
import epam.pratsaunik.tickets.command.RequestContent;
import epam.pratsaunik.tickets.exception.CommandException;
import epam.pratsaunik.tickets.service.Service;
import epam.pratsaunik.tickets.servlet.AttributeName;
import epam.pratsaunik.tickets.util.ConfigurationManager2;

/**
 * Class{@code ClearCartCommand}is used to remove items from Shopping Cart
 */
public class ClearCartCommand extends AbstractCommand {
    public ClearCartCommand(Service service) {
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
    public CommandResult execute(RequestContent content)  {
        CommandResult commandResult = new CommandResult();
        content.setSessionAttribute(AttributeName.SHOPPING_CART,null);
        commandResult.setResponsePage(ConfigurationManager2.CART_PAGE_PATH.getProperty());
        commandResult.setResponseType(CommandResult.ResponseType.FORWARD);
        return commandResult;

    }
}
