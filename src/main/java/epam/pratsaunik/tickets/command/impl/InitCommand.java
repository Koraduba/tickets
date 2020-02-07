package epam.pratsaunik.tickets.command.impl;

import epam.pratsaunik.tickets.command.AbstractCommand;
import epam.pratsaunik.tickets.command.RequestContent;
import epam.pratsaunik.tickets.entity.User;
import epam.pratsaunik.tickets.exception.ServiceLevelException;
import epam.pratsaunik.tickets.service.Service;
import epam.pratsaunik.tickets.service.impl.UserServiceImpl;
import epam.pratsaunik.tickets.util.ConfigurationManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class InitCommand extends AbstractCommand {
    private final static Logger log = LogManager.getLogger();

    public InitCommand(Service service) {
        super(service);
    }

    @Override
    public String execute(RequestContent content) {
        String page=null;
        try {
            ((UserServiceImpl) service).createAdmin();
            page=ConfigurationManager.getInstance().getProperty(ConfigurationManager.LOGIN_PAGE_PATH);

        } catch (ServiceLevelException e) {
            log.error("Exception in InitCommand" + e);
            page=ConfigurationManager.getInstance().getProperty(ConfigurationManager.ERROR_PAGE_PATH);
        }
        return page;
    }
}

