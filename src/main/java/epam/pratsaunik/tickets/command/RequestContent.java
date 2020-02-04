package epam.pratsaunik.tickets.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class RequestContent {
    private final static Logger log = LogManager.getLogger();
    private Map<String, String[]> requestParameters = new HashMap<>();
    private Map<String, Object> requestAttributes = new HashMap<>();
    private Map<String, Object> sessionAttributes = new HashMap<>();

    public void extractValues(HttpServletRequest request) {
        request.getParameterMap();
        for (Map.Entry<String, String []> entry : request.getParameterMap().entrySet()) {
            requestParameters.put(entry.getKey(), entry.getValue());
        }
    }

    public void insertAttributes(HttpServletRequest request) {
        for (Map.Entry<String, Object> entry : requestAttributes.entrySet()) {
            log.info(entry);
            request.setAttribute(entry.getKey(), entry.getValue());
        }
        for (Map.Entry<String, Object> entry : sessionAttributes.entrySet()) {
            log.info(entry);
            request.setAttribute(entry.getKey(), entry.getValue());
            request.getSession(true).setAttribute(entry.getKey(),entry.getValue());
        }
    }

    public void setSessionAttribute(String name, Object value) {
        log.info(name);
        sessionAttributes.put(name, value);
    }

    public void setRequestAttribute(String name, Object value) {
        log.info(name);
        requestAttributes.put(name, value);
    }

    public String getRequestParameter(String name) {
        log.info("getRequestParameter"+name);
        return requestParameters.get(name)[0];
    }

}
