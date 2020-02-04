package epam.pratsaunik.tickets.command;

import epam.pratsaunik.tickets.command.impl.LoginCommand;
import epam.pratsaunik.tickets.command.impl.NewUserCommand;
import epam.pratsaunik.tickets.command.impl.RegisterCommand;
import epam.pratsaunik.tickets.command.impl.UsersCommand;
import epam.pratsaunik.tickets.service.impl.UserServiceImpl;

public enum CommandType {
    LOGIN(new LoginCommand(new UserServiceImpl())),
    USERS(new UsersCommand(new UserServiceImpl())),
    REGISTER(new RegisterCommand(new UserServiceImpl())),
    NEW_USER(new NewUserCommand(new UserServiceImpl()));

    private AbstractCommand command;

    CommandType(AbstractCommand command) {
        this.command = command;
    }

    public AbstractCommand getCommand() {
        return command;
    }
}
