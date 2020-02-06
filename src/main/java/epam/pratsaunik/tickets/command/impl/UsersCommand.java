package epam.pratsaunik.tickets.command.impl;


import epam.pratsaunik.tickets.command.AbstractCommand;
import epam.pratsaunik.tickets.command.RequestContent;
import epam.pratsaunik.tickets.util.ConfigurationManager;
import epam.pratsaunik.tickets.entity.User;
import epam.pratsaunik.tickets.exception.ServiceLevelException;
import epam.pratsaunik.tickets.service.Service;
import epam.pratsaunik.tickets.service.impl.UserServiceImpl;
import epam.pratsaunik.tickets.servlet.AttributeName;

import javax.servlet.ServletContext;
import java.util.List;

public class UsersCommand extends AbstractCommand {

    public UsersCommand (Service service){
        super(service);
    }
    @Override
    public String execute(RequestContent content) {

        List<User> list = null;
        try {
            list = ((UserServiceImpl)service).findAllUsers();
        } catch (ServiceLevelException e) {
            e.printStackTrace();
        }
        content.setRequestAttribute(AttributeName.USERS, list);
        return ConfigurationManager.getInstance().getProperty(ConfigurationManager.USERS_PAGE_PATH);
    }
}
