package epam.pratsaunik.tickets.command.impl;

import epam.pratsaunik.tickets.command.AbstractCommand;
import epam.pratsaunik.tickets.command.RequestContent;
import epam.pratsaunik.tickets.service.Service;

public class AddVenueCommand extends AbstractCommand {
    public AddVenueCommand(Service service) {
        super(service);
    }

    @Override
    public String execute(RequestContent content) {
        return null;
    }
}
