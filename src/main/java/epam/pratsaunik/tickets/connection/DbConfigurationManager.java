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
    static final String DATABASE_PWD = "database.password";
    static final String DATABASE_POOLSIZE = "database.poolsize";
    static final String DATABASE_USE_UNICODE = "database.useUnicode";
    static final String DATABASE_ENCODING = "database.encoding";



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
