package epam.pratsaunik.tickets.command.impl;

import epam.pratsaunik.tickets.command.AbstractCommand;
import epam.pratsaunik.tickets.command.RequestContent;
import epam.pratsaunik.tickets.service.Service;
import epam.pratsaunik.tickets.util.ConfigurationManager;

public class NewVenueCommand extends AbstractCommand {
    public NewVenueCommand(Service service) {
        super(service);
    }

    @Override
    public String execute(RequestContent content) {
        return ConfigurationManager.getInstance().getProperty(ConfigurationManager.NEW_VENUE_PAGE_PATH);
    }
}
