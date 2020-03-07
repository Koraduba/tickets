package epam.pratsaunik.tickets.validator;

import epam.pratsaunik.tickets.command.RequestContent;
import epam.pratsaunik.tickets.servlet.ParameterName;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ValidatorTest {
    private static RequestContent content;

    @BeforeClass
    public static void initValidatorTest(){
    content = Mockito.mock(RequestContent.class);
    }

    @AfterClass
    public static void clearValidatorTest(){
        content=null;
    }

    @Test
    public void validateLogin() {
        String loginValid = "Aa0_-.";
        assertTrue(Validator.validateLogin(loginValid));
    }

    @Test
    public void validateLogin2() {
        String loginNotValid = "0Aa";
        assertFalse(Validator.validateLogin(loginNotValid));
    }


    @Test
    public void validateUser() {
        String loginValid = "Aa-";
        String emailValid = "test@test.by";
        String nameValid = "Aa";
        String surnameValid = "aa";
        String passwordValid = "1aA";
        Mockito.when(content.getRequestParameter(ParameterName.USER_NAME)).thenReturn(nameValid);
        Mockito.when(content.getRequestParameter(ParameterName.USER_SURNAME)).thenReturn(surnameValid);
        Mockito.when(content.getRequestParameter(ParameterName.USER_LOGIN)).thenReturn(loginValid);
        Mockito.when(content.getRequestParameter(ParameterName.USER_PASSWORD)).thenReturn(passwordValid);
        Mockito.when(content.getRequestParameter(ParameterName.USER_EMAIL)).thenReturn(emailValid);
        assertTrue(Validator.validateUser(content));
    }

    @Test
    public void validateUser2() {
        String loginValid = "Aa-)";
        String emailValid = "test@test.by";
        String nameValid = "Aa";
        String surnameValid = "aa";
        String passwordValid = "1aA";
        Mockito.when(content.getRequestParameter(ParameterName.USER_NAME)).thenReturn(nameValid);
        Mockito.when(content.getRequestParameter(ParameterName.USER_SURNAME)).thenReturn(surnameValid);
        Mockito.when(content.getRequestParameter(ParameterName.USER_LOGIN)).thenReturn(loginValid);
        Mockito.when(content.getRequestParameter(ParameterName.USER_PASSWORD)).thenReturn(passwordValid);
        Mockito.when(content.getRequestParameter(ParameterName.USER_EMAIL)).thenReturn(emailValid);
        assertFalse(Validator.validateUser(content));
    }

    @Test
    public void validateEvent() {
        String name="Event";
        String description="Description";
        String date="1980-09-20";
        String time="23:00";
        String price="333";
        Mockito.when(content.getRequestParameter(ParameterName.EVENT_NAME)).thenReturn(name);
        Mockito.when(content.getRequestParameter(ParameterName.EVENT_DESCRIPTION)).thenReturn(description);
        Mockito.when(content.getRequestParameter(ParameterName.EVENT_DATE)).thenReturn(date);
        Mockito.when(content.getRequestParameter(ParameterName.EVENT_TIME)).thenReturn(time);
        Mockito.when(content.getRequestParameter(ParameterName.TICKET_PRICE_STANDARD)).thenReturn(price);
        Mockito.when(content.getRequestParameter(ParameterName.TICKET_PRICE_VIP)).thenReturn(price);
        assertTrue(Validator.validateEvent(content));
    }

    @Test
    public void validateEvent2() {
        String name="Event";
        String description="Description";
        String date="1980-09-20";
        String time="24:00";
        String price="333";
        Mockito.when(content.getRequestParameter(ParameterName.EVENT_NAME)).thenReturn(name);
        Mockito.when(content.getRequestParameter(ParameterName.EVENT_DESCRIPTION)).thenReturn(description);
        Mockito.when(content.getRequestParameter(ParameterName.EVENT_DATE)).thenReturn(date);
        Mockito.when(content.getRequestParameter(ParameterName.EVENT_TIME)).thenReturn(time);
        Mockito.when(content.getRequestParameter(ParameterName.TICKET_PRICE_STANDARD)).thenReturn(price);
        Mockito.when(content.getRequestParameter(ParameterName.TICKET_PRICE_VIP)).thenReturn(price);
        assertFalse(Validator.validateEvent(content));
    }

    @Test
    public void validateOrderLine() {
        String quantity="1";
        Mockito.when(content.getRequestParameter(ParameterName.ORDER_LINE_QUANTITY)).thenReturn(quantity);
        assertTrue(Validator.validateOrderLine(content));
    }

    @Test
    public void validateOrderLine2() {
        String quantity="200000";
        Mockito.when(content.getRequestParameter(ParameterName.ORDER_LINE_QUANTITY)).thenReturn(quantity);
        assertFalse(Validator.validateOrderLine(content));
    }

    @Test
    public void validateVenue() {
        String name="venue";
        String capacity="123";
        Mockito.when(content.getRequestParameter(ParameterName.VENUE_NAME)).thenReturn(name);
        Mockito.when(content.getRequestParameter(ParameterName.VENUE_CAPACITY)).thenReturn(capacity);
        assertTrue(Validator.validateVenue(content));
    }

    @Test
    public void validateVenue2() {
        String name="VEnue";
        String capacity="123";
        Mockito.when(content.getRequestParameter(ParameterName.VENUE_NAME)).thenReturn(name);
        Mockito.when(content.getRequestParameter(ParameterName.VENUE_CAPACITY)).thenReturn(capacity);
        assertFalse(Validator.validateVenue(content));
    }
}