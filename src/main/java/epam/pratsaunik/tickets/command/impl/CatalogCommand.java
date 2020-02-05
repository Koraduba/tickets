package epam.pratsaunik.tickets.command.impl;

import epam.pratsaunik.tickets.command.AbstractCommand;
import epam.pratsaunik.tickets.command.RequestContent;
import epam.pratsaunik.tickets.entity.Role;
import epam.pratsaunik.tickets.service.Service;
import epam.pratsaunik.tickets.servlet.AttributeName;
import epam.pratsaunik.tickets.util.ConfigurationManager;

public class CatalogCommand extends AbstractCommand {
    public CatalogCommand(Service service) {
        super(service);
    }

    @Override
    public String execute(RequestContent content) {
        return ConfigurationManager.getInstance().getProperty(ConfigurationManager.CATALOG_PAGE_PATH);
    }
}
