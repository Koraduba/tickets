package epam.pratsaunik.tickets.command;


import epam.pratsaunik.tickets.service.Service;

public abstract class AbstractCommand {
    protected Service service;
    public AbstractCommand(Service service){
        this.service=service;
    }
    public abstract CommandResult execute(RequestContent content);
}
