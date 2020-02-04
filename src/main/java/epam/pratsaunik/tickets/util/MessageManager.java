package epam.pratsaunik.tickets.util;

import java.util.ResourceBundle;

public class MessageManager {

    private static final String BUNDLE_NAME = "messages";
    public static final String INPUT_ERROR = "message.inputerror";
    public static final String NO_SUCH_USER = "message.nosuchuser";

    private final static ResourceBundle resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME);
    private MessageManager() { }
    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}
