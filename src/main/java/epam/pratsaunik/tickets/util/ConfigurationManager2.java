package epam.pratsaunik.tickets.util;

import java.util.ResourceBundle;

public enum ConfigurationManager2 {
    USERS_PAGE_PATH("users.page.path"),
    LOGIN_PAGE_PATH("login.page.path"),
    REGISTRATION_PAGE_PATH("registration.page.path"),
    ERROR_PAGE_PATH("error.page.path"),
    CATALOG_PAGE_PATH("catalog.page.path"),
    NEW_EVENT_PAGE_PATH("newevent.page.path"),
    NEW_VENUE_PAGE_PATH("newvenue.page.path"),
    HOME_PAGE_PATH("home.page.path"),
    EVENT_PAGE_PATH("event.page.path"),
    CART_PAGE_PATH("cart.page.path"),
    PROFILE_PAGE_PATH("profile.page.path"),
    ORDER_PAGE_PATH("order.page.path"),
    ORDERS_PAGE_PATH("orders.page.path"),
    UPLOAD_PAGE_PATH("upload.page.path"),
    UPLOAD_LAYOUT_PATH("uploadlayout.page.path"),
    STATISTIC_PAGE_PATH("statistic.page.path"),
    CHANGE_PASSWORD_PAGE_PATH("changepassword.page.path"),
    EDIT_EVENT_PAGE_PATH("editevent.page.path"),
    MY_EVENTS_PAGE_PATH("myevents.page.path");

    private String key;
    private ResourceBundle resourceBundle;
    private final static String BUNDLE_NAME = "config";

    ConfigurationManager2(String key) {
        this.key = key;
        resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME);
    }

    public String getProperty() {
        return (String) resourceBundle.getObject(key);
    }
}
