package epam.pratsaunik.tickets.command.impl;


import epam.pratsaunik.tickets.command.AbstractCommand;
import epam.pratsaunik.tickets.command.RequestContent;
import epam.pratsaunik.tickets.util.ConfigurationManager;
import epam.pratsaunik.tickets.entity.User;
import epam.pratsaunik.tickets.exception.ServiceLevelException;
import epam.pratsaunik.tickets.service.Service;
import epam.pratsaunik.tickets.service.impl.UserServiceImpl;
import epam.pratsaunik.tickets.servlet.AttributeName;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import java.util.List;

public class UsersCommand extends AbstractCommand {
    private final static int RECORDS_PER_PAGE=3;
    private final static Logger log = LogManager.getLogger();

    public UsersCommand (Service service){
        super(service);
    }
    @Override
    public String execute(RequestContent content) {

        List<User> list = null;
        try {
            int nOfRecords = ((UserServiceImpl)service).getNumberOfRecords();
            int nOfPages = nOfRecords / RECORDS_PER_PAGE;
            if (nOfPages % RECORDS_PER_PAGE > 0) {
                nOfPages++;
            }
            int currentPage=Integer.parseInt(content.getRequestParameter("currentPage"));
            list = ((UserServiceImpl)service).findRange(currentPage, RECORDS_PER_PAGE);
            log.debug("Current page:"+currentPage+" nOfPages:"+nOfPages+" Size of list"+list.size());
            content.setSessionAttribute("nOfPages",nOfPages);
            content.setSessionAttribute("currentPage",currentPage);
            content.setSessionAttribute(AttributeName.USERS, list);
        } catch (ServiceLevelException e) {
            e.printStackTrace();
        }
        return ConfigurationManager.getInstance().getProperty(ConfigurationManager.USERS_PAGE_PATH);
    }
}
