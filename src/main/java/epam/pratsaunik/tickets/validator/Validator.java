package epam.pratsaunik.tickets.validator;

import epam.pratsaunik.tickets.command.RequestContent;
import epam.pratsaunik.tickets.service.Service;
import epam.pratsaunik.tickets.servlet.AttributeName;
import epam.pratsaunik.tickets.servlet.ParameterName;
import epam.pratsaunik.tickets.util.MessageType;
import epam.pratsaunik.tickets.util.MessageManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Validator of input information based on given regular expressions
 * @author Dzmitry Mikulich
 * @version 1.0
 */
public class Validator {
    private final static Logger log = LogManager.getLogger();
    private final static String LOGIN_REGEX = "^[a-zA-Z][a-zA-Z0-9-_\\.]{1,20}$";
    private final static String EMAIL_REGEX = "^[-\\w.]+@([A-z0-9][-A-z0-9]+\\.)+[A-z]{2,4}$";
    private final static String NAME_REGEX = "^[a-zA-Z][a-z]{1,20}$";
    private final static String SURNAME_REGEX = "^[a-zA-Z][a-z]{1,20}$";
    private final static String PASSWORD_REGEX = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s).*$";
    private final static String EVENT_NAME_REGEX = "^[a-zA-Z][a-z]{1,20}$";
    private final static String EVENT_DESCRIPTION_REGEX = "^\\w{1,120}$";
    private final static String EVENT_DATE_REGEX = "^(19|20)\\d\\d-((0[1-9]|1[012])-(0[1-9]|[12]\\d)|(0[13-9]|1[012])-30|(0[13578]|1[02])-31)$";
    private final static String EVENT_TIME_REGEX = "^([0-1]\\d|2[0-3])(:[0-5]\\d)$";
    private final static String TICKET_PRICE_REGEX = "^\\d{1,4}$";
    private final static String ORDER_LINE_QUANTITY_REGEX = "^\\d{1,3}$";
    private final static String VENUE_NAME_REGEX = "^[a-zA-Z][a-z]{1,20}$";
    private final static String VENUE_CAPACITY_REGEX = "^\\d{1,4}$";


    /**
     *
     * @param login login input
     * @return {@codetrue} if login corresponds to given regular expression
     */
    public static boolean validateLogin(String login) {

        Pattern pattern = Pattern.compile(LOGIN_REGEX);
        Matcher matcher = pattern.matcher(login);
        return matcher.matches();
    }

    /**
     *
     * @param content {@code RequestContent} instance to provide request parameters ans session attributes
     * @return {@code true} if all user data fields correspond to given regular expressions
     * @see RequestContent
     */
    public static boolean validateUser(RequestContent content) {
        boolean result = true;
        content.setRequestAttribute(AttributeName.USER_EMAIL, content.getRequestParameter(ParameterName.USER_EMAIL));
        content.setRequestAttribute(AttributeName.USER_SURNAME, content.getRequestParameter(ParameterName.USER_SURNAME));
        content.setRequestAttribute(AttributeName.USER_NAME, content.getRequestParameter(ParameterName.USER_NAME));
        content.setRequestAttribute(AttributeName.USER_LOGIN, content.getRequestParameter(ParameterName.USER_LOGIN));
        Pattern pattern = Pattern.compile(NAME_REGEX);
        Matcher matcher = pattern.matcher(content.getRequestParameter(ParameterName.USER_NAME));
        if (!matcher.matches()) {
            content.setRequestAttribute(AttributeName.ERROR_USER_NAME_MESSAGE, MessageManager.INSTANCE.getProperty(MessageType.INPUT_ERROR));
            result = false;
        }
        pattern = Pattern.compile(SURNAME_REGEX);
        matcher = pattern.matcher(content.getRequestParameter(ParameterName.USER_SURNAME));
        if (!matcher.matches()) {
            content.setRequestAttribute(AttributeName.ERROR_USER_SURNAME_MESSAGE, MessageManager.INSTANCE.getProperty(MessageType.INPUT_ERROR));
            result = false;
        }
        pattern = Pattern.compile(EMAIL_REGEX);
        matcher = pattern.matcher(content.getRequestParameter(ParameterName.USER_EMAIL));
        if (!matcher.matches()) {
            content.setRequestAttribute(AttributeName.ERROR_USER_EMAIL_MESSAGE, MessageManager.INSTANCE.getProperty(MessageType.INPUT_ERROR));
            result = false;
        }

        pattern = Pattern.compile(LOGIN_REGEX);
        matcher = pattern.matcher(content.getRequestParameter(ParameterName.USER_LOGIN));
        if (!matcher.matches()) {
            content.setRequestAttribute(AttributeName.ERROR_USER_LOGIN_MESSAGE, MessageManager.INSTANCE.getProperty(MessageType.INPUT_ERROR));
            result = false;
        }

        pattern = Pattern.compile(PASSWORD_REGEX);
        matcher = pattern.matcher(content.getRequestParameter(ParameterName.USER_PASSWORD));
        if (!matcher.matches()) {
            content.setRequestAttribute(AttributeName.ERROR_USER_PASSWORD_MESSAGE, MessageManager.INSTANCE.getProperty(MessageType.INPUT_ERROR));
            result = false;
        }

        return result;
    }
    /**
     *
     * @param content {@code RequestContent} instance to provide request parameters ans session attributes
     * @return {@code true} if all event data fields correspond to given regular expressions
     * @see RequestContent
     */
    public static boolean validateEvent(RequestContent content) {
        boolean result = true;
        Pattern pattern = Pattern.compile(EVENT_NAME_REGEX);
        Matcher matcher = pattern.matcher(content.getRequestParameter(ParameterName.EVENT_NAME));
        if (!matcher.matches()) {
            content.setRequestAttribute(AttributeName.ERROR_EVENT_NAME_MESSSAGE, MessageManager.INSTANCE.getProperty(MessageType.INPUT_ERROR));
            result = false;
        }
        pattern = Pattern.compile(EVENT_DATE_REGEX);
        matcher = pattern.matcher(content.getRequestParameter(ParameterName.EVENT_DATE));
        if (!matcher.matches()) {
            content.setRequestAttribute(AttributeName.ERROR_EVENT_DATE_MESSAGE, MessageManager.INSTANCE.getProperty(MessageType.INPUT_ERROR));
            result = false;
        }
        pattern = Pattern.compile(EVENT_TIME_REGEX);
        matcher = pattern.matcher(content.getRequestParameter(ParameterName.EVENT_TIME));
        if (!matcher.matches()) {
            content.setRequestAttribute(AttributeName.ERROR_EVENT_TIME_MESSAGE, MessageManager.INSTANCE.getProperty(MessageType.INPUT_ERROR));
            result = false;
        }
        pattern = Pattern.compile(EVENT_DESCRIPTION_REGEX);
        matcher = pattern.matcher(content.getRequestParameter(ParameterName.EVENT_DESCRIPTION));
        if (!matcher.matches()) {
            content.setRequestAttribute(AttributeName.ERROR_EVENT_DESCRIPTION_MESSAGE, MessageManager.INSTANCE.getProperty(MessageType.INPUT_ERROR));

            result = false;
        }
        pattern = Pattern.compile(TICKET_PRICE_REGEX);
        matcher = pattern.matcher(content.getRequestParameter(ParameterName.TICKET_PRICE_STANDARD));
        if (!matcher.matches()) {
            content.setRequestAttribute(AttributeName.ERROR_STANDARD_TICKET_PRICE_MESSAGE, MessageManager.INSTANCE.getProperty(MessageType.INPUT_ERROR));

            result = false;
        }
        pattern = Pattern.compile(TICKET_PRICE_REGEX);
        matcher = pattern.matcher(content.getRequestParameter(ParameterName.TICKET_PRICE_VIP));
        if (!matcher.matches()) {
            content.setRequestAttribute(AttributeName.ERROR_VIP_TICKET_PRICE_MESSAGE, MessageManager.INSTANCE.getProperty(MessageType.INPUT_ERROR));

            result = false;
        }
        return result;
    }

    /**
     *
     * @param content {@code RequestContent} instance to provide request parameters ans session attributes
     * @return {@code true} if quantity input correspond to given regular expression
     * @see RequestContent
     */
    public static boolean validateOrderLine(RequestContent content) {
        boolean result = true;
        Pattern pattern = Pattern.compile(ORDER_LINE_QUANTITY_REGEX);
        Matcher matcher = pattern.matcher(content.getRequestParameter(ParameterName.ORDER_LINE_QUANTITY));
        if (!matcher.matches()) {
            content.setRequestAttribute(AttributeName.ERROR_ORDER_LINE_QUANTITY_MESSAGE, MessageManager.INSTANCE.getProperty(MessageType.INPUT_ERROR));
            result = false;
        }

        return result;
    }

    /**
     *
     * @param content {@code RequestContent} instance to provide request parameters ans session attributes
     * @return {@code true} if all venue data fields correspond to given regular expressions
     * @see RequestContent
     */
    public static boolean validateVenue(RequestContent content) {
        boolean result = true;
        Pattern pattern = Pattern.compile(VENUE_NAME_REGEX);
        Matcher matcher = pattern.matcher(content.getRequestParameter(ParameterName.VENUE_NAME));
        if (!matcher.matches()) {
            content.setRequestAttribute(AttributeName.ERROR_VENUE_NAME_MESSAGE, MessageManager.INSTANCE.getProperty(MessageType.INPUT_ERROR));
            result = false;
        }
        pattern = Pattern.compile(VENUE_CAPACITY_REGEX);
        matcher = pattern.matcher(content.getRequestParameter(ParameterName.VENUE_CAPACITY));
        if (!matcher.matches()) {
            content.setRequestAttribute(AttributeName.ERROR_VENUE_CAPACITY_MESSAGE, MessageManager.INSTANCE.getProperty(MessageType.INPUT_ERROR));
            result = false;
        }

        return result;
    }




}
