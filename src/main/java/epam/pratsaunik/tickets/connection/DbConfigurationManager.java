package epam.pratsaunik.tickets.connection;

import java.util.ResourceBundle;

 class DbConfigurationManager {

    private DbConfigurationManager() { };

    private static DbConfigurationManager instance;

    private ResourceBundle resourceBundle;

    private static final String BUNDLE_NAME = "config";
     static final String DATABASE_DRIVER_NAME = "database.driver.name";
     static final String DATABASE_URL = "database.url";
     static final String DATABASE_USERNAME = "database.username";
    public static final String DATABASE_PWD = "database.password";

    public static final String USERS_PAGE_PATH = "users.page.path";

    public static final String COMMAND_USERS="command.users";

     static DbConfigurationManager getInstance() {
        if (instance == null) {
            instance = new DbConfigurationManager();
            instance.resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME);
        }
        return instance;
    }

     String getProperty(String key) {
        return (String) resourceBundle.getObject(key);
    }
}
