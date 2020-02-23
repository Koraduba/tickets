package epam.pratsaunik.tickets.command;

import epam.pratsaunik.tickets.entity.Role;
import epam.pratsaunik.tickets.servlet.AttributeName;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class RequestContent {
    private final static Logger log = LogManager.getLogger();
    private Map<String, String[]> requestParameters = new HashMap<>();
    private Map<String, Object> requestAttributes = new HashMap<>();
    private Map<String, Object> sessionAttributes = new HashMap<>();

    public void extractValues(HttpServletRequest request) {
        request.getParameterMap();
        for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
            requestParameters.put(entry.getKey(), entry.getValue());
        }
        Enumeration<String> enumeration = request.getSession().getAttributeNames();
        while (enumeration.hasMoreElements()) {
            String key = enumeration.nextElement();
            sessionAttributes.put(key, request.getSession().getAttribute(key));
        }
    }

    public void insertAttributes(HttpServletRequest request) {
        for (Map.Entry<String, Object> entry : requestAttributes.entrySet()) {
            request.setAttribute(entry.getKey(), entry.getValue());
        }
        HttpSession session = request.getSession(true);
        for (Map.Entry<String, Object> entry : sessionAttributes.entrySet()) {
            if (entry.getValue() != null) {
                session.setAttribute(entry.getKey(), entry.getValue());
            } else {
                if (entry.getKey() != null) {
                    session.removeAttribute(entry.getKey());
                }
            }
        }
    }

    public void setSessionAttribute(String name, Object value) {
        log.info(name);
        sessionAttributes.put(name, value);
    }

    public Object getSessionAttribute(String name) {
        return sessionAttributes.get(name);
    }

    public void setRequestAttribute(String name, Object value) {
        log.info(name);
        requestAttributes.put(name, value);
    }

    public String getRequestParameter(String name) {
        log.info("getRequestParameter" + name);
        String[] results = requestParameters.get(name);
        if (results != null) {
            return results[0];
        } else {
            return null;
        }
    }

}
