package epam.pratsaunik.tickets.command;

public class CommandFactory {

    public final static CommandFactory instance = new CommandFactory();
    private CommandFactory(){}

    /**
     *
     * @param commandName command parameter from request
     * @return command type
     */
    public AbstractCommand getCommand(String commandName) {
        CommandType type = CommandType.valueOf(commandName.toUpperCase()); // FIXME: 1/29/2020
        AbstractCommand command =type.getCommand();
        return command;
    }
}
