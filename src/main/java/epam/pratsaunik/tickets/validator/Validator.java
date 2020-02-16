package epam.pratsaunik.tickets.validator;

import epam.pratsaunik.tickets.command.RequestContent;
import epam.pratsaunik.tickets.servlet.AttributeName;
import epam.pratsaunik.tickets.servlet.ParameterName;
import epam.pratsaunik.tickets.util.MessageType;
import epam.pratsaunik.tickets.util.MessageManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    private final static Logger log = LogManager.getLogger();
    final static String LOGIN_REGEX = "[A-Z][a-z]+";
    final static String EMAIL_REGEX = "[a-z]+@[a-z]+.[a-z]+";
    final static String NAME_REGEX = "[A-Z][a-z]+";
    final static String SURNAME_REGEX = "[A-Z][a-z]+";

    public static boolean validateLogin(String login) {

        Pattern pattern = Pattern.compile(LOGIN_REGEX);
        Matcher matcher = pattern.matcher(login);
        return matcher.matches();
    }

    public static boolean validateUser(RequestContent content) {
        boolean result = true;
        content.setRequestAttribute(AttributeName.USER_EMAIL, content.getRequestParameter(ParameterName.USER_EMAIL));
        content.setRequestAttribute(AttributeName.USER_SURNAME, content.getRequestParameter(ParameterName.USER_SURNAME));
        content.setRequestAttribute(AttributeName.USER_NAME, content.getRequestParameter(ParameterName.USER_NAME));
        Pattern pattern = Pattern.compile(NAME_REGEX);
        Matcher matcher = pattern.matcher(content.getRequestParameter(ParameterName.USER_NAME));
        if (!matcher.matches()) {
            content.setRequestAttribute(AttributeName.USER_NAME, "NOT_VALID");
            result = false;
        }
        pattern = Pattern.compile(SURNAME_REGEX);
        log.info(pattern.pattern());
        matcher = pattern.matcher(content.getRequestParameter(ParameterName.USER_SURNAME));
        if (!matcher.matches()) {
            content.setRequestAttribute(AttributeName.USER_SURNAME, "NOT_VALID");
            result = false;
        }
        pattern = Pattern.compile(EMAIL_REGEX);
        matcher = pattern.matcher(content.getRequestParameter(ParameterName.USER_EMAIL));
        if (!matcher.matches()) {
            content.setRequestAttribute(AttributeName.USER_EMAIL, "NOT_VALID");
            result = false;
        }
        if (!result){
            content.setRequestAttribute(AttributeName.ERROR_LOGIN_PASS_MESSAGE, MessageManager.INSTANCE.getProperty(MessageType.INPUT_ERROR));
        }
        return result;
    }
}
