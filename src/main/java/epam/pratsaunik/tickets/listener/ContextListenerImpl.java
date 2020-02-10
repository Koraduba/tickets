package epam.pratsaunik.tickets.listener;

import epam.pratsaunik.tickets.connection.ConnectionPoll;
import epam.pratsaunik.tickets.exception.ConnectionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextListenerImpl implements ServletContextListener {
    private final static Logger log = LogManager.getLogger();

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            ConnectionPoll.getInstance().destroyPoll();
            log.debug("CP destroyed");
        } catch (ConnectionException e) {
            log.debug("CP destruction failed"+e);
    }
    }
}
