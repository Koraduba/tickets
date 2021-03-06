package epam.pratsaunik.tickets.command;

import epam.pratsaunik.tickets.command.impl.*;
import epam.pratsaunik.tickets.service.impl.EventServiceImpl;
import epam.pratsaunik.tickets.service.impl.OrderServiceImpl;
import epam.pratsaunik.tickets.service.impl.UserServiceImpl;

public enum CommandType {
    LOGIN(new LoginCommand(new UserServiceImpl())),
    LOGIN_PAGE(new LoginPageCommand(new UserServiceImpl())),
    USERS(new UsersCommand(new UserServiceImpl())),
    REGISTER(new RegisterCommand(new UserServiceImpl())),
    NEW_USER_PAGE(new NewUserPageCommand(new UserServiceImpl())),
    INIT(new InitCommand(new UserServiceImpl())),
    NEW_EVENT_PAGE(new NewEventPageCommand(new EventServiceImpl())),
    NEW_VENUE_PAGE(new NewVenuePageCommand(new EventServiceImpl())),
    NEW_EVENT(new NewEventCommand(new EventServiceImpl())),
    ADD_VENUE(new AddVenueCommand(new EventServiceImpl())),
    CATALOG(new CatalogCommand(new EventServiceImpl())),
    EVENT(new EventCommand(new EventServiceImpl())),
    UPLOAD_PAGE(new UploadPageCommand(new EventServiceImpl())),
    ORDER_LINE(new OrderLineCommand(new EventServiceImpl())),
    CART(new CartCommand(new OrderServiceImpl())),
    ORDER(new OrderCommand(new OrderServiceImpl())),
    HOME(new HomeCommand(new OrderServiceImpl())),
    ORDERS(new OrdersCommand(new OrderServiceImpl())),
    PROFILE(new ProfileCommand(new UserServiceImpl())),
    STATISTIC(new StatisticCommand(new OrderServiceImpl())),
    ORDERS_BELOW_THRESHOLD(new OrdersBelowThresholdCommand(new OrderServiceImpl())),
    ORDERS_ABOVE_THRESHOLD(new OrdersAboveThresholdCommand(new OrderServiceImpl())),
    GUEST(new GuestCommand(new UserServiceImpl())),
    LOGOUT(new LogoutCommand(new UserServiceImpl())),
    NEW_PASSWORD_PAGE(new NewPasswordPageCommand(new UserServiceImpl())),
    NEW_PASSWORD(new NewPasswordCommand(new UserServiceImpl())),
    EDIT_EVENT_PAGE(new EditEventPageCommand(new EventServiceImpl())),
    MY_EVENTS(new MyEventsCommand(new EventServiceImpl())),
    CHANGE_LOCALE(new ChangeLocaleCommand(new UserServiceImpl())),
    EDIT_EVENT(new EditEventCommand(new EventServiceImpl())),
    CLEAR_CART(new ClearCartCommand(new OrderServiceImpl())),
    DELETE_USER(new DeleteUserCommand(new UserServiceImpl())),
    ORDER_INFO(new OrderInfoCommand(new OrderServiceImpl()));

    private AbstractCommand command;

    CommandType(AbstractCommand command) {
        this.command = command;
    }

    public AbstractCommand getCommand() {
        return command;
    }

}
