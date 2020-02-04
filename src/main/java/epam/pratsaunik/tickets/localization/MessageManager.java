package epam.pratsaunik.tickets.localization;

import java.util.Locale;
import java.util.ResourceBundle;

public enum MessageManager {
    BY(ResourceBundle.getBundle("resource.message",new Locale("be","BY"))),
    EN(ResourceBundle.getBundle("resource.message",new Locale("en","US")));

    private ResourceBundle resourceBundle;
    MessageManager(ResourceBundle resourceBundle){
        this.resourceBundle=resourceBundle;
    }

    public String getMessage(String key){
        return resourceBundle.getString(key);
    }

}
