package epam.pratsaunik.tickets.command.impl;

import epam.pratsaunik.tickets.command.AbstractCommand;
import epam.pratsaunik.tickets.command.CommandResult;
import epam.pratsaunik.tickets.command.RequestContent;
import epam.pratsaunik.tickets.entity.Event;
import epam.pratsaunik.tickets.entity.Venue;
import epam.pratsaunik.tickets.exception.CommandException;
import epam.pratsaunik.tickets.exception.ServiceLevelException;
import epam.pratsaunik.tickets.service.Service;
import epam.pratsaunik.tickets.service.impl.EventServiceImpl;
import epam.pratsaunik.tickets.util.ConfigurationManager2;
import epam.pratsaunik.tickets.util.InputKeeper;
import epam.pratsaunik.tickets.util.MessageManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UploadCommand extends AbstractCommand {
    private final static Logger log = LogManager.getLogger();
    public UploadCommand(Service service) {
        super(service);
    }
    @Override
    public CommandResult execute(RequestContent content) throws CommandException {
        CommandResult commandResult=new CommandResult();
        log.debug("UploadCommand launched");
        InputKeeper keeper = new InputKeeper();
        keeper.keepEvent(content);
        commandResult.setResponsePage(ConfigurationManager2.UPLOAD_PAGE_PATH.getProperty());
        commandResult.setResponseType(CommandResult.ResponseType.FORWARD);
        return commandResult;
    }
}
