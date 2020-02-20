package epam.pratsaunik.tickets.command.impl;

import epam.pratsaunik.tickets.command.AbstractCommand;
import epam.pratsaunik.tickets.command.CommandResult;
import epam.pratsaunik.tickets.command.RequestContent;
import epam.pratsaunik.tickets.util.ConfigurationManager;
import epam.pratsaunik.tickets.service.Service;
import epam.pratsaunik.tickets.util.ConfigurationManager2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NewUserCommand extends AbstractCommand {

    private final static Logger log = LogManager.getLogger();

    public NewUserCommand(Service service) {
        super(service);
    }

    @Override
    public CommandResult execute(RequestContent content) {
        CommandResult commandResult=new CommandResult();
        commandResult.setResponsePage(ConfigurationManager2.REGISTRATION_PAGE_PATH.getProperty());
        commandResult.setResponseType(CommandResult.ResponseType.FORWARD);
        return commandResult;
    }
}
