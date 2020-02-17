package epam.pratsaunik.tickets.command.impl;

import epam.pratsaunik.tickets.command.AbstractCommand;
import epam.pratsaunik.tickets.command.RequestContent;
import epam.pratsaunik.tickets.service.Service;
import epam.pratsaunik.tickets.util.ConfigurationManager;

public class EventCommand extends AbstractCommand {
    public EventCommand(Service service) {
        super(service);
    }

    @Override
    public String execute(RequestContent content) {
        return ConfigurationManager.getInstance().getProperty(ConfigurationManager.EVENT_PAGE_PATH);
    }
}
