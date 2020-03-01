package epam.pratsaunik.tickets.command.impl;

import epam.pratsaunik.tickets.command.AbstractCommand;
import epam.pratsaunik.tickets.command.CommandResult;
import epam.pratsaunik.tickets.command.RequestContent;
import epam.pratsaunik.tickets.service.Service;
import epam.pratsaunik.tickets.servlet.AttributeName;
import epam.pratsaunik.tickets.servlet.ParameterName;
import epam.pratsaunik.tickets.util.ConfigurationManager;
import epam.pratsaunik.tickets.util.ConfigurationManager2;
import epam.pratsaunik.tickets.util.InputKeeper;

public class NewVenueCommand extends AbstractCommand {
    public NewVenueCommand(Service service) {
        super(service);
    }

    @Override
    public CommandResult execute(RequestContent content) {
        CommandResult commandResult=new CommandResult();
        InputKeeper.getInstance().keepEvent(content);
        content.setSessionAttribute(AttributeName.MODE,content.getRequestParameter(ParameterName.MODE));
        commandResult.setResponsePage(ConfigurationManager2.NEW_VENUE_PAGE_PATH.getProperty());
        commandResult.setResponseType(CommandResult.ResponseType.FORWARD);
        return commandResult;
    }
}
