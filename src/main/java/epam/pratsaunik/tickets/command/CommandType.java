package epam.pratsaunik.tickets.command;

import epam.pratsaunik.tickets.command.impl.*;
import epam.pratsaunik.tickets.service.impl.EventServiceImpl;
import epam.pratsaunik.tickets.service.impl.UserServiceImpl;

public enum CommandType {
    LOGIN(new LoginCommand(new UserServiceImpl())),
    USERS(new UsersCommand(new UserServiceImpl())),
    REGISTER(new RegisterCommand(new UserServiceImpl())),
    NEW_USER(new NewUserCommand(new UserServiceImpl())),
    INIT(new InitCommand(new UserServiceImpl())),
    NEW_EVENT(new NewEventCommand(new EventServiceImpl())),
    NEW_VENUE(new NewVenueCommand(new EventServiceImpl())),
    ADD_EVENT(new AddEventCommand(new EventServiceImpl())),
    ADD_VENUE(new AddVenueCommand(new EventServiceImpl()));

    private AbstractCommand command;

    CommandType(AbstractCommand command) {
        this.command = command;
    }

    public AbstractCommand getCommand() {
        return command;
    }

}
