package epam.pratsaunik.tickets.validator;

import epam.pratsaunik.tickets.command.RequestContent;
import epam.pratsaunik.tickets.servlet.AttributeName;
import epam.pratsaunik.tickets.servlet.ParameterName;
import epam.pratsaunik.tickets.util.MessageType;
import epam.pratsaunik.tickets.util.MessageManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.smartcardio.ATR;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    private final static Logger log = LogManager.getLogger();
    private final static String LOGIN_REGEX = "[A-Z][a-z]+";
    private final static String EMAIL_REGEX = "[a-z]+@[a-z]+.[a-z]+";
    private final static String NAME_REGEX = "[A-Z][a-z]+";
    private final static String SURNAME_REGEX = "[A-Z][a-z]+";
    private final static String EVENT_NAME_REGEX = "\\w{1,120}";
    private final static String EVENT_DESCRIPTION_REGEX = "\\w{1,120}";
    private final static String EVENT_DATE_REGEX = ".{1,10}";
    private final static String EVENT_TIME_REGEX = ".{1,10}";
    private final static String TICKET_PRICE_REGEX = "\\d{1,5}";


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
        if (!result) {
            content.setRequestAttribute(AttributeName.ERROR_LOGIN_PASS_MESSAGE, MessageManager.INSTANCE.getProperty(MessageType.INPUT_ERROR));
        }
        return result;
    }

    public static boolean validateEvent(RequestContent content) {
        boolean result = true;
        content.setRequestAttribute(AttributeName.EVENT_NAME, content.getRequestParameter(ParameterName.EVENT_NAME));
        content.setRequestAttribute(AttributeName.EVENT_DATE, content.getRequestParameter(ParameterName.EVENT_DATE));
        content.setRequestAttribute(AttributeName.EVENT_TIME, content.getRequestParameter(ParameterName.EVENT_TIME));
        content.setRequestAttribute(AttributeName.EVENT_DESCRIPTION, content.getRequestParameter(ParameterName.EVENT_DESCRIPTION));
        content.setRequestAttribute(AttributeName.TICKET_PRICE_STANDARD, content.getRequestParameter(ParameterName.TICKET_PRICE_STANDARD));
        content.setRequestAttribute(AttributeName.TICKET_PRICE_VIP, content.getRequestParameter(ParameterName.TICKET_PRICE_VIP));
        Pattern pattern = Pattern.compile(EVENT_NAME_REGEX);
        Matcher matcher = pattern.matcher(content.getRequestParameter(ParameterName.EVENT_NAME));
        if (!matcher.matches()) {
            content.setRequestAttribute(AttributeName.EVENT_NAME, "NOT VALID");
            result = false;
        }
        pattern = Pattern.compile(EVENT_DATE_REGEX);
        matcher = pattern.matcher(content.getRequestParameter(ParameterName.EVENT_DATE));
        if (!matcher.matches()) {
            content.setRequestAttribute(AttributeName.EVENT_DATE, "NOT VALID");
            result = false;
        }
        pattern = Pattern.compile(EVENT_TIME_REGEX);
        matcher = pattern.matcher(content.getRequestParameter(ParameterName.EVENT_TIME));
        if (!matcher.matches()) {
            content.setRequestAttribute(AttributeName.EVENT_TIME, "NOT VALID");
            result = false;
        }
        pattern = Pattern.compile(EVENT_DESCRIPTION_REGEX);
        matcher = pattern.matcher(content.getRequestParameter(ParameterName.EVENT_DESCRIPTION));
        if (!matcher.matches()) {
            content.setRequestAttribute(AttributeName.EVENT_DESCRIPTION, "NOT VALID");
            result = false;
        }
        pattern = Pattern.compile(TICKET_PRICE_REGEX);
        matcher = pattern.matcher(content.getRequestParameter(ParameterName.TICKET_PRICE_STANDARD));
        if (!matcher.matches()) {
            content.setRequestAttribute(AttributeName.TICKET_PRICE_STANDARD, "NOT VALID");
            result = false;
        }
        pattern = Pattern.compile(TICKET_PRICE_REGEX);
        matcher = pattern.matcher(content.getRequestParameter(ParameterName.TICKET_PRICE_VIP));
        if (!matcher.matches()) {
            content.setRequestAttribute(AttributeName.TICKET_PRICE_VIP, "NOT VALID");
            result = false;
        }

        if (!result) {
            content.setRequestAttribute(AttributeName.ERROR_NEW_EVENT_MESSAGE, MessageManager.INSTANCE.getProperty(MessageType.INPUT_ERROR));
        }

        return result;
    }

}
