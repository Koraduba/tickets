package epam.pratsaunik.tickets.command.impl;

import epam.pratsaunik.tickets.command.AbstractCommand;
import epam.pratsaunik.tickets.command.RequestContent;
import epam.pratsaunik.tickets.util.ConfigurationManager;
import epam.pratsaunik.tickets.service.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NewUserCommand extends AbstractCommand {

    private final static Logger log = LogManager.getLogger();

    public NewUserCommand(Service service) {
        super(service);
    }

    @Override
    public String execute(RequestContent content) {
        return ConfigurationManager.getInstance().getProperty(ConfigurationManager.REGISTRATION_PAGE_PATH);
    }
}
