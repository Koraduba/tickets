package epam.pratsaunik.tickets.command;


import epam.pratsaunik.tickets.service.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class AbstractCommand {
    protected Service service;
    public AbstractCommand(Service service){
        this.service=service;
    }
    public abstract String execute(RequestContent content);
}
