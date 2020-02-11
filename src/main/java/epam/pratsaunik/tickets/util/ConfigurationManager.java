package epam.pratsaunik.tickets.util;

import java.util.ResourceBundle;

public class ConfigurationManager {

    private ConfigurationManager() { };

    private static ConfigurationManager instance;

    private ResourceBundle resourceBundle;

    private static final String BUNDLE_NAME = "config";
    public static final String USERS_PAGE_PATH = "users.page.path";
    public static final String LOGIN_PAGE_PATH = "login.page.path";
    public static final String REGISTRATION_PAGE_PATH = "registration.page.path";
    public static final String ERROR_PAGE_PATH = "error.page.path";
    public static final String CATALOG_PAGE_PATH = "catalog.page.path";
    public static final String NEW_EVENT_PAGE_PATH = "newevent.page.path";
    public static final String NEW_VENUE_PAGE_PATH = "newvenue.page.path";



    public static final String COMMAND_USERS="command.users";

    public static ConfigurationManager getInstance() {
        if (instance == null) {
            instance = new ConfigurationManager();
            instance.resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME);
        }
        return instance;
    }

    public String getProperty(String key) {
        return (String) resourceBundle.getObject(key);
    }
}
