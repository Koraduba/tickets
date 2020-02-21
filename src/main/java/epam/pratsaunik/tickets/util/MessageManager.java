package epam.pratsaunik.tickets.util;

import java.util.Locale;
import java.util.ResourceBundle;

public enum  MessageManager {
    INSTANCE;
    private final String BUNDLE_NAME = "messages";
    private ResourceBundle resourceBundle;
    private MessageManager() {
        resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME, Locale.getDefault());
    }
    public void changeResource(Locale locale) {
        resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME, locale);
    }
    public String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}
